package com.service.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.service.inventory.model.Inventory;
import com.service.inventory.service.InventoryService;

@RestController
@RequestMapping("/inventories")
public class InventoryController {
    
	@Autowired
	InventoryService inventService;
	
	@Value("{server.port}")
	String serverPort;
	
	@GetMapping("/")
	public String welcome() {
		return "### Welcome to Inventory Service from port number "+serverPort+" ###";
	}
	
	@PostMapping("/add")
	public ResponseEntity<Inventory> addInventory(@RequestBody Inventory inventory)
	{
		Inventory invent = inventService.addProductAvail(inventory);
		return new ResponseEntity<Inventory>(invent, HttpStatus.OK);
		
	}
	
	@GetMapping("/get/{productId}")
	public ResponseEntity<Inventory> getInventory(@PathVariable long productId)
	{
		Inventory invent = inventService.getProductAvail(productId);
		return new ResponseEntity<Inventory>(invent, HttpStatus.OK);
		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Inventory>> getAllInventory()
	{
		List<Inventory> invent = inventService.getAllProductAvail();
		return new ResponseEntity<List<Inventory>>(invent, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<Inventory> deleteInventory(@PathVariable long productId)
	{
		inventService.deleteProductAvail(productId);
		return new ResponseEntity<Inventory>(HttpStatus.ACCEPTED);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<Inventory> updateInventory(@RequestBody Inventory inventory)
	{
		Inventory invent = inventService.updateProductAvail(inventory);
		return new ResponseEntity<Inventory>(invent, HttpStatus.ACCEPTED);
		
	}
	
}
