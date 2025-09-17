package vn.iotstar.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.Category;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.services.CategoryService;

@Service
public class CategoryServicesImpl implements CategoryService {

  @Autowired
  CategoryRepository categoryRepository;

  public CategoryServicesImpl(CategoryRepository categoryRepository) {

	this.categoryRepository = categoryRepository;
  }

  @Override
public List<Category> findAll(Sort sort) {
	return categoryRepository.findAll(sort);
  }

  @Override
public <S extends Category> S save(S entity) {
	return categoryRepository.save(entity);
  }

  @Override
public Page<Category> findAll(Pageable pageable) {
	return categoryRepository.findAll(pageable);
  }

  @Override
public List<Category> findAll() {
	return categoryRepository.findAll();
  }

  @Override
public List<Category> findAllById(Iterable<Long> ids) {
	return categoryRepository.findAllById(ids);
  }

  @Override
public Optional<Category> findById(Long id) {
	return categoryRepository.findById(id);
  }

  @Override
public long count() {
	return categoryRepository.count();
  }

  @Override
public void deleteById(Long id) {
	categoryRepository.deleteById(id);
  }

  @Override
public void delete(Category entity) {
	categoryRepository.delete(entity);
  }

  @Override
public <S extends Category> List<S> findAll(Example<S> example, Sort sort) {
	return categoryRepository.findAll(example, sort);
  }

  @Override
public void deleteAll() {
	categoryRepository.deleteAll();
  }


  @Override
  public Page<Category> search(String name, Pageable pageable) {
    if (name == null || name.trim().isEmpty()) {
      return categoryRepository.findAll(pageable);
    }
    return categoryRepository.findByCategorynameContaining(name.trim(), pageable);
  }

//CategoryServicesImpl.java
@Override
public List<Category> findActive() {
   return categoryRepository.findByStatusTrueOrderByCategorynameAsc();
}

@Override
public Optional<Category> findActiveById(Long id) {
   return categoryRepository.findByCategoryIdAndStatusTrue(id);
}



}
