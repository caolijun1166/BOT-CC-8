<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>Pleas Login</title>
<!-- jQuery框架 -->
<script src="botcc/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript"> 
	var i = 5; 
	function countDown(){
		if(i==0){
			window.location.href="login.html";
		}
		$("#countdown").text("您还未登录，请先登录 ！"+i+"秒后自动跳转登陆界面");
		i=i-1;
		setTimeout("countDown()",1000);
	}
</script>
 
</head>
<body onload="countDown()">
<h2>Hello there, this is BOT-CC's hint</h2>
<div>
	<p id="countdown"></p>
</div>
</body>
</html>