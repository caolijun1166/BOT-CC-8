package com.cn.bot.controllers;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cn.bot.dtos.User;
import com.cn.bot.services.UserService;
import com.cn.bot.util.CodeDict;
import com.cn.bot.util.Constant;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@RequestParam("username")String username,@RequestParam("password")String password,HttpSession session){
		JSONObject resJson = new JSONObject();
		User userExist = userService.queryUserByLoginInfo(username, password);
		if(userExist == null)
			resJson.put(Constant.CONSTANT_STATUS, CodeDict.CODEDICT_ERROR);
		else{
			resJson.put(Constant.CONSTANT_STATUS, CodeDict.CODEDICT_SUCCESS);
			session.setAttribute("isLogin", "true");
			session.setMaxInactiveInterval(120);
		}
		return "loginSuccess";
	}
	
	@RequestMapping(value="regist", method=RequestMethod.POST)
	@ResponseBody
	public JSONObject regist(@RequestParam("mobile")String mobile, @RequestParam("username")String username, @RequestParam("password")String password,@RequestParam("authority")String authority){
		
		JSONObject resJson = new JSONObject();
		boolean isSuccess = userService.insertUser(mobile, username, password, authority);
		if(isSuccess == true){
			resJson.put(Constant.CONSTANT_STATUS, CodeDict.CODEDICT_SUCCESS);
			return resJson;
		}
		else{
			resJson.put(Constant.CONSTANT_STATUS, CodeDict.CODEDICT_ERROR);
			return resJson;
		}
		
	}
}
