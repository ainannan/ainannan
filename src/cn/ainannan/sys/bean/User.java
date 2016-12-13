package cn.ainannan.sys.bean;

import javax.persistence.Entity;

@Entity
public class User {
	private int id;
	private String userName;
	private String password;
	private int state;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public User(int id, String userName, String password, int state) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.state = state;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

}
