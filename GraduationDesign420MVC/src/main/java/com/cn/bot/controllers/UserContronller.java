package com.cn.bot.controllers;

import javax.annotation.Resource;

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
public class UserContronller {
	@Resource
	private UserService userService;
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public JSONObject login(@RequestParam("username")String username,@RequestParam("password")String password){
		JSONObject resJson = new JSONObject();
		User userExist = userService.queryUserByLoginInfo(username, password);
		if(userExist == null)
			resJson.put(Constant.CONSTANT_STATUS, CodeDict.CODEDICT_ERROR);
		else
			resJson.put(Constant.CONSTANT_STATUS, CodeDict.CODEDICT_SUCCESS);
		return resJson;
	}
}
