package goodjob.dto;

public class GJ_C_FILE_L {
	int file_c_code;
	String file_c_name;
	String file_c_sname;
	
	public GJ_C_FILE_L() {}

	public GJ_C_FILE_L(int file_c_code, String file_c_name, String file_c_sname) {
		super();
		this.file_c_code = file_c_code;
		this.file_c_name = file_c_name;
		this.file_c_sname = file_c_sname;
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
}
