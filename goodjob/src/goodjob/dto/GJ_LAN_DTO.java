package goodjob.dto;

public class GJ_LAN_DTO {
	private int lan_code;
	private int lan_c_code;
	private String lan_grade;
	private int mem_pr_num;
	
	public GJ_LAN_DTO() {}

	public GJ_LAN_DTO(int lan_code, int lan_c_code, String lan_grade, int mem_pr_num) {
		super();
		this.lan_code = lan_code;
		this.lan_c_code = lan_c_code;
		this.lan_grade = lan_grade;
		this.mem_pr_num = mem_pr_num;
	}

	public int getLan_code() {
		return lan_code;
	}

	public void setLan_code(int lan_code) {
		this.lan_code = lan_code;
	}

	public int getLan_c_code() {
		return lan_c_code;
	}

	public void setLan_c_code(int lan_c_code) {
		this.lan_c_code = lan_c_code;
	}

	public String getLan_grade() {
		return lan_grade;
	}

	public void setLan_grade(String lan_grade) {
		this.lan_grade = lan_grade;
	}

	public int getMem_pr_num() {
		return mem_pr_num;
	}

	public void setMem_pr_num(int mem_pr_num) {
		this.mem_pr_num = mem_pr_num;
	}
	
}