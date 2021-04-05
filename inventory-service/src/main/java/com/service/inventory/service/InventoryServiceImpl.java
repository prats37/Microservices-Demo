package com.service.inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.inventory.model.Inventory;
import com.service.inventory.repository.InventoryDAO;

@Service
public class InventoryServiceImpl implements InventoryService {
    
	@Autowired
	InventoryDAO dao;
	
	
	@Override
	public Inventory addProductAvail(Inventory inventory) {
        return dao.save(inventory);
	}

	@Override
	public Inventory updateProductAvail(Inventory inventory) {
		Optional<Inventory> invent=dao.findById(inventory.getProductId());
		if(invent.isPresent()){
			invent.get().setProductAvailNo(inventory.getProductAvailNo());
			return dao.save(invent.get());
		}
		else{
			return null;
		}
	}

	@Override
	public void deleteProductAvail(long productId) {
		Optional<Inventory> invent=dao.findById(productId);
		if(invent.isPresent()){
		    dao.deleteById(productId);
		}
	}

	@Override
	public List<Inventory> getAllProductAvail() {
		List<Inventory> list=dao.findAll();
		return list;
	}

	@Override
	public Inventory getProductAvail(long productId) {
		Optional<Inventory> invent =dao.findById(productId);
		if(invent.isPresent()) {
			return invent.get();
		}else
			return null;
	}

}
