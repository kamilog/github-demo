package goodjob.dto;

import java.sql.Date;

public class GJ_C_RECRUIT_DTO {
/*
	select mem_cr_num,mem_Cr_gyoung,mem_c_name,mem_cr_date,mem_cr_sal 
	from gj_mem_recruit mr,gj_mem_c mc
	where mr.mem_c_id=mc.mem_c_id;
*/
	private int mem_cr_num;
	private String mem_cr_gyoung;
	private String mem_c_name;
	private Date mem_cr_date;
	private String mem_cr_sal;
	private String mem_c_id;
	
	public GJ_C_RECRUIT_DTO() {}

	public GJ_C_RECRUIT_DTO(int mem_cr_num, String mem_cr_gyoung, String mem_c_name, Date mem_cr_date,
			String mem_cr_sal, String mem_c_id) {
		super();
		this.mem_cr_num = mem_cr_num;
		this.mem_cr_gyoung = mem_cr_gyoung;
		this.mem_c_name = mem_c_name;
		this.mem_cr_date = mem_cr_date;
		this.mem_cr_sal = mem_cr_sal;
		this.mem_c_id = mem_c_id;
	}

	public int getMem_cr_num() {
		return mem_cr_num;
	}

	public void setMem_cr_num(int mem_cr_num) {
		this.mem_cr_num = mem_cr_num;
	}

	public String getMem_cr_gyoung() {
		return mem_cr_gyoung;
	}

	public void setMem_cr_gyoung(String mem_cr_gyoung) {
		this.mem_cr_gyoung = mem_cr_gyoung;
	}

	public String getMem_c_name() {
		return mem_c_name;
	}

	public void setMem_c_name(String mem_c_name) {
		this.mem_c_name = mem_c_name;
	}

	public Date getMem_cr_date() {
		return mem_cr_date;
	}

	public void setMem_cr_date(Date mem_cr_date) {
		this.mem_cr_date = mem_cr_date;
	}

	public String getMem_cr_sal() {
		return mem_cr_sal;
	}

	public void setMem_cr_sal(String mem_cr_sal) {
		this.mem_cr_sal = mem_cr_sal;
	}

	public String getMem_c_id() {
		return mem_c_id;
	}

	public void setMem_c_id(String mem_c_id) {
		this.mem_c_id = mem_c_id;
	}
	
}
