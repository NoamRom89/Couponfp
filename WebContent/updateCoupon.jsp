<%@page import="com.sun.xml.internal.txw2.Document"%>
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
	.clear{
		clear:both;
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
	.updateCouponLink{
		cursor:pointer;
		padding:5px;
		background-color:orange;
		border:2px solid white;
		text-align:center;
	}
	.no-products{
		font-size: 25px;
		text-align: center;
		margin-top: 35px;
	}
	.inputUpdate input, .inputUpdate form, .inputUpdate select{
		float:left;
	}
	input,select {
		margin-right:5px;
	}
</style>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script>
	
	function updateCoupon(id,name,price,dateEnd,category){
		$('#updateName').val(name);
		$('#updatePrice').val(price);
		// manipulate Date
		var nd = new Date(dateEnd);
		var d = nd.getDate();
		var m = nd.getMonth() + 1;
		var y = nd.getFullYear();
		var hour = nd.getHours();
		var minute = nd.getMinutes();
		if(m < 10){
			m = '0' + m;	
		}
		if(d < 10){
			d = '0' + d;
		}
		var fullDate = y + "-" + m + "-" + d; 
		$('#updateDate').val(fullDate);
		// manipulate Hour
		if(hour < 10){
			hour = '0' + hour;	
		}
		if(minute < 10){
			minute = '0' + minute;
		}
		var fullTime = hour + ":" + minute;
		$('#updateTime').val(fullTime);
		console.log(category);
		$('#couponForm > select').val(category);
		$('#updateId').val(id);
	}

</script>

</head>
<body>
	<div id="wrapper">
		<a href="/CouponFinalProject/CouponController/main"> Back to main menu </a><br>
		<div class="inputUpdate">
			<form id="couponForm" action="updateCoupon" method="get">
				<input id="updateId" type="hidden" name="idUpdate">
				<input id="updateName" type="text" name="nameUpdate">
				<input id="updatePrice" type="text" name="priceUpdate">
				<input id="updateDate" type="date" name="dateUpdate">
				<input id="updateTime" type="time" name="timeUpdate">
				<%
					
					CouponCategories categories = (CouponCategories)application.getAttribute("CouponCategories");
					String HTMLCouponUpdate = categories.toSelectOnlyHTML("categoryUpdate");
					out.println(HTMLCouponUpdate);
				%>
				<input type="submit" value="Update Coupon">
			</form>
		
		</div>
		<div class="clear"></div>
			<%
				String HTMLSelect = categories.toSelectHTML("updateCoupon");
				out.println(HTMLSelect);
				Collection<Coupon> couponList = (Collection<Coupon>)request.getAttribute("coupons");
				if(!couponList.isEmpty()){
					Iterator it = couponList.iterator();
					out.println("<table>");
					out.println("<tr> <th>Id</th> <th>Name</th> <th>Price</th> <th>Date Expires</th> <th>Category</th> <th>Update</th> </tr>");
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
						<td> <span class="updateCouponLink" onclick="updateCoupon(<%= c.getId() %>,'<%= c.getName() %>',<%= c.getPrice() %>,'<%= (Date)c.getDateEnds() %>',<%= c.getCategoryNumber() %>)"> Update </span> </td>
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