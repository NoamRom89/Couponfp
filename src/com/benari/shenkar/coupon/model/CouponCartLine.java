package com.benari.shenkar.coupon.model;

import org.apache.log4j.Logger;

public class CouponCartLine {
	private Coupon coupon;
	private int quantity;
	final static Logger logger = Logger.getLogger(CouponCartLine.class);
	
	public CouponCartLine(Coupon coupon, int quantity) {
		super();
		this.coupon = coupon;
		this.quantity = quantity;
	}
	
	public Coupon getCoupon() {
		return coupon;
	}
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
