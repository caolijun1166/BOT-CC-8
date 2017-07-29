package com.cn.bot.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.bot.daos.UserDAO;
import com.cn.bot.dtos.User;
import com.cn.bot.services.UserService;

@Service
public class UserServicesImpl implements UserService {
	@Resource
	private UserDAO userDAO;
	public User queryUserByLoginInfo(String username, String password){
		return userDAO.queryUserByLoginInfo(username, password);
	}
}