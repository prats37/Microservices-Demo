package com.wipro.product.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
@Table(name="product")
@SequenceGenerator(name="prodId",sequenceName="productIdGenerator",initialValue=1000)
public class Product implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6438733872666733631L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="prodId")
	@Column(name="product_id",unique=true,nullable=false)
	private long productId;
	@Column(name="product_name",unique=true,nullable=false)
	private String productName;
	@Column(name="category")
	private String productCategory;
	@Column(name="mfg")
	private String manufacturingDate;
	@Column(name="best_before")
	private String bestBefore;
	@Column(name="prod_desc")
	private String productDescription;
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productCategory=" + productCategory
				+ ", manufacturingDate=" + manufacturingDate + ", bestBefore=" + bestBefore + ", productDescription="
				+ productDescription + "]";
	}
	
	
	
	

}
