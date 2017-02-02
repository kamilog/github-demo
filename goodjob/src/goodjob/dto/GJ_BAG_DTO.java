package goodjob.dto;

public class GJ_BAG_DTO {
	private int bag_num;
	private String bag_comment;
	private String mem_c_id;
	private int mem_pr_num;
	
	public GJ_BAG_DTO() {}

	public GJ_BAG_DTO(int bag_num, String bag_comment, String mem_c_id, int mem_pr_num) {
		super();
		this.bag_num = bag_num;
		this.bag_comment = bag_comment;
		this.mem_c_id = mem_c_id;
		this.mem_pr_num = mem_pr_num;
	}

	public int getBag_num() {
		return bag_num;
	}

	public void setBag_num(int bag_num) {
		this.bag_num = bag_num;
	}

	public String getBag_comment() {
		return bag_comment;
	}

	public void setBag_comment(String bag_comment) {
		this.bag_comment = bag_comment;
	}

	public String getMem_c_id() {
		return mem_c_id;
	}

	public void setMem_c_id(String mem_c_id) {
		this.mem_c_id = mem_c_id;
	}

	public int getMem_pr_num() {
		return mem_pr_num;
	}

	public void setMem_pr_num(int mem_pr_num) {
		this.mem_pr_num = mem_pr_num;
	}
	
}
