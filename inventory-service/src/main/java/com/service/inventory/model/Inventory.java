package com.service.inventory.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "inventory")
public class Inventory implements Serializable{
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 3406642543294880321L;
	
	@Id
    private long productId;
    private long productAvailNo;	
    
    
	public Inventory() {
		// TODO Auto-generated constructor stub
	}

	public Inventory(long productId, long productAvailNo) {
		this.productId = productId;
		this.productAvailNo = productAvailNo;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getProductAvailNo() {
		return productAvailNo;
	}

	public void setProductAvailNo(long productAvailNo) {
		this.productAvailNo = productAvailNo;
	}

	

	@Override
	public String toString() {
		return "Inventory [productId=" + productId + ", productAvailNo="+ productAvailNo + "]";
	}
    
	
    
}
