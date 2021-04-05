package com.wipro.price.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wipro.price.entity.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price,Integer>{

	@Query("SELECT p from Price p where p.productId = :productId")
	Optional<Price> findById(long productId);
	
	
	
}
