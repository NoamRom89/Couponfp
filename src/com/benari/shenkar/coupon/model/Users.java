package com.benari.shenkar.coupon.model;

import javax.persistence.*;

import org.apache.log4j.Logger;

@Entity
@Table (name="users")
public class Users {
	@Id
    @GeneratedValue
    @Column (name="UserId")
	private int userId;
	@Column (nullable=false, name="UserName")
	private String userName;
	@Column (nullable=false, name="UserPassword")
	private String userPassword;
	@Column (nullable=false, name="IsAdmin")
	private boolean isAdmin;
	final static Logger logger = Logger.getLogger(Users.class);
	
	public Users(int userId, String userName, String userPassword,
			boolean isAdmin) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.isAdmin = isAdmin;
	}

	public Users(){}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
		
	
}
