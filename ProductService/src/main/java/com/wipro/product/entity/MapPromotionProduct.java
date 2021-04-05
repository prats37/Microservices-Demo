package com.wipro.product.entity;

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
public class MapPromotionProduct {
	
	private int promotionID;
	private long productId;
	@Override
	public String toString() {
		return "Promotion [promotionID=" + promotionID + ", productId=" + productId + "]";
	}

}
