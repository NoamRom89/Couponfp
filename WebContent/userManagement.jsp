<%@ page language="java" contentType="text/html; charset=windows-1255" import="java.util.*, com.benari.shenkar.coupon.model.*"
    pageEncoding="windows-1255"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>Insert title here</title>
<span>
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
	td{
		width:80px;
		height:80px;
		text-align:center;
	}
	.add-user{
		margin-top:20px;
	}
	.deleteUser{
		cursor:pointer;
		padding:5px;
		background-color:orange;
		border:2px solid white;
		text-align:center;
	}
</style>
</span>
</head>
<body>
	<div id="wrapper">
	
		<a href="/CouponFinalProject/CouponController/main"> Back to main menu </a><br>
		<div style="margin-top:15px;">
			<h1> User Management </h1>
			<%
				MySQLCouponDAO DAO = (MySQLCouponDAO)application.getAttribute("CouponsDAO");
				Collection<Users> usersList = DAO.getAllUsers();
				if(!usersList.isEmpty()){
					Iterator it = usersList.iterator();
					out.println("<table style='margin-top:15px;'>");
					out.println("<tr> <th>UserId</th> <th>UserName</th> <th>Delete</th> </tr>");
					while(it.hasNext()){
						Users u = (Users)it.next();
			%>	
						<tr>
						<td> <%= u.getUserId() %> </td>
						<td> <%= u.getUserName() %> </td>
						<td> <a class="deleteUser" href="/CouponFinalProject/CouponController/userManagement?userIdDelete=<%= u.getUserId() %>"> Delete </a> </td>
						</tr>
					
			<%	
					}
					out.println("</table>");	
				}else{
					out.println("<p class='no-products'> No Users To Display </p>");
				}
			%>
		</div>
		<div class="add-user">
			<h2> Add User </h2>
			<form action="userManagement" method="get">
				Username <input type="text" name="uname">
				Password <input type="text" name="pword">
				<input type="submit" value="Add User">
			</form>
		</div>
		
	</div>
</body>
</html>