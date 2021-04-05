package com.wipro.promotion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.wipro.promotion.entity.MapPromotionProduct;
import com.wipro.promotion.entity.Promotion;
import com.wipro.promotion.service.PromotionService;

@RestController
@RequestMapping("/api/v1")
public class PromotionController {
	
	@Autowired
	private PromotionService promotionService;
	
	@Value("${server.port}")
	private String portNumber;
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@GetMapping
	public String welcome() {
		return "### Greeting from Promotion Service at port number "+portNumber+" ###";
	}
	
	@PostMapping("/promotion")
	public Promotion addNewPromotion(@RequestBody Promotion promotion) {
		return promotionService.addPromotion(promotion);
	}
	
	@PutMapping("/promotion")
	public Promotion updatePromotion(@RequestBody Promotion promotion) {
		return promotionService.updatePromotion(promotion);
	}
	
	@GetMapping("/promotions")
	public List<Promotion> viewAllPromotion(){
		return promotionService.viewAllPromotion();
	}
	
	@GetMapping("/promotion/promocode/{promotionID}")
	public Promotion findPromotionById(@PathVariable int promotionID) {
		return promotionService.findPromotionById(promotionID);
	}
	
	@GetMapping("/promotion/productcode/{promotionCode}")
	public Promotion findPromotionByCode(@PathVariable String promotionCode) {
		return promotionService.findPromotionByCode(promotionCode);
	}
	
	@DeleteMapping("/promotion/{promotionID}")
	public void deletePromotion(@PathVariable int promotionID) {
		promotionService.deletePromotion(promotionID);
	}
	
	@GetMapping("/map-promotion/{productId}/{promotionID}")
	public MapPromotionProduct mapProductToPromotion(@PathVariable long productId,@PathVariable int promotionID) {
		return promotionService.mapProductToPromotion(productId,promotionID);
	}
	
	@GetMapping("/promotion/{productId}")
	public List<Promotion> findPromotionForProduct(@PathVariable long productId){
		return promotionService.findPromotionForProduct(productId);
	}
	
}
