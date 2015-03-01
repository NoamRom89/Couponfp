package com.benari.shenkar.coupon.model.interfaces;
import java.util.Collection;

import com.benari.shenkar.coupon.model.*;

public interface ICategoryCouponDAO {
	public Collection<CouponCategory> getAllCategories() throws CouponException;
	//public boolean addCategory(CouponCategory newCategory) throws CouponException;
	//public boolean deleteCategory(int id) throws CouponException;
}
