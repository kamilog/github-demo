package goodjob.dto;

public class GJ_LOG_P_DTO {
	private int log_p_code;
	private String log_p_type;
	private String log_p_ipaddr;
	private String log_p_time;
	private String mem_p_id;
	
	public GJ_LOG_P_DTO() {}

	public GJ_LOG_P_DTO(int log_p_code, String log_p_type, String log_p_ipaddr, String log_p_time, String mem_p_id) {
		super();
		this.log_p_code = log_p_code;
		this.log_p_type = log_p_type;
		this.log_p_ipaddr = log_p_ipaddr;
		this.log_p_time = log_p_time;
		this.mem_p_id = mem_p_id;
	}

	public int getLog_p_code() {
		return log_p_code;
	}

	public void setLog_p_code(int log_p_code) {
		this.log_p_code = log_p_code;
	}

	public String getLog_p_type() {
		return log_p_type;
	}

	public void setLog_p_type(String log_p_type) {
		this.log_p_type = log_p_type;
	}

	public String getLog_p_ipaddr() {
		return log_p_ipaddr;
	}

	public void setLog_p_ipaddr(String log_p_ipaddr) {
		this.log_p_ipaddr = log_p_ipaddr;
	}

	public String getLog_p_time() {
		return log_p_time;
	}

	public void setLog_p_time(String log_p_time) {
		this.log_p_time = log_p_time;
	}

	public String getMem_p_id() {
		return mem_p_id;
	}

	public void setMem_p_id(String mem_p_id) {
		this.mem_p_id = mem_p_id;
	}
	
	
}
