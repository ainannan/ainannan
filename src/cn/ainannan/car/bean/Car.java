package cn.ainannan.car.bean;

import javax.persistence.Entity;

@Entity
public class Car {
	// Car(id,niandu,month,money,type:[油费,过路费,违章罚款,其它],state:[已付款,未付款],inputdate)
	private int id;
	private int niandu;
	private int month;
	private int money;
	private int type; // 1、油费,2、过路费,3、违章罚款,4、其它
	private int state; // 1、已付款,2、未付款
	private String inputDate;
	private String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNiandu() {
		return niandu;
	}

	public void setNiandu(int niandu) {
		this.niandu = niandu;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", niandu=" + niandu + ", month=" + month
				+ ", money=" + money + ", type=" + type + ", state=" + state
				+ ", inputDate=" + inputDate + ", remark=" + remark + "]";
	}

	public Car(int id, int niandu, int month, int money, int type, int state,
			String inputDate, String remark) {
		super();
		this.id = id;
		this.niandu = niandu;
		this.month = month;
		this.money = money;
		this.type = type;
		this.state = state;
		this.inputDate = inputDate;
		this.remark = remark;
	}

	public Car() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
