package goodjob.dto;

import java.sql.Date;

public class GJ_MY_RESUME {
	int num;
	String title;
	Date date;
	
	public GJ_MY_RESUME() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GJ_MY_RESUME(int num, String title, Date date) {
		super();
		this.num = num;
		this.title = title;
		this.date = date;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
