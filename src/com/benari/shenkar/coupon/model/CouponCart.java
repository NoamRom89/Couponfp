package com.benari.shenkar.coupon.model;

import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class CouponCart {
	private Map<Coupon,CouponCartLine> lines = new Hashtable<Coupon,CouponCartLine>();
	final static Logger logger = Logger.getLogger(CouponCart.class);
	
	public void addProduct(Coupon coupon) {
		if(lines.containsKey(coupon)) {
			//a shopping cart line for this product already exists
			CouponCartLine line = lines.get(coupon);
			line.setQuantity(line.getQuantity()+1);			
		}
		else {
			//we need to create a new shopping cart line
			CouponCartLine line = new CouponCartLine(coupon, 1);
			lines.put(coupon, line);
		}
			
	}
	
	public void deleteProduct(int couponId){
		for (Coupon key : lines.keySet()) {
		    if (key.getId() == couponId) {
		        lines.remove(key);
		    }
		}
	}
	
	public void deleteAllProducts(){
		lines.clear();
	}
	
	public void cartUpdate(){
		for(Coupon key : lines.keySet()){
			if(key.getDateEnds().before(new Date())){
				lines.remove(key);
			}
		}
	}
	
	public double getTotal() {
		//Collection<ShoppingCartLine> shoppingCartLines = this.lines.values();
		
		//Iterator<ShoppingCartLine> iterator = shoppingCartLines.iterator();
		return 0;
	}
	
	public String toHTML() {
		// Make HTML element through String.
		StringBuffer sb = new StringBuffer();
		float fullPrice = 0;
		
		Collection<CouponCartLine> couponLines = this.lines.values();
		if(couponLines.size() <= 0){
			return "<p class='no-products'> No Products To Display </p>";
		}
		Iterator<CouponCartLine> iterator = couponLines.iterator();
		sb.append("<table>");
		sb.append("<tr><th>product</th><th>quantity</th><th>price per item</th><th>total</th><th>Delete from cart</th></tr>");
		while(iterator.hasNext()){
			CouponCartLine line = iterator.next();
			int quantity = line.getQuantity();
			double price = line.getCoupon().getPrice();
			sb.append("<tr>");
			sb.append("<td>" + line.getCoupon().getName() + "</td>");
			sb.append("<td>" + quantity + "</td>");
			sb.append("<td>" + price + "</td>");
			sb.append("<td>" + price * quantity + "</td>");
			sb.append("<td> <a href='shoppingCart?idDelete="+ line.getCoupon().getId() +"'> delete </a> </td>");
			sb.append("</tr>");
			fullPrice += price * quantity;
		}
		
		sb.append("</table><br><br><div class='totalPrice'> Total Price : "+ fullPrice +"$ </div> <br><br><br> <div class='buyAll'><a href='thankyou'> BUY All Products! </a></div> ");
		
		return sb.toString();
	}	
}
