package vn.iotstar.controllers.admin;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import vn.iotstar.entity.Category;
import vn.iotstar.model.CategoryModel;
import vn.iotstar.services.CategoryService;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  // ⚠️ Inject uploadDir ở field (ổn định hơn là ở tham số method)
  @Value("${app.upload.dir}")
  private String uploadDir;

  @GetMapping({""})
  public String list(ModelMap model,
					 @RequestParam(defaultValue = "0") int page,
					 @RequestParam(defaultValue = "5") int size) {
	  	Pageable pageable = PageRequest.of(page, size, Sort.by("categoryId").descending());
	  	model.addAttribute("page", categoryService.findAll(pageable));
    List<Category> list = categoryService.findAll();
    model.addAttribute("categories", list);
    return "admin/categories/list";
  }

  @GetMapping("/search")
  public String searchPaged(ModelMap model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size,
                            @RequestParam(required = false) String name) {
      Pageable pageable = PageRequest.of(page, size, Sort.by("categoryId").descending());
      Page<Category> result = categoryService.search(name, pageable);

      model.addAttribute("page", result);   // chứa danh sách + info phân trang
      model.addAttribute("name", name);     // giữ lại từ khóa tìm kiếm
      return "admin/categories/search";     // => search.jsp
  }


  @GetMapping("/add")
  public String add(ModelMap model) {
    CategoryModel cate = new CategoryModel();
    cate.setIsEdit(false);
    model.addAttribute("category", cate);
    return "admin/categories/addOrEdit";
  }

  @PostMapping("/saveOrUpdate")
  public ModelAndView saveOrUpdate(ModelMap model,
                                   @Valid @ModelAttribute("category") CategoryModel cate,
                                   BindingResult result,
                                   RedirectAttributes ra) throws IOException {
    if (result.hasErrors()) {
      return new ModelAndView("admin/categories/addOrEdit", model);
    }

    // Nếu có ID -> lấy bản ghi cũ để giữ dữ liệu không có trong form (nếu cần)
    Category entity = (cate.getCategoryId() != null)
        ? categoryService.findById(cate.getCategoryId()).orElse(new Category())
        : new Category();

    // copy trừ images (sẽ set sau)
    BeanUtils.copyProperties(cate, entity, "images");

    // Xử lý file
    MultipartFile file = cate.getImageFile();
    if (file != null && !file.isEmpty()) {
      // đảm bảo thư mục tồn tại
      Path root = Paths.get(uploadDir);
      if (Files.notExists(root)) Files.createDirectories(root);

      // tạo tên file duy nhất + giữ đuôi
      String original = file.getOriginalFilename();
      String ext = "";
      if (original != null) {
        int dot = original.lastIndexOf('.');
        if (dot >= 0) ext = original.substring(dot);
      }
      String filename = UUID.randomUUID().toString().replace("-", "") + ext;

      // lưu file
      Files.copy(file.getInputStream(), root.resolve(filename), StandardCopyOption.REPLACE_EXISTING);

      // gán tên file vào entity (DB)
      entity.setImages(filename);
    } else {
    	// không upload mới: nếu form có images cũ thì giữ, ngược lại set ảnh mặc định
        if (cate.getImages() != null && !cate.getImages().isBlank()) {
            entity.setImages(cate.getImages());
        } else {
            entity.setImages("noimage.png");
        }
      }
    

    categoryService.save(entity);

    ra.addFlashAttribute("message",
        Boolean.TRUE.equals(cate.getIsEdit()) ? "Cập nhật thành công." : "Thêm mới thành công.");
    return new ModelAndView("redirect:/admin/categories");
  }

  @GetMapping("/edit/{categoryId}")
  public ModelAndView edit(ModelMap model, @PathVariable Long categoryId) {
    Optional<Category> opt = categoryService.findById(categoryId);
    if (opt.isPresent()) {
      CategoryModel cate = new CategoryModel();
      BeanUtils.copyProperties(opt.get(), cate);
      cate.setIsEdit(true);
      model.addAttribute("category", cate);
      return new ModelAndView("admin/categories/addOrEdit", model);
    }
    model.addAttribute("message", "Category không tồn tại!");
    return new ModelAndView("forward:/admin/categories", model);
  }

  @GetMapping("/delete/{categoryId}")
  public ModelAndView delete(@PathVariable Long categoryId, RedirectAttributes ra) {
    categoryService.deleteById(categoryId);
    ra.addFlashAttribute("message", "Đã xóa Category!");
    return new ModelAndView("redirect:/admin/categories");
  }
}
