package goodjob.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import goodjob.dbcp.DbcpBean;
import goodjob.dto.GJ_DETAIL_RESUME;
import goodjob.dto.GJ_EXPINFO_L;
import goodjob.dto.GJ_HAK_L;
import goodjob.dto.GJ_LAN_L;
import goodjob.dto.GJ_LICENSE_L;
import goodjob.dto.GJ_P_FILE_DTO;
import goodjob.dto.GJ_P_FILE_L;
import goodjob.dto.GJ_RESUME_S;
import goodjob.dto.GJ_LAN_CER_L;
import goodjob.dto.GJ_LAN_DTO;

public class SearchK2DAO {
	
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
					+ "mp.mem_p_birth, to_number(to_char(sysdate, 'yyyy'))- to_number(substr(mp.mem_p_birth,0,2))+1-1900 \"AGE\", mp.mem_p_email, mp.mem_p_phone, mp.mem_p_addr, "
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
				+ "to_number(substr(mp.mem_p_birth,0,2))+1-1900 \"AGE\" from gj_mem_resume mr, gj_mem_p mp where mp.mem_p_id = mr.mem_p_id) bb where bb.age >= "+ age1 +" and bb.age <= "+ age2 +")");
		
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
		sql.append("select * from (select aa.*, rownum \"RNUM\" from (select mr.mem_pr_num \"NUM\", mp.mem_p_name \"NAME\", mp.mem_p_gender \"GENDER\", to_number(to_char(sysdate, 'yyyy'))- to_number(substr(mp.mem_p_birth,0,2))+1-1900 \"AGE\", "
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
				+ "to_number(substr(mp.mem_p_birth,0,2))+1-1900 \"AGE\" from gj_mem_resume mr, gj_mem_p mp where mp.mem_p_id = mr.mem_p_id) bb where bb.age >= "+ age1 +" and bb.age <= "+ age2 +")");
		
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
	public ArrayList<GJ_RESUME_S> searchResume(String word, int startRow, int endRow){
		//--이름 성별 나이 제목 희망직종 경력사항 외국어 자격증 수정일
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] temp = word.trim().split(" ");  // 검색어의 양옆 공백을 자른후 공백을 기준으로 나눔.
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select aa.*, rownum \"RNUM\" from (select mr.mem_pr_num \"NUM\", mp.mem_p_name \"NAME\", mp.mem_p_gender \"GENDER\", to_number(to_char(sysdate, 'yyyy'))- to_number(substr(mp.mem_p_birth,0,2))+1-1900 \"AGE\", "
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
	
	public ArrayList<GJ_P_FILE_L> getPFilelist(int num){
		ArrayList<GJ_P_FILE_L> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = DbcpBean.getConn();
			String sql = "select gf.file_p_name, gf.file_p_sname from gj_p_file gf, gj_mem_resume mr "
					+ "where gf.mem_pr_num = mr.mem_pr_num and mr.mem_pr_num = ?";
		
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_P_FILE_L dto = new GJ_P_FILE_L(rs.getInt("file_c_code"), rs.getString("file_c_name"), rs.getString("file_c_sname"));
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
	
	
}
