<%@ page language="java" contentType="text/html; charset=windows-1255" import="java.util.*, com.benari.shenkar.coupon.model.Coupon, com.benari.shenkar.coupon.model.Users"
    pageEncoding="windows-1255"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title> Coupon Details </title>
<style>
	*{
		padding:0;
		margin:0;	
	}
	body{
		background-color:#B0E0E6;
	}
	a, a:visited, a:link, a:active{
		text-decoration: none;
		font-size:18px;
	}
	#wrapper{
		width:980px;
		margin:0 auto;
		display:block;
	}
	span{
		display:block;
		margin-bottom:10px;
		font-size:23px;
	}
	
</style>
</head>
<body>
	<div id="wrapper"> 
		<a href="/CouponFinalProject/CouponController/main"> Back to main menu </a><br>
		<%
			int quantity = 1;
			Coupon c = (Coupon)request.getAttribute("coupon");
			Users user = (Users)session.getAttribute("currentUser");
			out.println("<div><span>Coupon Id : </span> <span>" + c.getId() + "</span></div>");
			out.println("<div><span>Coupon Name : </span> <span>" + c.getName() + "</span></div>");
			out.println("<div><span>Coupon Price : </span> <span>" + c.getPrice() + "</span></div>");
			out.println("<div><span>Created on : </span> <span>" + c.getDateCreated() + "</span></div>");
			out.println("<div><span>Expires on : </span> <span>" + c.getDateEnds() + "</span></div>");
		%>
		
		<span> Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. </span>
		
		<%
			out.println("<span> <a href=\'/CouponFinalProject/CouponController/shoppingCart?couponId=" + c.getId() + "\'> BUY </a> </span>");	
		%>
	</div>
</body>
</html>