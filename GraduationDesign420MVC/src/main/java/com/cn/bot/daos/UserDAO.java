package com.cn.bot.daos;

import org.apache.ibatis.annotations.Param;

import com.cn.bot.dtos.User;

public interface UserDAO {
	//��¼
	public User queryUserByLoginInfo(@Param("username")String username, @Param("password")String password);
	//ע��
}
