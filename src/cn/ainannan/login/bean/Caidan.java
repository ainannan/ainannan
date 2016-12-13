package cn.ainannan.login.bean;

import javax.persistence.Entity;

@Entity
public class Caidan {
	private int id;
	private String name;
	private String url;
	private int clickRate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getClickRate() {
		return clickRate;
	}

	public void setClickRate(int clickRate) {
		this.clickRate = clickRate;
	}

	public Caidan(int id, String name, String url, int clickRate) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.clickRate = clickRate;
	}

	public Caidan() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Caidan [id=" + id + ", name=" + name + ", url=" + url
				+ ", clickRate=" + clickRate + "]";
	}

}
