package goodjob.dto;

public class GJ_REC_JIWON {
	int recruit_num;
	String title;
	int jiwon_cnt;
	
	public GJ_REC_JIWON(int recruit_num, String title, int jiwon_cnt) {
		this.recruit_num = recruit_num;
		this.title = title;
		this.jiwon_cnt = jiwon_cnt;
	}

	public GJ_REC_JIWON() {}
	
	public int getRecruit_num() {
		return recruit_num;
	}
	public void setRecruit_num(int recruit_num) {
		this.recruit_num = recruit_num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getJiwon_cnt() {
		return jiwon_cnt;
	}
	public void setJiwon_cnt(int jiwon_cnt) {
		this.jiwon_cnt = jiwon_cnt;
	}
	
}
