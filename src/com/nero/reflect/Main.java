package com.nero.reflect;

import java.util.List;

import com.nero.reflect.dao.IUserDao;
import com.nero.reflect.dao.UserDaoIpml;
import com.nero.reflect.entity.User;

public class Main {

	public static void main(String[] args) throws Exception {
		IUserDao userDao = new UserDaoIpml();
		
		User user = new User();
		user.setUserID("00004");
		user.setName("ddddd");
		userDao.updateUser(user);
		
		List<User> li = userDao.selectuAllUser();
		System.out.println(li.toString());
	}
}
