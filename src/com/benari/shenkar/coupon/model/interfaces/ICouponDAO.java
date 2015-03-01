package com.benari.shenkar.coupon.model.interfaces;

import java.util.Collection;
import java.util.Date;

import com.benari.shenkar.coupon.model.*;

public interface ICouponDAO {
	public Coupon getCoupon(int id) throws CouponException;
	public void addCoupon(Coupon newCoupon) throws CouponException;
	public boolean updateCoupon(int id, String name, float price, Date dateEnds, int category) throws CouponException;
	public boolean removeCoupon(int id) throws CouponException;
	public Collection<Coupon> getAllCoupons() throws CouponException;
	public Collection<Coupon> getGroupOfCoupons(int groupId) throws CouponException;
}
