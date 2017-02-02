package goodjob.dto;

public class GJ_HAK_DTO {
	private int hak_code;
	private String hak_type;
	private String hak_name;
	private String hak_date;
	private int mem_pr_num;
	private int major_code;
	
	public GJ_HAK_DTO() {}

	public GJ_HAK_DTO(int hak_code, String hak_type, String hak_name, String hak_date, int mem_pr_num, int major_code) {
		super();
		this.hak_code = hak_code;
		this.hak_type = hak_type;
		this.hak_name = hak_name;
		this.hak_date = hak_date;
		this.mem_pr_num = mem_pr_num;
		this.major_code = major_code;
	}

	public int getHak_code() {
		return hak_code;
	}

	public void setHak_code(int hak_code) {
		this.hak_code = hak_code;
	}

	public String getHak_type() {
		return hak_type;
	}

	public void setHak_type(String hak_type) {
		this.hak_type = hak_type;
	}

	public String getHak_name() {
		return hak_name;
	}

	public void setHak_name(String hak_name) {
		this.hak_name = hak_name;
	}

	public String getHak_date() {
		return hak_date;
	}

	public void setHak_date(String hak_date) {
		this.hak_date = hak_date;
	}

	public int getMem_pr_num() {
		return mem_pr_num;
	}

	public void setMem_pr_num(int mem_pr_num) {
		this.mem_pr_num = mem_pr_num;
	}

	public int getMajor_code() {
		return major_code;
	}

	public void setMajor_code(int major_code) {
		this.major_code = major_code;
	}
}
