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
public class Product{
	private long productId;
	private String productName;
	private String productCategory;
	private String manufacturingDate;
	private String bestBefore;
	private String productDescription;
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productCategory=" + productCategory
				+ ", manufacturingDate=" + manufacturingDate + ", bestBefore=" + bestBefore + ", productDescription="
				+ productDescription + "]";
	}
	
	
	
	

}
