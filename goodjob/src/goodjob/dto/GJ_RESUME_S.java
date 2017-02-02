package goodjob.dto;

import java.sql.Date;

public class GJ_RESUME_S {
	//--이름 성별 나이 제목 희망직종 경력사항 외국어 자격증 수정일
	int num;
	String name;
	String gender;
	int age;
	String title;
	String jik;
	String gyoung;
	int lan_cnt;
	int lic_cnt;
	Date resume_date;
	
	public GJ_RESUME_S(){
		
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public GJ_RESUME_S(int num, String name, String gender, int age, String title, String jik, String gyoung, int lan_cnt,
			int lic_cnt, Date resume_date) {
		this.num = num;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.title = title;
		this.jik = jik;
		this.gyoung = gyoung;
		this.lan_cnt = lan_cnt;
		this.lic_cnt = lic_cnt;
		this.resume_date = resume_date;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getJik() {
		return jik;
	}
	public void setJik(String jik) {
		this.jik = jik;
	}
	public String getGyoung() {
		return gyoung;
	}
	public void setGyoung(String gyoung) {
		this.gyoung = gyoung;
	}
	public int getLan_cnt() {
		return lan_cnt;
	}
	public void setLan_cnt(int lan_cnt) {
		this.lan_cnt = lan_cnt;
	}
	public int getLic_cnt() {
		return lic_cnt;
	}
	public void setLic_cnt(int lic_cnt) {
		this.lic_cnt = lic_cnt;
	}
	public Date getResume_date() {
		return resume_date;
	}
	public void setResume_date(Date resume_date) {
		this.resume_date = resume_date;
	}
	
	
}
