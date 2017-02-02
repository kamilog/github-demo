package goodjob.dto;

public class GJ_C_GRADE_DTO {
	private int grade_num;
	private String grade_money;
	private String grade;
	private String mem_c_id;

	public GJ_C_GRADE_DTO(){}

	public GJ_C_GRADE_DTO(int grade_num, String grade_money, String grade, String mem_c_id) {
		super();
		this.grade_num = grade_num;
		this.grade_money = grade_money;
		this.grade = grade;
		this.mem_c_id = mem_c_id;
	}

	public int getGrade_num() {
		return grade_num;
	}

	public void setGrade_num(int grade_num) {
		this.grade_num = grade_num;
	}

	public String getGrade_money() {
		return grade_money;
	}

	public void setGrade_money(String grade_money) {
		this.grade_money = grade_money;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getMem_c_id() {
		return mem_c_id;
	}

	public void setMem_c_id(String mem_c_id) {
		this.mem_c_id = mem_c_id;
	}	

}
