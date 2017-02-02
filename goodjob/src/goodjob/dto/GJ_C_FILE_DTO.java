package goodjob.dto;

import java.sql.Date;

public class GJ_C_FILE_DTO {
	private int file_c_code;
	private String file_c_name;
	private String file_c_sname;
	private Date file_c_date;
	private int mem_fr_num;
	
	public GJ_C_FILE_DTO() {}

	public GJ_C_FILE_DTO(int file_c_code, String file_c_name, String file_c_sname, Date file_c_date, int mem_fr_num) {
		super();
		this.file_c_code = file_c_code;
		this.file_c_name = file_c_name;
		this.file_c_sname = file_c_sname;
		this.file_c_date = file_c_date;
		this.mem_fr_num = mem_fr_num;
	}

	public int getFile_c_code() {
		return file_c_code;
	}

	public void setFile_c_code(int file_c_code) {
		this.file_c_code = file_c_code;
	}

	public String getFile_c_name() {
		return file_c_name;
	}

	public void setFile_c_name(String file_c_name) {
		this.file_c_name = file_c_name;
	}

	public String getFile_c_sname() {
		return file_c_sname;
	}

	public void setFile_c_sname(String file_c_sname) {
		this.file_c_sname = file_c_sname;
	}

	public Date getFile_c_date() {
		return file_c_date;
	}

	public void setFile_c_date(Date file_c_date) {
		this.file_c_date = file_c_date;
	}

	public int getMem_fr_num() {
		return mem_fr_num;
	}

	public void setMem_fr_num(int mem_fr_num) {
		this.mem_fr_num = mem_fr_num;
	}
	
	
}
