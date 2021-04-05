package com.wipro.price.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Promotion {
	@Id
	@GeneratedValue
	private int promotionID;
	private String promotionName;
	@Column(unique=true)
	private String promotionCode;
	private float discountPercentage;
	private String description;
	public int getPromotionID() {
		return promotionID;
	}
	public void setPromotionID(int promotionID) {
		this.promotionID = promotionID;
	}
	public String getPromotionName() {
		return promotionName;
	}
	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}
	public String getPromotionCode() {
		return promotionCode;
	}
	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}
	public float getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(float discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Promotion [promotionID=" + promotionID + ", promotionName=" + promotionName + ", promotionCode="
				+ promotionCode + ", discountPercentage=" + discountPercentage + ", description=" + description + "]";
	}
	
	
	

}
