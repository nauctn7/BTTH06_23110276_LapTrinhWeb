package vn.iotstar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.Video;

public interface VideoService {

	void deleteAll();

	void delete(Video entity);

	void deleteById(String id);

	long count();

	Optional<Video> findById(String id);

	List<Video> findAllById(Iterable<String> ids);

	List<Video> findAll();

	Page<Video> findAll(Pageable pageable);

	<S extends Video> S save(S entity);

	List<Video> findAll(Sort sort);

	Page<Video> findByTitleContainingIgnoreCase(String title, Pageable pageable);

	Page<Video> search(String title, Pageable pageable);

}
