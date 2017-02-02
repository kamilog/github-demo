package goodjob.dto;

public class GJ_LAN_C_DTO {
	private int lan_c_code;
	private String lan_c_name;
	
	public GJ_LAN_C_DTO() {}
	
	public GJ_LAN_C_DTO(int lan_c_code, String lan_c_name) {
		super();
		this.lan_c_code = lan_c_code;
		this.lan_c_name = lan_c_name;
	}
	public int getLan_c_code() {
		return lan_c_code;
	}
	public void setLan_c_code(int lan_c_code) {
		this.lan_c_code = lan_c_code;
	}
	public String getLan_c_name() {
		return lan_c_name;
	}
	public void setLan_c_name(String lan_c_name) {
		this.lan_c_name = lan_c_name;
	}
	
}
