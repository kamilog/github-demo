package goodjob.dto;

public class GJ_LAN_CER_L {
	private String lan_cer_name;
	private String lan_cer_level;
	private String lan_cer_date;
	
	
	public GJ_LAN_CER_L(String lan_cer_name, String lan_cer_level, String lan_cer_date) {
		this.lan_cer_name = lan_cer_name;
		this.lan_cer_level = lan_cer_level;
		this.lan_cer_date = lan_cer_date;
	}
	
	
	public GJ_LAN_CER_L() {}


	public String getLan_cer_name() {
		return lan_cer_name;
	}
	public void setLan_cer_name(String lan_cer_name) {
		this.lan_cer_name = lan_cer_name;
	}
	public String getLan_cer_level() {
		return lan_cer_level;
	}
	public void setLan_cer_level(String lan_cer_level) {
		this.lan_cer_level = lan_cer_level;
	}
	public String getLan_cer_date() {
		return lan_cer_date;
	}
	public void setLan_cer_date(String lan_cer_date) {
		this.lan_cer_date = lan_cer_date;
	}
	
}
