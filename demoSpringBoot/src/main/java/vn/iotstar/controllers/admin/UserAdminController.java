// src/main/java/vn/iotstar/controllers/admin/UserAdminController.java
package vn.iotstar.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import vn.iotstar.entity.User;
import vn.iotstar.model.UserModel;
import vn.iotstar.services.UserService;

import java.nio.file.*;
import java.util.UUID;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService service;

    @Value("${app.upload.dir}")
    private String uploadDir; // thư mục lưu ảnh (đã map /images/** trong WebConfig)

    // LIST + SEARCH
    @GetMapping({""})
    public String list(ModelMap model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(required = false) String q) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        model.addAttribute("page", service.search(q, pageable));
        model.addAttribute("q", q);
        return "admin/users/list";
    }

    //SEARCH RIENG
    @GetMapping("/search")
    public String searchUser(ModelMap model,
					   @RequestParam(defaultValue = "0") int page,
					   @RequestParam(defaultValue = "10") int size,
					   @RequestParam(required = false) String q) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
		model.addAttribute("page", service.search(q, pageable));
		model.addAttribute("q", q);
		return "admin/users/search";
	}
    // ADD
    @GetMapping("/add")
    public String add(ModelMap model) {
        UserModel um = new UserModel();
        um.setIsEdit(false);
        model.addAttribute("user", um);
        return "admin/users/addOrEdit";
    }

    // EDIT
    @GetMapping("/edit/{username}")
    public String edit(@PathVariable String username, ModelMap model, RedirectAttributes ra) {
        var opt = service.findByUsername(username);
        if (opt.isEmpty()) {
            ra.addFlashAttribute("message", "Người dùng không tồn tại.");
            return "redirect:/admin/users";
        }
        UserModel um = new UserModel();
        BeanUtils.copyProperties(opt.get(), um);
        um.setIsEdit(true);
        um.setPassword(""); // không hiển thị mật khẩu cũ
        model.addAttribute("user", um);
        return "admin/users/addOrEdit";
    }

    // SAVE/UPDATE
    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(@ModelAttribute("user") UserModel form,
                                     RedirectAttributes ra) throws Exception {

        User entity;
        if (Boolean.TRUE.equals(form.getIsEdit())) {
            // lấy bản ghi cũ
            entity = service.findByUsername(form.getUsername()).orElse(new User());
            String oldPassword = entity.getPassword();

            // copy các field (trừ password & images vì xử lý riêng)
            BeanUtils.copyProperties(form, entity, "password", "images");

            // password: nếu form để trống -> giữ cũ; ngược lại -> dùng giá trị mới (có thể hash sau)
            if (form.getPassword() == null || form.getPassword().isBlank()) {
                entity.setPassword(oldPassword);
            } else {
                entity.setPassword(form.getPassword()); // TODO: hash nếu cần
            }
        } else {
            // tạo mới
            entity = new User();
            BeanUtils.copyProperties(form, entity, "images");
            if (entity.getPassword() == null || entity.getPassword().isBlank()) {
                ra.addFlashAttribute("message", "Vui lòng nhập mật khẩu khi tạo tài khoản mới.");
                return new ModelAndView("redirect:/admin/users/add");
            }
        }

        // ẢNH: upload hoặc đặt noimages.png nếu rỗng
        MultipartFile file = form.getImageFile();
        if (file != null && !file.isEmpty()) {
            Path root = Paths.get(uploadDir);
            if (Files.notExists(root)) Files.createDirectories(root);

            String ext = "";
            var original = file.getOriginalFilename();
            if (original != null && original.lastIndexOf('.') >= 0) {
                ext = original.substring(original.lastIndexOf('.'));
            }
            String filename = "avatar_" + UUID.randomUUID().toString().replace("-", "") + ext;

            Files.copy(file.getInputStream(), root.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            entity.setImages(filename);
        } else {
            // không upload mới: nếu form có images cũ thì giữ, ngược lại set ảnh mặc định
            if (form.getImages() != null && !form.getImages().isBlank()) {
                entity.setImages(form.getImages());
            } else {
                entity.setImages("noimages.png");
            }
        }

        service.save(entity);
        ra.addFlashAttribute("message", Boolean.TRUE.equals(form.getIsEdit())
                ? "Cập nhật người dùng thành công."
                : "Tạo mới người dùng thành công.");
        return new ModelAndView("redirect:/admin/users");
    }

    // DELETE
    @GetMapping("/delete/{username}")
    public ModelAndView delete(@PathVariable String username, RedirectAttributes ra) {
        service.deleteByUsername(username);
        ra.addFlashAttribute("message", "Đã xóa người dùng.");
        return new ModelAndView("redirect:/admin/users");
    }
}
