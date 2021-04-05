package com.wipro.promotion.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="MapPromotionProduct")
public class MapPromotionProduct {
	@Id
	private int promotionID;
	private long productId;
	@Override
	public String toString() {
		return "Promotion [promotionID=" + promotionID + ", productId=" + productId + "]";
	}
	
	
}
