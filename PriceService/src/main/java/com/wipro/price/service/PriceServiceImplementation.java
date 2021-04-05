package com.wipro.price.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

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
import com.wipro.price.dao.PriceRepository;
import com.wipro.price.entity.Price;
import com.wipro.price.entity.Promotion;
import com.wipro.price.utility.CustomError;

@Service
public class PriceServiceImplementation implements PriceService{
	
	@Autowired
	PriceRepository priceRepo;
	
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
	public ResponseEntity<Price> addPrice(@Valid Price price) {
		ResponseEntity<Price> response=null;
		Price savedPrice=new Price();
		if(price.getDiscountedPrice()==0) {
			price.setDiscountedPrice(price.getProductPrice());
		}
		try {
			savedPrice=priceRepo.save(price);
			response=new ResponseEntity(savedPrice,HttpStatus.OK);
		}catch(Exception e) {
			response=new ResponseEntity(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@Override
	public ResponseEntity<Price> viewPrice(long productId) {
		ResponseEntity<?> response=null;
		try {
			Optional<Price> fetchedPriceObj=priceRepo.findById(productId);
			Price outputPrice=new Price();
			if(fetchedPriceObj.isEmpty()) {
				response=new ResponseEntity(new CustomError("No product found with given id"),HttpStatus.NO_CONTENT);
			}else {
				outputPrice.setProductId(productId);
				outputPrice.setProductPrice(fetchedPriceObj.get().getProductPrice());
				outputPrice.setDiscountedPrice(fetchedPriceObj.get().getDiscountedPrice());
				response=new ResponseEntity<>(outputPrice,HttpStatus.OK);
			}
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return (ResponseEntity<Price>) response;
	}

	@Override
	public ResponseEntity<Price> updatePrice(@Valid Price price) {
		ResponseEntity<?> response=null;
		try {
			Optional<Price> fetchedPrices=priceRepo.findById(price.getProductId());
			Price outputPriceObj=new Price();
			if(fetchedPrices.isEmpty()) {
				response=new ResponseEntity(new CustomError("No product found with given id"),HttpStatus.NO_CONTENT);
			}else {
				Price fetchedPrice=fetchedPrices.get();
				fetchedPrice.setProductId(price.getProductId());
				fetchedPrice.setProductPrice(price.getProductPrice());
				fetchedPrice.setDiscountedPrice(price.getDiscountedPrice());
				outputPriceObj=priceRepo.save(fetchedPrice);
				response=new ResponseEntity<>(outputPriceObj,HttpStatus.OK);
			}
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return (ResponseEntity<Price>) response;
	}

	@Override
	public ResponseEntity<Price> deletePrice(long productId) {
		Price price=new Price();
		price.setProductId(productId);
		ResponseEntity<Price> response=null;
		try {
			Optional<Price> ifPriceExist=priceRepo.findById(productId);
			if(ifPriceExist.isPresent()) {
				priceRepo.delete(price);
				response=new ResponseEntity(new CustomError("Price deleted successfully"),HttpStatus.OK);
			}else {
				response=new ResponseEntity(new CustomError("Price does not exist for given product id"),HttpStatus.NO_CONTENT);
			}
		}catch(Exception e) {
			response=new ResponseEntity(new CustomError(e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	
	@Override
	@HystrixCommand(fallbackMethod="defaultMessage")
	public ResponseEntity<List<Price>> getPromotionPrice(long productId,double productPrice ,double discountedPrice) {
		ResponseEntity<?> response=null;
		List<Price> priceList=new ArrayList();
		try {
			RestTemplate restTemplate=restTemplateBuilder.build();
			InstanceInfo instanceInfo=eurekaClient.getNextServerFromEureka("promotion-service", false);
			String baseUrl=instanceInfo.getHomePageUrl();
			baseUrl=baseUrl+"api/v1/promotion/"+productId;
			ResponseEntity<Promotion[]> resp=restTemplate.getForEntity(baseUrl, Promotion[].class);
			Promotion[] promotions=resp.getBody();
			List<Promotion> promotionsList=new ArrayList<Promotion>();
			promotionsList=Arrays.asList(promotions);
			if(promotionsList.size()>0) {
				ListIterator<Promotion> ltr=promotionsList.listIterator();
				while(ltr.hasNext()) {
					Promotion promotion=ltr.next();
					double discountPercent=promotion.getDiscountPercentage();
					double discountPrice=discountedPrice-(discountedPrice*discountPercent/100);
					Price price=new Price();
					price.setProductId(productId);
					price.setProductPrice(productPrice);
					price.setDiscountedPrice(discountPrice);
					priceList.add(price);
			}
				response=new ResponseEntity(priceList,HttpStatus.OK);
		}
		}catch(Exception e) {
			response=new ResponseEntity(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return (ResponseEntity<List<Price>>) response;
	}
	
	public ResponseEntity<List<Price>> defaultMessage(long productId,double productPrice,double discountedPrice){
		List<Price> priceList=new ArrayList();
		return new ResponseEntity(priceList,HttpStatus.NOT_FOUND);
	}
	
}
