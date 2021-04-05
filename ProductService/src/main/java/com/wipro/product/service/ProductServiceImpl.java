package com.wipro.product.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.wipro.product.entity.Inventory;
import com.wipro.product.entity.MapPromotionProduct;
import com.wipro.product.entity.Price;
import com.wipro.product.entity.Product;
import com.wipro.product.entity.Promotion;
import com.wipro.product.repository.ProductRepository;
import com.wipro.product.utility.CustomError;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	EurekaClient eurekaClient;
	
	@Autowired
	RestTemplateBuilder restTemplateBuilder;
	
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Override
	public ResponseEntity<Product> addNewProduct(@Valid Product product) {
		Product addedProduct=new Product();
		ResponseEntity<Product> response=null;
		try {
			addedProduct=productRepo.save(product);
			response=new ResponseEntity<Product>(addedProduct,HttpStatus.CREATED);
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@Override
	public ResponseEntity<Product> viewProductById(long productId) {
		Product fetchedProduct=new Product();
		ResponseEntity<Product> response=null;
		try {
			fetchedProduct=productRepo.findById(productId);
			if(fetchedProduct!=null) {
				response=new ResponseEntity<Product>(fetchedProduct,HttpStatus.FOUND);
			}else {
				response=new ResponseEntity(new CustomError("No product is available with given id"),HttpStatus.NO_CONTENT);
			}
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@Override
	public ResponseEntity<List<Product>> viewAllProducts() {
		List<Product> productList=new ArrayList<Product>();
		ResponseEntity<List<Product>> response=null;
		try {
			List<Product> fetchedProdList=productRepo.findAll();
			if(fetchedProdList.size()>0) {
				response=new ResponseEntity<List<Product>>(fetchedProdList,HttpStatus.FOUND);
			}else {
				response=new ResponseEntity(new CustomError("No product is available"),HttpStatus.NO_CONTENT);
			}
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@Override
	public ResponseEntity<Product> viewProductByName(String productName) {
		Product fetchedProduct=new Product();
		ResponseEntity<Product> response=null;
		try {
			fetchedProduct=productRepo.findByName(productName);
			if(fetchedProduct!=null) {
				response=new ResponseEntity<Product>(fetchedProduct,HttpStatus.OK);
			}else {
				response=new ResponseEntity(new CustomError("No product is available with this name"),HttpStatus.NO_CONTENT);
			}
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@Override
	public ResponseEntity<List<Product>> viewProductBycategory(String productCategory) {
		List<Product> productList=new ArrayList<Product>();
		ResponseEntity<List<Product>> response=null;
		try {
			List<Product> fetchedProdList=productRepo.findBycategory(productCategory);
			if(fetchedProdList.size()>0) {
				response=new ResponseEntity<List<Product>>(fetchedProdList,HttpStatus.FOUND);
			}else {
				response=new ResponseEntity(new CustomError("No product is available with given category"),HttpStatus.NO_CONTENT);
			}
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@Override
	public ResponseEntity<Product> updateProduct(@Valid Product product) {
		Product updatedProduct=new Product();
		ResponseEntity<Product> response=null;
		try {
			Product fetchedProduct=productRepo.findById(product.getProductId());
			if(fetchedProduct!=null) {
				Product toBeUpdated=new Product();
				toBeUpdated.setProductId(fetchedProduct.getProductId());
				toBeUpdated.setProductName(product.getProductName());
				toBeUpdated.setProductCategory(product.getProductCategory());
				toBeUpdated.setManufacturingDate(product.getManufacturingDate());
				toBeUpdated.setBestBefore(product.getBestBefore());
				toBeUpdated.setProductDescription(product.getProductDescription());
				updatedProduct=productRepo.save(toBeUpdated);
				response=new ResponseEntity<Product>(updatedProduct,HttpStatus.OK);
			}else {
				response=new ResponseEntity(new CustomError("No product found with given id"),HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@Override
	public ResponseEntity<Product> deleteProduct(long productId) {
		ResponseEntity<Product> response=null;
		try {
			Product fetchedProduct=productRepo.findById(productId);
			if(fetchedProduct!=null) {
				productRepo.delete(fetchedProduct);
				response=new ResponseEntity(new CustomError("Product Deleted Successfully"),HttpStatus.OK);
			}else {
				response=new ResponseEntity(new CustomError("No product found with given id to be deleted"),HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	
	public String getPriceUrl() {
		InstanceInfo instanceInfo=eurekaClient.getNextServerFromEureka("PriceService", false);
		String baseUrl=instanceInfo.getHomePageUrl();
		return baseUrl;
	}
	
	public ResponseEntity<Price> defaultMessageForPriceAddition(Price price) {
		return new ResponseEntity(new CustomError("Price Service is temprarily down!! Please try after some time"),HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@Override
	@HystrixCommand(fallbackMethod="defaultMessageForPriceAddition")
	public ResponseEntity<Price> addPrice(@Valid Price price) {
		String baseUrl=getPriceUrl();
		ResponseEntity<Price> response=null;
		try {
			Product prod=productRepo.findById(price.getProductId());
			if(prod!=null) {
				baseUrl=baseUrl+"price/add";
				RestTemplate restTemplate=restTemplateBuilder.build();
				response=restTemplate.postForEntity(baseUrl, price, Price.class);
			}else {
				response=new ResponseEntity(new CustomError("Price can't be added as no product exists for given product id"),HttpStatus.NOT_ACCEPTABLE);
			}
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@Override
	@HystrixCommand(fallbackMethod="defaultMessageForPromoPriceView")
	public ResponseEntity<Price> viewPromotionPrice(long productId) {
		String baseUrl=getPriceUrl();
		ResponseEntity<Price> response=null;
		try {
			baseUrl=baseUrl+"price/view/promotion/"+productId;
			RestTemplate restTemplate=restTemplateBuilder.build();
			response=restTemplate.getForEntity(baseUrl,Price.class);
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	public ResponseEntity<Price> defaultMessageForPromoPriceView(long productId) {
		return new ResponseEntity(new CustomError("Price Service is temprarily down!! Please try after some time"),HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	@HystrixCommand(fallbackMethod="defaultMessageForPriceDelete")
	public ResponseEntity<Price> deletePrice(long productId) {
		String baseUrl=getPriceUrl();
		ResponseEntity<Price> response=null;
		try {
			baseUrl=baseUrl+"price/delete/"+productId;
			RestTemplate restTemplate=restTemplateBuilder.build();
			restTemplate.delete(baseUrl);
			response=new ResponseEntity(new CustomError("Price Deleted successfully"),HttpStatus.OK);
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	public ResponseEntity<Price> defaultMessageForPriceDelete(long productId) {
		return new ResponseEntity(new CustomError("Price Service is temprarily down!! Please try after some time"),HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	@HystrixCommand(fallbackMethod="defaultMessageForPriceView")
	public ResponseEntity<Price> viewPrice(long productId) {
		String baseUrl=getPriceUrl();
		ResponseEntity<Price> response=null;
		try {
			baseUrl=baseUrl+"price/view/"+productId;
			RestTemplate restTemplate=restTemplateBuilder.build();
			response=restTemplate.getForEntity(baseUrl,Price.class);
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	public ResponseEntity<Price> defaultMessageForPriceView(long productId) {
		return new ResponseEntity(new CustomError("Price Service is temprarily down!! Please try after some time"),HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	@HystrixCommand(fallbackMethod="defaultMessageForPriceUpdate")
	public ResponseEntity<Price> updatePrice(@Valid Price price) {
		String baseUrl=getPriceUrl();
		ResponseEntity<Price> response=null;
		try {
			Product prod=productRepo.findById(price.getProductId());
			if(prod!=null) {
				baseUrl=baseUrl+"price/update";
				RestTemplate restTemplate=restTemplateBuilder.build();
				restTemplate.put(baseUrl, price);
				response=new ResponseEntity(new CustomError("Price updated successfully"),HttpStatus.CREATED);
			}else {
				response=new ResponseEntity(new CustomError("Price can't be updated as no product exists for given product id"),HttpStatus.NOT_ACCEPTABLE);
			}
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	public ResponseEntity<Price> defaultMessageForPriceUpdate(Price price) {
		return new ResponseEntity(new CustomError("Price Service is temprarily down!! Please try after some time"),HttpStatus.SERVICE_UNAVAILABLE);
	}

	
	public String getPromotionUrl() {
		InstanceInfo instanceInfo=eurekaClient.getNextServerFromEureka("promotion-service", false);
		String baseUrl=instanceInfo.getHomePageUrl();
		return baseUrl;
	}
	
	
	@Override
	@HystrixCommand(fallbackMethod="defaultMessageForPromotionAddition")
	public ResponseEntity<Promotion> addPromotion(Promotion promotion) {
		String baseUrl=getPromotionUrl();
		ResponseEntity<Promotion> response=null;
		try {
			baseUrl=baseUrl+"api/v1/promotion";
			RestTemplate restTemplate=restTemplateBuilder.build();
			response=restTemplate.postForEntity(baseUrl, promotion, Promotion.class);
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	public ResponseEntity<Promotion> defaultMessageForPromotionAddition(Promotion price) {
		return new ResponseEntity(new CustomError("Promotion Service is temprarily down!! Please try after some time"),HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	@HystrixCommand(fallbackMethod="defaultMessageForPromotionUpdation")
	public ResponseEntity<Promotion> updatePromotion(Promotion promotion) {
		String baseUrl=getPromotionUrl();
		ResponseEntity<Promotion> response=null;
		try {
			baseUrl=baseUrl+"api/v1/promotion";
			RestTemplate restTemplate=restTemplateBuilder.build();
			restTemplate.put(baseUrl, promotion);
			response=new ResponseEntity(new CustomError("Promotion updated successfully"),HttpStatus.CREATED);
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	public ResponseEntity<Promotion> defaultMessageForPromotionUpdation(Promotion promotion) {
		return new ResponseEntity(new CustomError("Promotion Service is temprarily down!! Please try after some time"),HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	@HystrixCommand(fallbackMethod="defaultMessageForPromotionDeletion")
	public ResponseEntity<Promotion> deletePromotion(int promotionID) {
		String baseUrl=getPromotionUrl();
		ResponseEntity<Promotion> response=null;
		try {
			baseUrl=baseUrl+"api/v1/promotion/"+promotionID;
			RestTemplate restTemplate=restTemplateBuilder.build();
			restTemplate.delete(baseUrl);
			response=new ResponseEntity(new CustomError("Promotion Deleted successfully"),HttpStatus.OK);
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	public ResponseEntity<Promotion> defaultMessageForPromotionDeletion(int promotionID) {
		return new ResponseEntity(new CustomError("Promotion Service is temprarily down!! Please try after some time"),HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	@HystrixCommand(fallbackMethod="defaultMessageForMapPromotionToProduct")
	public ResponseEntity<MapPromotionProduct> mapPromotionProduct(long productId, int promotionID) {
		String baseUrl=getPromotionUrl();
		ResponseEntity<MapPromotionProduct> response=null;
		try {
			baseUrl=baseUrl+"api/v1//map-promotion/"+productId+"/"+promotionID;
			RestTemplate restTemplate=restTemplateBuilder.build();
			response=restTemplate.getForEntity(baseUrl,MapPromotionProduct.class);
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	public ResponseEntity<MapPromotionProduct> defaultMessageForMapPromotionToProduct(long productId, int promotionID) {
		return new ResponseEntity(new CustomError("Promotion Service is temprarily down!! Please try after some time"),HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	@HystrixCommand(fallbackMethod="defaultMessageForfindPromoForProduct")
	public ResponseEntity<Promotion[]> findPromoForProduct(long productId) {
		String baseUrl=getPromotionUrl();
		ResponseEntity<Promotion[]> response=null;
		try {
			Product prod=productRepo.findById(productId);
			if(prod!=null) {
				baseUrl=baseUrl+"api/v1//promotion/"+productId;
				RestTemplate restTemplate=restTemplateBuilder.build();
				response=restTemplate.getForEntity(baseUrl,Promotion[].class);
			}else {
				response=new ResponseEntity(new CustomError("There is no product with given Id"),HttpStatus.NO_CONTENT);
			}
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	public ResponseEntity<Promotion[]> defaultMessageForfindPromoForProduct(long productId) {
		return new ResponseEntity(new CustomError("Promotion Service is temprarily down!! Please try after some time"),HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	@HystrixCommand(fallbackMethod="defaultMessageForViewPromotion")
	public ResponseEntity<Promotion> viewPromotion(int promotionID) {
		String baseUrl=getPromotionUrl();
		ResponseEntity<Promotion> response=null;
		try {
			baseUrl=baseUrl+"api/v1/promotion/promocode/"+promotionID;
			RestTemplate restTemplate=restTemplateBuilder.build();
			response=restTemplate.getForEntity(baseUrl,Promotion.class);
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	public ResponseEntity<Promotion> defaultMessageForViewPromotion(int promotionID) {
		return new ResponseEntity(new CustomError("Promotion Service is temprarily down!! Please try after some time"),HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	public String getInventoryUrl() {
		InstanceInfo instanceInfo=eurekaClient.getNextServerFromEureka("inventory-service", false);
		String baseUrl=instanceInfo.getHomePageUrl();
		return baseUrl;
	}

	@Override
	@HystrixCommand(fallbackMethod="defaultMsgForAddInventory")
	public ResponseEntity<Inventory> addInventory(Inventory inventory) {
		String baseUrl=getInventoryUrl();
		ResponseEntity<Inventory> response=null;
		try {
			Product prod=productRepo.findById(inventory.getProductId());
			if(prod!=null) {
				baseUrl=baseUrl+"inventories/add";
				RestTemplate restTemplate=restTemplateBuilder.build();
				response=restTemplate.postForEntity(baseUrl, inventory, Inventory.class);
			}else {
				response=new ResponseEntity(new CustomError("Inventory can't be added as no product exists for given product id"),HttpStatus.NOT_ACCEPTABLE);
			}
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	public ResponseEntity<Inventory> defaultMsgForAddInventory(Inventory inventory) {
		return new ResponseEntity(new CustomError("Inventory Service is temprarily down!! Please try after some time"),HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	@HystrixCommand(fallbackMethod="defaultMsgForViewInventory")
	public ResponseEntity<Inventory> viewInventory(long productId) {
		String baseUrl=getInventoryUrl();
		ResponseEntity<Inventory> response=null;
		try {
			baseUrl=baseUrl+"inventories/get/"+productId;
			RestTemplate restTemplate=restTemplateBuilder.build();
			response=restTemplate.getForEntity(baseUrl,Inventory.class);
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	
	public ResponseEntity<Inventory> defaultMsgForViewInventory(long productId) {
		return new ResponseEntity(new CustomError("Inventory Service is temprarily down!! Please try after some time"),HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	@HystrixCommand(fallbackMethod="defaultMsgForDeleteInventory")
	public ResponseEntity<Inventory> deleteInventory(long productId) {
		String baseUrl=getInventoryUrl();
		ResponseEntity<Inventory> response=null;
		try {
			baseUrl=baseUrl+"inventories/delete/"+productId;
			RestTemplate restTemplate=restTemplateBuilder.build();
			restTemplate.delete(baseUrl);
			response=new ResponseEntity(new CustomError("Inventory Deleted successfully"),HttpStatus.OK);
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	public ResponseEntity<Inventory> defaultMsgForDeleteInventory(long productId) {
		return new ResponseEntity(new CustomError("Inventory Service is temprarily down!! Please try after some time"),HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	@HystrixCommand(fallbackMethod="defaultMsgForUpdateInventory")
	public ResponseEntity<Inventory> updateInventory(Inventory inventory) {
		String baseUrl=getInventoryUrl();
		ResponseEntity<Inventory> response=null;
		try {
			baseUrl=baseUrl+"inventories/update";
			RestTemplate restTemplate=restTemplateBuilder.build();
			restTemplate.put(baseUrl, inventory);
			response=new ResponseEntity(new CustomError("Inventory updated successfully"),HttpStatus.CREATED);
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	public ResponseEntity<Inventory> defaultMsgForUpdateInventory(Inventory inventory) {
		return new ResponseEntity(new CustomError("Inventory Service is temprarily down!! Please try after some time"),HttpStatus.SERVICE_UNAVAILABLE);
	}

	
	

}
