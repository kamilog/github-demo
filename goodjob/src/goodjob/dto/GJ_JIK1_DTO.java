package goodjob.dto;

public class GJ_JIK1_DTO {
	private int jik1_code;
	private String jik1_name;
	
	public GJ_JIK1_DTO() {}

	public GJ_JIK1_DTO(int jik1_code, String jik1_name) {
		super();
		this.jik1_code = jik1_code;
		this.jik1_name = jik1_name;
	}

	public int getJik1_code() {
		return jik1_code;
	}

	public void setJik1_code(int jik1_code) {
		this.jik1_code = jik1_code;
	}

	public String getJik1_name() {
		return jik1_name;
	}

	public void setJik1_name(String jik1_name) {
		this.jik1_name = jik1_name;
	}
}
