package com.wipro.product.entity;

import javax.persistence.Transient;

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

	@Override
	public String toString() {
		return "Price [productId=" + productId + ", productPrice=" + productPrice + ", discountedPrice="
				+ discountedPrice + "]";
	}
	
	
	
	
	

}
