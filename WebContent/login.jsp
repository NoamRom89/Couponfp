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
	}
	#wrapper{
		width:980px;
		margin:0 auto;
		display:block;
	}
	form{
		width: 260px;
		height: 230px;
		display: block;
		background-color: #B7C3D0;
		margin: 0 auto;
		box-shadow: -5px 5px 5px grey;
	}
	form span{
		font-size:20px;
		margin-left: 6px;
	}
	form > div{
		position:relative;
	}
	input{
		display: block;
		width: 220px;
		padding: 2px;
		margin-bottom: 4px;
		margin: 0 auto;
	}
	input[type="submit"]{
		margin-top:10px;
	}
	.udetails span{
		font-size:13px !important;
	}
</style>
</head>
<body>
	<div id="wrapper">
		<h1> LOGIN </h1>
		<form method="get" action="login">
			<div style="top:26px;"> 
				<span>Username :</span> <input type="text" name="uname">
				<span>Password :</span> <input type="text" name="pword">
				<input type="submit" title="Log in" >
			</div>
			<div class="udetails" style="top: 45px;">
				<div> 
					<span> username: Admin </span>
					<span> password: admin </span>
				</div>
				<div> 
					<span> username: User </span>
					<span> password: user </span>
				</div>
			</div>
		</form>
	</div>
</body>
</html>