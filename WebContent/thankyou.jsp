<%@ page language="java" contentType="text/html; charset=windows-1255" import="com.benari.shenkar.coupon.model.CouponCart"
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
	.no-products{
		font-size: 25px;
		text-align: center;
		margin-top: 35px;
	}
	
	
</style>
</head>
<body>
<div id="wrapper">
		<a href="/CouponFinalProject/CouponController/main"> Back to main menu </a><br>
		<span> Thank you! </span>
		<%
			CouponCart cart =  (CouponCart)session.getAttribute("cart");
			cart.deleteAllProducts();
		%>
	</div>
</body>
</html>