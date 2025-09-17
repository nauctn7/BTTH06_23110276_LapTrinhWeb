package vn.iotstar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.Category;

public interface CategoryService {

	void deleteAll();

	<S extends Category> List<S> findAll(Example<S> example, Sort sort);

	void delete(Category entity);

	void deleteById(Long id);

	long count();

	Optional<Category> findById(Long id);

	List<Category> findAllById(Iterable<Long> ids);

	List<Category> findAll();

	Page<Category> findAll(Pageable pageable);

	<S extends Category> S save(S entity);

	List<Category> findAll(Sort sort);

	Page<Category> search(String name, Pageable pageable);
	// CategoryService.java
	List<Category> findActive();
	Optional<Category> findActiveById(Long id);

}
