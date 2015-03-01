package com.benari.shenkar.coupon.model;

import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class CouponCategories {
	private static CouponCategories instance = null;
	private Collection<CouponCategory> categories;
	final static Logger logger = Logger.getLogger(CouponCategories.class);
	
	private CouponCategories(){}
	
	public static CouponCategories getInstance(){
		if(instance == null) instance = new CouponCategories();
		return instance;
	}

	public Collection<CouponCategory> getCategories() {
		return categories;
	}

	public void setCategories(Collection<CouponCategory> categories) {
		this.categories = categories;
	}
	
	public String getCategoryName(int cnum){
		Iterator<CouponCategory> coupon = categories.iterator();
		while(coupon.hasNext()){
			CouponCategory cc = coupon.next();
			if(cc.getCategoryId() == cnum) return cc.getCategoryName();
		}
		return null;
	}
	
	public String toSelectHTML(String action){
		StringBuffer sb = new StringBuffer();
		
		if(!categories.isEmpty()){
			Iterator cit = categories.iterator();
			sb.append("<form action='"+ action +"' method='get'>");
			sb.append("<select name='cCategory'>");
			sb.append("<option value = " + 0 + "> All Coupons");
			while(cit.hasNext()){
				CouponCategory cc = (CouponCategory)cit.next();
				sb.append("<option value = " + cc.getCategoryId() + ">" + cc.getCategoryName());
			}
			sb.append("</select>");
			sb.append("<input type='submit' value='Get Coupons'>");
			sb.append("</form>");
		}
		
		return sb.toString();
		
	}
	
	public String toSelectOnlyHTML(String paramName){
		StringBuffer sb = new StringBuffer();
		
		if(!categories.isEmpty()){
			Iterator cit = categories.iterator();
			sb.append("<select name='" + paramName + "'>");
			//sb.append("<option value = " + 0 + "> All Coupons");
			while(cit.hasNext()){
				CouponCategory cc = (CouponCategory)cit.next();
				sb.append("<option value = " + cc.getCategoryId() + ">" + cc.getCategoryName());
			}
			sb.append("</select>");
		}
		
		return sb.toString();
		
	}
}
