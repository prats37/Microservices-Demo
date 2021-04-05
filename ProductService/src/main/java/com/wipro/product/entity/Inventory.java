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
public class Inventory {
	
	private long productId;
	private long productAvailNo;
	@Override
	public String toString() {
		return "Inventory [productId=" + productId + ", productAvailNo=" + productAvailNo + "]";
	}
	
	

}
