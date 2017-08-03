package com.nero.reflect.dao;

import java.util.List;

import com.nero.reflect.entity.User;

public interface IUserDao {
	User selectUserByPrimaryKey(String id) throws Exception;
	List<User> selectuAllUser() throws Exception;
	void updateUser(User user) throws Exception;
}
