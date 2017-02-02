package goodjob.dto;

public class GJ_LICENSE_L {
	String license_name;
	String license_date;
	
	public GJ_LICENSE_L(){}
	
	public GJ_LICENSE_L(String license_name, String license_date) {
		this.license_name = license_name;
		this.license_date = license_date;
	}
	
	public String getLicense_name() {
		return license_name;
	}
	public void setLicense_name(String license_name) {
		this.license_name = license_name;
	}
	public String getLicense_date() {
		return license_date;
	}
	public void setLicense_date(String license_date) {
		this.license_date = license_date;
	}
	
}
