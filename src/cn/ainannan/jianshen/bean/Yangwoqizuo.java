package cn.ainannan.jianshen.bean;

import javax.persistence.Entity;

@Entity
public class Yangwoqizuo {
	private int id;
	private int count;
	private String inputDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	@Override
	public String toString() {
		return "Fuwocheng [id=" + id + ", count=" + count + ", inputDate="
				+ inputDate + "]";
	}

	public Yangwoqizuo(int id, int count, String inputDate) {
		super();
		this.id = id;
		this.count = count;
		this.inputDate = inputDate;
	}

	public Yangwoqizuo() {
		super();
		// TODO Auto-generated constructor stub
	}

}
