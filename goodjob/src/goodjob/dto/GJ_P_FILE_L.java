package goodjob.dto;

public class GJ_P_FILE_L {
	int file_p_code;
	String file_p_name;
	String file_p_sname;
	
	
	public int getFile_p_code() {
		return file_p_code;
	}

	public void setFile_p_code(int file_p_code) {
		this.file_p_code = file_p_code;
	}

	public GJ_P_FILE_L() {}

	public GJ_P_FILE_L(int file_p_code, String file_p_name, String file_p_sname) {
		this.file_p_code = file_p_code;
		this.file_p_name = file_p_name;
		
		this.file_p_sname = file_p_sname;
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
	
	
}
