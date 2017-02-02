package goodjob.dto;

public class GJ_HAK_L {
	private String hak_date;
	private String hak_type;
	private String hak_name;
	private String hak_major;
	
	
	public GJ_HAK_L(String hak_date, String hak_type, String hak_name, String hak_major) {
		this.hak_date = hak_date;
		this.hak_type = hak_type;
		this.hak_name = hak_name;
		this.hak_major = hak_major;
	}
	
	public GJ_HAK_L() {
		
	}

	public String getHak_date() {
		return hak_date;
	}

	public void setHak_date(String hak_date) {
		this.hak_date = hak_date;
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

	public String getHak_major() {
		return hak_major;
	}

	public void setHak_major(String hak_major) {
		this.hak_major = hak_major;
	}

	
}
