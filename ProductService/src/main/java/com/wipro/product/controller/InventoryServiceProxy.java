package com.wipro.product.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wipro.product.entity.Inventory;

@FeignClient(name="inventory-service")
public interface InventoryServiceProxy {
	@PostMapping("/inventories/add")
	public ResponseEntity<Inventory> addInventory(@RequestBody Inventory inventory);
	@GetMapping("/inventories/get/{productId}")
	public ResponseEntity<Inventory> getInventory(@PathVariable long productId);
	@DeleteMapping("/inventories/delete/{productId}")
	public ResponseEntity<Inventory> deleteInventory(@PathVariable long productId);
	@PutMapping("/inventories/update")
	public ResponseEntity<Inventory> updateInventory(@RequestBody Inventory inventory);
}
