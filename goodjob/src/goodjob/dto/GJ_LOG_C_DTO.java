package goodjob.dto;

public class GJ_LOG_C_DTO {
	private int log_c_code;
	private String log_c_type;
	private String log_c_ipaddr;
	private String log_c_time;
	private String mem_c_id;
	
	public GJ_LOG_C_DTO() {}

	public GJ_LOG_C_DTO(int log_c_code, String log_c_type, String log_c_ipaddr, String log_c_time, String mem_c_id) {
		super();
		this.log_c_code = log_c_code;
		this.log_c_type = log_c_type;
		this.log_c_ipaddr = log_c_ipaddr;
		this.log_c_time = log_c_time;
		this.mem_c_id = mem_c_id;
	}

	public int getLog_c_code() {
		return log_c_code;
	}

	public void setLog_c_code(int log_c_code) {
		this.log_c_code = log_c_code;
	}

	public String getLog_c_type() {
		return log_c_type;
	}

	public void setLog_c_type(String log_c_type) {
		this.log_c_type = log_c_type;
	}

	public String getLog_c_ipaddr() {
		return log_c_ipaddr;
	}

	public void setLog_c_ipaddr(String log_c_ipaddr) {
		this.log_c_ipaddr = log_c_ipaddr;
	}

	public String getLog_c_time() {
		return log_c_time;
	}

	public void setLog_c_time(String log_c_time) {
		this.log_c_time = log_c_time;
	}

	public String getMem_c_id() {
		return mem_c_id;
	}

	public void setMem_c_id(String mem_c_id) {
		this.mem_c_id = mem_c_id;
	}
	
	
}
