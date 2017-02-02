package goodjob.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import goodjob.dbcp.DbcpBean;
import goodjob.dto.GJ_C_FILE_DTO;
import goodjob.dto.GJ_C_FILE_L;
import goodjob.dto.GJ_DETAIL_RECRUIT;
import goodjob.dto.GJ_DETAIL_RESUME;
import goodjob.dto.GJ_EXPINFO_L;
import goodjob.dto.GJ_HAK_L;
import goodjob.dto.GJ_LAN_L;
import goodjob.dto.GJ_LICENSE_L;
import goodjob.dto.GJ_P_FILE_DTO;
import goodjob.dto.GJ_P_FILE_L;
import goodjob.dto.GJ_RECRUIT_S;
import goodjob.dto.GJ_REC_JIWON;
import goodjob.dto.GJ_RESUME_S;
import goodjob.dto.GJ_LAN_CER_L;

public class SearchKDAO {
	//listJiwonJa
	public ArrayList<GJ_REC_JIWON> listJiwonJa(String id){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GJ_REC_JIWON> list = new ArrayList<>();
		try{ 
			con = DbcpBean.getConn();
			String sql = "select gr.mem_cr_num, gr.mem_cr_title, NVL(gj.cnt,0) \"CNT\" from gj_mem_recruit gr, gj_mem_c gc, (select mem_cr_num, count(*) \"CNT\" from gj_jiwon group by mem_cr_num) gj "
					+ "where gr.mem_c_id = gc.mem_c_id and gr.mem_cr_num = gj.mem_cr_num(+) and gc.mem_c_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_REC_JIWON grj = new GJ_REC_JIWON( rs.getInt("mem_cr_num"), rs.getString("mem_cr_title"), rs.getInt("CNT"));
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
		
	public ArrayList<GJ_RESUME_S> jiwonJa(int num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GJ_RESUME_S> list = new ArrayList<>();
		try{ 
			con = DbcpBean.getConn();
			String sql = "select mr.mem_pr_num \"NUM\", mp.mem_p_name \"NAME\", mp.mem_p_gender \"GENDER\", to_number(to_char(sysdate, 'yyyy'))- to_number(substr(mp.mem_p_birth,3,2))+1-1900 \"AGE\", "
					+ "mr.mem_pr_title \"TITLE\", jk1.jik1_name||'-'||jk2.jik2_name \"JIK\", mr.mem_pr_gyoung \"GYOUNG\", NVL(lan.cnt,0) \"LAN_CNT\", NVL(lic.cnt,0)\"LIC_CNT\" ,gj.jiwon_date \"DATE\" from "
					+ "gj_mem_p mp, gj_mem_resume mr, (select mem_pr_num, count(*) \"CNT\" from gj_lan group by  mem_pr_num) lan,  (select mem_pr_num, count(*)\"CNT\" from gj_license group by mem_pr_num) lic, "
					+ "gj_jik1 jk1, gj_jik2 jk2, ( select mem_pr_num, jiwon_date from gj_jiwon where mem_cr_num = ?) gj where jk1.jik1_code = jk2.jik1_code and jk2.jik2_code(+) = mr.jik2_code and mp.mem_p_id = mr.mem_p_id and mr.mem_pr_num = lan.mem_pr_num(+) and mr.mem_pr_num = lic.mem_pr_num(+)"
					+ " and mr.mem_pr_num = gj.mem_pr_num";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
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
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public int jiwonCnt(int num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int n = 0;
		try{ 
			con = DbcpBean.getConn();
			String sql = "select count(*) from gj_jiwon where mem_cr_num = ? group by mem_cr_num";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()){
				n = rs.getInt(1);
			}
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	//////////////////////////////////////////////////////////////////////채용공고검색관련
	public ArrayList<GJ_RECRUIT_S> categoryRecruit(String exp,String area,String sal,String jobtype,String jik,String hak, String[] lan_n_list, String[] lan_g_list,String license,String word,int startRow,int endRow){
		ArrayList<GJ_RECRUIT_S> list = new ArrayList<>() ;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] temp = word.trim().split(" ");  // 검색어의 양옆 공백을 자른후 공백을 기준으로 나눔.
		StringBuffer sql = new StringBuffer();
		try{ //재학기간 학력구분 학교명 전공
			con = DbcpBean.getConn();
			sql.append("select * from (select aa.*, rownum \"RNUM\" from (select gm.mem_cr_num, gc.mem_c_name, gm.mem_cr_title, gm.mem_cr_gyoung, gm.mem_cr_hak, gm.mem_cr_jobtype, gm.mem_cr_addr, gm.mem_cr_sal, gm.mem_cr_rtime from gj_mem_recruit gm, gj_mem_c gc where gm.mem_c_id = gc.mem_c_id and gm.mem_cr_check = '1'");
		
			if(temp[0].length() !=0){ //검색값이 빈값이 아닐 경우
				// 제목 희망직종 경력사항 외국어 자격증
				sql.append(" and gm.mem_cr_num = any( select gm.mem_cr_num from gj_mem_recruit gm where ");
				for(int i=0; i<temp.length; i++){
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm, gj_mem_c gc where gc.mem_c_id =  gm.mem_c_id and gc.mem_c_name like '%"+temp[i]+"%') or ");  //업체명
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_title like '%"+temp[i]+"%') or "); //제목
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_gyoung like '%"+temp[i]+"%') or "); //경력
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_hak like '%"+temp[i]+"%') or ");  //학력
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_jobtype like '%"+temp[i]+"%') or ");  //근무형태
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_addr like '%"+temp[i]+"%') or ");  //근무지
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_sal like '%"+temp[i]+"%')");  //연봉
					if(i!= temp.length-1){
						sql.append(" and ");
					}
				}
				sql.append(")");
			}
			if(jik.length() != 0)
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm, gj_jik2 jk2 where jk2.jik2_code = gm.jik2_code and jk2.jik2_name = '"+jik+"')");
			if(exp.length() != 0)
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_gyoung like '%"+exp+"%')");
			if(area.length() != 0)  //지역
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_addr like '%"+area+"%')");
			if(hak.length() != 0)  //학력
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_hak like '%"+hak+"%')");
			if(sal.length() != 0)  //연봉
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_sal like '%"+sal+"%')");
			if(jobtype.length() != 0)  //근무형태
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_jobtype like '%"+jobtype+"%')");
			if(license.length() != 0)  //자격증
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_license like '%"+license+"%')");
			for(int i=0; i<lan_n_list.length; i++){	
				String temp_grade = "";
				if(lan_n_list[i].length() == 0)
					break;
				if(lan_g_list[i].length()!=0 )
					temp_grade = "("+lan_g_list[i] + ")";
				
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_lan like '%"+lan_n_list[i]+temp_grade+"%')");
			}
			
			sql.append(" order by gc.mem_c_name ) aa ) where rnum>="+startRow+" and rnum<="+endRow+"");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_RECRUIT_S dto = new GJ_RECRUIT_S(rs.getInt("mem_cr_num"), rs.getString("mem_c_name"), rs.getString("mem_cr_title"), rs.getString("mem_cr_gyoung"), rs.getString("mem_cr_hak"), 
						rs.getString("mem_cr_jobtype"), rs.getString("mem_cr_addr"), rs.getString("mem_cr_sal"), rs.getString("mem_cr_rtime") );
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
	
	
	public ArrayList<GJ_RECRUIT_S> categoryRecruit(String select, String order,String exp,String area,String sal,String jobtype,String jik,String hak, String[] lan_n_list, String[] lan_g_list,String license,String word,int startRow,int endRow){
		ArrayList<GJ_RECRUIT_S> list = new ArrayList<>() ;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] temp = word.trim().split(" ");  // 검색어의 양옆 공백을 자른후 공백을 기준으로 나눔.
		StringBuffer sql = new StringBuffer();
		try{ //재학기간 학력구분 학교명 전공
			con = DbcpBean.getConn();
			sql.append("select * from (select aa.*, rownum \"RNUM\" from (select gm.mem_cr_num, gc.mem_c_name, gm.mem_cr_title, gm.mem_cr_gyoung, gm.mem_cr_hak, gm.mem_cr_jobtype, gm.mem_cr_addr, gm.mem_cr_sal, gm.mem_cr_rtime from gj_mem_recruit gm, gj_mem_c gc where gm.mem_c_id = gc.mem_c_id and gm.mem_cr_check = '1'");
		
			if(temp[0].length() !=0){ //검색값이 빈값이 아닐 경우
				// 제목 희망직종 경력사항 외국어 자격증
				sql.append(" and gm.mem_cr_num = any( select gm.mem_cr_num from gj_mem_recruit gm where ");
				for(int i=0; i<temp.length; i++){
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm, gj_mem_c gc where gc.mem_c_id =  gm.mem_c_id and gc.mem_c_name like '%"+temp[i]+"%') or ");  //업체명
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_title like '%"+temp[i]+"%') or "); //제목
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_gyoung like '%"+temp[i]+"%') or "); //경력
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_hak like '%"+temp[i]+"%') or ");  //학력
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_jobtype like '%"+temp[i]+"%') or ");  //근무형태
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_addr like '%"+temp[i]+"%') or ");  //근무지
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_sal like '%"+temp[i]+"%')");  //연봉
					if(i!= temp.length-1){
						sql.append(" and ");
					}
				}
				sql.append(")");
			}
			if(jik.length() != 0)
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm, gj_jik2 jk2 where jk2.jik2_code = gm.jik2_code and jk2.jik2_name = '"+jik+"')");
			if(exp.length() != 0)
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_gyoung like '%"+exp+"%')");
			if(area.length() != 0)  //지역
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_addr like '%"+area+"%')");
			if(hak.length() != 0)  //학력
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_hak like '%"+hak+"%')");
			if(sal.length() != 0)  //연봉
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_sal like '%"+sal+"%')");
			if(jobtype.length() != 0)  //근무형태
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_jobtype like '%"+jobtype+"%')");
			if(license.length() != 0)  //자격증
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_license like '%"+license+"%')");
			for(int i=0; i<lan_n_list.length; i++){	
				String temp_grade = "";
				if(lan_n_list[i].length() == 0)
					break;
				if(lan_g_list[i].length()!=0 )
					temp_grade = "("+lan_g_list[i] + ")";
				
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_lan like '%"+lan_n_list[i]+temp_grade+"%')");
			}
			
			sql.append(" order by "+select+" "+order+" ) aa ) where rnum>="+startRow+" and rnum<="+endRow+"");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_RECRUIT_S dto = new GJ_RECRUIT_S(rs.getInt("mem_cr_num"), rs.getString("mem_c_name"), rs.getString("mem_cr_title"), rs.getString("mem_cr_gyoung"), rs.getString("mem_cr_hak"), 
						rs.getString("mem_cr_jobtype"), rs.getString("mem_cr_addr"), rs.getString("mem_cr_sal"), rs.getString("mem_cr_rtime") );
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
	
	
	public ArrayList<GJ_RECRUIT_S> searchRecruit(String search, int startRow, int endRow){
		ArrayList<GJ_RECRUIT_S> list = new ArrayList<>() ;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] temp = search.trim().split(" ");  // 검색어의 양옆 공백을 자른후 공백을 기준으로 나눔.
		StringBuffer sql = new StringBuffer();
		try{ //재학기간 학력구분 학교명 전공
			con = DbcpBean.getConn();
			sql.append("select * from (select aa.*, rownum \"RNUM\" from (select gm.mem_cr_num, gc.mem_c_name, gm.mem_cr_title, gm.mem_cr_gyoung, gm.mem_cr_hak, gm.mem_cr_jobtype, gm.mem_cr_addr, gm.mem_cr_sal, gm.mem_cr_rtime from gj_mem_recruit gm, gj_mem_c gc where gm.mem_c_id = gc.mem_c_id and gm.mem_cr_check = '1'");
		
			if(temp[0].length() !=0){ //검색값이 빈값이 아닐 경우
				// 제목 희망직종 경력사항 외국어 자격증
				sql.append(" and gm.mem_cr_num = any( select gm.mem_cr_num from gj_mem_recruit gm where ");
				for(int i=0; i<temp.length; i++){
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm, gj_mem_c gc where gc.mem_c_id =  gm.mem_c_id and gc.mem_c_name like '%"+temp[i]+"%') or ");  //업체명
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_title like '%"+temp[i]+"%') or "); //제목
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_gyoung like '%"+temp[i]+"%') or "); //경력
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_hak like '%"+temp[i]+"%') or ");  //학력
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_jobtype like '%"+temp[i]+"%') or ");  //근무형태
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_addr like '%"+temp[i]+"%') or ");  //근무지
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_sal like '%"+temp[i]+"%')");  //연봉
					if(i!= temp.length-1){
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
			sql.append(" order by gc.mem_c_name ) aa ) where rnum>="+startRow+" and rnum<="+endRow+"");
			
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_RECRUIT_S dto = new GJ_RECRUIT_S(rs.getInt("mem_cr_num"), rs.getString("mem_c_name"), rs.getString("mem_cr_title"), rs.getString("mem_cr_gyoung"), rs.getString("mem_cr_hak"), 
						rs.getString("mem_cr_jobtype"), rs.getString("mem_cr_addr"), rs.getString("mem_cr_sal"), rs.getString("mem_cr_rtime") );
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
	public ArrayList<GJ_RECRUIT_S> searchRecruit(String search, int startRow, int endRow, String select, String order){
		ArrayList<GJ_RECRUIT_S> list = new ArrayList<>() ;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] temp = search.trim().split(" ");  // 검색어의 양옆 공백을 자른후 공백을 기준으로 나눔.
		StringBuffer sql = new StringBuffer();
		try{ //재학기간 학력구분 학교명 전공
			con = DbcpBean.getConn();
			sql.append("select * from (select aa.*, rownum \"RNUM\" from (select gm.mem_cr_num, gc.mem_c_name, gm.mem_cr_title, gm.mem_cr_gyoung, gm.mem_cr_hak, gm.mem_cr_jobtype, gm.mem_cr_addr, gm.mem_cr_sal, gm.mem_cr_rtime, NVL(jw.jiwonja,0) from (select jw.mem_cr_num ,count(*) jiwonja from  gj_mem_recruit gm, gj_jiwon jw where gm.mem_cr_num = jw.MEM_CR_NUM GROUP BY jw.mem_cr_num) jw, gj_mem_recruit gm, gj_mem_c gc where jw.mem_cr_num(+) = gm.mem_cr_num and gm.mem_c_id = gc.mem_c_id and gm.mem_cr_check = '1'");
		
			if(temp[0].length() !=0){ //검색값이 빈값이 아닐 경우
				// 제목 희망직종 경력사항 외국어 자격증
				sql.append(" and gm.mem_cr_num = any( select gm.mem_cr_num from gj_mem_recruit gm where ");
				for(int i=0; i<temp.length; i++){
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm, gj_mem_c gc where gc.mem_c_id =  gm.mem_c_id and gc.mem_c_name like '%"+temp[i]+"%') or ");  //업체명
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_title like '%"+temp[i]+"%') or "); //제목
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_gyoung like '%"+temp[i]+"%') or "); //경력
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_hak like '%"+temp[i]+"%') or ");  //학력
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_jobtype like '%"+temp[i]+"%') or ");  //근무형태
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_addr like '%"+temp[i]+"%') or ");  //근무지
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_sal like '%"+temp[i]+"%')");  //연봉
					if(i!= temp.length-1){
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
			sql.append(" order by "+select+" "+order+" ) aa ) where rnum>="+startRow+" and rnum<="+endRow+"");
			
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_RECRUIT_S dto = new GJ_RECRUIT_S(rs.getInt("mem_cr_num"), rs.getString("mem_c_name"), rs.getString("mem_cr_title"), rs.getString("mem_cr_gyoung"), rs.getString("mem_cr_hak"), 
						rs.getString("mem_cr_jobtype"), rs.getString("mem_cr_addr"), rs.getString("mem_cr_sal"), rs.getString("mem_cr_rtime") );
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
	public ArrayList<GJ_RESUME_S> searchResume(String select, String order, String word, int startRow, int endRow){
		//--이름 성별 나이 제목 희망직종 경력사항 외국어 자격증 수정일
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] temp = word.trim().split(" ");  // 검색어의 양옆 공백을 자른후 공백을 기준으로 나눔.
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select aa.*, rownum \"RNUM\" from (select mr.mem_pr_num \"NUM\", mp.mem_p_name \"NAME\", mp.mem_p_gender \"GENDER\", to_number(to_char(sysdate, 'yyyy'))- to_number(substr(mp.mem_p_birth,3,2))+1-1900 \"AGE\", "
		+ "mr.mem_pr_title \"TITLE\", jk1.jik1_name||'-'||jk2.jik2_name \"JIK\", mr.mem_pr_gyoung \"GYOUNG\", NVL(lan.cnt,0) \"LAN_CNT\", NVL(lic.cnt,0)\"LIC_CNT\" ,mr.mem_pr_date from "
		+ "gj_mem_p mp, gj_mem_resume mr, (select mem_pr_num, count(*) \"CNT\" from gj_lan group by  mem_pr_num) lan,  (select mem_pr_num, count(*)\"CNT\" from gj_license group by mem_pr_num) lic, "
		+ "gj_jik1 jk1, gj_jik2 jk2 where jk1.jik1_code = jk2.jik1_code and jk2.jik2_code(+) = mr.jik2_code and mp.mem_p_id = mr.mem_p_id and mr.mem_pr_num = lan.mem_pr_num(+) and mr.mem_pr_num = lic.mem_pr_num(+) and mr.mem_pr_check = '1'");
		ArrayList<GJ_RESUME_S> list = new ArrayList<>();
		
		if(temp[0].length() !=0){ //검색값이 빈값이 아닐 경우
			// 제목 희망직종 경력사항 외국어 자격증
			sql.append(" and mr.mem_pr_num = any( select mr.mem_pr_num from gj_mem_resume mr where ");
			for(int i=0; i<temp.length; i++){
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_title like '%"+temp[i]+"%') or ");  //제목
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr, gj_jik2 jk2 where jk2.jik2_code = mr.jik2_code and jk2.jik2_name like '%"+temp[i]+"%') or "); //희망직종
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_gyoung like '%"+temp[i]+"%') or "); //경력사항
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_lan gl, gj_mem_resume mr, gj_lan_c gc where gl.lan_c_code = gc.lan_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.lan_name like '%"+temp[i]+"%') or ");  //외국어
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_license gl, gj_mem_resume mr, gj_license_c gc where gl.license_c_code = gc.license_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.license_name like '%"+temp[i]+"%')");  //자격증
				if(i!= temp.length-1){
					sql.append(" or ");
				}
			}
			sql.append(")");
		}
		
		/*
		if( temp.length == 1){    //검색어가 하나일 경우
			if(temp[0].length() !=0){ //검색값이 빈값이 아닐 경우
				// 제목 희망직종 경력사항 외국어 자격증
				sql.append("and mr.mem_pr_num = any((select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_title like '%"+temp[0]+"%'),");  //제목
				sql.append("(select mr.mem_pr_num from gj_mem_resume mr, gj_jik2 jk2 where jk2.jik2_code = mr.jik2_code and jk2.jik2_name like '%"+temp[0]+"%'),"); //희망직종
				sql.append("(select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_gyoung like '%"+temp[0]+"%'),"); //경력사항
				sql.append("(select mr.mem_pr_num from gj_lan gl, gj_mem_resume mr, gj_lan_c gc where gl.lan_c_code = gc.lan_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.lan_name like '%"+temp[0]+"%'),");  //외국어
				sql.append("(select mr.mem_pr_num from gj_license gl, gj_mem_resume mr, gj_license_c gc where gl.license_c_code = gc.license_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.license_name like '%"+temp[0]+"%')) ");  //자격증
			}
		}else{  //검색어가 다수 일 경우
			sql.append("and mr.mem_pr_num = any(");
			for(int i=0; i<temp.length; i++){
				sql.append("select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_title like '%"+temp[i]+"%'),");  //제목
				sql.append("select mr.mem_pr_num from gj_mem_resume mr, gj_jik2 jk2 where jk2.jik2_code = mr.jik2_code and jk2.jik2_name like '%"+temp[i]+"%'),"); //희망직종
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_gyoung like '%"+temp[i]+"%'),"); //경력사항
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_lan gl, gj_mem_resume mr, gj_lan_c gc where gl.lan_c_code = gc.lan_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.lan_name like '%"+temp[i]+"%'),");  //외국어
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_license gl, gj_mem_resume mr, gj_license_c gc where gl.license_c_code = gc.license_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.license_name like '%"+temp[i]+"%')");  //자격증
				if(i!= temp.length-1){
					sql.append(",");
				}
			}
			sql.append(")");
		}
		*/
		try{
			sql.append(" order by "+select+" "+order+" ) aa ) where rnum>="+startRow+" and rnum<="+endRow+"");
			//-- 경력순, 자격증수순, 수정일순, 나이순 정렬
			//--이름 성별 나이 제목 희망직종 경력사항 외국어 자격증 수정일
			con = DbcpBean.getConn();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_RESUME_S grs = new GJ_RESUME_S(rs.getInt("NUM"), rs.getString("NAME"), rs.getString("GENDER"), rs.getInt("AGE"), rs.getString("TITLE"), 
						rs.getString("JIK"), rs.getString("GYOUNG"), rs.getInt("LAN_CNT"), rs.getInt("LIC_CNT"), rs.getDate("mem_pr_date") );
				list.add(grs);
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
	public int countCGRecruit(String exp,String area,String sal,String jobtype,String jik,String hak, String[] lan_n_list, String[] lan_g_list,String license,String word){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] temp = word.trim().split(" ");  // 검색어의 양옆 공백을 자른후 공백을 기준으로 나눔.
		StringBuffer sql = new StringBuffer();
		int n = -1;
		try{ //재학기간 학력구분 학교명 전공
			con = DbcpBean.getConn();
			sql.append("select count(*) from gj_mem_recruit gm, gj_mem_c gc where gm.mem_c_id = gc.mem_c_id and gm.mem_cr_check = '1'");
		
			if(temp[0].length() !=0){ //검색값이 빈값이 아닐 경우
				// 제목 희망직종 경력사항 외국어 자격증
				sql.append(" and gm.mem_cr_num = any( select gm.mem_cr_num from gj_mem_recruit gm where ");
				for(int i=0; i<temp.length; i++){
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm, gj_mem_c gc where gc.mem_c_id =  gm.mem_c_id and gc.mem_c_name like '%"+temp[i]+"%') or ");  //업체명
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_title like '%"+temp[i]+"%') or "); //제목
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_gyoung like '%"+temp[i]+"%') or "); //경력
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_hak like '%"+temp[i]+"%') or ");  //학력
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_jobtype like '%"+temp[i]+"%') or ");  //근무형태
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_addr like '%"+temp[i]+"%') or ");  //근무지
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_sal like '%"+temp[i]+"%')");  //연봉
					if(i!= temp.length-1){
						sql.append(" and ");
					}
				}
				sql.append(")");
			}
			if(jik.length() != 0)
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm, gj_jik2 jk2 where jk2.jik2_code = gm.jik2_code and jk2.jik2_name = '"+jik+"')");
			if(exp.length() != 0)
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_gyoung like '%"+exp+"%')");
			if(area.length() != 0)  //지역
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_addr like '%"+area+"%')");
			if(hak.length() != 0)  //학력
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_hak like '%"+hak+"%')");
			if(sal.length() != 0)  //연봉
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_sal like '%"+sal+"%')");
			if(jobtype.length() != 0)  //근무형태
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_jobtype like '%"+jobtype+"%')");
			if(license.length() != 0)  //전공
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_license like '%"+license+"%')");
			for(int i=0; i<lan_n_list.length; i++){	
				String temp_grade = "";
				if(lan_n_list[i].length() == 0)
					break;
				if(lan_g_list[i].length()!=0 )
					temp_grade = "("+lan_g_list[i] + ")";
				
				sql.append(" and gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_lan like '%"+lan_n_list[i]+temp_grade+"%')");
			}
			
			sql.append(" order by gc.mem_c_name ");
			
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				n = rs.getInt(1);
			}
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	
	public int countRecruit(String search){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] temp = search.trim().split(" ");  // 검색어의 양옆 공백을 자른후 공백을 기준으로 나눔.
		StringBuffer sql = new StringBuffer();
		int n = -1;
		try{ //재학기간 학력구분 학교명 전공
			con = DbcpBean.getConn();
			sql.append("select count(*) from gj_mem_recruit gm, gj_mem_c gc where gm.mem_c_id = gc.mem_c_id and gm.mem_cr_check='1'");
		
			if(temp[0].length() !=0){ //검색값이 빈값이 아닐 경우
				// 제목 희망직종 경력사항 외국어 자격증
				sql.append(" and gm.mem_cr_num = any( select gm.mem_cr_num from gj_mem_recruit gm where ");
				for(int i=0; i<temp.length; i++){
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm, gj_mem_c gc where gc.mem_c_id =  gm.mem_c_id and gc.mem_c_name like '%"+temp[i]+"%') or ");  //업체명
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_title like '%"+temp[i]+"%') or "); //제목
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_gyoung like '%"+temp[i]+"%') or "); //경력
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_hak like '%"+temp[i]+"%') or ");  //학력
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_jobtype like '%"+temp[i]+"%') or ");  //근무형태
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_addr like '%"+temp[i]+"%') or ");  //근무지
					sql.append("gm.mem_cr_num = any(select gm.mem_cr_num from gj_mem_recruit gm where gm.mem_cr_sal like '%"+temp[i]+"%')");  //연봉
					if(i!= temp.length-1){
						sql.append(" or ");
					}
				}
				sql.append(") ");
			}
			sql.append(" order by gc.mem_c_name ");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				n = rs.getInt(1);
			}
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	public void upHit(int num){  //해당번호 조회수 올리기
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
			String sql = "update gj_mem_recruit set mem_cr_hit = mem_cr_hit+1 where mem_cr_num = ?";
			con = DbcpBean.getConn();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return;
		}
		finally{
			DbcpBean.close(null, pstmt, con);
		}
	}
	public GJ_DETAIL_RECRUIT getDetailRecruit(int num){  //해당번호의 이력서 내용가져오기
		GJ_DETAIL_RECRUIT dto = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		
		/*
		 *int num, String title, String img, String simg, String name, String bossname, String year,
			String jabon, String machul, String up_name, String sawon, String c_addr, String homepage, String hak,
			String gyoung, String license, String lan_cer, String lan, String sal, String addr, String jobtype,
			String jobtime, String jik, int recruitnumber, String age, String gender, String work, String rtime,
			String chadate, String document, String howto, String qna, String qnaname, int hit
		 * 
		 */
		try{
			String sql = "select gr.mem_cr_title, gc.mem_c_img, gc.mem_c_simg, gc.mem_c_name, gc.mem_c_bossname, gc.mem_c_year, gc.mem_c_jabon, gc.mem_c_machul, gu.up_name, gc.mem_c_sawon, gc.mem_c_addr, gc.mem_c_homepage, gr.mem_cr_hak, gr.mem_cr_gyoung, gr.mem_cr_license, gr.mem_cr_lan_cer, gr.mem_cr_lan, "
					+ "gr.mem_cr_sal, gr.mem_cr_addr, gr.mem_cr_jobtype, gr.mem_cr_jobtime, jik1.jik1_name||'-'||jik2.jik2_name \"jik\", gr.mem_cr_recruitnumber, gr.mem_cr_age, gr.mem_cr_gender, gr.mem_cr_workout, gr.mem_cr_rtime, gr.mem_cr_chadate, gr.mem_cr_document, "
					+ "gr.mem_cr_howto, gr.mem_cr_qna, gr.mem_cr_qnaname, gr.mem_cr_hit from gj_mem_recruit gr, gj_mem_c gc, gj_jik1 jik1, gj_jik2 jik2, gj_up gu where gr.mem_c_id = gc.mem_c_id and jik1.jik1_code = jik2.jik1_code and jik2.jik2_code = gr.jik2_code and gc.up_code = gu.up_code and gr.mem_cr_num = ?";
			con = DbcpBean.getConn();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()){
				dto = new GJ_DETAIL_RECRUIT(num, rs.getString("mem_cr_title"), rs.getString("mem_c_img"), rs.getString("mem_c_simg"), rs.getString("mem_c_name"), rs.getString("mem_c_bossname"),
						rs.getString("mem_c_year"), rs.getString("mem_c_jabon"), rs.getString("mem_c_machul"), rs.getString("up_name"), rs.getString("mem_c_sawon"),
						rs.getString("mem_c_addr"), rs.getString("mem_c_homepage"), rs.getString("mem_cr_hak"), rs.getString("mem_cr_gyoung"), rs.getString("mem_cr_license"),
						rs.getString("mem_cr_lan_cer"), rs.getString("mem_cr_lan"), rs.getString("mem_cr_sal"), rs.getString("mem_cr_addr"), rs.getString("mem_cr_jobtype"),
						rs.getString("mem_cr_jobtime"), rs.getString("JIK"), rs.getInt("mem_cr_recruitnumber"), rs.getString("mem_cr_age"), rs.getString("mem_cr_gender"),
						rs.getString("mem_cr_workout"), rs.getString("mem_cr_rtime"), rs.getString("mem_cr_chadate"), rs.getString("mem_cr_document"), rs.getString("mem_cr_howto"),
						rs.getString("mem_cr_qna"), rs.getString("mem_cr_qnaname"), rs.getInt("mem_cr_hit"));
			}
			return dto;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
	
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////이력서 검색 관련
	public GJ_DETAIL_RESUME getDetailResume(int num){  //해당번호의 이력서 내용가져오기
		GJ_DETAIL_RESUME dto = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;  //외국어 전용
		ResultSet rs = null;
		ResultSet rs1 = null; //외국어 전용
		/*
		 * public GJ_DETAIL_RESUME(int num, String title, String jik, String wishsal, String img, String simg, String name,
			String birth, String age, String email, String phone, String addr, String marry, String byoung, String bohoon,
			String boho, String handy, String gyoung, <String lan>, String prize, String resume) {
		 * 
		 */
		try{
			con = DbcpBean.getConn();
			String sql = "select mr.mem_pr_title, jik1.jik1_name||'-'||jik2.jik2_name \"JIK\", mr.mem_pr_wishsal, mr.mem_pr_img, mr.mem_pr_simg, mp.mem_p_name, "
					+ "mp.mem_p_birth, to_number(to_char(sysdate, 'yyyy'))- to_number(substr(mp.mem_p_birth,3,2))+1-1900 \"AGE\", mp.mem_p_email, mp.mem_p_phone, mp.mem_p_addr, "
					+ "mr.mem_pr_marry, mr.mem_pr_byoung, mr.mem_pr_bohoon, mr.mem_pr_boho, mr.mem_pr_handy, mr.mem_pr_gyoung, mr.mem_pr_exp, mr.mem_pr_prize, mr.mem_pr_resume "
					+ "from gj_mem_resume mr, gj_mem_p mp, gj_jik1 jik1, gj_jik2 jik2 where jik1.jik1_code = jik2.jik1_code and jik2.jik2_code(+) = mr.jik2_code and mp.mem_p_id = mr.mem_p_id "
					+ "and mr.mem_pr_num = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()){
				sql = "select gc.lan_name, gl.lan_grade from gj_lan_c gc, gj_lan gl, gj_mem_resume mr where mr.mem_pr_num = gl.mem_pr_num and gc.lan_c_code = gl.lan_c_code and mr.mem_pr_num = ?";
				String lan = "";
				pstmt1 = con.prepareStatement(sql);
				pstmt1.setInt(1, num);
				rs1 = pstmt1.executeQuery();
				while(rs1.next()){
					lan += rs1.getString("lan_name")+"("+rs1.getString("lan_grade")+") ";
				}
				dto = new GJ_DETAIL_RESUME(
						 num, rs.getString("mem_pr_title"), rs.getString("JIK"), rs.getString("mem_pr_wishsal"), rs.getString("mem_pr_img"), rs.getString("mem_pr_simg"), rs.getString("mem_p_name"),
						 rs.getString("mem_p_birth"), rs.getInt("AGE"), rs.getString("mem_p_email"), rs.getString("mem_p_phone"), rs.getString("mem_p_addr"), rs.getString("mem_pr_marry"),
						 rs.getString("mem_pr_byoung"), rs.getString("mem_pr_bohoon"), rs.getString("mem_pr_boho"), rs.getString("mem_pr_handy"), rs.getString("mem_pr_gyoung"),
						 rs.getString("mem_pr_exp"), lan, rs.getString("mem_pr_prize"), rs.getString("mem_pr_resume")		 
				);
			}
			return dto;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
	
	}
	
	public ArrayList<GJ_HAK_L> getHak(int num){
		ArrayList<GJ_HAK_L> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{ //재학기간 학력구분 학교명 전공
			con = DbcpBean.getConn();
			String sql = "select gh.hak_date, gh.hak_type, gh.hak_name, gm.major_name from gj_hak gh, gj_major gm, gj_mem_resume mr "
					+ "where gh.major_code = gm.major_code and gh.mem_pr_num = mr.mem_pr_num and mr.mem_pr_num = ?";
		
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_HAK_L dto = new GJ_HAK_L(rs.getString("hak_date"), rs.getString("hak_type"), rs.getString("hak_name"), rs.getString("major_name") );
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
	
	// 이력서 번호로 자격/면허시험 리스트 가져옴
	public ArrayList<GJ_LICENSE_L> getLiclist(int num){
		ArrayList<GJ_LICENSE_L> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{ //자격명 취득일자
			con = DbcpBean.getConn();
			String sql = "select gc.license_name, gl.license_date from gj_license gl, gj_license_c gc, gj_mem_resume mr "
					+ "where gl.license_c_code = gc.license_c_code and gl.mem_pr_num = mr.mem_pr_num and mr.mem_pr_num = ?";
		
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_LICENSE_L dto = new GJ_LICENSE_L(rs.getString("license_name"),rs.getString("license_date"));
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
	public ArrayList<GJ_C_FILE_L> getCFilelist(int num){
		ArrayList<GJ_C_FILE_L> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = DbcpBean.getConn();
			String sql = "select gf.file_c_code, gf.file_c_name, gf.file_c_sname from gj_c_file gf, gj_mem_recruit gr "
					+ "where gf.mem_cr_num = gr.mem_cr_num and gr.mem_cr_num = ?";
		
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_C_FILE_L dto = new GJ_C_FILE_L(rs.getInt("file_c_code"), rs.getString("file_c_name"), rs.getString("file_c_sname"));
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
	
	public ArrayList<GJ_P_FILE_L> getPFilelist(int num){
		ArrayList<GJ_P_FILE_L> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = DbcpBean.getConn();
			String sql = "select gf.file_p_code, gf.file_p_name, gf.file_p_sname from gj_p_file gf, gj_mem_resume mr "
					+ "where gf.mem_pr_num = mr.mem_pr_num and mr.mem_pr_num = ?";
		
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_P_FILE_L dto = new GJ_P_FILE_L(rs.getInt("file_p_code"), rs.getString("file_p_name"), rs.getString("file_p_sname"));
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
	
	public ArrayList<GJ_P_FILE_L> getPFilelist_insert(){
		ArrayList<GJ_P_FILE_L> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = DbcpBean.getConn();
			String sql = "select gf.file_p_code, gf.file_p_name, gf.file_p_sname from gj_p_file gf, gj_mem_resume mr "
					+ "where gf.mem_pr_num = mr.mem_pr_num and mr.mem_pr_num = (select gj_mem_resume_seq.currval from dual)";
		
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_P_FILE_L dto = new GJ_P_FILE_L(rs.getInt("file_p_code"), rs.getString("file_p_name"), rs.getString("file_p_sname"));
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
	//이력서 번호로 공인어학점수 가져옴
	public ArrayList<GJ_LAN_CER_L> getLancerlist(int num){
		ArrayList<GJ_LAN_CER_L> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		/*
		 * 
		 *시험명, 점수/등급, 취득일자
		 */
		try{
			con = DbcpBean.getConn();
			String sql = "select gc.lan_cer_name, gl.lan_cer_level, gl.lan_cer_date from gj_lan_cer gl, gj_lan_cer_c gc, gj_mem_resume mr "
					+ "where gl.lan_cer_c_code = gc.lan_cer_c_code and gl.mem_pr_num = mr.mem_pr_num and mr.mem_pr_num = ?";
		
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_LAN_CER_L dto = new GJ_LAN_CER_L(rs.getString("lan_cer_name"), rs.getString("lan_cer_level"), rs.getString("lan_cer_date"));
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
	///////////////////////////////
	public ArrayList<GJ_EXPINFO_L> getExplist(int num){
		ArrayList<GJ_EXPINFO_L> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		/*
		 * private int expinfo_code;
			private String expinfo_date;
			private String expinfo_name;
			private String expinfo_grade;
			private String expinfo_detail;
			private int mem_pr_num;
		 * 
		 */
		try{ //
			con = DbcpBean.getConn();
			String sql = "select ge.expinfo_date, ge.expinfo_cname, ge.expinfo_grade, ge.expinfo_detail from gj_expinfo ge, gj_mem_resume mr "
					+ "where ge.mem_pr_num = mr.mem_pr_num and mr.mem_pr_num = ?";
		
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_EXPINFO_L dto = new GJ_EXPINFO_L(rs.getString("expinfo_date"), rs.getString("expinfo_cname"), rs.getString("expinfo_grade"), rs.getString("expinfo_detail"));
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
	///////////////////////////////////////////
	
	public int countResume(String word){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = -1;
		String[] temp = word.trim().split(" ");  // 검색어의 양옆 공백을 자른후 공백을 기준으로 나눔.
		StringBuffer sql = new StringBuffer();
		try{
			con = DbcpBean.getConn();
			sql.append("select count(*) from gj_mem_resume mr where mr.mem_pr_check = '1'");
			
			if(temp[0].length() != 0){ //검색어가 빈값이 아닌경우
				sql.append(" and mr.mem_pr_num = any( select mr.mem_pr_num from gj_mem_resume mr where ");
				for(int i=0; i<temp.length; i++){
					sql.append("mr.mem_pr_num = any(select mr1.mem_pr_num from gj_mem_resume mr1 where mr1.mem_pr_title like '%"+temp[i]+"%') or ");  //제목
					sql.append("mr.mem_pr_num = any(select mr2.mem_pr_num from gj_mem_resume mr2, gj_jik2 jk2 where jk2.jik2_code = mr2.jik2_code and jk2.jik2_name like '%"+temp[i]+"%') or "); //희망직종
					sql.append("mr.mem_pr_num = any(select mr3.mem_pr_num from gj_mem_resume mr3 where mr3.mem_pr_gyoung like '%"+temp[i]+"%') or "); //경력사항
					sql.append("mr.mem_pr_num = any(select mr4.mem_pr_num from gj_lan gl, gj_mem_resume mr4, gj_lan_c gc where gl.lan_c_code = gc.lan_c_code and gl.mem_pr_num = mr4.mem_pr_num and gc.lan_name like '%"+temp[i]+"%') or ");  //외국어
					sql.append("mr.mem_pr_num = any(select mr5.mem_pr_num from gj_license gl, gj_mem_resume mr5, gj_license_c gc where gl.license_c_code = gc.license_c_code and gl.mem_pr_num = mr5.mem_pr_num and gc.license_name like '%"+temp[i]+"%')");  //자격증
					if(i!= temp.length-1){
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
			
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				cnt = rs.getInt(1);
			}
			return cnt;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return cnt;
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	//
	public int countCGResume(String jik, String exp, String hak, String major, String[] lan_n_list, String[] lan_g_list, String license, String gender, String age1, String age2, String word){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = -1;
		String[] temp = word.trim().split(" ");  // 검색어의 양옆 공백을 자른후 공백을 기준으로 나눔.
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from gj_mem_resume mr where mr.mem_pr_check = '1' ");
		if(temp[0].length() !=0){ //검색값이 빈값이 아닐 경우
			// 제목 희망직종 경력사항 외국어 자격증
			sql.append("and mr.mem_pr_num = any( select mr.mem_pr_num from gj_mem_resume mr where ");
			for(int i=0; i<temp.length; i++){
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_title like '%"+temp[i]+"%') or ");  //제목
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr, gj_jik2 jk2 where jk2.jik2_code = mr.jik2_code and jk2.jik2_name like '%"+temp[i]+"%') or "); //희망직종
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_gyoung like '%"+temp[i]+"%') or "); //경력사항
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_lan gl, gj_mem_resume mr, gj_lan_c gc where gl.lan_c_code = gc.lan_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.lan_name like '%"+temp[i]+"%') or ");  //외국어
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_license gl, gj_mem_resume mr, gj_license_c gc where gl.license_c_code = gc.license_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.license_name like '%"+temp[i]+"%')");  //자격증
				if(i!= temp.length-1){
					sql.append(" and ");
				}
			}
			sql.append(")");
		}
		if(jik.length() != 0)
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr, gj_jik2 jk2 where jk2.jik2_code = mr.jik2_code and jk2.jik2_name = '"+jik+"')");
		if(exp.length() != 0)
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_gyoung like '%"+exp+"%')");
		if(hak.length() != 0)  //학력
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr, gj_hak gh where mr.mem_pr_num = gh.mem_pr_num and gh.hak_type = '"+hak+"')");
		if(major.length() != 0)  //전공
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr, gj_hak gh, gj_major gm  where mr.mem_pr_num = gh.mem_pr_num and gh.major_code = gm.major_code and gm.major_name = '"+major+"')");
		for(int i=0; i<lan_n_list.length; i++){	
			if(lan_n_list[i].length() == 0)
				break;
			
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_lan gl, gj_mem_resume mr, gj_lan_c gc where gl.lan_c_code = gc.lan_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.lan_name = '"+lan_n_list[i]+"' and gl.lan_grade like '%"+lan_g_list[i]+"%')");
		}if(license.length() !=0){
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_license gl, gj_mem_resume mr, gj_license_c gc where gl.license_c_code = gc.license_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.license_name = '"+license+"')");
		}
		if(gender.length() != 0){
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr, gj_mem_p mp where mr.mem_p_id = mp.mem_p_id and mp.mem_p_gender = '"+gender+"')");
		}
		if(age1.length() == 0)
			age1 = "0";
		if(age2.length() == 0)
			age2 = "150";
			
		sql.append(" and mr.mem_pr_num = any(select bb.num from (select mr.mem_pr_num \"NUM\", to_number(to_char(sysdate, 'yyyy'))- "
				+ "to_number(substr(mp.mem_p_birth,3,2))+1-1900 \"AGE\" from gj_mem_resume mr, gj_mem_p mp where mp.mem_p_id = mr.mem_p_id) bb where bb.age >= "+ age1 +" and bb.age <= "+ age2 +")");
		
		try{
			con = DbcpBean.getConn();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				cnt = rs.getInt(1);
			}
			return cnt;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return cnt;
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	//--  전공 검색
	public ArrayList<String> searchMajor(String major){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<String> list = new ArrayList<>();
		try{
			String sql = "select major_name \"NAME\" from gj_major where major_name like ?";
			con = DbcpBean.getConn();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+major+"%");
			rs = pstmt.executeQuery();
			while(rs.next()){
				list.add(rs.getString("NAME"));
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
	//-- 전체 외국어 리스트
	public ArrayList<String> allLan(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<String> list = new ArrayList<>();
		try{
			String sql = "select lan_name from gj_lan_c";
			//--이름 성별 나이 제목 희망직종 경력사항 외국어 자격증 수정일
			con = DbcpBean.getConn();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				list.add(rs.getString("lan_name"));
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
	//--  자격증 검색
		public ArrayList<String> searchLicense(String license_name){
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ArrayList<String> list = new ArrayList<>();
			try{
				String sql = "select license_name \"NAME\" from gj_license_c where license_name like ?";
				con = DbcpBean.getConn();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+license_name+"%");
				rs = pstmt.executeQuery();
				while(rs.next()){
					list.add(rs.getString("NAME"));
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
		
	//--해당 이력서 외국어 리스트
	public ArrayList<GJ_LAN_L> listLan(int num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GJ_LAN_L> list = new ArrayList<>();
		try{
			String sql = "select gc.lan_name \"NAME\", gl.lan_grade \"GRADE\" from gj_lan_c gc, gj_lan gl where gc.lan_c_code = gl.lan_c_code and gl.mem_pr_num = ?";
			//--이름 성별 나이 제목 희망직종 경력사항 외국어 자격증 수정일
			con = DbcpBean.getConn();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_LAN_L grs = new GJ_LAN_L(rs.getString("NAME"), rs.getString("GRADE"));
				list.add(grs);
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
	
	//--자격증리스트
	public ArrayList<GJ_LICENSE_L> listLic(int num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GJ_LICENSE_L> list = new ArrayList<>();
		try{
			String sql = "select gc.license_name \"NAME\", gl.license_date \"DATE\" from gj_license_c gc, gj_license gl where gc.license_c_code = gl.license_c_code and gl.mem_pr_num = ?";
			con = DbcpBean.getConn();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_LICENSE_L grs = new GJ_LICENSE_L(rs.getString("NAME"), rs.getString("DATE"));
				list.add(grs);
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
	public ArrayList<GJ_RESUME_S> categoryResume(String jik, String exp, String hak, String major, String[] lan_n_list, String[] lan_g_list, 
			String license, String gender, String age1, String age2, String word, int startRow, int endRow){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] temp = word.trim().split(" ");  // 검색어의 양옆 공백을 자른후 공백을 기준으로 나눔.
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select aa.*, rownum \"RNUM\" from (select mr.mem_pr_num \"NUM\", mp.mem_p_name \"NAME\", mp.mem_p_gender \"GENDER\", to_number(to_char(sysdate, 'yyyy'))- to_number(substr(mp.mem_p_birth,3,2))+1-1900 \"AGE\", "
		+ "mr.mem_pr_title \"TITLE\", jk1.jik1_name||'-'||jk2.jik2_name \"JIK\", mr.mem_pr_gyoung \"GYOUNG\", NVL(lan.cnt,0) \"LAN_CNT\", NVL(lic.cnt,0)\"LIC_CNT\" ,mr.mem_pr_date \"DATE\" from "
		+ "gj_mem_p mp, gj_mem_resume mr, (select mem_pr_num, count(*) \"CNT\" from gj_lan group by  mem_pr_num) lan,  (select mem_pr_num, count(*)\"CNT\" from gj_license group by mem_pr_num) lic, "
		+ "gj_jik1 jk1, gj_jik2 jk2 where jk1.jik1_code = jk2.jik1_code and jk2.jik2_code(+) = mr.jik2_code and mp.mem_p_id = mr.mem_p_id and mr.mem_pr_num = lan.mem_pr_num(+) and mr.mem_pr_num = lic.mem_pr_num(+) and mr.mem_pr_check = '1'");
		ArrayList<GJ_RESUME_S> list = new ArrayList<>();
		if(temp[0].length() !=0){ //검색값이 빈값이 아닐 경우
			// 제목 희망직종 경력사항 외국어 자격증
			sql.append(" and mr.mem_pr_num = any( select mr.mem_pr_num from gj_mem_resume mr where ");
			for(int i=0; i<temp.length; i++){
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_title like '%"+temp[i]+"%') or ");  //제목
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr, gj_jik2 jk2 where jk2.jik2_code = mr.jik2_code and jk2.jik2_name like '%"+temp[i]+"%') or "); //희망직종
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_gyoung like '%"+temp[i]+"%') or "); //경력사항
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_lan gl, gj_mem_resume mr, gj_lan_c gc where gl.lan_c_code = gc.lan_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.lan_name like '%"+temp[i]+"%') or ");  //외국어
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_license gl, gj_mem_resume mr, gj_license_c gc where gl.license_c_code = gc.license_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.license_name like '%"+temp[i]+"%')");  //자격증
				if(i!= temp.length-1){
					sql.append(" and ");
				}
			}
			sql.append(")");
		}
		if(jik.length() != 0)
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr, gj_jik2 jk2 where jk2.jik2_code = mr.jik2_code and jk2.jik2_name = '"+jik+"')");
		if(exp.length() != 0)
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_gyoung like '%"+exp+"%')");
		if(hak.length() != 0)  //학력
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr, gj_hak gh where mr.mem_pr_num = gh.mem_pr_num and gh.hak_type = '"+hak+"')");
		if(major.length() != 0)  //전공
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr, gj_hak gh, gj_major gm  where mr.mem_pr_num = gh.mem_pr_num and gh.major_code = gm.major_code and gm.major_name = '"+major+"')");
		for(int i=0; i<lan_n_list.length; i++){	
			if(lan_n_list[i].length() == 0)
				break;
			
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_lan gl, gj_mem_resume mr, gj_lan_c gc where gl.lan_c_code = gc.lan_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.lan_name = '"+lan_n_list[i]+"' and gl.lan_grade like '%"+lan_g_list[i]+"%')");
		}if(license.length() !=0){
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_license gl, gj_mem_resume mr, gj_license_c gc where gl.license_c_code = gc.license_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.license_name = '"+license+"')");
		}
		if(gender.length() != 0){
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr, gj_mem_p mp where mr.mem_p_id = mp.mem_p_id and mp.mem_p_gender = '"+gender+"')");
		}
		if(age1.length() == 0)
			age1 = "0";
		if(age2.length() == 0)
			age2 = "150";
			
		sql.append(" and mr.mem_pr_num = any(select bb.num from (select mr.mem_pr_num \"NUM\", to_number(to_char(sysdate, 'yyyy'))- "
				+ "to_number(substr(mp.mem_p_birth,3,2))+1-1900 \"AGE\" from gj_mem_resume mr, gj_mem_p mp where mp.mem_p_id = mr.mem_p_id) bb where bb.age >= "+ age1 +" and bb.age <= "+ age2 +")");
		
		try{
			sql.append(" order by mp.mem_p_name ) aa ) where rnum>="+startRow+" and rnum<="+endRow+"");
			//-- 경력순, 자격증수순, 수정일순, 나이순 정렬
			//--이름 성별 나이 제목 희망직종 경력사항 외국어 자격증 수정일
			con = DbcpBean.getConn();
			pstmt = con.prepareStatement(sql.toString());
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
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	
	
	public ArrayList<GJ_RESUME_S> categoryResume(String select,String order,String jik, String exp, String hak, String major, String[] lan_n_list, String[] lan_g_list, 
			String license, String gender, String age1, String age2, String word, int startRow, int endRow){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] temp = word.trim().split(" ");  // 검색어의 양옆 공백을 자른후 공백을 기준으로 나눔.
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select aa.*, rownum \"RNUM\" from (select mr.mem_pr_num \"NUM\", mp.mem_p_name \"NAME\", mp.mem_p_gender \"GENDER\", to_number(to_char(sysdate, 'yyyy'))- to_number(substr(mp.mem_p_birth,3,2))+1-1900 \"AGE\", "
		+ "mr.mem_pr_title \"TITLE\", jk1.jik1_name||'-'||jk2.jik2_name \"JIK\", mr.mem_pr_gyoung \"GYOUNG\", NVL(lan.cnt,0) \"LAN_CNT\", NVL(lic.cnt,0)\"LIC_CNT\" ,mr.mem_pr_date \"DATE\" from "
		+ "gj_mem_p mp, gj_mem_resume mr, (select mem_pr_num, count(*) \"CNT\" from gj_lan group by  mem_pr_num) lan,  (select mem_pr_num, count(*)\"CNT\" from gj_license group by mem_pr_num) lic, "
		+ "gj_jik1 jk1, gj_jik2 jk2 where jk1.jik1_code = jk2.jik1_code and jk2.jik2_code(+) = mr.jik2_code and mp.mem_p_id = mr.mem_p_id and mr.mem_pr_num = lan.mem_pr_num(+) and mr.mem_pr_num = lic.mem_pr_num(+) and mr.mem_pr_check = '1'");
		ArrayList<GJ_RESUME_S> list = new ArrayList<>();
		if(temp[0].length() !=0){ //검색값이 빈값이 아닐 경우
			// 제목 희망직종 경력사항 외국어 자격증
			sql.append(" and mr.mem_pr_num = any( select mr.mem_pr_num from gj_mem_resume mr where ");
			for(int i=0; i<temp.length; i++){
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_title like '%"+temp[i]+"%') or ");  //제목
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr, gj_jik2 jk2 where jk2.jik2_code = mr.jik2_code and jk2.jik2_name like '%"+temp[i]+"%') or "); //희망직종
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_gyoung like '%"+temp[i]+"%') or "); //경력사항
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_lan gl, gj_mem_resume mr, gj_lan_c gc where gl.lan_c_code = gc.lan_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.lan_name like '%"+temp[i]+"%') or ");  //외국어
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_license gl, gj_mem_resume mr, gj_license_c gc where gl.license_c_code = gc.license_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.license_name like '%"+temp[i]+"%')");  //자격증
				if(i!= temp.length-1){
					sql.append(" and ");
				}
			}
			sql.append(")");
		}
		if(jik.length() != 0)
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr, gj_jik2 jk2 where jk2.jik2_code = mr.jik2_code and jk2.jik2_name = '"+jik+"')");
		if(exp.length() != 0)
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_gyoung like '%"+exp+"%')");
		if(hak.length() != 0)  //학력
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr, gj_hak gh where mr.mem_pr_num = gh.mem_pr_num and gh.hak_type = '"+hak+"')");
		if(major.length() != 0)  //전공
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr, gj_hak gh, gj_major gm  where mr.mem_pr_num = gh.mem_pr_num and gh.major_code = gm.major_code and gm.major_name = '"+major+"')");
		for(int i=0; i<lan_n_list.length; i++){	
			if(lan_n_list[i].length() == 0)
				break;
			
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_lan gl, gj_mem_resume mr, gj_lan_c gc where gl.lan_c_code = gc.lan_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.lan_name = '"+lan_n_list[i]+"' and gl.lan_grade like '%"+lan_g_list[i]+"%')");
		}if(license.length() !=0){
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_license gl, gj_mem_resume mr, gj_license_c gc where gl.license_c_code = gc.license_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.license_name = '"+license+"')");
		}
		if(gender.length() != 0){
			sql.append(" and mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr, gj_mem_p mp where mr.mem_p_id = mp.mem_p_id and mp.mem_p_gender = '"+gender+"')");
		}
		if(age1.length() == 0)
			age1 = "0";
		if(age2.length() == 0)
			age2 = "150";
			
		sql.append(" and mr.mem_pr_num = any(select bb.num from (select mr.mem_pr_num \"NUM\", to_number(to_char(sysdate, 'yyyy'))- "
				+ "to_number(substr(mp.mem_p_birth,3,2))+1-1900 \"AGE\" from gj_mem_resume mr, gj_mem_p mp where mp.mem_p_id = mr.mem_p_id) bb where bb.age >= "+ age1 +" and bb.age <= "+ age2 +")");
		
		try{
			sql.append(" order by "+select+" "+order+" ) aa ) where rnum>="+startRow+" and rnum<="+endRow+"");
			//-- 경력순, 자격증수순, 수정일순, 나이순 정렬
			//--이름 성별 나이 제목 희망직종 경력사항 외국어 자격증 수정일
			con = DbcpBean.getConn();
			pstmt = con.prepareStatement(sql.toString());
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
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	
	
	public ArrayList<GJ_RESUME_S> searchResume(String word, int startRow, int endRow){
		//--이름 성별 나이 제목 희망직종 경력사항 외국어 자격증 수정일
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] temp = word.trim().split(" ");  // 검색어의 양옆 공백을 자른후 공백을 기준으로 나눔.
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select aa.*, rownum \"RNUM\" from (select mr.mem_pr_num \"NUM\", mp.mem_p_name \"NAME\", mp.mem_p_gender \"GENDER\", to_number(to_char(sysdate, 'yyyy'))- to_number(substr(mp.mem_p_birth,3,2))+1-1900 \"AGE\", "
		+ "mr.mem_pr_title \"TITLE\", jk1.jik1_name||'-'||jk2.jik2_name \"JIK\", mr.mem_pr_gyoung \"GYOUNG\", NVL(lan.cnt,0) \"LAN_CNT\", NVL(lic.cnt,0)\"LIC_CNT\" ,mr.mem_pr_date \"DATE\" from "
		+ "gj_mem_p mp, gj_mem_resume mr, (select mem_pr_num, count(*) \"CNT\" from gj_lan group by  mem_pr_num) lan,  (select mem_pr_num, count(*)\"CNT\" from gj_license group by mem_pr_num) lic, "
		+ "gj_jik1 jk1, gj_jik2 jk2 where jk1.jik1_code = jk2.jik1_code and jk2.jik2_code(+) = mr.jik2_code and mp.mem_p_id = mr.mem_p_id and mr.mem_pr_num = lan.mem_pr_num(+) and mr.mem_pr_num = lic.mem_pr_num(+) and mr.mem_pr_check = '1'");
		ArrayList<GJ_RESUME_S> list = new ArrayList<>();
		
		if(temp[0].length() !=0){ //검색값이 빈값이 아닐 경우
			// 제목 희망직종 경력사항 외국어 자격증
			sql.append(" and mr.mem_pr_num = any( select mr.mem_pr_num from gj_mem_resume mr where ");
			for(int i=0; i<temp.length; i++){
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_title like '%"+temp[i]+"%') or ");  //제목
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr, gj_jik2 jk2 where jk2.jik2_code = mr.jik2_code and jk2.jik2_name like '%"+temp[i]+"%') or "); //희망직종
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_gyoung like '%"+temp[i]+"%') or "); //경력사항
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_lan gl, gj_mem_resume mr, gj_lan_c gc where gl.lan_c_code = gc.lan_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.lan_name like '%"+temp[i]+"%') or ");  //외국어
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_license gl, gj_mem_resume mr, gj_license_c gc where gl.license_c_code = gc.license_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.license_name like '%"+temp[i]+"%')");  //자격증
				if(i!= temp.length-1){
					sql.append(" or ");
				}
			}
			sql.append(")");
		}
		
		/*
		if( temp.length == 1){    //검색어가 하나일 경우
			if(temp[0].length() !=0){ //검색값이 빈값이 아닐 경우
				// 제목 희망직종 경력사항 외국어 자격증
				sql.append("and mr.mem_pr_num = any((select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_title like '%"+temp[0]+"%'),");  //제목
				sql.append("(select mr.mem_pr_num from gj_mem_resume mr, gj_jik2 jk2 where jk2.jik2_code = mr.jik2_code and jk2.jik2_name like '%"+temp[0]+"%'),"); //희망직종
				sql.append("(select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_gyoung like '%"+temp[0]+"%'),"); //경력사항
				sql.append("(select mr.mem_pr_num from gj_lan gl, gj_mem_resume mr, gj_lan_c gc where gl.lan_c_code = gc.lan_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.lan_name like '%"+temp[0]+"%'),");  //외국어
				sql.append("(select mr.mem_pr_num from gj_license gl, gj_mem_resume mr, gj_license_c gc where gl.license_c_code = gc.license_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.license_name like '%"+temp[0]+"%')) ");  //자격증
			}
		}else{  //검색어가 다수 일 경우
			sql.append("and mr.mem_pr_num = any(");
			for(int i=0; i<temp.length; i++){
				sql.append("select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_title like '%"+temp[i]+"%'),");  //제목
				sql.append("select mr.mem_pr_num from gj_mem_resume mr, gj_jik2 jk2 where jk2.jik2_code = mr.jik2_code and jk2.jik2_name like '%"+temp[i]+"%'),"); //희망직종
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_mem_resume mr where mr.mem_pr_gyoung like '%"+temp[i]+"%'),"); //경력사항
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_lan gl, gj_mem_resume mr, gj_lan_c gc where gl.lan_c_code = gc.lan_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.lan_name like '%"+temp[i]+"%'),");  //외국어
				sql.append("mr.mem_pr_num = any(select mr.mem_pr_num from gj_license gl, gj_mem_resume mr, gj_license_c gc where gl.license_c_code = gc.license_c_code and gl.mem_pr_num = mr.mem_pr_num and gc.license_name like '%"+temp[i]+"%')");  //자격증
				if(i!= temp.length-1){
					sql.append(",");
				}
			}
			sql.append(")");
		}
		*/
		try{
			sql.append(" order by mp.mem_p_name ) aa ) where rnum>="+startRow+" and rnum<="+endRow+"");
			//-- 경력순, 자격증수순, 수정일순, 나이순 정렬
			//--이름 성별 나이 제목 희망직종 경력사항 외국어 자격증 수정일
			con = DbcpBean.getConn();
			pstmt = con.prepareStatement(sql.toString());
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
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
		
	}
	
	public GJ_P_FILE_DTO detail1(int file_p_code){
      Connection con=null;
      PreparedStatement pstmt=null;
      ResultSet rs=null;
      try{
         con=DbcpBean.getConn();
         String sql="select * from gj_p_file where file_p_code=?";
         pstmt=con.prepareStatement(sql);
         pstmt.setInt(1,file_p_code);
         rs=pstmt.executeQuery();
         if(rs.next()){
            GJ_P_FILE_DTO dto=new GJ_P_FILE_DTO(
                  rs.getInt("file_p_code"),
                  rs.getString("file_p_name"),
                  rs.getString("file_p_sname"),
                  rs.getDate("file_p_date"),
                  rs.getInt("mem_pr_num"));
            return dto;
         }
         return null;
      }catch(SQLException se){
         System.out.println(se.getMessage());
         return null;
      }finally{
         DbcpBean.close(rs, pstmt, con);
      }
   }
	
	public GJ_C_FILE_DTO detail(int file_c_code){
	      Connection con=null;
	      PreparedStatement pstmt=null;
	      ResultSet rs=null;
	      try{
	         con=DbcpBean.getConn();
	         String sql="select * from gj_c_file where file_c_code=?";
	         pstmt=con.prepareStatement(sql);
	         pstmt.setInt(1,file_c_code);
	         rs=pstmt.executeQuery();
	         if(rs.next()){
	            GJ_C_FILE_DTO dto=new GJ_C_FILE_DTO(
	                  rs.getInt("file_c_code"),
	                  rs.getString("file_c_name"),
	                  rs.getString("file_c_sname"),
	                  rs.getDate("file_c_date"),
	                  rs.getInt("mem_cr_num"));
	            return dto;
	         }
	         return null;
	      }catch(SQLException se){
	         System.out.println(se.getMessage());
	         return null;
	      }finally{
	         DbcpBean.close(rs, pstmt, con);
	      }
	   }
}
