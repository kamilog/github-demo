package goodjob.dto;

public class GJ_ADDR_DTO {
	private int addr_code;
	private String addr_city1;
	private int addr_post;
	private String addr_city2;
	private String addr_city3;
	private String addr_detail;
	
	public GJ_ADDR_DTO() {}

	public GJ_ADDR_DTO(int addr_code, String addr_city1, int addr_post, String addr_city2, String addr_city3,
			String addr_detail) {
		super();
		this.addr_code = addr_code;
		this.addr_city1 = addr_city1;
		this.addr_post = addr_post;
		this.addr_city2 = addr_city2;
		this.addr_city3 = addr_city3;
		this.addr_detail = addr_detail;
	}

	public int getAddr_code() {
		return addr_code;
	}

	public void setAddr_code(int addr_code) {
		this.addr_code = addr_code;
	}

	public String getAddr_city1() {
		return addr_city1;
	}

	public void setAddr_city1(String addr_city1) {
		this.addr_city1 = addr_city1;
	}

	public int getAddr_post() {
		return addr_post;
	}

	public void setAddr_post(int addr_post) {
		this.addr_post = addr_post;
	}

	public String getAddr_city2() {
		return addr_city2;
	}

	public void setAddr_city2(String addr_city2) {
		this.addr_city2 = addr_city2;
	}

	public String getAddr_city3() {
		return addr_city3;
	}

	public void setAddr_city3(String addr_city3) {
		this.addr_city3 = addr_city3;
	}

	public String getAddr_detail() {
		return addr_detail;
	}

	public void setAddr_detail(String addr_detail) {
		this.addr_detail = addr_detail;
	}
}
