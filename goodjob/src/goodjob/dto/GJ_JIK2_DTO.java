package goodjob.dto;

public class GJ_JIK2_DTO {
	private int jik2_code;
	private String jik2_name;
	private int jik1_code;
	
	public GJ_JIK2_DTO() {}

	public GJ_JIK2_DTO(int jik2_code, String jik2_name, int jik1_code) {
		super();
		this.jik2_code = jik2_code;
		this.jik2_name = jik2_name;
		this.jik1_code = jik1_code;
	}

	public int getJik2_code() {
		return jik2_code;
	}

	public void setJik2_code(int jik2_code) {
		this.jik2_code = jik2_code;
	}

	public String getJik2_name() {
		return jik2_name;
	}

	public void setJik2_name(String jik2_name) {
		this.jik2_name = jik2_name;
	}

	public int getJik1_code() {
		return jik1_code;
	}

	public void setJik1_code(int jik1_code) {
		this.jik1_code = jik1_code;
	}
}
