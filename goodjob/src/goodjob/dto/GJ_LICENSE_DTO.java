package goodjob.dto;

import java.sql.Date;

public class GJ_LICENSE_DTO {
	private int license_code;
	private int license_c_code;
	private String license_date;
	private int mem_pr_num;
	
	
	public GJ_LICENSE_DTO() {}


	public GJ_LICENSE_DTO(int license_code, int license_c_code, String license_date, int mem_pr_num) {
		super();
		this.license_code = license_code;
		this.license_c_code = license_c_code;
		this.license_date = license_date;
		this.mem_pr_num = mem_pr_num;
	}


	public int getLicense_code() {
		return license_code;
	}


	public void setLicense_code(int license_code) {
		this.license_code = license_code;
	}


	public int getLicense_c_code() {
		return license_c_code;
	}


	public void setLicense_c_code(int license_c_code) {
		this.license_c_code = license_c_code;
	}


	public String getLicense_date() {
		return license_date;
	}


	public void setLicense_date(String license_date) {
		this.license_date = license_date;
	}


	public int getMem_pr_num() {
		return mem_pr_num;
	}


	public void setMem_pr_num(int mem_pr_num) {
		this.mem_pr_num = mem_pr_num;
	}


}
