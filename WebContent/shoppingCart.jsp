<%@ page language="java" contentType="text/html; charset=windows-1255" import="com.benari.shenkar.coupon.model.CouponCart"
    pageEncoding="windows-1255"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title> Coupon Cart </title>
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
	table{
		width: 600px;
		margin: 0 auto;
		border: 2px solid;
		margin-top: 20px;
		box-shadow: 8px 5px 7px 0px #fff;
	}
	th {
		text-align: center;
	}
	
	td {
		text-align: center;
		border-bottom:1px groove #fff;
	}
	
	.totalPrice{
		font-size: 30px;
		text-decoration: underline;
		text-align: center;
	}
	
	.buyAll{
		padding:10px;
		background-color:orange;
		border:2px solid white;
		cursor:pointer;
		text-align:center;
	}
	
</style>
</head>
<body>
	<div id="wrapper">
		<a style="margin-right:50px;" href="/CouponFinalProject/CouponController/getCoupons"> Click to See All Coupons </a>
		<a href="/CouponFinalProject/CouponController/main"> Back to main menu </a><br>
		<%
			response.setIntHeader("Refresh", 5000);
			CouponCart cart =  (CouponCart)session.getAttribute("cart");
			cart.cartUpdate();
			if(cart != null)	out.println(cart.toHTML());
			
		%>
	</div>
	
</body>
</html>