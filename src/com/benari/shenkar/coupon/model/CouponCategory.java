package com.benari.shenkar.coupon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.log4j.Logger;

@Entity
@Table (name="couponcategory")
public class CouponCategory {
	@Id
    @GeneratedValue
    @Column (name="CategoryId")
	private int categoryId;
	@Column (nullable=false, name="CategoryName")
	private String categoryName;
	final static Logger logger = Logger.getLogger(CouponCategory.class);
	
	public CouponCategory() { }
	
	public CouponCategory(int categoryId, String categoryName) {
		super();
		this.setCategoryId(categoryId);
		this.setCategoryName(categoryName);
	}

	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
	
}
