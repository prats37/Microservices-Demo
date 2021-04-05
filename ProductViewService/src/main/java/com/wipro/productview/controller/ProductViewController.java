package com.wipro.productview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.productview.model.ProductView;
import com.wipro.productview.service.ProductViewService;
import com.wipro.productview.utility.CustomError;

@RestController
@RequestMapping("/product/view")
public class ProductViewController {
	
	@Value("${server.port}")
	String serverPort;
	
	@Autowired
	private ProductViewService productViewServ;
	
	@GetMapping("/byName/{productName}")
	public ResponseEntity<ProductView> getProductByName(@PathVariable String productName){
		ResponseEntity<ProductView> response=new ResponseEntity(new CustomError("Request Not Processed"),HttpStatus.NOT_IMPLEMENTED);
		response=productViewServ.viewProducts(productName);
		return response;
	}

}
