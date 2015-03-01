package com.benari.shenkar.coupon.model;

public class CouponException extends Exception {
	public CouponException(String msg){
		super("COUPON EXCEPTION : " + msg);
	}
	
	public CouponException(String msg, Throwable cause){
		super("COUPON EXCEPTION : " + msg,cause);
	}
}
