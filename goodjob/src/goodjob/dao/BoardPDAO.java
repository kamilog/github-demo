package goodjob.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import goodjob.dbcp.DbcpBean;
import goodjob.dto.GJ_BAG_DTO;
import goodjob.dto.GJ_BOARD_NEWS_DTO;
import goodjob.dto.GJ_BOARD_NOTICE_DTO;
import goodjob.dto.GJ_DETAIL_RECRUIT;
import goodjob.dto.GJ_MEM_RESUME_DTO;
import goodjob.dto.GJ_RECOMMEND_C_DTO;
import goodjob.dto.GJ_RECRUIT_DETAIL_DTO;
import goodjob.dto.GJ_RESUME_S;

public class BoardPDAO {
	public int comu_freeboard_write(String board_free_title, String board_free_contents, String mem_p_id){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		try{
			String sql="insert into gj_board_free values(gj_board_free_seq.nextval,?,?,sysdate,0,0,0,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, board_free_title);
			pstmt.setString(2, board_free_contents);
			pstmt.setString(3, mem_p_id);
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null, pstmt, con);
		}
	}
	
	
	public ArrayList<GJ_RECRUIT_DETAIL_DTO> login_c_reco(String mem_p_id, int jik2_code){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<GJ_RECRUIT_DETAIL_DTO> list=new ArrayList<>();
		try{
			String sql="SELECT distinct *   FROM gj_mem_recruit cr, gj_mem_c mc, gj_c_grade cg  "
					+ " WHERE  cr.MEM_C_ID = mc.mem_c_id AND mc.mem_c_id = cg.MEM_C_ID "
					+ "AND jik2_code in (SELECT jik2_code FROM gj_mem_resume "
					+ "WHERE mem_p_id = ? and jik2_code=?) and rownum<4 ORDER BY grade DESC";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mem_p_id);
			pstmt.setInt(1, jik2_code);
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
				int mem_cr_num=rs.getInt("mem_cr_num");
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
				jik2_code=rs.getInt("jik2_code");
			
				dto= new GJ_RECRUIT_DETAIL_DTO(mem_c_id, mem_c_pwd, mem_c_name, mem_c_comnum,
						mem_c_bossname, mem_c_addr, mem_c_homepage, mem_c_intro, mem_c_year,
						mem_c_sawon, mem_c_jabon, mem_c_machul, mem_c_date, mem_c_img, mem_c_simg,
						up_code, mem_cr_num, mem_cr_title, mem_cr_gyoung, mem_cr_sal,
						mem_cr_jobtype, mem_cr_jobtime, mem_cr_rtime, mem_cr_chadate, 
						mem_cr_workout, mem_cr_recruitnumber, mem_cr_age, mem_cr_gender,
						mem_cr_document, mem_cr_howto, mem_cr_addr, mem_cr_qna, mem_cr_qnaname,
						mem_cr_hak, mem_cr_license, mem_cr_lan_cer, mem_cr_lan, mem_cr_date,
						mem_cr_check, mem_cr_hit, jik2_code);
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
	
	public ArrayList<GJ_MEM_RESUME_DTO> resume_title(String mem_p_id){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<GJ_MEM_RESUME_DTO> list=new ArrayList<>();
		try{

			String sql="select * from gj_mem_resume where mem_p_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mem_p_id);
			rs=pstmt.executeQuery();
			GJ_MEM_RESUME_DTO  dto=null;
			while(rs.next()){
				dto=new GJ_MEM_RESUME_DTO();
				dto.setMem_pr_num(rs.getInt("mem_pr_num"));
				dto.setMem_pr_title(rs.getString("mem_pr_title"));
				dto.setMem_pr_simg(rs.getString("mem_pr_simg"));
				dto.setJik2_code(rs.getInt("jik2_code"));
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
	public ArrayList<GJ_RECOMMEND_C_DTO> re_c(int mem_pr_num){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<GJ_RECOMMEND_C_DTO> list=new ArrayList<>();
		try{

			String sql="select * from (select mem_cr_num, mem_cr_title "
					+ "from gj_mem_recruit cr, gj_c_grade g where cr.MEM_C_ID=g.MEM_C_ID "
					+ "and jik2_code=(select jik2_code from gj_mem_resume where mem_pr_num=?) "
					+ " ORDER BY to_number(grade_money) desc) where rownum<4";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, mem_pr_num);
			rs=pstmt.executeQuery();
			GJ_RECOMMEND_C_DTO  dto=null;
			while(rs.next()){
				int mem_cr_num=rs.getInt("mem_cr_num");
				String mem_cr_title=rs.getString("mem_cr_title");
				dto=new GJ_RECOMMEND_C_DTO(mem_cr_num, mem_cr_title);
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
	public int like_resume(GJ_BAG_DTO dto){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		try{
			String sql=" insert into gj_bag values(gj_bag_seq.nextval,?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, dto.getBag_comment());
			pstmt.setString(2, dto.getMem_c_id());
			pstmt.setInt(3, dto.getMem_pr_num());
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null, pstmt, con);
		}
	}
	public ArrayList<Integer> resume_star(String mem_c_id){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<Integer> list=new ArrayList<>();
		try{

			String sql="select*from gj_mem_resume mr, gj_bag b where mr.mem_pr_num=b.mem_pr_num and mem_c_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mem_c_id);
			rs=pstmt.executeQuery();
			while(rs.next()){
				list.add(rs.getInt(1));
			}
			return list;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public ArrayList<GJ_RESUME_S> wish_resume(String mem_c_id){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<GJ_RESUME_S> list=new ArrayList<>();
		try{
			String sql="select mr.mem_pr_num \"NUM\", mp.mem_p_name \"NAME\", mp.mem_p_gender \"GENDER\", to_number(to_char(sysdate, 'yyyy'))- to_number(substr(mp.mem_p_birth,0,2))+1-1900 \"AGE\", "
		+ "mr.mem_pr_title \"TITLE\", jk1.jik1_name||'-'||jk2.jik2_name \"JIK\", mr.mem_pr_gyoung \"GYOUNG\", NVL(lan.cnt,0) \"LAN_CNT\", NVL(lic.cnt,0)\"LIC_CNT\" ,mr.mem_pr_date \"DATE\" from "
		+ "gj_mem_p mp, gj_mem_resume mr, (select mem_pr_num, count(*) \"CNT\" from gj_lan group by  mem_pr_num) lan,  (select mem_pr_num, count(*)\"CNT\" from gj_license group by mem_pr_num) lic, "
		+ "gj_jik1 jk1, gj_jik2 jk2 where jk1.jik1_code = jk2.jik1_code and jk2.jik2_code(+) = mr.jik2_code and mp.mem_p_id = mr.mem_p_id and mr.mem_pr_num = lan.mem_pr_num(+) and mr.mem_pr_num = lic.mem_pr_num(+) and mr.mem_pr_check = '1' and "
		+ "mr.mem_pr_num=any(select g.mem_pr_num from gj_bag g, gj_mem_resume mr where g.mem_pr_num=mr.mem_pr_num and g.mem_c_id=?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mem_c_id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_RESUME_S grs = new GJ_RESUME_S(rs.getInt("NUM"), rs.getString("NAME"), rs.getString("GENDER"), rs.getInt("AGE"), rs.getString("TITLE"), 
						rs.getString("JIK"), rs.getString("GYOUNG"), rs.getInt("LAN_CNT"), rs.getInt("LIC_CNT"), rs.getDate("DATE") );
				list.add(grs);
			}
						
			return list;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public int delete_bag(String mem_c_id ,int mem_pr_num){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		try{
			String sql="delete from gj_bag where mem_c_id=? and mem_pr_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mem_c_id);
			pstmt.setInt(2, mem_pr_num);
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null, pstmt, con);
		}
	}
	public ArrayList<GJ_DETAIL_RECRUIT>primium_recruit(){
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select * from(select mem_cr_num,mem_c_simg,mem_cr_title,rownum "
	            + "from gj_c_Grade gg,GJ_MEM_C c,GJ_MEM_RECRUIT mr "
	            + "where gg.MEM_C_ID=c.mem_c_id and gg.MEM_C_ID=mr.MEM_C_ID"
	            + " and c.MEM_C_id=mr.MEM_C_ID order by to_number(grade_money)desc)a where rownum<4";
		try{
			con=DbcpBean.getConn();
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			ArrayList<GJ_DETAIL_RECRUIT>list=new ArrayList<>();
			while(rs.next()){
				GJ_DETAIL_RECRUIT dto=new GJ_DETAIL_RECRUIT();
				dto.setSimg(rs.getString("mem_c_simg"));
				dto.setTitle(rs.getString("mem_cr_title"));
				dto.setNum(rs.getInt("mem_cr_num"));
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
	   public ArrayList<GJ_BOARD_NOTICE_DTO>div_notice(){
		      Connection con=null;
		      PreparedStatement pstmt=null;
		      ResultSet rs=null;
		      String sql="select a.*,rownum from (select*from gj_board_notice order by board_notice_date desc)a where rownum<6";
		      try{
		         con=DbcpBean.getConn();
		         pstmt=con.prepareStatement(sql);
		         rs=pstmt.executeQuery();
		         ArrayList<GJ_BOARD_NOTICE_DTO>list=new ArrayList<>();
		         while(rs.next()){
		            GJ_BOARD_NOTICE_DTO dto=new GJ_BOARD_NOTICE_DTO();
		            dto.setBoard_notice_num(rs.getInt("board_notice_num"));
		            dto.setBoard_notice_title(rs.getString("board_notice_title"));         
		            
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
		   public ArrayList<GJ_BOARD_NEWS_DTO>div_news(){
		      Connection con=null;
		      PreparedStatement pstmt=null;
		      ResultSet rs=null;
		      String sql="select a.*,rownum from (select*from gj_board_news order by board_news_date desc)a where rownum<6";
		      try{
		         con=DbcpBean.getConn();
		         pstmt=con.prepareStatement(sql);
		         rs=pstmt.executeQuery();
		         ArrayList<GJ_BOARD_NEWS_DTO>list=new ArrayList<>();
		         while(rs.next()){
		            GJ_BOARD_NEWS_DTO dto=new GJ_BOARD_NEWS_DTO();
		            dto.setBoard_news_num(rs.getInt("board_news_num"));
		            dto.setBoard_news_title(rs.getString("board_news_title"));         
		            
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
	
}
