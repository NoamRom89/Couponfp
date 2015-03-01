<%@ page language="java" contentType="text/html; charset=windows-1255" import="java.util.*, com.benari.shenkar.coupon.model.*"
    pageEncoding="windows-1255"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>Insert title here</title>
</head>
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
	}
	#wrapper{
		width:980px;
		margin:0 auto;
		display:block;
	}
	ul{
		list-style-type: none;
	}
	ul li{
		width: 100%;
		border-width: 1px;
		border-style: outset;
		padding: 13px;
		background-color: #50A6C2;
		text-align: center;
	}
	ul li:hover{
		background-color: #009ACD;
		border-style: inset;
	}
	ul li a{
		font-size:25px;
		color:#151515;
	}
	
	
	

</style>
<body>
	<div id="wrapper">
		<%
			Users user = (Users)session.getAttribute("currentUser");
		%>
		<div> Hello <% out.println(user.getUserName()); %>, Have a wonderful day. <a href="login"> Logout </a>  </div> 
		<header>
			<h1> Coupon Management </h1>
			<nav style="margin-top:20px;">
				<ul>
					<li> <a href="getCoupons"> Get Coupons </a>
					
				<% 
					
					if(user.getIsAdmin()){
						out.println("<li> <a href='addCoupon'> Add Coupon </a>");
						out.println("<li> <a href='updateCoupon'> Update Coupon </a>");
						out.println("<li> <a href='deleteCoupon'> Delete Coupon </a>");
						out.println("<li> <a href='userManagement'> User Management </a>");
					}
				%>
					<li> <a href="shoppingCart"> Shopping Cart </a>
				</ul>
			</nav>
		</header>
	
	</div>
</body>
</html>