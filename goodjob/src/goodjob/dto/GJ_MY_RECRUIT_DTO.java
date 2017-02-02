package goodjob.dto;

public class GJ_MY_RECRUIT_DTO {
	int recruit_num;
	int resume_num;
	String recruit_title;
	String resume_title;
	
	
	public GJ_MY_RECRUIT_DTO(int recruit_num, int resume_num, String recruit_title, String resume_title) {
		this.recruit_num = recruit_num;
		this.resume_num = resume_num;
		this.recruit_title = recruit_title;
		this.resume_title = resume_title;
	}
	
	public GJ_MY_RECRUIT_DTO() {
	
	}

	public int getRecruit_num() {
		return recruit_num;
	}
	public void setRecruit_num(int recruit_num) {
		this.recruit_num = recruit_num;
	}
	public int getResume_num() {
		return resume_num;
	}
	public void setResume_num(int resume_num) {
		this.resume_num = resume_num;
	}
	public String getRecruit_title() {
		return recruit_title;
	}
	public void setRecruit_title(String recruit_title) {
		this.recruit_title = recruit_title;
	}
	public String getResume_title() {
		return resume_title;
	}
	public void setResume_title(String resume_title) {
		this.resume_title = resume_title;
	}
	
}
