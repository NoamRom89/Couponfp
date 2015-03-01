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
	table{
		width:100%;
	}
	td,th{
		width: 100px;
		text-align: center;
		color: #151515;
		padding: 4px;
	}
	td{
		font-size: 17px;
	}
	th{
		font-size: 19px;
		font-weight:600;
	}
	.no-products{
		font-size: 25px;
		text-align: center;
		margin-top: 35px;
	}
	.deleteCouponLink{
		cursor:pointer;
		padding:5px;
		background-color:orange;
		border:2px solid white;
		text-align:center;
	}
</style>
</head>
<body>
	<div id="wrapper">
		<a href="/CouponFinalProject/CouponController/main"> Back to main menu </a><br>
		<%
			CouponCategories categories = (CouponCategories)application.getAttribute("CouponCategories");
			String HTMLSelect = categories.toSelectHTML("deleteCoupon");
			out.println(HTMLSelect);
			Collection<Coupon> couponList = (Collection<Coupon>)request.getAttribute("coupons");
			if(!couponList.isEmpty()){
				Iterator it = couponList.iterator();
				out.println("<table>");
				out.println("<tr> <th>Id</th> <th>Name</th> <th>Price</th> <th>Date Expires</th> <th>Category</th> <th>Delete</th> </tr>");
				while(it.hasNext()){
					Coupon c = (Coupon)it.next();
					if( !(c.getDateEnds().before(new Date())) ){
		%>	
					<tr>
					<td> <%= c.getId() %> </td>
					<td> <%= c.getName() %> </td>
					<td> <%= c.getPrice() %> </td>
					<td> <%= c.getDateEnds() %> </td>
					<td> <%= categories.getCategoryName(c.getCategoryNumber()) %> </td>
					<td> <a class="deleteCouponLink" href="/CouponFinalProject/CouponController/deleteCoupon?couponId=<%= c.getId() %>"> Delete </a> </td>
					</tr>
				
		<%	
					}
				}
				out.println("</table>");	
			}else{
				out.println("<p class='no-products'> No Products To Display </p>");
			}
			
		%>	
	</div>
</body>
</html>