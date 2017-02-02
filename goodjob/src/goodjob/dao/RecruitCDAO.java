package goodjob.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import goodjob.dbcp.DbcpBean;
import goodjob.dto.GJ_C_FILE_DTO;
import goodjob.dto.GJ_MEM_RECRUIT_DTO;
import goodjob.dto.GJ_P_FILE_DTO;

public class RecruitCDAO {
	
	public String getJik2_name (int jik2_code){
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String n="";
		try{
			con=DbcpBean.getConn();
			String sql="select jik2_name from gj_jik2 where jik2_code=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,jik2_code);
			rs=pstmt.executeQuery();
			if(rs.next()){
				n=rs.getString("jik2_name");
			}
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs,pstmt,con);
		}
	}
			
	public int insert(GJ_MEM_RECRUIT_DTO mem){
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int n=-1;
		try{
			con=DbcpBean.getConn();
			String sql="insert into gj_mem_recruit values(GJ_MEM_RECRUIT_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,mem.getMem_cr_title());
			pstmt.setString(2,mem.getMem_cr_gyoung());
			pstmt.setString(3,mem.getMem_cr_sal());
			pstmt.setString(4,mem.getMem_cr_jobtype());
			pstmt.setString(5,mem.getMem_cr_jobtime());
			pstmt.setString(6,mem.getMem_cr_rtime());
			pstmt.setString(7,mem.getMem_cr_chadate());
			pstmt.setString(8,mem.getMem_cr_workout());
			pstmt.setInt(9,mem.getMem_cr_recruitnumber());
			pstmt.setString(10,mem.getMem_cr_age());
			pstmt.setString(11,mem.getMem_cr_gender());
			pstmt.setString(12,mem.getMem_cr_document());
			pstmt.setString(13,mem.getMem_cr_howto());
			pstmt.setString(14,mem.getMem_cr_addr());
			pstmt.setString(15,mem.getMem_cr_qna());
			pstmt.setString(16,mem.getMem_cr_qnaname());
			pstmt.setString(17,mem.getMem_cr_hak());
			pstmt.setString(18,mem.getMem_cr_license());
			pstmt.setString(19,mem.getMem_cr_lan_cer());
			pstmt.setString(20,mem.getMem_cr_lan());
			pstmt.setBoolean(21,mem.isMem_cr_check());
			pstmt.setInt(22,mem.getMem_cr_hit());
			pstmt.setString(23,mem.getMem_c_id());
			pstmt.setInt(24,mem.getJik2_code());
			pstmt.executeUpdate();
			pstmt.close();
			sql="select GJ_MEM_RECRUIT_SEQ.currval from dual";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				n=rs.getInt(1);
			}
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt,con);
		}
	}
	
	public GJ_MEM_RECRUIT_DTO all(int mem_cr_num){
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		GJ_MEM_RECRUIT_DTO mm=null;
		try{
			con=DbcpBean.getConn();
			String sql="select * from gj_mem_recruit where mem_cr_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,mem_cr_num);
			rs=pstmt.executeQuery();
			if(rs.next()){
				int num=rs.getInt("mem_cr_num");
				String title=rs.getString("mem_cr_title");
				String gyoung=rs.getString("mem_cr_gyoung");
				String sal=rs.getString("mem_cr_sal");
				String jobtype=rs.getString("mem_cr_jobtype");
				String jobtime=rs.getString("mem_cr_jobtime");
				String rtime=rs.getString("mem_cr_rtime");
				String chadate=rs.getString("mem_cr_chadate");
				String workout=rs.getString("mem_cr_workout");
				int recruitnumber=rs.getInt("mem_cr_recruitnumber");
				String age=rs.getString("mem_cr_age");
				String gender=rs.getString("mem_cr_gender");
				String document=rs.getString("mem_cr_document");
				String howto=rs.getString("mem_cr_howto");
				String addr=rs.getString("mem_cr_addr");
				String qna=rs.getString("mem_cr_qna");
				String qnaname=rs.getString("mem_cr_qnaname");
				String hak=rs.getString("mem_cr_hak");
				String license=rs.getString("mem_cr_license");
				String lan_cer=rs.getString("mem_cr_lan_cer");
				String lan=rs.getString("mem_cr_lan");
				Date date=rs.getDate("mem_cr_date");
				boolean check=rs.getBoolean("mem_cr_check");
				int hit=rs.getInt("mem_cr_hit");
				String mem_c_id=rs.getString("mem_c_id");
				int jik2_code=rs.getShort("jik2_code");
				mm=new GJ_MEM_RECRUIT_DTO(num,title,gyoung,sal,jobtype,jobtime,rtime,chadate,workout,
						recruitnumber,age,gender,document,howto,addr,qna,qnaname,hak,license,lan_cer,
						lan,date,check,hit,	mem_c_id,jik2_code);
			}
			return mm;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs,pstmt,con);
		}
	}
	
	public int update1(GJ_MEM_RECRUIT_DTO mem){
		Connection con=null;
		PreparedStatement pstmt=null;
		try{
			con=DbcpBean.getConn();
			String sql="update GJ_MEM_RECRUIT set MEM_CR_TITLE=?,MEM_CR_GYOUNG=?,MEM_CR_SAL=?,MEM_CR_JOBTYPE=?,"
					+ "MEM_CR_JOBTIME=?,MEM_CR_RTIME=?,MEM_CR_CHADATE=?,MEM_CR_WORKOUT=?,MEM_CR_RECRUITNUMBER=?,"
					+ "MEM_CR_AGE=?,MEM_CR_GENDER=?,MEM_CR_DOCUMENT=?,MEM_CR_HOWTO=?,MEM_CR_ADDR=?,MEM_CR_QNA=?,"
					+ "MEM_CR_QNANAME=?,MEM_CR_HAK=?,MEM_CR_LICENSE=?,MEM_CR_LAN_CER=?,MEM_CR_LAN=?,"
					+ "MEM_CR_CHECK=?,JIK2_CODE=? WHERE MEM_CR_NUM=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,mem.getMem_cr_title());
			pstmt.setString(2,mem.getMem_cr_gyoung());
			pstmt.setString(3,mem.getMem_cr_sal());
			pstmt.setString(4,mem.getMem_cr_jobtype());
			pstmt.setString(5,mem.getMem_cr_jobtime());
			pstmt.setString(6,mem.getMem_cr_rtime());
			pstmt.setString(7,mem.getMem_cr_chadate());
			pstmt.setString(8,mem.getMem_cr_workout());
			pstmt.setInt(9,mem.getMem_cr_recruitnumber());
			pstmt.setString(10,mem.getMem_cr_age());
			pstmt.setString(11,mem.getMem_cr_gender());
			pstmt.setString(12,mem.getMem_cr_document());
			pstmt.setString(13,mem.getMem_cr_howto());
			pstmt.setString(14,mem.getMem_cr_addr());
			pstmt.setString(15,mem.getMem_cr_qna());
			pstmt.setString(16,mem.getMem_cr_qnaname());
			pstmt.setString(17,mem.getMem_cr_hak());
			pstmt.setString(18,mem.getMem_cr_license());
			pstmt.setString(19,mem.getMem_cr_lan_cer());
			pstmt.setString(20,mem.getMem_cr_lan());
			pstmt.setBoolean(21,mem.isMem_cr_check());
			pstmt.setInt(22,mem.getJik2_code());
			pstmt.setInt(23,mem.getMem_cr_num());
			int n=pstmt.executeUpdate();
			
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt,con);
		}
	}
	
	public ArrayList<GJ_MEM_RECRUIT_DTO> list(String mem_c_id){
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			con=DbcpBean.getConn();
			String sql="select * from gj_mem_recruit where mem_c_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,mem_c_id);
			rs=pstmt.executeQuery();
			ArrayList<GJ_MEM_RECRUIT_DTO> list=new ArrayList<>();
			GJ_MEM_RECRUIT_DTO mm=null;
			while(rs.next()){
				//GJ_MEM_RECRUIT_DTO mm=new GJ_MEM_RECRUIT_DTO();
				//mm.setMem_c_id(rs.getString("mem_c_id"));
				//mm.setMem_cr_num(rs.getInt("mem_cr_num"));
				//mm.setMem_cr_title(rs.getString("mem_cr_title"));
				int num=rs.getInt("mem_cr_num");
				String title=rs.getString("mem_cr_title");
				String gyoung=rs.getString("mem_cr_gyoung");
				String sal=rs.getString("mem_cr_sal");
				String jobtype=rs.getString("mem_cr_jobtype");
				String jobtime=rs.getString("mem_cr_jobtime");
				String rtime=rs.getString("mem_cr_rtime");
				String chadate=rs.getString("mem_cr_chadate");
				String workout=rs.getString("mem_cr_workout");
				int recruitnumber=rs.getInt("mem_cr_recruitnumber");
				String age=rs.getString("mem_cr_age");
				String gender=rs.getString("mem_cr_gender");
				String document=rs.getString("mem_cr_document");
				String howto=rs.getString("mem_cr_howto");
				String addr=rs.getString("mem_cr_addr");
				String qna=rs.getString("mem_cr_qna");
				String qnaname=rs.getString("mem_cr_qnaname");
				String hak=rs.getString("mem_cr_hak");
				String license=rs.getString("mem_cr_license");
				String lan_cer=rs.getString("mem_cr_lan_cer");
				String lan=rs.getString("mem_cr_lan");
				Date date=rs.getDate("mem_cr_date");
				boolean check=rs.getBoolean("mem_cr_check");
				int hit=rs.getInt("mem_cr_hit");
				mem_c_id=rs.getString("mem_c_id");
				int jik2_code=rs.getShort("jik2_code");
				mm=new GJ_MEM_RECRUIT_DTO(num,title,gyoung,sal,jobtype,jobtime,rtime,chadate,workout,
						recruitnumber,age,gender,document,howto,addr,qna,qnaname,hak,license,lan_cer,
						lan,date,check,hit,	mem_c_id,jik2_code);
				list.add(mm);
			}
			return list;
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs,pstmt,con);
		}
	}
	public int delete(int mem_cr_num){
		Connection con=null;
		PreparedStatement pstmt=null;
		try{
			con=DbcpBean.getConn();
			String sql="delete from gj_mem_recruit where mem_cr_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,mem_cr_num);
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt,con);
		}
	}
	
	public int fileinsert(GJ_C_FILE_DTO file){
		Connection con=null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmt1=null;
		ResultSet rs=null;
		try{
			con=DbcpBean.getConn();
			String sql="select gj_mem_recruit_seq.currval from dual";
			String sql1="insert into gj_c_file values(gj_c_file_seq.nextval,?,?,sysdate,gj_mem_recruit_seq.currval)";
			pstmt=con.prepareStatement(sql);
			pstmt1=con.prepareStatement(sql1);
			pstmt1.setString(1,file.getFile_c_name());
			pstmt1.setString(2,file.getFile_c_sname());
			rs=pstmt.executeQuery();
			rs.next();
			int n=pstmt1.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt,con);
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
}

