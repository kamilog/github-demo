package goodjob.dto;

import java.sql.Date;

public class GJ_BOARD_FREE_DTO {
	private int board_free_num;
	private String board_free_title;
	private String board_free_contents;
	private Date board_free_date;
	private int board_free_ref;
	private int board_free_lev;
	private int board_free_step;
	private String mem_p_id;
	
	public GJ_BOARD_FREE_DTO() {}

	public GJ_BOARD_FREE_DTO(int board_free_num, String board_free_title, String board_free_contents,
			Date board_free_date, int board_free_ref, int board_free_lev, int board_free_step, String mem_p_id) {
		super();
		this.board_free_num = board_free_num;
		this.board_free_title = board_free_title;
		this.board_free_contents = board_free_contents;
		this.board_free_date = board_free_date;
		this.board_free_ref = board_free_ref;
		this.board_free_lev = board_free_lev;
		this.board_free_step = board_free_step;
		this.mem_p_id = mem_p_id;
	}

	public int getBoard_free_num() {
		return board_free_num;
	}

	public void setBoard_free_num(int board_free_num) {
		this.board_free_num = board_free_num;
	}

	public String getBoard_free_title() {
		return board_free_title;
	}

	public void setBoard_free_title(String board_free_title) {
		this.board_free_title = board_free_title;
	}

	public String getBoard_free_contents() {
		return board_free_contents;
	}

	public void setBoard_free_contents(String board_free_contents) {
		this.board_free_contents = board_free_contents;
	}

	public Date getBoard_free_date() {
		return board_free_date;
	}

	public void setBoard_free_date(Date board_free_date) {
		this.board_free_date = board_free_date;
	}

	public int getBoard_free_ref() {
		return board_free_ref;
	}

	public void setBoard_free_ref(int board_free_ref) {
		this.board_free_ref = board_free_ref;
	}

	public int getBoard_free_lev() {
		return board_free_lev;
	}

	public void setBoard_free_lev(int board_free_lev) {
		this.board_free_lev = board_free_lev;
	}

	public int getBoard_free_step() {
		return board_free_step;
	}

	public void setBoard_free_step(int board_free_step) {
		this.board_free_step = board_free_step;
	}

	public String getMem_p_id() {
		return mem_p_id;
	}

	public void setMem_p_id(String mem_p_id) {
		this.mem_p_id = mem_p_id;
	}
	
	
}
