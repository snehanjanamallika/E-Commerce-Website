package com.codewithme.www.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codewithme.www.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{

	@Query("SELECT p FROM Product p WHERE " +
			 "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
			+ "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " 
			+ "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<Product> search(@Param("keyword")String keyword);
	

}
