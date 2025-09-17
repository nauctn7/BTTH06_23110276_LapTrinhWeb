package vn.iotstar.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iotstar.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	// tìm theo tên chứa chuỗi (đúng với field trong entity của bạn là "categoryname")
	  List<Category> findByCategorynameContaining(String name);

	  // bản có phân trang
	  Page<Category> findByCategorynameContaining(String name, Pageable pageable);
	  Page<Category> findByCategorynameContainingIgnoreCase(String name, Pageable pageable);
	  List<Category> findByStatusTrueOrderByCategorynameAsc();
	// CategoryRepository.java
	 
	  Optional<Category> findByCategoryIdAndStatusTrue(Long categoryId);


}
