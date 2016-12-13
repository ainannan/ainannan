package cn.ainannan.jianshen.tizhong.bean;

import javax.persistence.Entity;

@Entity
public class Tizhong {
	private int id;
	private double weight;
	private String inputDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public Tizhong(int id, double weight, String inputDate) {
		super();
		this.id = id;
		this.weight = weight;
		this.inputDate = inputDate;
	}

	public Tizhong() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Tizhong [id=" + id + ", weight=" + weight + ", inputDate="
				+ inputDate + "]";
	}

}
