package cn.ainannan.shf.bean;

import javax.persistence.Entity;

@Entity
public class ShenghuofeiShezhi {
	private int id;
	private String niandu;
	private String month;
	private double money;
	private double yiyongMoney;
	private double weiyongMoney;
	private double rijunMoney;
	private int jiezhangState;  	// 是否结账状态 1已结账，0未结账
	private int isChaozhi;			// 1.超支 0.未超支
	private double chaozhiMoney;

	public ShenghuofeiShezhi(int id, String niandu, String month, double money,
			double yiyongMoney, String inputDate) {

		this.id = id;
		this.niandu = niandu;
		this.month = month;
		this.money = money;
		this.yiyongMoney = yiyongMoney;
	}

	public ShenghuofeiShezhi(String niandu, String month) {
		this.niandu = niandu;
		this.month = month;
	}

	public ShenghuofeiShezhi(int id, String niandu, String month, double money, double yiyongMoney,
			double weiyongMoney, double rijunMoney,int jiezhangState, int isChaozhi, double chaozhiMoney) {
		super();
		this.id = id;
		this.niandu = niandu;
		this.month = month;
		this.money = money;
		this.yiyongMoney = yiyongMoney;
		this.weiyongMoney = weiyongMoney;
		this.rijunMoney = rijunMoney;
		this.jiezhangState = jiezhangState;
		this.isChaozhi = isChaozhi;
		this.chaozhiMoney = chaozhiMoney;
	}

	public ShenghuofeiShezhi() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNiandu() {
		return niandu;
	}

	public void setNiandu(String niandu) {
		this.niandu = niandu;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getYiyongMoney() {
		return yiyongMoney;
	}

	public void setYiyongMoney(double yiyongMoney) {
		this.yiyongMoney = yiyongMoney;
	}

	public double getWeiyongMoney() {
		return weiyongMoney;
	}

	public void setWeiyongMoney(double weiyongMoney) {
		this.weiyongMoney = weiyongMoney;
	}

	public double getRijunMoney() {
		return rijunMoney;
	}

	public void setRijunMoney(double rijunMoney) {
		this.rijunMoney = rijunMoney;
	}

	public int getIsChaozhi() {
		return isChaozhi;
	}

	public void setIsChaozhi(int isChaozhi) {
		this.isChaozhi = isChaozhi;
	}

	public double getChaozhiMoney() {
		return chaozhiMoney;
	}

	public void setChaozhiMoney(double chaozhiMoney) {
		this.chaozhiMoney = chaozhiMoney;
	}

	public int getJiezhangState() {
		return jiezhangState;
	}

	public void setJiezhangState(int jiezhangState) {
		this.jiezhangState = jiezhangState;
	}
}
