package goodjob.dto;

public class GJ_TEMP_DTO {
	private int code;
	private String str;
			
	public GJ_TEMP_DTO() {}
	
	public GJ_TEMP_DTO(int code, String str) {
		this.code = code;
		this.str = str;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
}
