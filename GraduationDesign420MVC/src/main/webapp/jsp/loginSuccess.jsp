<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Success !</title>
<script type="text/javascript"> 
	var i = 5; 
	function countDown(){
		if(i==1){
			window.location.href="../history.html";
		} 
		document.getElementById("countdown").innerHTML=i+"秒后自动跳转到实时温湿度页面";
		i=i-1;
		setTimeout("countDown()",1000);
	}
</script>
</head>
<body onload="countDown()">
<h1>Login Success ! Welcome,${user.username}</h1>
<p id="countdown"></p>
</body>
</html>