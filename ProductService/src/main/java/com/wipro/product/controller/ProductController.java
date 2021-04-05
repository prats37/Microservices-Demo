package com.wipro.product.controller;

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

import com.wipro.product.entity.Inventory;
import com.wipro.product.entity.MapPromotionProduct;
import com.wipro.product.entity.Price;
import com.wipro.product.entity.Product;
import com.wipro.product.entity.Promotion;
import com.wipro.product.service.ProductService;
import com.wipro.product.utility.CustomError;

@RestController
@RefreshScope
@RequestMapping("/product")
public class ProductController {
	
	@Value("${server.port}")
	String serverPort;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/")
	public String welcome() {
		return "### Welcome to product service from port number "+serverPort+" ###";
	}
	
	@PostMapping("/add")
	public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product){
		ResponseEntity<Product> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);;
		response=productService.addNewProduct(product);
		return response;
	}
	
	@GetMapping("/view/{productId}")
	public ResponseEntity<Product> viewProductById(@PathVariable long productId){
		ResponseEntity<Product> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);;
		response=productService.viewProductById(productId);
		return response;
	}
	
	@GetMapping("/view/all")
	public ResponseEntity<List<Product>> viewAllProducts(){
		ResponseEntity<List<Product>> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);;
		response=productService.viewAllProducts();
		return response;
	}
	
	@GetMapping("/view/byproductname/{productName}")
	public ResponseEntity<Product> viewProductById(@PathVariable String productName){
		ResponseEntity<Product> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);
		response=productService.viewProductByName(productName);
		return response;
	}
	
	@GetMapping("/view/byproductcategory/{productCategory}")
	public ResponseEntity<List<Product>> viewProductByCategory(@PathVariable String productCategory){
		ResponseEntity<List<Product>> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);
		response=productService.viewProductBycategory(productCategory);
		return response;
	}
	
	@PutMapping("/update")
	public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product){
		ResponseEntity<Product> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);;
		response=productService.updateProduct(product);
		return response;
	}
	
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<Product> deleteProduct(@PathVariable long productId){
		ResponseEntity<Product> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);
		response=productService.deleteProduct(productId);
		return response;
	}
	
	@PostMapping("/price/add")
	public ResponseEntity<Price> addPrice(@Valid @RequestBody Price price){
		ResponseEntity<Price> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);
		response=productService.addPrice(price);
		return response;
	}
	
	@GetMapping("/price/view/{productId}")
	public ResponseEntity<Price> viewPrice(@PathVariable long productId){
		ResponseEntity<Price> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);
		response=productService.viewPrice(productId);
		return response;
	}
	
	@GetMapping("/price/view/promotion/{productId}")
	public ResponseEntity<Price> viewPromotionPrice(@PathVariable long productId){
		ResponseEntity<Price> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);
		response=productService.viewPromotionPrice(productId);
		return response;
	}
	
	@DeleteMapping("/price/delete/{productId}")
	public ResponseEntity<Price> deletePrice(@PathVariable long productId){
		ResponseEntity<Price> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);
		response=productService.deletePrice(productId);
		return response;
	}
	
	@PutMapping("/price/update")
	public ResponseEntity<Price> updatePrice(@Valid @RequestBody Price price){
		ResponseEntity<Price> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);
		response=productService.updatePrice(price);
		return response;
	}
	
	@PostMapping("/promotion/add")
	public ResponseEntity<Promotion> addNewPromotion(@RequestBody Promotion promotion) {
		ResponseEntity<Promotion> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);
		response=productService.addPromotion(promotion);
		return response;
	}
	
	@GetMapping("/promotion/view/{promotionID}")
	public ResponseEntity<Promotion> findPromotionById(@PathVariable int promotionID){
		ResponseEntity<Promotion> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);
		response=productService.viewPromotion(promotionID);
		return response;
	}
	
	@PutMapping("/promotion/update")
	public ResponseEntity<Promotion> updatePromotion(@RequestBody Promotion promotion){
		ResponseEntity<Promotion> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);
		response=productService.updatePromotion(promotion);
		return response;
	}
	
	@DeleteMapping("/promotion/delete/{promotionID}")
	public ResponseEntity<Promotion> deletePromotion(@PathVariable int promotionID) {
		ResponseEntity<Promotion> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);
		response=productService.deletePromotion(promotionID);
		return response;
	}
	
	@GetMapping("promotion/map-promotion/{productId}/{promotionID}")
	public ResponseEntity<MapPromotionProduct> mapProductToPromotion(@PathVariable long productId,@PathVariable int promotionID){
		ResponseEntity<MapPromotionProduct> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);
		response=productService.mapPromotionProduct(productId,promotionID);
		return response;
	}
	
	@GetMapping("/promotion/prodpromo/{productId}")
	public ResponseEntity<Promotion[]> findPromotionForProduct(@PathVariable long productId){
		ResponseEntity<Promotion[]> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);
		response=productService.findPromoForProduct(productId);
		return response;
	}
	
	@PostMapping("/inventory/add")
	public ResponseEntity<Inventory> addInventory(@RequestBody Inventory inventory){
		ResponseEntity<Inventory> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);
		response=productService.addInventory(inventory);
		return response;
	}
	
	@GetMapping("/inventory/view/{productId}")
	public ResponseEntity<Inventory> getInventory(@PathVariable long productId){
		ResponseEntity<Inventory> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);
		response=productService.viewInventory(productId);
		return response;
	}
	
	@DeleteMapping("/inventory/delete/{productId}")
	public ResponseEntity<Inventory> deleteInventory(@PathVariable long productId){
		ResponseEntity<Inventory> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);
		response=productService.deleteInventory(productId);
		return response;
	}
	
	@PutMapping("/inventory/update")
	public ResponseEntity<Inventory> updateInventory(@RequestBody Inventory inventory){
		ResponseEntity<Inventory> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);
		response=productService.updateInventory(inventory);
		return response;
	}
}
