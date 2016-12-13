package cn.ainannan.shf.bean;

import javax.persistence.Entity;

@Entity
public class Shenghuofei {
	private int id;
	private double money;
	private int zhichuType; // 1.饭钱 2.水果 3.零食 4.其它
	private int zhichuren; // 1.共有 2.楠楠 3.羊羊
	private String inputDate;
	private String remark;
	private int state; // 1.已入账 0.未入账

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getZhichuType() {
		return zhichuType;
	}

	public void setZhichuType(int zhichuType) {
		this.zhichuType = zhichuType;
	}

	public int getZhichuren() {
		return zhichuren;
	}

	public void setZhichuren(int zhichuren) {
		this.zhichuren = zhichuren;
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Shenghuofei(int id, double money, int zhichuType, int zhichuren,
			String inputDate, String remark, int state) {
		super();
		this.id = id;
		this.money = money;
		this.zhichuType = zhichuType;
		this.zhichuren = zhichuren;
		this.inputDate = inputDate;
		this.remark = remark;
		this.state = state;
	}

	public Shenghuofei() {
		super();
	}
	
	
}
