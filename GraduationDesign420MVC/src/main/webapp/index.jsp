<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false" import="java.util.concurrent.TimeUnit" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pleas Login</title>
<script type="text/javascript"> 
	var i = 10; 
	function shownum(){
		if(i==0){
			window.location.href="login.html";
		}
		i=i-1; 
		document.getElementById("countdown").innerHTML="您还未登录，请先登录 ！"+i+"秒后自动跳转登陆界面";
		setTimeout('shownum()',1000);
	}
</script>
 
</head>
<body onload="shownum()">
<h2>Hello</h2>
<div>
	<p id="countdown">
	</p>
</div>
</body>
</html>