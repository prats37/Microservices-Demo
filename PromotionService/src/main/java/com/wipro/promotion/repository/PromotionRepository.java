package com.wipro.promotion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wipro.promotion.entity.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion,Integer>{

	@Query("SELECT p from Promotion p where p.promotionCode = :promotionCode")
	Promotion findByPromotionCode(String promotionCode);

}
