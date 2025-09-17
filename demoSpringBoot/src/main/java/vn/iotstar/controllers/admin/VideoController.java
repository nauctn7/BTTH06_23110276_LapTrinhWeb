package vn.iotstar.controllers.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.iotstar.entity.Video;
import vn.iotstar.model.VideoModel;
import vn.iotstar.services.CategoryService;
import vn.iotstar.services.VideoService;

import java.io.IOException;


@Controller
@RequestMapping("/admin/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    private final CategoryService categoryService;

    @Value("${app.upload.dir}") // dùng chung thư mục uploads/
    private String uploadDir;

    // List + search + paginate
    @GetMapping({""})
    public String search(ModelMap model,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "5") int size,
                         @RequestParam(required = false) String title) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        Page<Video> result = videoService.search(title, pageable);

        model.addAttribute("page", result);
        model.addAttribute("title", title);
        return "admin/videos/list";
    }
    // SEARCH RIENG
    @GetMapping("/search")
    public String searchVideo(ModelMap model,
					   @RequestParam(defaultValue = "0") int page,
					   @RequestParam(defaultValue = "5") int size,
					   @RequestParam(required = false) String title) {
    			Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
    			Page<Video> result = videoService.search(title, pageable);
    			model.addAttribute("page", result);
    			model.addAttribute("title", title);
    			return "admin/videos/search";
    			
    }

    @GetMapping("/add")
    public String add(ModelMap model) {
        VideoModel vm = new VideoModel();
        vm.setIsEdit(false);
        model.addAttribute("video", vm);
        model.addAttribute("categories", categoryService.findActive()); // chỉ active
        return "admin/videos/addOrEdit";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(ModelMap model, @PathVariable("id") String id) {
        var opt = videoService.findById(id);
        if (opt.isEmpty()) {
            model.addAttribute("message", "Video không tồn tại!");
            return new ModelAndView("redirect:/admin/videos/search");
        }
        VideoModel vm = new VideoModel();
        org.springframework.beans.BeanUtils.copyProperties(opt.get(), vm);
        vm.setCategoryId(opt.get().getCategory() != null ? opt.get().getCategory().getCategoryId() : null);
        vm.setIsEdit(true);

        model.addAttribute("video", vm);
        model.addAttribute("categories", categoryService.findActive()); // chỉ active
        return new ModelAndView("admin/videos/addOrEdit", model);
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model,
                                     @Valid @ModelAttribute("video") VideoModel form,
                                     BindingResult result,
                                     RedirectAttributes ra) throws IOException {
        // nếu validate fail, nhớ nạp lại categories để dropdown có dữ liệu
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return new ModelAndView("admin/videos/addOrEdit", model);
        }

        boolean isEdit = form.getIsEdit() != null && form.getIsEdit();

        Video entity;
        if (isEdit) {
            // EDIT: phải có videoId hợp lệ
            entity = videoService.findById(form.getVideoId()).orElse(new Video());
        } else {
            // ADD: nếu videoId trống -> tự sinh
            entity = new Video();
            if (form.getVideoId() == null || form.getVideoId().isBlank()) {
                String gen = "VID" + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                form.setVideoId(gen);
            }
            entity.setVideoId(form.getVideoId());
        }

        // copy trừ poster + category (xử lý riêng)
        org.springframework.beans.BeanUtils.copyProperties(form, entity, "poster", "category", "videoId");

        // gán Category (cho phép null nếu không chọn)
        if (form.getCategoryId() != null) {
            entity.setCategory(categoryService.findById(form.getCategoryId()).orElse(null));
        } else {
            entity.setCategory(null);
        }

        // xử lý upload poster
        org.springframework.web.multipart.MultipartFile posterFile = form.getPosterFile();
        if (posterFile != null && !posterFile.isEmpty()) {
            java.nio.file.Path root = java.nio.file.Paths.get(uploadDir);
            if (java.nio.file.Files.notExists(root)) java.nio.file.Files.createDirectories(root);
            String original = posterFile.getOriginalFilename();
            String ext = "";
            if (original != null) {
                int dot = original.lastIndexOf('.');
                if (dot >= 0) ext = original.substring(dot);
            }
            String filename = "poster_" + java.util.UUID.randomUUID().toString().replace("-", "") + ext;
            java.nio.file.Files.copy(posterFile.getInputStream(), root.resolve(filename),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            entity.setPoster(filename);
        } else if (form.getPoster() != null && !form.getPoster().isBlank()) {
        	entity.setPoster(form.getPoster());  // giữ lại ảnh cũ
        } else {
            entity.setPoster("noimage.png");  
        }

        videoService.save(entity);
        ra.addFlashAttribute("message", isEdit ? "Cập nhật video thành công." : "Thêm video thành công.");
        return new ModelAndView("redirect:/admin/videos/search");
    }


    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") String id, RedirectAttributes ra) {
        videoService.deleteById(id);
        ra.addFlashAttribute("message", "Đã xóa video.");
        return new ModelAndView("redirect:/admin/videos/search");
    }
}
