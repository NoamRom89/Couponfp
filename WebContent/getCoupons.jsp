<%@ page language="java" contentType="text/html; charset=windows-1255" import="java.util.*, com.benari.shenkar.coupon.model.*"
    pageEncoding="windows-1255"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title> Main </title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
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
	
</style>
<script>

function getLocation() {
    if (navigator.geolocation) {
    	navigator.geolocation.watchPosition(showPosition);
		// add a form and send to getCoupons the longtitde and altitude to getCoupons
	} else { 
		// x.innerHTML = "Geolocation is not supported by this browser.";
	}
}

function showPosition(position) {
	var xAxis = position.coords.latitude;
	var yAxis = position.coords.longitude;
	var form = $(document.createElement('form'));
    $(form).attr("action", "getCoupons");
    $(form).attr("method", "GET");

    var input = $("<input>").attr("type", "hidden").attr("name", "x").val(xAxis);
    $(form).append($(input));
    var input = $("<input>").attr("type", "hidden").attr("name", "y").val(yAxis);
    $(form).append($(input));
    $(form).submit();
	console.log("here");
}
</script>
</head>
<body>
	<div id="wrapper">
		<a href="/CouponFinalProject/CouponController/main"> Back to main menu </a><br>
		<button onclick='getLocation()'> Order by Geolocation </button>
		<%
			response.setIntHeader("Refresh", 5000);
			CouponCategories categories = (CouponCategories)application.getAttribute("CouponCategories");
			String HTMLSelect = categories.toSelectHTML("getCoupons");
			out.println(HTMLSelect);
			Collection<Coupon> couponList = (Collection<Coupon>)request.getAttribute("coupons");
			List<CouponDistance> couponDistanceList = (List<CouponDistance>)request.getAttribute("couponDistance");
			System.out.println(couponDistanceList + " " + couponList);
			if(couponDistanceList != null && !couponDistanceList.isEmpty()){
				Iterator it = couponDistanceList.iterator();
				out.println("<table>");
				out.println("<tr> <th>Id</th> <th>Name</th> <th>Price</th> <th>Date Expires</th> <th>Category</th> <th> distance </th> <th>Details</th> </tr>");
				while(it.hasNext()){
					CouponDistance cd = (CouponDistance)it.next();
					Coupon c = cd.getCoupon();
					if( !(c.getDateEnds().before(new Date())) ){
			%>
						<tr>
							<td> <%= c.getId() %> </td>
							<td> <%= c.getName() %> </td>
							<td> <%= c.getPrice() %> </td>
							<td> <%= c.getDateEnds() %> </td>
							<td> <%= categories.getCategoryName(c.getCategoryNumber()) %> </td>
							<td> <%= cd.getDistance() %> </td>
							<td> <a href="/CouponFinalProject/CouponController/couponDetail?couponId=<%= c.getId() %>"> Details </a> </td>
						</tr>
			<%
					}
				}
				out.println("</table>");
				
			}else if(couponList != null && !couponList.isEmpty()){
				Iterator it = couponList.iterator();
				out.println("<table>");
				out.println("<tr> <th>Id</th> <th>Name</th> <th>Price</th> <th>Date Expires</th> <th>Category</th> <th>Details</th> </tr>");
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
						<td> <a href="/CouponFinalProject/CouponController/couponDetail?couponId=<%= c.getId() %>"> Details </a> </td>
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