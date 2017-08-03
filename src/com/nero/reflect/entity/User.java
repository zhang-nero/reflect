package com.nero.reflect.entity;

@Table(name = "user")
public class User {
	@Colom(name = "ID")
	private String userID;
	@Colom(name = "userName")
	private String name;

	public String getUserID() {
		return userID;
	}

	public String getName() {
		return name;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User [ID=" + userID + ", name=" + name + "]";
	}

}
