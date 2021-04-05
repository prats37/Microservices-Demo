package com.wipro.product.entity;

import javax.persistence.Column;

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
public class Promotion {
	
	private int promotionID;
	private String promotionName;
	@Column(unique=true)
	private String promotionCode;
	private float discountPercentage;
	private String description;
	@Override
	public String toString() {
		return "Promotion [promotionID=" + promotionID + ", promotionName=" + promotionName + ", promotionCode="
				+ promotionCode + ", discountPercentage=" + discountPercentage + ", description=" + description + "]";
	}

}
