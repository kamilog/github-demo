package goodjob.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import goodjob.dbcp.DbcpBean;
import goodjob.dto.GJ_BOARD_FREE_DTO;
import goodjob.dto.GJ_BOARD_NEWS_DTO;
import goodjob.dto.GJ_BOARD_NOTICE_DTO;
import goodjob.dto.GJ_MY_RECRUIT_DTO;
import goodjob.dto.GJ_MY_RESUME;
import goodjob.dto.GJ_RECRUIT_KIM;
import goodjob.dto.GJ_RESUME_KIM;

public class MyRecruitKDAO {
	/*
	 * ArrayList<GJ_BOARD_NOTICE_DTO> noticeList = dao.notice_list(String findall);
		ArrayList<GJ_BOARD_NEWS_DTO> newsList = dao.news_list(String findall);
		ArrayList<GJ_BOARD_FREE_DTO> freeList = dao.free_list(String findall);
		ArrayList<GJ_RESUME_KIM> resumeList = dao.resume_list(String findall);
		ArrayList<GJ_RECRUIT_KIM> recruitList = dao.recruit_list(String findall);
	 * 
	 */
	public int insertRec(int mem_cr_num, int mem_pr_num){
		Connection con = null;
		PreparedStatement pstmt = null;
		int n=-1;
		try{ 
			con = DbcpBean.getConn();
			String sql = "insert into gj_jiwon values(gj_hak_seq.nextval, ?, ?, sysdate, '0')";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, mem_cr_num);
			pstmt.setInt(2, mem_pr_num);
			n = pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}
		finally{
			DbcpBean.close(null, pstmt, con);
		}
	}
	public ArrayList<GJ_MY_RESUME> myResumeList (String id){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GJ_MY_RESUME> list = new ArrayList<>();
		try{ 
			con = DbcpBean.getConn();
			String sql = "select gm.mem_pr_num, gm.mem_pr_title, gm.mem_pr_date from gj_mem_resume gm, gj_mem_p gp where gp.mem_p_id = gm.mem_p_id and gp.mem_p_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_MY_RESUME dto = new GJ_MY_RESUME(
						rs.getInt(1), rs.getString(2), rs.getDate(3)
				);
				list.add(dto);
						
			}
			return list;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	
	public boolean checkPSN (String id){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean check = false;
		try{ 
			con = DbcpBean.getConn();
			String sql = "select * from gj_mem_p gp where gp.mem_p_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				check = true;
			}
			return check;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return false;
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public boolean checkMyRecruit (String id, int num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean check = false;
		try{ 
			con = DbcpBean.getConn();
			String sql = "select * from gj_jiwon gj, gj_mem_resume gm, gj_mem_p gp where gj.mem_pr_num = gm.mem_pr_num and gp.mem_p_id = gm.mem_p_id and gp.mem_p_id = ? and gj.mem_cr_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, num);
			rs = pstmt.executeQuery();
			if(rs.next()){
				check = true;
			}
			return check;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return false;
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public ArrayList<GJ_RECRUIT_KIM> recruitList (String findall){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GJ_RECRUIT_KIM> list = new ArrayList<>();
		try{ 
			con = DbcpBean.getConn();
			String sql = "select gm.mem_cr_num, gc.mem_c_name, gm.mem_cr_title from gj_mem_recruit gm, gj_mem_c gc where gc.mem_c_id = gm.mem_c_id and gm.mem_cr_title like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+findall+"%");
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_RECRUIT_KIM dto = new GJ_RECRUIT_KIM(
						rs.getInt(1), rs.getString(2), rs.getString(3)
				);
				list.add(dto);
						
			}
			return list;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	
	public ArrayList<GJ_RESUME_KIM> resumeList (String findall){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GJ_RESUME_KIM> list = new ArrayList<>();
		try{ 
			con = DbcpBean.getConn();
			String sql = "select gm.mem_pr_num, gp.mem_p_name, gm.mem_pr_title from gj_mem_resume gm, gj_mem_p gp where gp.mem_p_id = gm.mem_p_id and gm.mem_pr_title like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+findall+"%");
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_RESUME_KIM dto = new GJ_RESUME_KIM(
						rs.getInt(1), rs.getString(2), rs.getString(3)
				);
				list.add(dto);
						
			}
			return list;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	
	public ArrayList<GJ_BOARD_FREE_DTO> freeList (String findall){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GJ_BOARD_FREE_DTO> list = new ArrayList<>();
		try{ 
			con = DbcpBean.getConn();
			String sql = "select * from gj_board_free where board_free_title like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+findall+"%");
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_BOARD_FREE_DTO dto = new GJ_BOARD_FREE_DTO(
						rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), 0,0,0,""
				);
				list.add(dto);
						
			}
			return list;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public ArrayList<GJ_BOARD_NEWS_DTO> newsList (String findall){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GJ_BOARD_NEWS_DTO> list = new ArrayList<>();
		try{ 
			con = DbcpBean.getConn();
			String sql = "select * from gj_board_news where board_news_title like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+findall+"%");
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_BOARD_NEWS_DTO dto = new GJ_BOARD_NEWS_DTO(
						rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4)
				);
				list.add(dto);
						
			}
			return list;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	
	public ArrayList<GJ_BOARD_NOTICE_DTO> noticeList (String findall){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GJ_BOARD_NOTICE_DTO> list = new ArrayList<>();
		try{ 
			con = DbcpBean.getConn();
			String sql = "select * from gj_board_notice where board_notice_title like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+findall+"%");
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_BOARD_NOTICE_DTO dto = new GJ_BOARD_NOTICE_DTO(
						rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4)
				);
				list.add(dto);
						
			}
			return list;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	
	public int outJiwon(int res_num, int rec_num){
		Connection con = null;
		PreparedStatement pstmt = null;
		try{ 
			con = DbcpBean.getConn();
			String sql = "delete from gj_jiwon where mem_pr_num = ? and mem_cr_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, res_num);
			pstmt.setInt(2, rec_num);
			int n = pstmt.executeUpdate();
		
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}
		finally{
			DbcpBean.close(null, pstmt, con);
		}
	}
	
	public ArrayList<GJ_MY_RECRUIT_DTO> my_recruit_list (String id){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GJ_MY_RECRUIT_DTO> list = new ArrayList<>();
		try{ 
			con = DbcpBean.getConn();
			String sql = "select gj.mem_cr_num, gr.mem_pr_num, gm.mem_cr_title, gr.mem_pr_title from gj_mem_resume gr, gj_jiwon gj, gj_mem_recruit gm where gj.mem_cr_num = gm.mem_cr_num and gr.mem_pr_num = gj.mem_pr_num and gr.mem_p_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_MY_RECRUIT_DTO grj = new GJ_MY_RECRUIT_DTO( rs.getInt("mem_cr_num"),  rs.getInt("mem_pr_num"), rs.getString("mem_cr_title"), rs.getString("mem_pr_title"));
				list.add(grj);
			}
			return list;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
}
