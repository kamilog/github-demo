package goodjob.dto;

public class GJ_UP_DTO {
	private int up_code;
	private String up_name;
	
	public GJ_UP_DTO() {}

	public GJ_UP_DTO(int up_code, String up_name) {
		super();
		this.up_code = up_code;
		this.up_name = up_name;
	}

	public int getUp_code() {
		return up_code;
	}

	public void setUp_code(int up_code) {
		this.up_code = up_code;
	}

	public String getUp_name() {
		return up_name;
	}

	public void setUp_name(String up_name) {
		this.up_name = up_name;
	}
	
}
