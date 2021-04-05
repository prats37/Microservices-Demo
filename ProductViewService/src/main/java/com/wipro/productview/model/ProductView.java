package com.wipro.productview.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductView {
	
	private long productId;
	private String productName;
	private String productCategory;
	private String productMfg;
	private String bestBefore;
	private String productDescription;
	private double productPrice;
	private double regularDiscountedPrice;
	private List<Price> promotionsAvailable;
	private long stockOfProduct;

}
