package goodjob.dto;

public class GJ_LAN_L {
	String lan_name;
	String lan_grade;
	
	public GJ_LAN_L() {}
	
	public GJ_LAN_L(String lan_name, String lan_grade) {
		this.lan_name = lan_name;
		this.lan_grade = lan_grade;
	}

	public String getLan_name() {
		return lan_name;
	}
	public void setLan_name(String lan_name) {
		this.lan_name = lan_name;
	}
	public String getLan_grade() {
		return lan_grade;
	}
	public void setLan_grade(String lan_grade) {
		this.lan_grade = lan_grade;
	}
	
}
