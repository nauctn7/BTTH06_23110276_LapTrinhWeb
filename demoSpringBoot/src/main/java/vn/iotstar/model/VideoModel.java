package vn.iotstar.model;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoModel {
    private String videoId;
    private boolean active;

    @NotEmpty(message = "Mô tả không được để trống")
    private String description;

    private String poster;                 // tên file lưu trong DB

    // Đúng tên field mà controller dùng
    private MultipartFile posterFile;      // <-- ĐỔI THÀNH NÀY, bỏ imageFile

    @NotEmpty(message = "Tiêu đề không được để trống")
    private String title;

    private int views;
    private Long categoryId;
    private Boolean isEdit = false;
}
