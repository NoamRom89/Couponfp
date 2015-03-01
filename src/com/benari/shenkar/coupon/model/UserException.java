package com.benari.shenkar.coupon.model;

public class UserException extends Exception {
	public UserException(String msg){
		super("USER EXCEPTION : " + msg);
	}
	
	public UserException(String msg, Throwable cause){
		super("USER EXCEPTION : " + msg,cause);
	}
}
