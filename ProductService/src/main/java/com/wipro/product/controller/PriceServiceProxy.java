package com.wipro.product.controller;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wipro.product.entity.Price;

@FeignClient(name="inventory-service")
public interface PriceServiceProxy {
	
	@PostMapping("/price/add")
	public ResponseEntity<Price> addPrice(@Valid @RequestBody Price price);
	@GetMapping("/price/view/{productId}")
	public ResponseEntity<Price> fetchPrice(@PathVariable long productId);
	@DeleteMapping("/price/delete/{productId}")
	public ResponseEntity<Price> deletePrice(@PathVariable long productId);
	@PutMapping("/price/update")
	public ResponseEntity<Price> updatePrice(@Valid @RequestBody Price price);

}
