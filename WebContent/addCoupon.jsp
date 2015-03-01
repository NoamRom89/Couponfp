<%@ page language="java" contentType="text/html; charset=windows-1255" import="java.util.*, com.benari.shenkar.coupon.model.*"
    pageEncoding="windows-1255"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>Insert title here</title>
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
	h1{
		margin-bottom:20px;
	}
	form{
		width: 320px;
		background: cadetblue;
		border: 2px dotted;
		padding: 50px;	
	}
	input,span{
		display: block;
	}
	input[type=submit]{
		margin-top:20px;
	}
	
</style>
</head>
<body>
	<div id="wrapper">
		<a href="/CouponFinalProject/CouponController/main"> Back to main menu </a><br>
		<h1> Add Product </h1>
		<form id="couponForm" action="addCoupon" method="get">
				<span>Coupon Name </span> <input id="cName" type="text" name="newCName">
				<span>Coupon Price </span><input id="cPrice" type="text" name="newCPrice">
				<br><br>
				<span>Coupon Date Expiration </span><input id="cDate" type="date" name="newCDate">
				<span>Coupon Time Expires </span> <input id="cTime" type="time" name="newCTime">
				<span>Coupon Category </span>
				<%
					CouponCategories categories = (CouponCategories)application.getAttribute("CouponCategories");
					String HTMLCouponUpdate = categories.toSelectOnlyHTML("newCategory");
					out.println(HTMLCouponUpdate);
				%>
				<input type="submit" value="Add Coupon">
			</form>
	</div>
</body>
</html>