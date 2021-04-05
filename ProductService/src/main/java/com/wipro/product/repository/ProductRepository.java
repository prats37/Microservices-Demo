package com.wipro.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wipro.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{

	@Query("SELECT p from Product p where p.productId= :productId")
	<Optional> Product findById(long productId);

	@Query("SELECT p from Product p where p.productName= :productName")
	<Optional> Product findByName(String productName);

	@Query("SELECT p from Product p where p.productCategory= :productCategory")
	<Optional> List<Product> findBycategory(String productCategory);

}
