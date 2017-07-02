package com.botcc.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import com.botcc.bean.User;
import com.botcc.services.IUserService;

@Controller
@RequestMapping("/user")
public class loginController {
	@Resource
	IUserService userService;
	@RequestMapping("login")
	public String toIndex(HttpServletRequest request,Model model){  
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("username is: " + username);
        System.out.println("password is: " + password);
        User user = this.userService.getUserByLoginInfo(username, password);
        if(user != null){
            //·µ»ØÂß¼­ÊÓÍ¼Ãû
        	System.out.println(user);
        	model.addAttribute("user", user);
        	return "loginSuccess";
        }
        else{
        	System.out.println(user);
        	return "loginFail";
        } 
    }  
}
