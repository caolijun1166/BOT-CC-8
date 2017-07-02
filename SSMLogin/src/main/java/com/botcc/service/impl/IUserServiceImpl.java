package com.botcc.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.botcc.bean.User;
import com.botcc.dao.IUserDao;
import com.botcc.services.IUserService;
@Service("userService")
public class IUserServiceImpl implements IUserService{
	@Resource
	private IUserDao userDao;
	public User getUserByLoginInfo(String username, String password){
		return this.userDao.selectByLoginInfo(username, password);
	}
}
