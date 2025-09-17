// src/main/java/vn/iotstar/services/impl/UserServiceImpl.java
package vn.iotstar.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
import vn.iotstar.entity.User;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.services.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    @Override
    public Page<User> search(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) return repo.findAll(pageable);
        return repo.findByUsernameContainingIgnoreCaseOrFullnameContainingIgnoreCase(keyword, keyword, pageable);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public User save(User u) {
        return repo.save(u);
    }

    @Override
    public void deleteByUsername(String username) {
        repo.deleteById(username);
    }
}
