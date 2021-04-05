package com.wipro.productview.model;


import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Price {
	
	private long productId;
	private double productPrice;
	private double discountedPrice;
	private String promotionApplied;

	@Override
	public String toString() {
		return "Price [productId=" + productId + ", productPrice=" + productPrice + ", discountedPrice="
				+ discountedPrice + ", promotionApplied=" + promotionApplied + "]";
	}
	
	
	
	
	

}
