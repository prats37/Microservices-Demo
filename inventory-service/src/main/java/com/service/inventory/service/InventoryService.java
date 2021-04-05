package com.service.inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.service.inventory.model.Inventory;

@Service
public interface InventoryService {
    
	public Inventory addProductAvail(Inventory inventory);
	public Inventory updateProductAvail(Inventory inventory);
	public void deleteProductAvail(long productId);
	public List<Inventory> getAllProductAvail();
	public Inventory getProductAvail(long productId);
}
