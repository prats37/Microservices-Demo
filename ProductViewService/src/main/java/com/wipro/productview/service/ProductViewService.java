package com.wipro.productview.service;

import org.springframework.http.ResponseEntity;

import com.wipro.productview.model.ProductView;

public interface ProductViewService {

	ResponseEntity<ProductView> viewProducts(String productName);

}
