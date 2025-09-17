// src/main/java/vn/iotstar/repository/UserRepository.java
package vn.iotstar.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.*;
import vn.iotstar.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Page<User> findByUsernameContainingIgnoreCaseOrFullnameContainingIgnoreCase(
            String u, String f, Pageable pageable);

    Optional<User> findByUsername(String username);
}
