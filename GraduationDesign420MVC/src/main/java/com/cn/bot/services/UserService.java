package com.cn.bot.services;

import com.cn.bot.dtos.User;

public interface UserService {
	//根据用户名、密码查询用户
	public User queryUserByLoginInfo(String username, String password);
	
	//将手机、用户名、密码、用户权限写入数据库, 插入成功的返回值为记录的主键
	public boolean insertUser(String mobile, String username, String password, String authority);
}
