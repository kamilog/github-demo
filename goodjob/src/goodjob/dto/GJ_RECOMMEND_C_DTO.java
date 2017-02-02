package goodjob.dto;

public class GJ_RECOMMEND_C_DTO {
	
	private int mem_cr_num;
	private String mem_cr_title;
	
	
	public GJ_RECOMMEND_C_DTO() {}


	public GJ_RECOMMEND_C_DTO(int mem_cr_num, String mem_cr_title) {
		super();
		this.mem_cr_num = mem_cr_num;
		this.mem_cr_title = mem_cr_title;
	}


	public int getMem_cr_num() {
		return mem_cr_num;
	}


	public void setMem_cr_num(int mem_cr_num) {
		this.mem_cr_num = mem_cr_num;
	}


	public String getMem_cr_title() {
		return mem_cr_title;
	}


	public void setMem_cr_title(String mem_cr_title) {
		this.mem_cr_title = mem_cr_title;
	}


}
