package com.nero.reflect.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.nero.reflect.entity.Colom;
import com.nero.reflect.entity.User;

public class UserDaoIpml implements IUserDao {

	private static ThreadLocal<Connection> conn = new ThreadLocal<Connection>() {
		@SuppressWarnings("finally")
		protected synchronized Connection initialValue() {
			Connection conn = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sales_db", "root", "1234");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				return conn;
			}
		};
	};

	@Override
	public User selectUserByPrimaryKey(String id) throws Exception {
		PreparedStatement preparedStatement = conn.get().prepareStatement("select id, userName from user where id = ?");
		preparedStatement.setString(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<User> listUser = getUsersFromSqlResult(resultSet);
		return listUser.size() == 0 ? null: listUser.get(0);
	}

	@Override
	public List<User> selectuAllUser() throws Exception {

		Statement stm = conn.get().createStatement();
		ResultSet resultSet = stm.executeQuery("select id, userName from user");
		return getUsersFromSqlResult(resultSet);
	}

	@Override
	public void updateUser(User user) throws Exception {
		PreparedStatement preparedStatement = conn.get().prepareStatement("insert into user(id, username) values(?, ?)");
		preparedStatement.setString(1, user.getUserID());
		preparedStatement.setString(2, user.getName());
		preparedStatement.execute();
	}

	private User getUserFromSqlResult(ResultSet resultSet) throws Exception {
		User user = new User();
		Class<? extends User> clazz = user.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Colom colom = field.getAnnotation(Colom.class);
			if (colom == null) {
				continue;
			}
			String colomName = colom.name();
			field.setAccessible(true);
			field.set(user, resultSet.getString(colomName));
		}

		return user;

	}

	private List<User> getUsersFromSqlResult(ResultSet resultSet) throws Exception {
		
		List<User> listUser = new ArrayList<User>();
		while (resultSet.next()) {
			listUser.add(getUserFromSqlResult(resultSet));
		}

		return listUser;

	}

}
