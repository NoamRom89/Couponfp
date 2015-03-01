package com.benari.shenkar.coupon.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

public class CouponDistance {
	
	private Coupon coupon;
	private Double distance;
	final static Logger logger = Logger.getLogger(CouponDistance.class);
	
	public CouponDistance(Coupon coupon, Double distance) {
		super();
		this.coupon = coupon;
		this.distance = distance;
	}
	
	public CouponDistance() {}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public double distance(double lat1, double lon1, double lat2, double lon2) {
		final int R = 6371; // Radius of the earth in KM
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2)
				* Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.asin(Math.sqrt(a));
		return R * c;
	}
	
	public List<CouponDistance> orderByLocation(List<Coupon> list, double lat,double lon){
		
		if (list != null) {
	
			List<CouponDistance> couponDistances = new ArrayList<CouponDistance>();
	
			for (Coupon coupon : list) {
				Double distance = distance(lat, lon, coupon.getX(),
						coupon.getY());
				couponDistances.add(new CouponDistance(coupon, distance));
			}
	
			Collections.sort(couponDistances, new Comparator<CouponDistance>() {
	
				@Override
				public int compare(CouponDistance o1, CouponDistance o2) {
					if (o1.getDistance() > o2.getDistance()) {
						return 1;
					} else if (o1.getDistance() < o2.getDistance()) {
						return -1;
					} else {
						return 0;
					}
				}
	
			});
			return couponDistances;
		}
		return null;
	}

	@Override
	public String toString() {
		return "CouponDistance [coupon=" + coupon + ", distance=" + distance
				+ "]";
	}
	
	
}