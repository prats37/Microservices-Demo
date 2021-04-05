package com.wipro.price.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.wipro.price.entity.Price;

public interface PriceService {

	ResponseEntity<Price> addPrice(@Valid Price price);

	ResponseEntity<Price> viewPrice(@Valid long productId);

	ResponseEntity<Price> updatePrice(@Valid Price price);

	ResponseEntity<Price> deletePrice(@Valid long productId);

	ResponseEntity<List<Price>> getPromotionPrice(long productId, double productPrice, double discountedPrice);

}
