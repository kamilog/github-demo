package goodjob.dto;

public class GJ_RECRUIT_S {
	int num;
	String name;
	String title;
	String gyoung;
	String hak;
	String jobtype;
	String addr;
	String sal;
	String rtime;
	
	
	public GJ_RECRUIT_S(int num, String name, String title, String gyoung, String hak, String jobtype, String addr,
			String sal, String rtime) {
		this.num = num;
		this.name = name;
		this.title = title;
		this.gyoung = gyoung;
		this.hak = hak;
		this.jobtype = jobtype;
		this.addr = addr;
		this.sal = sal;
		this.rtime = rtime;
	}
	public GJ_RECRUIT_S() {
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGyoung() {
		return gyoung;
	}
	public void setGyoung(String gyoung) {
		this.gyoung = gyoung;
	}
	public String getHak() {
		return hak;
	}
	public void setHak(String hak) {
		this.hak = hak;
	}
	public String getJobtype() {
		return jobtype;
	}
	public void setJobtype(String jobtype) {
		this.jobtype = jobtype;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getSal() {
		return sal;
	}
	public void setSal(String sal) {
		this.sal = sal;
	}
	public String getRtime() {
		return rtime;
	}
	public void setRtime(String rtime) {
		this.rtime = rtime;
	}
	
}
