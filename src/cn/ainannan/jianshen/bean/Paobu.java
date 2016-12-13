package cn.ainannan.jianshen.bean;

public class Paobu {
	private int id;
	private int countTime; // 运动时间 ， 分钟
	private double countKM; // 运动长度
	private String inputDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCountTime() {
		return countTime;
	}

	public void setCountTime(int countTime) {
		this.countTime = countTime;
	}

	public double getCountKM() {
		return countKM;
	}

	public void setCountKM(double countKM) {
		this.countKM = countKM;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	@Override
	public String toString() {
		return "Paobu [id=" + id + ", countTime=" + countTime + ", countKM="
				+ countKM + ", inputDate=" + inputDate + "]";
	}

	public Paobu(int id, int countTime, double countKM, String inputDate) {
		super();
		this.id = id;
		this.countTime = countTime;
		this.countKM = countKM;
		this.inputDate = inputDate;
	}

	public Paobu() {
		super();
		// TODO Auto-generated constructor stub
	}

}
