package com.benari.shenkar.coupon.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.Object.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.benari.shenkar.coupon.model.Coupon;
import com.benari.shenkar.coupon.model.CouponCart;
import com.benari.shenkar.coupon.model.CouponCategories;
import com.benari.shenkar.coupon.model.CouponDistance;
import com.benari.shenkar.coupon.model.CouponException;
import com.benari.shenkar.coupon.model.MySQLCouponDAO;
import com.benari.shenkar.coupon.model.UserException;
import com.benari.shenkar.coupon.model.Users;

/**
 * Servlet implementation class CouponController
 **/

@WebServlet("/CouponController/*")
public class CouponController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(CouponController.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CouponController() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init(){
		try {
			CouponCategories.getInstance().setCategories(MySQLCouponDAO.getInstance().getAllCategories());
			getServletContext().setAttribute("CouponCategories", CouponCategories.getInstance());
			getServletContext().setAttribute("CouponsDAO", MySQLCouponDAO.getInstance());
		} catch (CouponException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("deprecation")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter pw = response.getWriter();
		RequestDispatcher dispatcher = null;
		String path = request.getPathInfo();
		System.out.println(path);
		// user is sent in order to log in
		if(path.endsWith("/*")){
			// entering the website for the first time directs you to the login page.
			dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
		}else if(path.endsWith("/login")){
			// gets the values and checks if user is already signed up in the system
			String uname = request.getParameter("uname");
			String pword = request.getParameter("pword");
			try{
				if(uname != null && pword != null){
					// checks if details written correctly
					boolean isConnected = MySQLCouponDAO.getInstance().validateUser(uname, pword);
					if(isConnected){
						// Save cookie - username and hashed password
						HttpSession session = request.getSession();
						Users user =  MySQLCouponDAO.getInstance().getUserDetails(uname);
						session.setAttribute("currentUser", user);
						session.setMaxInactiveInterval(30*60);
						// add current user to cookie
						Cookie loginCookie = new Cookie(Integer.toString(user.getUserId()), user.getUserName());
						loginCookie.setMaxAge(60 * 60);
						response.addCookie(loginCookie);
						dispatcher = getServletContext().getRequestDispatcher("/main.jsp");
						dispatcher.forward(request, response);
					}else{
						dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
						dispatcher.forward(request, response);
					}
				}else{
					// if here, user wants to logout from the system.
					HttpSession session = request.getSession();
					// This will deactivate the session
					session.invalidate();
					dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
					dispatcher.forward(request, response);
				}				
			}catch(UserException e){
				e.printStackTrace();
			}
		}else if(path.endsWith("/main")){
			HttpSession session = request.getSession();
			if(session.getAttribute("currentUser") != null){
				// if user is connected, redirect to main page.
				dispatcher = getServletContext().getRequestDispatcher("/main.jsp");
				dispatcher.forward(request, response);
			}else{
				// else, login again.
				dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
				dispatcher.forward(request, response);
			}
		}else if(path.endsWith("/getCoupons")){
			/**
			 * Get All coupons
			 */
			String couponCategory = request.getParameter("cCategory");
			String xAxis = request.getParameter("x");
			String yAxis = request.getParameter("y");
			if(couponCategory != null && Integer.parseInt(couponCategory) > 0 && xAxis == null && yAxis == null){
				// if here, user wants to get a specific category of coupons
				try {
					request.setAttribute("coupons", MySQLCouponDAO.getInstance().getGroupOfCoupons(Integer.parseInt(couponCategory)));
				} catch (CouponException e) {
					e.printStackTrace();
				}
			}else if(xAxis == null && yAxis == null){
				// if here, get all unexpired coupons
				try {
					request.setAttribute("coupons", MySQLCouponDAO.getInstance().getAllCoupons());
				} catch (CouponException e) {
					e.printStackTrace();
				}
			}else{
				double x = Double.parseDouble(xAxis);
				double y = Double.parseDouble(yAxis);
				try {
					CouponDistance cd = new CouponDistance();
					// Get all coupons order by DISTANCE
					List<Coupon> coupons = (List<Coupon>) MySQLCouponDAO.getInstance().getAllCoupons();
					List<CouponDistance> cdListObject = cd.orderByLocation(coupons, x, y);
					request.setAttribute("couponDistance", cdListObject);
				} catch (CouponException e) {
					e.printStackTrace();
				}
			}
			
			dispatcher = getServletContext().getRequestDispatcher("/getCoupons.jsp");
			dispatcher.forward(request, response);
		}else if(path.endsWith("/couponDetail")){
			// directs to coupon details page with the specific Coupon as an attribute.
			String id = request.getParameter("couponId");	
			if(id != null){
				try {
					request.setAttribute("coupon", MySQLCouponDAO.getInstance().getCoupon(Integer.parseInt(id)));
					dispatcher = getServletContext().getRequestDispatcher("/couponDetail.jsp");
					dispatcher.forward(request, response);
				} catch (NumberFormatException | CouponException e) {
					e.printStackTrace();
				}
			}
		}else if(path.endsWith("/deleteCoupon")){
			// deleting a specific coupon.
			String couponId = request.getParameter("couponId");
			String couponCategory = request.getParameter("cCategory");
			// sets the right attribute to the page.
			try {
				if(couponCategory != null && Integer.parseInt(couponCategory) > 0){
					request.setAttribute("coupons", MySQLCouponDAO.getInstance().getGroupOfCoupons(Integer.parseInt(couponCategory)));
				}else{
					request.setAttribute("coupons", MySQLCouponDAO.getInstance().getAllCoupons());
				}
			} catch (CouponException e) {
				e.printStackTrace();
			}
			// act accordingly to what user is asking for.
			// validate coupon details.
			if(couponId != null && Integer.parseInt(request.getParameter("couponId")) > 0){
				// if here we want to delete a coupon
				try {
					int cId = Integer.parseInt(request.getParameter("couponId"));
					boolean isDeleted = MySQLCouponDAO.getInstance().removeCoupon(cId);
					if(isDeleted){
						request.setAttribute("coupons", MySQLCouponDAO.getInstance().getAllCoupons());
					}
				} catch (CouponException e) {
					e.printStackTrace();
				}
			}
			// if here we just entered the delete coupon section.
			dispatcher = getServletContext().getRequestDispatcher("/deleteCoupon.jsp");
			dispatcher.forward(request, response);
		}else if(path.endsWith("/addCoupon")){
			// if here user wants to add a coupon
			String newCategory = request.getParameter("newCategory");
			String newCName = request.getParameter("newCName");
			String newCPrice = request.getParameter("newCPrice");
			String newCDate = request.getParameter("newCDate");
			String newCTime = request.getParameter("newCTime");
			// validate coupon details. all details needs to be filled correctly.
			if(newCategory!= null && newCName!= null && newCPrice!= null && newCDate!= null && newCTime != null){
				int category = Integer.parseInt(newCategory);
				float price = Float.parseFloat(newCPrice);
				// manipulate Dates and change of format.
				newCDate += " " + newCTime;
				DateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
				try {
					Date res = sdf.parse(newCDate);
					System.out.println("Date : " + res);
					Coupon nc = new Coupon(1,newCName,price,res,category);
					System.out.println(nc);
					MySQLCouponDAO.getInstance().addCoupon(nc);
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (CouponException e) {
					e.printStackTrace();
				}
			}
			
			dispatcher = getServletContext().getRequestDispatcher("/addCoupon.jsp");
			dispatcher.forward(request, response);
		}else if(path.endsWith("/updateCoupon")){
			// if here, user wants to update coupon details.
			String couponCategory = request.getParameter("cCategory");
			String couponId = request.getParameter("idUpdate");
			String nameUpdate = request.getParameter("nameUpdate");
			String priceUpdate = request.getParameter("priceUpdate");
			String dateUpdate = request.getParameter("dateUpdate");
			String timeUpdate = request.getParameter("timeUpdate");
			String categoryUpdate = request.getParameter("categoryUpdate");
			// set the right attribute to the page.
			try {				
				if(couponCategory != null && Integer.parseInt(couponCategory) > 0){
					request.setAttribute("coupons", MySQLCouponDAO.getInstance().getGroupOfCoupons(Integer.parseInt(couponCategory)));
				}else{
					request.setAttribute("coupons", MySQLCouponDAO.getInstance().getAllCoupons());
				}
				// validate all coupon details.
				if(couponId != null && nameUpdate != null && priceUpdate != null && dateUpdate != null && timeUpdate != null && categoryUpdate != null){
					// if here we want to update coupon
					int cId = Integer.parseInt(couponId);
					int cCategory = Integer.parseInt(categoryUpdate);
					float cPrice = Float.parseFloat(priceUpdate);
					// manipulate Dates and change of format.
					dateUpdate += " " + timeUpdate;
					DateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
					Date res = sdf.parse(dateUpdate);
					// send details in order to update coupon
					MySQLCouponDAO.getInstance().updateCoupon(cId,nameUpdate,cPrice,res,cCategory);
				}
				
			} catch (CouponException e) {
				e.printStackTrace();
			}
			// act accordingly to what user is asking for.
			catch (ParseException e) {
				e.printStackTrace();
			}
			
			// if here we just entered the delete coupon section.
			dispatcher = getServletContext().getRequestDispatcher("/updateCoupon.jsp");
			dispatcher.forward(request, response);
		}else if(path.endsWith("/shoppingCart")){
			/**
			 * If here, we face 2 options.
			 * 
			 * 1. 	add product to cart
			 * 		if the parameter couponId is not null we want to add coupon to the cart.
			 * 
			 * 2. 	delete product from cart
			 * 		if the parameter idDelete is not null we want to delete a coupon from the cart.
			 * 
			 */
			String couponIString = request.getParameter("couponId");
			String couponIdForDelete = request.getParameter("idDelete");
			HttpSession session = request.getSession();
			if(session.getAttribute("cart") == null) session.setAttribute("cart", new CouponCart());
			CouponCart cart = (CouponCart)session.getAttribute("cart");
			try {
				if(couponIString != null){
					int couponId = Integer.parseInt(couponIString);
					cart.addProduct((Coupon)MySQLCouponDAO.getInstance().getCoupon(couponId));
				}else if(couponIdForDelete != null){
					int couponIdDelete = Integer.parseInt(couponIdForDelete);
					cart.deleteProduct(couponIdDelete);
				}
				dispatcher = getServletContext().getRequestDispatcher("/shoppingCart.jsp");
				dispatcher.forward(request, response);
			} catch (CouponException e) {
				e.printStackTrace();
			}
		}else if(path.endsWith("/thankyou")){
			/**
			 * 
			 * redirects to a Thank you page in order to purchase all the items.
			 * as well as deletes all the items since user has just bought them.
			 * 
			 */
			dispatcher = getServletContext().getRequestDispatcher("/thankyou.jsp");
			dispatcher.forward(request, response);
		}else if(path.endsWith("/userManagement")){
			// If here,
			// Admin wants to see and manage users.
			// see all users, adding a user and deleteing a user are the options.
			String userIdDelete = request.getParameter("userIdDelete");
			String addUserName = request.getParameter("uname");
			String addUserPassword = request.getParameter("pword");
			if(userIdDelete != null){
				// If here,
				// admin wants to delete a user by userId
				int userId = Integer.parseInt(userIdDelete);
				// validation.
				if(userId > 0){
					// delete user only if delete request has transfered.
					// and only if userId is valid.
					try {
						boolean isDeleted = MySQLCouponDAO.getInstance().deleteUser(userId);
						if(isDeleted){
							System.out.println("Deleted");
						}
					} catch (UserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
			if(addUserName!=null && addUserPassword!=null){
				// if here admin has added a new user by entering username and password
				try {
					MySQLCouponDAO.getInstance().addNewUser(addUserName, addUserPassword);
				} catch (UserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			dispatcher = getServletContext().getRequestDispatcher("/userManagement.jsp");
			dispatcher.forward(request, response);			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
