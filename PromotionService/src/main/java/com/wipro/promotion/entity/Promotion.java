package com.wipro.promotion.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name="promotion")
public class Promotion {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
