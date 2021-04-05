package com.wipro.price.controller;

import java.util.List;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.price.entity.Price;
import com.wipro.price.service.PriceService;
import com.wipro.price.utility.CustomError;

@RestController
@RefreshScope
@RequestMapping("/price")
public class PriceController {
	
	@Autowired
	PriceService priceService;
	
	@Value("${server.port}")
	String serverPort;
	
	@GetMapping("/")
	public String getWelcome() {
		return "### Welcome to price service at port number "+serverPort+" ###";
	}
	
	@PostMapping("/add")
	public ResponseEntity<Price> addPrice(@Valid @RequestBody Price price){
		ResponseEntity<?> response=new ResponseEntity(
				new CustomError("Unable to add price")+price.toString(), HttpStatus.CONFLICT);
		if(price!=null) {
			response=priceService.addPrice(price);		
		}else {
			response=new ResponseEntity("Price should not be null",HttpStatus.BAD_REQUEST);
		}	
		return (ResponseEntity<Price>) response;
	}
	
	@GetMapping("/view/{productId}")
	public ResponseEntity<Price> viewPrice(@PathVariable long productId){
		ResponseEntity<Price> response=new ResponseEntity(
				new CustomError("Unable to view price "+productId), HttpStatus.CONFLICT);
		if(productId>=0) {
			response=priceService.viewPrice(productId);
		}else {
			response=new ResponseEntity(new CustomError("Product Id should be a +ve number"),HttpStatus.BAD_REQUEST);
		}	
		return (ResponseEntity<Price>) response;
	}
	
	@PutMapping("/update")
	public ResponseEntity<Price> updatePrice(@Valid @RequestBody Price price){
		ResponseEntity<?> response=new ResponseEntity(
				new CustomError("Unable to update price")+price.toString(), HttpStatus.CONFLICT);
		if(price!=null) {
			response=priceService.updatePrice(price);		
		}else {
			response=new ResponseEntity(new CustomError("Price should not be null"),HttpStatus.BAD_REQUEST);
		}	
		return (ResponseEntity<Price>) response;
	}
	
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<Price> deletePrice(@PathVariable long productId){
		ResponseEntity<?> response=new ResponseEntity(
				new CustomError("Unable to delete price "+productId), HttpStatus.CONFLICT);
		if(productId>=0) {
			response=priceService.deletePrice(productId);
		}else {
			response=new ResponseEntity(new CustomError("Price should not be null"),HttpStatus.BAD_REQUEST);
		}
		return (ResponseEntity<Price>) response;
	}
	
	@GetMapping("/view/promotion/{productId}")
	public ResponseEntity<List<Price>> viewPromotionPrice(@PathVariable long productId){
		ResponseEntity<?> response=null;
		ResponseEntity<Price> res=null;
		res=priceService.viewPrice(productId);
		if(res.getBody()!=null && res.getBody() instanceof Price) {
			response=(ResponseEntity<List<Price>>) priceService.getPromotionPrice(productId,res.getBody().getProductPrice(),res.getBody().getDiscountedPrice());
		}else {
			return new ResponseEntity(new CustomError("No price found for given product id"),HttpStatus.NO_CONTENT);
		}
		return (ResponseEntity<List<Price>>) response;
	}

}