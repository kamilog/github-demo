package goodjob.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import goodjob.dbcp.DbcpBean;
import goodjob.dto.GJ_BOARD_FREE_DTO;
import goodjob.dto.GJ_BOARD_NEWS_DTO;
import goodjob.dto.GJ_BOARD_NOTICE_DTO;
import goodjob.dto.GJ_C_GRADE_DTO;
import goodjob.dto.GJ_C_RECRUIT_DTO;
import goodjob.dto.GJ_MEM_C_DTO;
import goodjob.dto.GJ_MEM_P_DTO;
import goodjob.dto.GJ_MEM_RECRUIT_DTO;
import goodjob.dto.GJ_RECRUIT_DETAIL_DTO;

public class AdminPDAO {
	
	public ArrayList<GJ_MEM_C_DTO> search_c(String select,String search,int startRow,int endRow){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			String sql="select * from(select a.*, rownum rnum from (SELECT * FROM gj_mem_c "
					 + "WHERE "+select+" LIKE '%"+search+"%' order by mem_c_date desc)a) "
				  	 + "where rnum>=? and rnum<=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs=pstmt.executeQuery();
			ArrayList<GJ_MEM_C_DTO> list=new ArrayList<>();
			while(rs.next()){
				String mem_c_id=rs.getString("mem_c_id");
				String mem_c_pwd=rs.getString("mem_c_pwd");
				String mem_c_name=rs.getString("mem_c_name");
				String mem_c_comnum=rs.getString("mem_c_comnum");
				String mem_c_addr=rs.getString("mem_c_addr");
				String mem_c_bossname=rs.getString("mem_c_bossname");
				String mem_c_homepage=rs.getString("mem_c_homepage");
				String mem_c_intro=rs.getString("mem_c_intro");
				String mem_c_year=rs.getString("mem_c_year");
				String mem_c_sawon=rs.getString("mem_c_sawon");
				String mem_c_jabon=rs.getString("mem_c_jabon");
				String mem_c_machul=rs.getString("mem_c_machul");
				Date mem_c_date=rs.getDate("mem_c_date");
				String mem_c_img=rs.getString("mem_c_img");
				String mem_c_simg=rs.getString("mem_c_simg");
				int up_code=rs.getInt("up_code");
				
				GJ_MEM_C_DTO dto=new GJ_MEM_C_DTO(mem_c_id, mem_c_pwd, mem_c_name, mem_c_comnum, mem_c_bossname,
													mem_c_addr, mem_c_homepage, mem_c_intro, mem_c_year, mem_c_sawon, 
													mem_c_jabon,mem_c_machul, mem_c_date, mem_c_img, mem_c_simg, up_code);
				list.add(dto);
			}
			return list;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public ArrayList<GJ_MEM_P_DTO> search_p(String select,String search,int startRow,int endRow){
			Connection con=DbcpBean.getConn();
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			try{
				String sql="select * from(select a.*, rownum rnum from (SELECT * FROM gj_mem_p "
						 + "WHERE "+select+" LIKE '%"+search+"%' order by mem_p_date desc)a) "
					  	 + "where rnum>=? and rnum<=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				rs=pstmt.executeQuery();
				ArrayList<GJ_MEM_P_DTO> list=new ArrayList<>();
				while(rs.next()){
					String mem_p_id=rs.getString("mem_p_id");
					String mem_p_pwd=rs.getString("mem_p_pwd");
					String mem_p_name=rs.getString("mem_p_name");
					String mem_p_addr=rs.getString("mem_p_addr");
					String mem_p_phone=rs.getString("mem_p_phone");
					String mem_p_email=rs.getString("mem_p_email");
					String mem_p_gender=rs.getString("mem_p_gender");
					Date mem_p_date=rs.getDate("mem_p_date");
					String mem_p_birth=rs.getString("mem_p_birth");
					GJ_MEM_P_DTO dto= new GJ_MEM_P_DTO(mem_p_id, mem_p_pwd, mem_p_name, mem_p_birth,
							mem_p_addr, mem_p_phone, mem_p_email, mem_p_gender, mem_p_date);
					list.add(dto);
				}
				return list;
			}catch(SQLException se){
				System.out.println(se.getMessage());
				return null;
			}finally{
				DbcpBean.close(rs, pstmt, con);
			}
		}
	
	public GJ_MEM_C_DTO list_c_detail(String mem_c_id){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			String sql="select * from gj_mem_c where mem_c_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mem_c_id);
			rs=pstmt.executeQuery();

			GJ_MEM_C_DTO dto=null ;
			while(rs.next()){
				mem_c_id=rs.getString("mem_c_id");
				String mem_c_pwd=rs.getString("mem_c_pwd");
				String mem_c_name=rs.getString("mem_c_name");
				String mem_c_comnum=rs.getString("mem_c_comnum");
				String mem_c_addr=rs.getString("mem_c_addr");
				String mem_c_bossname=rs.getString("mem_c_bossname");
				String mem_c_homepage=rs.getString("mem_c_homepage");
				String mem_c_intro=rs.getString("mem_c_intro");
				String mem_c_year=rs.getString("mem_c_year");
				String mem_c_sawon=rs.getString("mem_c_sawon");
				String mem_c_jabon=rs.getString("mem_c_jabon");
				String mem_c_machul=rs.getString("mem_c_machul");
				Date mem_c_date=rs.getDate("mem_c_date");
				String mem_c_img=rs.getString("mem_c_img");
				String mem_c_simg=rs.getString("mem_c_simg");
				int up_code=rs.getInt("up_code");
				dto=new GJ_MEM_C_DTO(mem_c_id, mem_c_pwd, mem_c_name, mem_c_comnum, mem_c_bossname,
						mem_c_addr, mem_c_homepage, mem_c_intro, mem_c_year, mem_c_sawon,
						mem_c_jabon, mem_c_machul, mem_c_date, mem_c_img, mem_c_simg, up_code);
			}
			return dto;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public GJ_MEM_P_DTO list_p_detail(String mem_p_id){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			String sql="select * from gj_mem_p where mem_p_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mem_p_id);
			rs=pstmt.executeQuery();

			GJ_MEM_P_DTO dto=null ;
			while(rs.next()){
				mem_p_id=rs.getString("mem_p_id");
				String mem_p_pwd=rs.getString("mem_p_pwd");
				String mem_p_name=rs.getString("mem_p_name");
				String mem_p_addr=rs.getString("mem_p_addr");
				String mem_p_phone=rs.getString("mem_p_phone");
				String mem_p_email=rs.getString("mem_p_email");
				String mem_p_gender=rs.getString("mem_p_gender");
				Date mem_p_date=rs.getDate("mem_p_date");
				String mem_p_birth=rs.getString("mem_p_birth");
				dto=new GJ_MEM_P_DTO(mem_p_id, mem_p_pwd, mem_p_name, 
						mem_p_birth, mem_p_addr, mem_p_phone,
						mem_p_email, mem_p_gender, mem_p_date);
			}
			return dto;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public GJ_C_GRADE_DTO grade_c(String mem_c_id){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		GJ_C_GRADE_DTO dto=null;
		try{
			String sql="select * from gj_c_grade where mem_c_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mem_c_id);
			rs=pstmt.executeQuery();
			while(rs.next()){
				int grade_num =rs.getInt("grade_num");
				String grade_money=rs.getString("grade_money"); 
				String grade=rs.getString("grade");
				mem_c_id=rs.getString("mem_c_id");
				dto=new GJ_C_GRADE_DTO(grade_num, grade_money, grade, mem_c_id);
			}
			return dto;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public int searchCount_c(String select,String search){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int num=0;
		try{
			String sql="SELECT count(*) FROM gj_mem_c WHERE "+select+" LIKE '%"+search+"%'"; // 검색된 행의 총 개수 구하기
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				num=rs.getInt(1);
				
			}
			return num;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public int searchCount_p(String select,String search){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int num=0;
		try{
			String sql="SELECT count(*) FROM gj_mem_p WHERE "+select+" LIKE '%"+search+"%'"; 
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				num=rs.getInt(1);
			}
			return num;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public int delete_p(String mem_p_id){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		try{
			String sql="delete from gj_mem_p where mem_p_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mem_p_id);
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null, pstmt, con);
		}
	}
	public int delete_c(String mem_c_id ){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		try{
			String sql="delete from gj_mem_c where mem_c_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mem_c_id);
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null, pstmt, con);
		}
	}
	public int grade_update(String grade_money,String grade, String mem_c_id ){

		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		
		try{
			String sql="update gj_c_grade set grade=? ,grade_money=? where mem_c_id=?";
			pstmt=con.prepareStatement(sql);
			GJ_C_GRADE_DTO dto=new GJ_C_GRADE_DTO(0, grade_money, grade, mem_c_id);
			pstmt.setString(1, dto.getGrade_money());
			pstmt.setString(2, dto.getGrade());
			pstmt.setString(3, dto.getMem_c_id());
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null, pstmt, con);
		}
	}
	public int insert(GJ_BOARD_NOTICE_DTO dto){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		try{
			String sql="insert into gj_board_notice values(gj_board_notice_seq.nextval,?,?,sysdate)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, dto.getBoard_notice_title());
			pstmt.setString(2, dto.getBoard_notice_contents());
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null, pstmt, con);
		}
	}
	
	public int news_insert(GJ_BOARD_NEWS_DTO dto){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		try{
			String sql="insert into gj_board_news values(gj_board_news_seq.nextval,?,?,sysdate)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, dto.getBoard_news_title());
			pstmt.setString(2, dto.getBoard_news_contents());
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null, pstmt, con);
		}
	}
	public int notice_update(int board_notice_num,String board_notice_title, String board_notice_contents){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		
		try{
			String sql="update gj_board_notice set board_notice_title=?, board_notice_contents=? where board_notice_num=?";
			pstmt=con.prepareStatement(sql);
			GJ_BOARD_NOTICE_DTO dto=new GJ_BOARD_NOTICE_DTO(board_notice_num, board_notice_title, 
															board_notice_contents, null);
			pstmt.setString(1, dto.getBoard_notice_title());
			pstmt.setString(2, dto.getBoard_notice_contents());
			pstmt.setInt(3, dto.getBoard_notice_num());
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null, pstmt, con);
		}
	}
	public int news_update(int board_news_num,String board_news_title, String board_news_contents){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		
		try{
			String sql="update gj_board_news set board_news_title=?, board_news_contents=? where board_news_num=?";
			pstmt=con.prepareStatement(sql);
			GJ_BOARD_NEWS_DTO dto=new GJ_BOARD_NEWS_DTO(board_news_num, board_news_title, board_news_contents, null);
			pstmt.setString(1, dto.getBoard_news_title());
			pstmt.setString(2, dto.getBoard_news_contents());
			pstmt.setInt(3, dto.getBoard_news_num());
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null, pstmt, con);
		}
	}
	public GJ_BOARD_NEWS_DTO news_detail(int board_news_num){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			String sql="select * from gj_board_news where board_news_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, board_news_num);
			rs=pstmt.executeQuery();

			GJ_BOARD_NEWS_DTO dto=null ;
			while(rs.next()){
				board_news_num=rs.getInt("board_news_num");
				String board_news_title=rs.getString("board_news_title");
				String board_news_contents=rs.getString("board_news_contents");
				Date board_news_date=rs.getDate("board_news_date");
				dto=new GJ_BOARD_NEWS_DTO(board_news_num, board_news_title, 
						board_news_contents, board_news_date);
			}
			return dto;
		}catch(SQLException se){
			System.out.println(se.getMessage()+"dddd");
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public GJ_BOARD_NOTICE_DTO notice_detail(int board_notice_num){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			String sql="select * from gj_board_notice where board_notice_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, board_notice_num);
			rs=pstmt.executeQuery();

			GJ_BOARD_NOTICE_DTO dto=null ;
			while(rs.next()){
				board_notice_num=rs.getInt("board_notice_num");
				String board_notice_title=rs.getString("board_notice_title");
				String board_notice_contents=rs.getString("board_notice_contents");
				Date board_notice_date=rs.getDate("board_notice_date");
				dto=new GJ_BOARD_NOTICE_DTO(board_notice_num, board_notice_title, 
						board_notice_contents, board_notice_date);
			}
			return dto;
		}catch(SQLException se){
			System.out.println(se.getMessage()+"dddd");
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public GJ_BOARD_FREE_DTO board_free_detail(int board_free_num){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			String sql="select * from gj_board_free where board_free_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, board_free_num);
			rs=pstmt.executeQuery();

			GJ_BOARD_FREE_DTO dto=null ;
			while(rs.next()){
				board_free_num=rs.getInt("board_free_num");
				String mem_p_id=rs.getString("mem_p_id");
				String board_free_title=rs.getString("board_free_title");
				String board_free_contents=rs.getString("board_free_contents");
				Date board_free_date=rs.getDate("board_free_date");
				dto=new GJ_BOARD_FREE_DTO(board_free_num, board_free_title, 
						board_free_contents, board_free_date, 0, 0,0, mem_p_id);
			}
			return dto;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public int delete_free(int board_free_num ){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		try{
			String sql="delete from gj_board_free where board_free_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, board_free_num);
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null, pstmt, con);
		}
	}
	public ArrayList<GJ_BOARD_NOTICE_DTO> notice_search(String select,String search,int startNum,int endNum){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			String sql="select * from (select a.*, rownum rnum from (SELECT * FROM gj_board_notice "
					 + "WHERE "+select+" LIKE '%"+search+"%' order by board_notice_num desc)a) "
				  	 + "where rnum>=? and rnum<=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startNum);
			pstmt.setInt(2, endNum);
			rs=pstmt.executeQuery();
			ArrayList<GJ_BOARD_NOTICE_DTO> list=new ArrayList<>();
			while(rs.next()){
				int board_notice_num=rs.getInt("board_notice_num");
				String board_notice_title=rs.getString("board_notice_title");
				String board_notice_contents=rs.getString("board_notice_contents");
				Date board_notice_date=rs.getDate("board_notice_date");
				GJ_BOARD_NOTICE_DTO dto=new GJ_BOARD_NOTICE_DTO(board_notice_num, board_notice_title, 
																board_notice_contents, board_notice_date);
				list.add(dto);
			}
			return list;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public ArrayList<GJ_BOARD_NEWS_DTO> news_search(String select,String search,int startNum,int endNum){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			String sql="select * from (select a.*, rownum rnum from (SELECT * FROM gj_board_news "
					 + "WHERE "+select+" LIKE '%"+search+"%' order by board_news_num desc)a) "
				  	 + "where rnum>=? and rnum<=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startNum);
			pstmt.setInt(2, endNum);
			rs=pstmt.executeQuery();
			ArrayList<GJ_BOARD_NEWS_DTO> list=new ArrayList<>();
			while(rs.next()){
				int board_news_num=rs.getInt("board_news_num");
				String board_news_title=rs.getString("board_news_title");
				String board_news_contents=rs.getString("board_news_contents");
				Date board_news_date=rs.getDate("board_news_date");
				GJ_BOARD_NEWS_DTO dto=new GJ_BOARD_NEWS_DTO(board_news_num, board_news_title, 
																board_news_contents, board_news_date);
				list.add(dto);
			}
			return list;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public ArrayList<GJ_BOARD_FREE_DTO> board_free(String select,String search,int startNum,int endNum){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			String sql="select * from(select a.*, rownum rnum from (SELECT * FROM gj_board_free "
					 + "WHERE "+select+" LIKE '%"+search+"%' order by board_free_num desc)a) "
				  	 + "where rnum>=? and rnum<=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startNum);
			pstmt.setInt(2, endNum);
			rs=pstmt.executeQuery();
			ArrayList<GJ_BOARD_FREE_DTO> list=new ArrayList<>();
			while(rs.next()){
				int board_free_num=rs.getInt("board_free_num");
				String board_free_title=rs.getString("board_free_title");
				String board_free_contents=rs.getString("board_free_contents");
				Date board_free_date=rs.getDate("board_free_date");
				String mem_p_id=rs.getString("mem_p_id");
				GJ_BOARD_FREE_DTO dto=new GJ_BOARD_FREE_DTO(board_free_num, board_free_title,
															board_free_contents, board_free_date, 0, 
															0, 0, mem_p_id);
				list.add(dto);
			}
			return list;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public int count_board_free(String select,String search){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int num=0;
		try{
			String sql="SELECT count(*) FROM gj_board_free WHERE "+select+" LIKE '%"+search+"%'"; 
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				num=rs.getInt(1);
			}
			return num;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public ArrayList<GJ_C_RECRUIT_DTO> board_recruit(String select,String search,int startNum,int endNum){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			String sql="select * from(select a.*, rownum rnum from "
					+ "(select mem_cr_num,mem_Cr_gyoung,mem_c_name,mem_cr_date,mem_cr_sal,mr.mem_c_id"
					+ " from gj_mem_recruit mr,gj_mem_c mc	"
					+ "where mr.mem_c_id=mc.mem_c_id and "+select+" LIKE '%"+search+"%' order by mem_cr_num desc)a)"
							+ "	where rnum>=? and rnum<=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startNum);
			pstmt.setInt(2, endNum);
			rs=pstmt.executeQuery();
			ArrayList<GJ_C_RECRUIT_DTO> list=new ArrayList<>();
			while(rs.next()){
				int mem_cr_num=rs.getInt("mem_cr_num");
				String mem_cr_gyoung=rs.getString("mem_cr_gyoung");
				String mem_c_name=rs.getString("mem_c_name");
				Date mem_cr_date=rs.getDate("mem_cr_date");
				String mem_cr_sal=rs.getString("mem_cr_sal");
				String mem_c_id=rs.getString("mem_c_id");
				
				GJ_C_RECRUIT_DTO dto=new GJ_C_RECRUIT_DTO(mem_cr_num, mem_cr_gyoung, 
						mem_c_name, mem_cr_date, mem_cr_sal,mem_c_id);	
				list.add(dto);		
			}
			return list;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public String c_name(String mem_c_id){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			String sql="select mem_c_name from gj_mem_c where mem_c_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mem_c_id);
			rs=pstmt.executeQuery();
			String mem_c_name="";
			while(rs.next()){
				mem_c_name=rs.getString("mem_c_name");
			}
			return mem_c_name;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public int count_recruit(String select,String search){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int num=0;
		try{
			String sql="SELECT count(*) FROM gj_mem_recruit WHERE "+select+" LIKE '%"+search+"%'"; 
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				num=rs.getInt(1);
			}
			return num;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public int searchCount_news(String select,String search){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int num=0;
		try{
			String sql="select NVL(count(*),0) cnt from gj_board_news WHERE "+select+" LIKE '%"+search+"%'"; 
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				num=rs.getInt(1);
			}
			return num;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public int searchCount_list(String select,String search){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int num=0;
		try{
			String sql="select NVL(count(*),0) cnt from gj_board_notice WHERE "+select+" LIKE '%"+search+"%'"; 
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				num=rs.getInt(1);
			}
			return num;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public int getCount_list(){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int num=0;
		try{
			String sql="select NVL(count(*),0) cnt from gj_board_notice";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				num=rs.getInt(1);
			}
			return num;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public GJ_RECRUIT_DETAIL_DTO  board_recruit_detail(int mem_cr_num){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		System.out.println("num"+mem_cr_num);
		try{
			String sql="select * from gj_mem_recruit mr,gj_mem_c mc	"
					+ "where mr.mem_c_id=mc.mem_c_id and mem_cr_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, mem_cr_num);
			rs=pstmt.executeQuery();

			GJ_RECRUIT_DETAIL_DTO  dto=null ;
			while(rs.next()){
				String mem_c_pwd=rs.getString("mem_c_pwd");
				String mem_c_name=rs.getString("mem_c_name");
				String mem_c_comnum=rs.getString("mem_c_comnum");
				String mem_c_addr=rs.getString("mem_c_addr");
				String mem_c_bossname=rs.getString("mem_c_bossname");
				String mem_c_homepage=rs.getString("mem_c_homepage");
				String mem_c_intro=rs.getString("mem_c_intro");
				String mem_c_year=rs.getString("mem_c_year");
				String mem_c_sawon=rs.getString("mem_c_sawon");
				String mem_c_jabon=rs.getString("mem_c_jabon");
				String mem_c_machul=rs.getString("mem_c_machul");
				Date mem_c_date=rs.getDate("mem_c_date");
				String mem_c_img=rs.getString("mem_c_img");
				String mem_c_simg=rs.getString("mem_c_simg");
				int up_code=rs.getInt("up_code");
				mem_cr_num=rs.getInt("mem_cr_num");
				String mem_cr_title=rs.getString("mem_cr_title");
				String mem_cr_gyoung=rs.getString("mem_cr_gyoung");
				String mem_cr_sal=rs.getString("mem_cr_sal");
				String mem_cr_jobtype=rs.getString("mem_cr_jobtype");
				String mem_cr_jobtime=rs.getString("mem_cr_jobtime");
				String mem_cr_rtime=rs.getString("mem_cr_rtime");
				String mem_cr_chadate=rs.getString("mem_cr_chadate");
				String mem_cr_workout=rs.getString("mem_cr_workout");
				int mem_cr_recruitnumber=rs.getInt("mem_cr_recruitnumber");
				String mem_cr_age=rs.getString("mem_cr_age");
				String mem_cr_gender=rs.getString("mem_cr_gender");
				String mem_cr_document=rs.getString("mem_cr_document");
				String mem_cr_howto=rs.getString("mem_cr_howto");
				String mem_cr_addr=rs.getString("mem_cr_addr");
				String mem_cr_qna=rs.getString("mem_cr_qna");
				String mem_cr_qnaname=rs.getString("mem_cr_qnaname");
				String mem_cr_hak=rs.getString("mem_cr_hak");
				String mem_cr_license=rs.getString("mem_cr_license");
				String mem_cr_lan_cer=rs.getString("mem_cr_lan_cer");
				String mem_cr_lan=rs.getString("mem_cr_lan");
				Date mem_cr_date=rs.getDate("mem_cr_date");
				boolean mem_cr_check=rs.getBoolean("mem_cr_check");
				int mem_cr_hit=rs.getInt("mem_cr_hit");
				String mem_c_id=rs.getString("mem_c_id");
				
				dto=new GJ_RECRUIT_DETAIL_DTO(mem_c_id, mem_c_pwd, mem_c_name, 
						mem_c_comnum, mem_c_bossname, mem_c_addr, mem_c_homepage, mem_c_intro,
						mem_c_year, mem_c_sawon, mem_c_jabon, mem_c_machul, mem_c_date, mem_c_img,
						mem_c_simg, up_code, mem_cr_num, mem_cr_title,mem_cr_gyoung, mem_cr_sal, mem_cr_jobtype,
						mem_cr_jobtime, mem_cr_rtime, mem_cr_chadate, mem_cr_workout, mem_cr_recruitnumber,
						mem_cr_age, mem_cr_gender, mem_cr_document, mem_cr_howto, mem_cr_addr, mem_cr_qna,
						mem_cr_qnaname, mem_cr_hak, mem_cr_license, mem_cr_lan_cer, mem_cr_lan, 
						mem_cr_date, mem_cr_check, mem_cr_hit, 0);
				
			}
			return dto;
		}catch(SQLException se){
			System.out.println(se.getMessage()+"하히하힝");
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
}


