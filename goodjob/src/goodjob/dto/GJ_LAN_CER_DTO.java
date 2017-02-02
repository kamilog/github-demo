package goodjob.dto;

public class GJ_LAN_CER_DTO {
	private int lan_cer_code;
	private int lan_cer_c_code;
	private String lan_cer_level;
	private String lan_cer_date;
	private int mem_pr_num;
	
	public GJ_LAN_CER_DTO() {}

	public GJ_LAN_CER_DTO(int lan_cer_code, int lan_cer_c_code, String lan_cer_level, String lan_cer_date,
			int mem_pr_num) {
		super();
		this.lan_cer_code = lan_cer_code;
		this.lan_cer_c_code = lan_cer_c_code;
		this.lan_cer_level = lan_cer_level;
		this.lan_cer_date = lan_cer_date;
		this.mem_pr_num = mem_pr_num;
	}

	public int getLan_cer_code() {
		return lan_cer_code;
	}

	public void setLan_cer_code(int lan_cer_code) {
		this.lan_cer_code = lan_cer_code;
	}

	public int getLan_cer_c_code() {
		return lan_cer_c_code;
	}

	public void setLan_cer_c_code(int lan_cer_c_code) {
		this.lan_cer_c_code = lan_cer_c_code;
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

	public int getMem_pr_num() {
		return mem_pr_num;
	}

	public void setMem_pr_num(int mem_pr_num) {
		this.mem_pr_num = mem_pr_num;
	}

	
}
