package com.wipro.productview.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

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
import com.wipro.productview.model.Inventory;
import com.wipro.productview.model.Price;
import com.wipro.productview.model.Product;
import com.wipro.productview.model.ProductView;
import com.wipro.productview.model.Promotion;
import com.wipro.productview.utility.CustomError;

@Service
public class ProductViewServiceImpl implements ProductViewService{
	
	@Autowired
	EurekaClient eurekaClient;
	
	@Autowired
	RestTemplateBuilder restTemplateBuilder;
	
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	public String getPriceUrl() {
		InstanceInfo instanceInfo=eurekaClient.getNextServerFromEureka("PriceService", false);
		String baseUrl=instanceInfo.getHomePageUrl();
		return baseUrl;
	}
	
	public String getProductServiceUrl() {
		InstanceInfo instanceInfo=eurekaClient.getNextServerFromEureka("ProductService", false);
		String baseUrl=instanceInfo.getHomePageUrl();
		return baseUrl;
	}
	
	public String getPromotionServiceUrl() {
		InstanceInfo instanceInfo=eurekaClient.getNextServerFromEureka("promotion-service", false);
		String baseUrl=instanceInfo.getHomePageUrl();
		return baseUrl;
	}
	
	public String getInventoryServiceUrl() {
		InstanceInfo instanceInfo=eurekaClient.getNextServerFromEureka("inventory-service", false);
		String baseUrl=instanceInfo.getHomePageUrl();
		return baseUrl;
	}

	@Override
	@HystrixCommand(fallbackMethod="defaultMessageForViewProduct")
	public ResponseEntity<ProductView> viewProducts(String productName) {
		ProductView outputProduct=new ProductView();
		ResponseEntity<?> outputResponse=null;
		ResponseEntity<Product> productResponse=null;
		ResponseEntity<Price> priceResponse=null;
		ResponseEntity<Inventory> inventoryResponse=null;
		ResponseEntity<Promotion[]> promotionResponse=null;
		String baseUrlOfProd=getProductServiceUrl();
		baseUrlOfProd=baseUrlOfProd+"product/view/byproductname/"+productName;
		RestTemplate restTemplate=restTemplateBuilder.build();
		String baseUrlOfInventory=getInventoryServiceUrl();
		String baseUrlOfPrice=getPriceUrl();
		String baseUrlOfPromotion=getPromotionServiceUrl();
		try {
			productResponse=restTemplate.getForEntity(baseUrlOfProd,Product.class);
			if(productResponse.getStatusCode()==HttpStatus.OK) {
				baseUrlOfInventory=baseUrlOfInventory+"inventories/get/"+productResponse.getBody().getProductId();
				inventoryResponse=restTemplate.getForEntity(baseUrlOfInventory, Inventory.class);
				if(inventoryResponse.getStatusCode()==HttpStatus.OK) {
					baseUrlOfPrice=baseUrlOfPrice+"price/view/"+productResponse.getBody().getProductId();
					priceResponse=restTemplate.getForEntity(baseUrlOfPrice, Price.class);
					if(priceResponse.getStatusCode()==HttpStatus.OK) {
						baseUrlOfPromotion=baseUrlOfPromotion+"api/v1/promotion/"+productResponse.getBody().getProductId();
						promotionResponse=restTemplate.getForEntity(baseUrlOfPromotion, Promotion[].class);
						if(promotionResponse.getStatusCode()==HttpStatus.OK) {
							outputProduct.setProductId(productResponse.getBody().getProductId());
							outputProduct.setProductName(productResponse.getBody().getProductName());
							outputProduct.setProductCategory(productResponse.getBody().getProductCategory());
							outputProduct.setProductMfg(productResponse.getBody().getManufacturingDate());
							outputProduct.setBestBefore(productResponse.getBody().getBestBefore());
							outputProduct.setProductDescription(productResponse.getBody().getProductDescription());
							outputProduct.setStockOfProduct(inventoryResponse.getBody().getProductAvailNo());
							outputProduct.setProductPrice(priceResponse.getBody().getProductPrice());
							outputProduct.setRegularDiscountedPrice(priceResponse.getBody().getDiscountedPrice());
							Promotion[] promotions=promotionResponse.getBody();
							List<Promotion> promotionsList=new ArrayList<Promotion>();
							promotionsList=Arrays.asList(promotions);
							List<Price> priceList=new ArrayList<Price>();
							if(promotionsList.size()>0) {
								ListIterator<Promotion> ltr=promotionsList.listIterator();
								while(ltr.hasNext()) {
									Promotion promotion=ltr.next();
									double discountPercent=promotion.getDiscountPercentage();
									double discountedPrice=priceResponse.getBody().getDiscountedPrice();
									double discountPrice=discountedPrice-(discountedPrice*discountPercent/100);
									Price price=new Price();
									price.setProductId(productResponse.getBody().getProductId());
									price.setProductPrice(priceResponse.getBody().getProductPrice());
									price.setDiscountedPrice(discountPrice);
									price.setPromotionApplied(promotion.getPromotionCode());
									priceList.add(price);
								}
								outputProduct.setPromotionsAvailable(priceList);
								outputResponse=new ResponseEntity(outputProduct,HttpStatus.OK);
							}else {
								outputProduct.setProductName(productResponse.getBody().getProductName());
								outputProduct.setProductCategory(productResponse.getBody().getProductCategory());
								outputProduct.setProductMfg(productResponse.getBody().getManufacturingDate());
								outputProduct.setBestBefore(productResponse.getBody().getBestBefore());
								outputProduct.setStockOfProduct(inventoryResponse.getBody().getProductAvailNo());
								outputProduct.setProductPrice(priceResponse.getBody().getProductPrice());
								outputProduct.setRegularDiscountedPrice(priceResponse.getBody().getDiscountedPrice());
								outputResponse=new ResponseEntity(outputProduct,HttpStatus.OK);
							}
						}else {
							outputResponse=new ResponseEntity(new CustomError(promotionResponse.toString()),HttpStatus.NOT_FOUND);
						}
					}else {
						outputResponse=new ResponseEntity(new CustomError(priceResponse.toString()),HttpStatus.NO_CONTENT);
					}
				}else {
					outputResponse=new ResponseEntity(new CustomError(inventoryResponse.toString()),HttpStatus.NOT_FOUND);
				}
			}else {
				outputResponse=new ResponseEntity(new CustomError(productResponse.toString()),HttpStatus.NO_CONTENT);
			}
		}catch(Exception e) {
			outputResponse=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return (ResponseEntity<ProductView>) outputResponse;
	}
	
	public ResponseEntity<ProductView> defaultMessageForViewProduct(String productName){
		return new ResponseEntity(new CustomError("Service is temporarily down!! Please try after some time"),HttpStatus.NOT_FOUND);
	}

}
