package com.wipro.product.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.wipro.product.entity.Inventory;
import com.wipro.product.entity.MapPromotionProduct;
import com.wipro.product.entity.Price;
import com.wipro.product.entity.Product;
import com.wipro.product.entity.Promotion;

public interface ProductService {

	ResponseEntity<Product> addNewProduct(@Valid Product product);

	ResponseEntity<Product> viewProductById(long productId);

	ResponseEntity<List<Product>> viewAllProducts();

	ResponseEntity<Product> viewProductByName(String productName);

	ResponseEntity<List<Product>> viewProductBycategory(String productCategory);

	ResponseEntity<Product> updateProduct(@Valid Product product);

	ResponseEntity<Product> deleteProduct(long productId);

	ResponseEntity<Price> addPrice(@Valid Price price);

	ResponseEntity<Price> viewPromotionPrice(long productId);

	ResponseEntity<Price> deletePrice(long productId);

	ResponseEntity<Price> viewPrice(long productId);

	ResponseEntity<Price> updatePrice(@Valid Price price);

	ResponseEntity<Promotion> addPromotion(Promotion promotion);

	ResponseEntity<Promotion> updatePromotion(Promotion promotion);

	ResponseEntity<Promotion> deletePromotion(int promotionID);

	ResponseEntity<MapPromotionProduct> mapPromotionProduct(long productId, int promotionID);

	ResponseEntity<Promotion[]> findPromoForProduct(long productId);

	ResponseEntity<Promotion> viewPromotion(int promotionID);

	ResponseEntity<Inventory> addInventory(Inventory inventory);

	ResponseEntity<Inventory> viewInventory(long productId);

	ResponseEntity<Inventory> deleteInventory(long productId);

	ResponseEntity<Inventory> updateInventory(Inventory inventory);

}
