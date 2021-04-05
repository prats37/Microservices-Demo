package com.wipro.promotion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wipro.promotion.entity.MapPromotionProduct;

@Repository
public interface MapPromotionProductRepository extends JpaRepository<MapPromotionProduct,Integer>{

	@Query("SELECT promoProd from MapPromotionProduct promoProd where promoProd.productId= :productId")
	List<MapPromotionProduct> findAllPromotionByProductId(long productId);

}
