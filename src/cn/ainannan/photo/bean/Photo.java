package cn.ainannan.photo.bean;

import javax.persistence.Entity;

@Entity
public class Photo {
	private int id;
	private String name;
	private String path;
	private String shuoshuo;
	private String inputDate;

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getShuoshuo() {
		return shuoshuo;
	}

	public void setShuoshuo(String shuoshuo) {
		this.shuoshuo = shuoshuo;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public String toString() {
		return "Photo [id=" + id + ", name=" + name + ", path=" + path
				+ ", shuoshuo=" + shuoshuo + ", inputDate=" + inputDate + "]";
	}

	public Photo(int id, String name, String path, String shuoshuo,
			String inputDate) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
		this.shuoshuo = shuoshuo;
		this.inputDate = inputDate;
	}

	public Photo() {
		super();
		// TODO Auto-generated constructor stub
	}
}
