package goodjob.dto;

import java.sql.Date;

public class GJ_P_FILE_DTO {
	private int file_p_code;
	private String file_p_name;
	private String file_p_sname;
	private Date file_p_date;
	private int mem_pr_num;
	
	public GJ_P_FILE_DTO() {}

	public GJ_P_FILE_DTO(int file_p_code, String file_p_name, String file_p_sname, Date file_p_date, int mem_pr_num) {
		super();
		this.file_p_code = file_p_code;
		this.file_p_name = file_p_name;
		this.file_p_sname = file_p_sname;
		this.file_p_date = file_p_date;
		this.mem_pr_num = mem_pr_num;
	}

	public int getFile_p_code() {
		return file_p_code;
	}

	public void setFile_p_code(int file_p_code) {
		this.file_p_code = file_p_code;
	}

	public String getFile_p_name() {
		return file_p_name;
	}

	public void setFile_p_name(String file_p_name) {
		this.file_p_name = file_p_name;
	}

	public String getFile_p_sname() {
		return file_p_sname;
	}

	public void setFile_p_sname(String file_p_sname) {
		this.file_p_sname = file_p_sname;
	}

	public Date getFile_p_date() {
		return file_p_date;
	}

	public void setFile_p_date(Date file_p_date) {
		this.file_p_date = file_p_date;
	}

	public int getMem_pr_num() {
		return mem_pr_num;
	}

	public void setMem_pr_num(int mem_pr_num) {
		this.mem_pr_num = mem_pr_num;
	}
	
	
}
