package com.cn.bot.services;

import com.cn.bot.dtos.User;

public interface UserService {
	//�����û����������ѯ�û�
	public User queryUserByLoginInfo(String username, String password);
	
	//���ֻ����û��������롢�û�Ȩ��д�����ݿ�, ����ɹ��ķ���ֵΪ��¼������
	public boolean insertUser(String mobile, String username, String password, String authority);
}
