// src/main/java/vn/iotstar/model/UserModel.java
package vn.iotstar.model;

import org.springframework.web.multipart.MultipartFile;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private String username;
    private String password;      // cho phép bỏ trống khi edit => giữ mật khẩu cũ
    private String fullname;
    private String email;
    private String phone;

    private String images;        // tên file đang lưu trong DB (ẩn trên form)
    private MultipartFile imageFile; // file upload mới

    private boolean active = true;
    private Boolean isEdit = false;
}
