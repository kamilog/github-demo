package goodjob.dto;


public class GJ_EXPINFO_DTO {
	private int expinfo_code;
	private String expinfo_date;
	private String expinfo_cname;
	private String expinfo_grade;
	private String expinfo_detail;
	private int mem_pr_num;
	
	public GJ_EXPINFO_DTO() {}

	public GJ_EXPINFO_DTO(int expinfo_code, String expinfo_date, String expinfo_cname, String expinfo_grade,
			String expinfo_detail, int mem_pr_num) {
		super();
		this.expinfo_code = expinfo_code;
		this.expinfo_date = expinfo_date;
		this.expinfo_cname = expinfo_cname;
		this.expinfo_grade = expinfo_grade;
		this.expinfo_detail = expinfo_detail;
		this.mem_pr_num = mem_pr_num;
	}

	public int getExpinfo_code() {
		return expinfo_code;
	}

	public void setExpinfo_code(int expinfo_code) {
		this.expinfo_code = expinfo_code;
	}

	public String getExpinfo_date() {
		return expinfo_date;
	}

	public void setExpinfo_date(String expinfo_date) {
		this.expinfo_date = expinfo_date;
	}

	public String getExpinfo_cname() {
		return expinfo_cname;
	}

	public void setExpinfo_cname(String expinfo_cname) {
		this.expinfo_cname = expinfo_cname;
	}

	public String getExpinfo_grade() {
		return expinfo_grade;
	}

	public void setExpinfo_grade(String expinfo_grade) {
		this.expinfo_grade = expinfo_grade;
	}

	public String getExpinfo_detail() {
		return expinfo_detail;
	}

	public void setExpinfo_detail(String expinfo_detail) {
		this.expinfo_detail = expinfo_detail;
	}

	public int getMem_pr_num() {
		return mem_pr_num;
	}

	public void setMem_pr_num(int mem_pr_num) {
		this.mem_pr_num = mem_pr_num;
	}

}
