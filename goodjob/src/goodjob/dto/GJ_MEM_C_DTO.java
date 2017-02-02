package goodjob.dto;

import java.sql.Date;

public class GJ_MEM_C_DTO {
	private String mem_c_id;
	private String mem_c_pwd;
	private String mem_c_name;
	private String mem_c_comnum;
	private String mem_c_bossname;
	private String mem_c_addr;
	private String mem_c_homepage;
	private String mem_c_intro;
	private String mem_c_year;
	private String mem_c_sawon;
	private String mem_c_jabon;
	private String mem_c_machul;
	private Date mem_c_date;
	private String mem_c_img;
	private String mem_c_simg;
	private int up_code;
	
	public GJ_MEM_C_DTO() {}

	public GJ_MEM_C_DTO(String mem_c_id, String mem_c_pwd, String mem_c_name, String mem_c_comnum,
			String mem_c_bossname, String mem_c_addr, String mem_c_homepage, String mem_c_intro, String mem_c_year,
			String mem_c_sawon, String mem_c_jabon, String mem_c_machul, Date mem_c_date, String mem_c_img,
			String mem_c_simg, int up_code) {
		super();
		this.mem_c_id = mem_c_id;
		this.mem_c_pwd = mem_c_pwd;
		this.mem_c_name = mem_c_name;
		this.mem_c_comnum = mem_c_comnum;
		this.mem_c_bossname = mem_c_bossname;
		this.mem_c_addr = mem_c_addr;
		this.mem_c_homepage = mem_c_homepage;
		this.mem_c_intro = mem_c_intro;
		this.mem_c_year = mem_c_year;
		this.mem_c_sawon = mem_c_sawon;
		this.mem_c_jabon = mem_c_jabon;
		this.mem_c_machul = mem_c_machul;
		this.mem_c_date = mem_c_date;
		this.mem_c_img = mem_c_img;
		this.mem_c_simg = mem_c_simg;
		this.up_code = up_code;
	}

	public String getMem_c_id() {
		return mem_c_id;
	}

	public void setMem_c_id(String mem_c_id) {
		this.mem_c_id = mem_c_id;
	}

	public String getMem_c_pwd() {
		return mem_c_pwd;
	}

	public void setMem_c_pwd(String mem_c_pwd) {
		this.mem_c_pwd = mem_c_pwd;
	}

	public String getMem_c_name() {
		return mem_c_name;
	}

	public void setMem_c_name(String mem_c_name) {
		this.mem_c_name = mem_c_name;
	}

	public String getMem_c_comnum() {
		return mem_c_comnum;
	}

	public void setMem_c_comnum(String mem_c_comnum) {
		this.mem_c_comnum = mem_c_comnum;
	}

	public String getMem_c_bossname() {
		return mem_c_bossname;
	}

	public void setMem_c_bossname(String mem_c_bossname) {
		this.mem_c_bossname = mem_c_bossname;
	}

	public String getMem_c_addr() {
		return mem_c_addr;
	}

	public void setMem_c_addr(String mem_c_addr) {
		this.mem_c_addr = mem_c_addr;
	}

	public String getMem_c_homepage() {
		return mem_c_homepage;
	}

	public void setMem_c_homepage(String mem_c_homepage) {
		this.mem_c_homepage = mem_c_homepage;
	}

	public String getMem_c_intro() {
		return mem_c_intro;
	}

	public void setMem_c_intro(String mem_c_intro) {
		this.mem_c_intro = mem_c_intro;
	}

	public String getMem_c_year() {
		return mem_c_year;
	}

	public void setMem_c_year(String mem_c_year) {
		this.mem_c_year = mem_c_year;
	}

	public String getMem_c_sawon() {
		return mem_c_sawon;
	}

	public void setMem_c_sawon(String mem_c_sawon) {
		this.mem_c_sawon = mem_c_sawon;
	}

	public String getMem_c_jabon() {
		return mem_c_jabon;
	}

	public void setMem_c_jabon(String mem_c_jabon) {
		this.mem_c_jabon = mem_c_jabon;
	}

	public String getMem_c_machul() {
		return mem_c_machul;
	}

	public void setMem_c_machul(String mem_c_machul) {
		this.mem_c_machul = mem_c_machul;
	}

	public Date getMem_c_date() {
		return mem_c_date;
	}

	public void setMem_c_date(Date mem_c_date) {
		this.mem_c_date = mem_c_date;
	}

	public String getMem_c_img() {
		return mem_c_img;
	}

	public void setMem_c_img(String mem_c_img) {
		this.mem_c_img = mem_c_img;
	}

	public String getMem_c_simg() {
		return mem_c_simg;
	}

	public void setMem_c_simg(String mem_c_simg) {
		this.mem_c_simg = mem_c_simg;
	}

	public int getUp_code() {
		return up_code;
	}

	public void setUp_code(int up_code) {
		this.up_code = up_code;
	}


}