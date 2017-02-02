package goodjob.dto;

public class GJ_LAN_CER_C_DTO {
	private int lan_cer_c_code;
	private String lan_cer_name;
	
	public GJ_LAN_CER_C_DTO() {}

	public GJ_LAN_CER_C_DTO(int lan_cer_c_code, String lan_cer_name) {
		super();
		this.lan_cer_c_code = lan_cer_c_code;
		this.lan_cer_name = lan_cer_name;
	}

	public int getLan_cer_c_code() {
		return lan_cer_c_code;
	}

	public void setLan_cer_c_code(int lan_cer_c_code) {
		this.lan_cer_c_code = lan_cer_c_code;
	}

	public String getLan_cer_name() {
		return lan_cer_name;
	}

	public void setLan_cer_name(String lan_cer_name) {
		this.lan_cer_name = lan_cer_name;
	}
	
	
}
