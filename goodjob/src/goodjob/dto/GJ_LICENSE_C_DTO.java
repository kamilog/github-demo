package goodjob.dto;

public class GJ_LICENSE_C_DTO {
	private int lincense_c_code;
	private String lincense_name;
	
	public GJ_LICENSE_C_DTO() {}

	public GJ_LICENSE_C_DTO(int lincense_c_code, String lincense_name) {
		super();
		this.lincense_c_code = lincense_c_code;
		this.lincense_name = lincense_name;
	}

	public int getLincense_c_code() {
		return lincense_c_code;
	}

	public void setLincense_c_code(int lincense_c_code) {
		this.lincense_c_code = lincense_c_code;
	}

	public String getLincense_name() {
		return lincense_name;
	}

	public void setLincense_name(String lincense_name) {
		this.lincense_name = lincense_name;
	}
	
}
