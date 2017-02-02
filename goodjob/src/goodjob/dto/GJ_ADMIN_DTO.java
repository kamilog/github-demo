package goodjob.dto;

public class GJ_ADMIN_DTO {
	private String admin_id;
	private String admin_pwd;
	
	public GJ_ADMIN_DTO() {}

	public GJ_ADMIN_DTO(String admin_id, String admin_pwd) {
		super();
		this.admin_id = admin_id;
		this.admin_pwd = admin_pwd;
	}

	public String getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}

	public String getAdmin_pwd() {
		return admin_pwd;
	}

	public void setAdmin_pwd(String admin_pwd) {
		this.admin_pwd = admin_pwd;
	}
	
}
