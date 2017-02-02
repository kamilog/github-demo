package goodjob.dto;

import java.sql.Date;

public class GJ_BOARD_NOTICE_DTO {
	private int board_notice_num;
	private String board_notice_title;
	private String board_notice_contents;
	private Date board_notice_date;
	
	public GJ_BOARD_NOTICE_DTO() {}

	public GJ_BOARD_NOTICE_DTO(int board_notice_num, String board_notice_title, String board_notice_contents,
			Date board_notice_date) {
		super();
		this.board_notice_num = board_notice_num;
		this.board_notice_title = board_notice_title;
		this.board_notice_contents = board_notice_contents;
		this.board_notice_date = board_notice_date;
	}

	public int getBoard_notice_num() {
		return board_notice_num;
	}

	public void setBoard_notice_num(int board_notice_num) {
		this.board_notice_num = board_notice_num;
	}

	public String getBoard_notice_title() {
		return board_notice_title;
	}

	public void setBoard_notice_title(String board_notice_title) {
		this.board_notice_title = board_notice_title;
	}

	public String getBoard_notice_contents() {
		return board_notice_contents;
	}

	public void setBoard_notice_contents(String board_notice_contents) {
		this.board_notice_contents = board_notice_contents;
	}

	public Date getBoard_notice_date() {
		return board_notice_date;
	}

	public void setBoard_notice_date(Date board_notice_date) {
		this.board_notice_date = board_notice_date;
	}
}
