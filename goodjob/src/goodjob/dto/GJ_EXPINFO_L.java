package goodjob.dto;

public class GJ_EXPINFO_L {
	private String expinfo_date;
	private String expinfo_name;
	private String expinfo_grade;
	private String expinfo_detail;
	
	
	public GJ_EXPINFO_L(String expinfo_date, String expinfo_name, String expinfo_grade, String expinfo_detail) {
		this.expinfo_date = expinfo_date;
		this.expinfo_name = expinfo_name;
		this.expinfo_grade = expinfo_grade;
		this.expinfo_detail = expinfo_detail;
	}


	public String getExpinfo_date() {
		return expinfo_date;
	}


	public void setExpinfo_date(String expinfo_date) {
		this.expinfo_date = expinfo_date;
	}


	public String getExpinfo_name() {
		return expinfo_name;
	}


	public void setExpinfo_name(String expinfo_name) {
		this.expinfo_name = expinfo_name;
	}


	public String getExpinfo_grade() {
		return expinfo_grade;
	}


	public void setExpinfo_grade(String expinfo_grade) {
		this.expinfo_grade = expinfo_grade;
	}


	public String getExpinfo_detail() {
		return expinfo_detail;
	}


	public void setExpinfo_detail(String expinfo_detail) {
		this.expinfo_detail = expinfo_detail;
	}
	
	
	
}
