package com.wipro.product.controller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wipro.product.entity.Promotion;

@FeignClient(name="promtion-service")
public interface PromotionServiceProxy {
	
	@PostMapping("/api/v1/promotion")
	public Promotion addNewPromotion(@RequestBody Promotion promotion);
	@PutMapping("api/v1/promotion")
	public Promotion updatePromotion(@RequestBody Promotion promotion);
	@GetMapping("/api/v1/promotion/{productId}")
	public List<Promotion> findPromotionForProduct(@PathVariable long productId);
	

}
