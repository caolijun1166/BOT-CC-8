package com.botcc.services;

import com.botcc.bean.User;

public interface IUserService {
	public User getUserByLoginInfo(String username, String password);
}
