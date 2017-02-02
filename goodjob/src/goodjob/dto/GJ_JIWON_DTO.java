package goodjob.dto;

import java.sql.Date;

public class GJ_JIWON_DTO {
	private int jiwon_num;
	private int mem_cr_num;
	private int mem_pr_num;
	private Date jiwon_date;
	private char jiwon_check;
	
	public GJ_JIWON_DTO() {}

	public GJ_JIWON_DTO(int jiwon_num, int mem_cr_num, int mem_pr_num, Date jiwon_date, char jiwon_check) {
		super();
		this.jiwon_num = jiwon_num;
		this.mem_cr_num = mem_cr_num;
		this.mem_pr_num = mem_pr_num;
		this.jiwon_date = jiwon_date;
		this.jiwon_check = jiwon_check;
	}

	public int getJiwon_num() {
		return jiwon_num;
	}

	public void setJiwon_num(int jiwon_num) {
		this.jiwon_num = jiwon_num;
	}

	public int getMem_cr_num() {
		return mem_cr_num;
	}

	public void setMem_cr_num(int mem_cr_num) {
		this.mem_cr_num = mem_cr_num;
	}

	public int getMem_pr_num() {
		return mem_pr_num;
	}

	public void setMem_pr_num(int mem_pr_num) {
		this.mem_pr_num = mem_pr_num;
	}

	public Date getJiwon_date() {
		return jiwon_date;
	}

	public void setJiwon_date(Date jiwon_date) {
		this.jiwon_date = jiwon_date;
	}

	public char getJiwon_check() {
		return jiwon_check;
	}

	public void setJiwon_check(char jiwon_check) {
		this.jiwon_check = jiwon_check;
	}
}
