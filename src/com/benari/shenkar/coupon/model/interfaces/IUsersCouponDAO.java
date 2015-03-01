package com.benari.shenkar.coupon.model.interfaces;

import java.util.Collection;

import com.benari.shenkar.coupon.model.UserException;
import com.benari.shenkar.coupon.model.Users;

public interface IUsersCouponDAO {
	public boolean addNewUser(String uname, String pword) throws UserException;
	public boolean validateUser(String uname, String pword) throws UserException;
	public Users getUserDetails(String uname) throws UserException;
	public Collection<Users> getAllUsers() throws UserException;
	public Collection<Users> getAllAdmins() throws UserException;
	public boolean deleteUser(int userId) throws UserException;
}
