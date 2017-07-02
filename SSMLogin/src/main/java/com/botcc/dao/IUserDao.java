package com.botcc.dao;

import org.apache.ibatis.annotations.Param;

import com.botcc.bean.User;

public interface IUserDao {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);
    
    User selectByLoginInfo(@Param("username")String username, @Param("password")String password);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}