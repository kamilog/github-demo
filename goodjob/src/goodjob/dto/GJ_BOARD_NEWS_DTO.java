package goodjob.dto;

import java.sql.Date;

public class GJ_BOARD_NEWS_DTO {
	private int board_news_num;
	private String board_news_title;
	private String board_news_contents;
	private Date board_news_date;
	
	public GJ_BOARD_NEWS_DTO() {}

	public GJ_BOARD_NEWS_DTO(int board_news_num, String board_news_title, String board_news_contents,
			Date board_news_date) {
		super();
		this.board_news_num = board_news_num;
		this.board_news_title = board_news_title;
		this.board_news_contents = board_news_contents;
		this.board_news_date = board_news_date;
	}

	public int getBoard_news_num() {
		return board_news_num;
	}

	public void setBoard_news_num(int board_news_num) {
		this.board_news_num = board_news_num;
	}

	public String getBoard_news_title() {
		return board_news_title;
	}

	public void setBoard_news_title(String board_news_title) {
		this.board_news_title = board_news_title;
	}

	public String getBoard_news_contents() {
		return board_news_contents;
	}

	public void setBoard_news_contents(String board_news_contents) {
		this.board_news_contents = board_news_contents;
	}

	public Date getBoard_news_date() {
		return board_news_date;
	}

	public void setBoard_news_date(Date board_news_date) {
		this.board_news_date = board_news_date;
	}
}
