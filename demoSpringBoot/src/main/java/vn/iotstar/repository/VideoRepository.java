package vn.iotstar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.iotstar.entity.Video;

public interface VideoRepository extends JpaRepository<Video, String> {
    Page<Video> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
