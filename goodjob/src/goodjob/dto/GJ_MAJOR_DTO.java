package goodjob.dto;

public class GJ_MAJOR_DTO {
	private int major_code_number;
	private String major_name;
	
	public GJ_MAJOR_DTO() {}

	public GJ_MAJOR_DTO(int major_code_number, String major_name) {
		super();
		this.major_code_number = major_code_number;
		this.major_name = major_name;
	}

	public int getMajor_code_number() {
		return major_code_number;
	}

	public void setMajor_code_number(int major_code_number) {
		this.major_code_number = major_code_number;
	}

	public String getMajor_name() {
		return major_name;
	}

	public void setMajor_name(String major_name) {
		this.major_name = major_name;
	}
}
