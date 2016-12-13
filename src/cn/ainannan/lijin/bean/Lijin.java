package cn.ainannan.lijin.bean;

import javax.persistence.Entity;

@Entity
public class Lijin {
	private int id;
	private String name;
	private int money;
	private int guishuren;	// 1、楠楠 2、羊羊 3、共有
	private int state;		// 1、已还 0、未还
	private String inputDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGuishuren() {
		return guishuren;
	}

	public void setGuishuren(int guishuren) {
		this.guishuren = guishuren;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Lijin(int id, String name, int money,
			int guishuren, int state, String inputDate) {
		super();
		this.id = id;
		this.name = name;
		this.money = money;
		this.guishuren = guishuren;
		this.state = state;
		this.inputDate = inputDate;
	}

	public Lijin() {
		super();
	}

}
