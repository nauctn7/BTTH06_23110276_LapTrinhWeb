// src/main/java/vn/iotstar/services/UserService.java
package vn.iotstar.services;

import org.springframework.data.domain.*;
import vn.iotstar.entity.User;

import java.util.Optional;

public interface UserService {
    Page<User> search(String keyword, Pageable pageable);
    Optional<User> findByUsername(String username);
    User save(User u);
    void deleteByUsername(String username);
}
