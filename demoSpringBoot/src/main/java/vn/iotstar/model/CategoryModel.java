package vn.iotstar.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel {

    private Long categoryId;

    private String categorycode;

    @NotEmpty(message = "Tên danh mục không được để trống")
    @Length(min = 5, message = "Tên danh mục phải có ít nhất 5 ký tự")
    private String categoryname;

    // Lưu tên file ảnh trong DB
    private String images;

    // Dùng để nhận file upload từ form
    private MultipartFile imageFile;

    private boolean status;

    // Cờ để biết đây là thêm mới hay chỉnh sửa
    private Boolean isEdit = false;
}
