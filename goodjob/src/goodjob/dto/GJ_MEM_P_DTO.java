package goodjob.dto;

import java.sql.Date;

public class GJ_MEM_P_DTO {

	private String mem_p_id;
	   private String mem_p_pwd;
	   private String mem_p_name;
	   private String mem_p_birth;
	   private String mem_p_addr;
	   private String mem_p_phone;
	   private String mem_p_email;
	   private String mem_p_gender;
	   private Date mem_p_date;
	
	
	public GJ_MEM_P_DTO() {}


	public GJ_MEM_P_DTO(String mem_p_id, String mem_p_pwd, String mem_p_name, String mem_p_birth, String mem_p_addr,
			String mem_p_phone, String mem_p_email, String mem_p_gender, Date mem_p_date) {
		super();
		this.mem_p_id = mem_p_id;
		this.mem_p_pwd = mem_p_pwd;
		this.mem_p_name = mem_p_name;
		this.mem_p_birth = mem_p_birth;
		this.mem_p_addr = mem_p_addr;
		this.mem_p_phone = mem_p_phone;
		this.mem_p_email = mem_p_email;
		this.mem_p_gender = mem_p_gender;
		this.mem_p_date = mem_p_date;
	}


	public String getMem_p_id() {
		return mem_p_id;
	}


	public void setMem_p_id(String mem_p_id) {
		this.mem_p_id = mem_p_id;
	}


	public String getMem_p_pwd() {
		return mem_p_pwd;
	}


	public void setMem_p_pwd(String mem_p_pwd) {
		this.mem_p_pwd = mem_p_pwd;
	}


	public String getMem_p_name() {
		return mem_p_name;
	}


	public void setMem_p_name(String mem_p_name) {
		this.mem_p_name = mem_p_name;
	}


	public String getMem_p_birth() {
		return mem_p_birth;
	}


	public void setMem_p_birth(String mem_p_birth) {
		this.mem_p_birth = mem_p_birth;
	}


	public String getMem_p_addr() {
		return mem_p_addr;
	}


	public void setMem_p_addr(String mem_p_addr) {
		this.mem_p_addr = mem_p_addr;
	}


	public String getMem_p_phone() {
		return mem_p_phone;
	}


	public void setMem_p_phone(String mem_p_phone) {
		this.mem_p_phone = mem_p_phone;
	}


	public String getMem_p_email() {
		return mem_p_email;
	}


	public void setMem_p_email(String mem_p_email) {
		this.mem_p_email = mem_p_email;
	}


	public String getMem_p_gender() {
		return mem_p_gender;
	}


	public void setMem_p_gender(String mem_p_gender) {
		this.mem_p_gender = mem_p_gender;
	}


	public Date getMem_p_date() {
		return mem_p_date;
	}


	public void setMem_p_date(Date mem_p_date) {
		this.mem_p_date = mem_p_date;
	}

}
