// src/main/java/vn/iotstar/entity/User.java
package vn.iotstar.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "username", length = 100)
    private String username;     // PK

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "fullname", columnDefinition = "nvarchar(255)")
    private String fullname;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "images")
    private String images;       // tên file ảnh đại diện

    @Column(name = "active")
    private boolean active = true;
}
