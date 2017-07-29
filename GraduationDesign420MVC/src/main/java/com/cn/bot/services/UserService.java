package com.cn.bot.services;

import com.cn.bot.dtos.User;

public interface UserService {
	public User queryUserByLoginInfo(String username, String password);
}
