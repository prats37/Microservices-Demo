package com.service.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.inventory.model.Inventory;

@Repository
public interface InventoryDAO extends JpaRepository<Inventory, Long> {

}
