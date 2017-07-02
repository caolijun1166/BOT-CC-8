 function checkusername() {
            if (document.getElementById("username").value.length == 0) {
                document.getElementById("username_help").innerHTML = "Please enter your username";
                return false;
            }
            else
            {
                document.getElementById("username_help").innerHTML = "";
                return true;
            }
        }
 
 function checkpassword() {
     if (document.getElementById("password").value.length == 0) {
         document.getElementById("password_help").innerHTML = "Please enter your password";
         return false;
     }
     else
     {
         document.getElementById("password_help").innerHTML = "";
         return true;
     }
 }

 function checkpassword2() {
     if (document.getElementById("password2").value != document.getElementById("password").value) {
        document.getElementById("password2_help").innerHTML = "password not match"
        return false;
     }
     else
     {
         document.getElementById("password2_help").innerHTML = "";
         return true;
     }
 }

 function checklogin(form){

    /*调试语句：
    alert("username: "+form.elements["username"]);
    alert("password: "+form.elements["password"]);*/
    
    /*思考：
     为什么使用alert(form.elements[0]或alert(form.elements["username_help"]
     获取不到<span>*/

    /*调试语句：
    alert("username_help: "+document.getElementById('username_help'));
    alert("password_help: "+document.getElementById('password_help'));*/
    var username = checkusername();
    var password = checkpassword();
    /*调试语句：
    alert("username: "+username);
    alert("password_help: "+password);*/
    if(username&&password)
    	{
    		check();
    		form.submit();
    	}
    else
        alert("请输入用户名或密码！");

 }
 
function checkregister(form) {
    var username = checkusername();
    var password = checkpassword();
    var password2 = checkpassword2();
    if(username&&password&&password2)
        form.submit();
    else if (!password2) {
        alert("两次输入密码不一致！");
    }
    else
        alert("请输入用户名或密码！");
}

 function check()
 {
    var isChecked = document.getElementById("remember").checked;
    var username = document.getElementById("username").value;
	 if(isChecked){
//		 alert("被选中了+"+username);
		 writeCookie("username", username, 30);
//		 alert("write cookie success!");
	 }
	 else
	{
//		 alert("取消选中");
		 eraseCookie("username");
//		 alert("erase cookie success!");
	}
 }
 
 function show_username(){
    if(readCookie("username") != null){
//        alert(readCookie("username")+" in cookies");
        document.getElementById("username").value = readCookie("username");
        document.getElementById("remember").checked = true;
    }
 }

 function init(){
	 show_username();
 }