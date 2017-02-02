package goodjob.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import goodjob.dbcp.DbcpBean;
import goodjob.dto.GJ_MEM_C_DTO;

public class CCDAO {
	public int insert(GJ_MEM_C_DTO mem){
		Connection  con=null;
		PreparedStatement pstmt=null;
		try{
			con=DbcpBean.getConn();
			String sql="insert into gj_mem_c values(?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,mem.getMem_c_id());
			pstmt.setString(2,mem.getMem_c_pwd());
			pstmt.setString(3,mem.getMem_c_name());
			pstmt.setString(4,mem.getMem_c_comnum());
			pstmt.setString(5,mem.getMem_c_bossname());
			pstmt.setString(6,mem.getMem_c_addr());
			pstmt.setString(7,mem.getMem_c_homepage());
			pstmt.setString(8,mem.getMem_c_intro());
			pstmt.setString(9,mem.getMem_c_year());
			pstmt.setString(10,mem.getMem_c_sawon());
			pstmt.setString(11,mem.getMem_c_jabon());
			pstmt.setString(12,mem.getMem_c_machul());
			pstmt.setString(13,mem.getMem_c_img());
			pstmt.setString(14,mem.getMem_c_simg());
			pstmt.setInt(15,mem.getUp_code());
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt,con);
		}
	}
	
	public GJ_MEM_C_DTO all(String mem_c_id){
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		GJ_MEM_C_DTO mm=null;
		try{
			con=DbcpBean.getConn();
			String sql="select * from gj_mem_c where mem_c_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,mem_c_id);
			rs=pstmt.executeQuery();
			if(rs.next()){
				String id=rs.getString("mem_c_id");
				String pwd=rs.getString("mem_c_pwd");
				String name=rs.getString("mem_c_name");
				String comnum=rs.getString("mem_c_comnum");
				String bossname=rs.getString("mem_c_bossname");
				String addr=rs.getString("mem_c_addr");
				String homepage=rs.getString("mem_c_homepage");
				String intro=rs.getString("mem_c_intro");
				String year=rs.getString("mem_c_year");
				String sawon=rs.getString("mem_c_sawon");
				String jabon=rs.getString("mem_c_jabon");
				String machul=rs.getString("mem_c_machul");
				Date date=rs.getDate("mem_c_date");
				String img=rs.getString("mem_c_img");
				String simg=rs.getString("mem_c_simg");
				int up_code=rs.getInt("up_code");
				 mm=new GJ_MEM_C_DTO(id,pwd,name,comnum,bossname,addr,homepage,intro,year,sawon,jabon,machul,date,
						 img,simg,up_code);
			}
			return mm;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs,pstmt,con);
		}
	}
	
	public int update1(GJ_MEM_C_DTO mem){
		Connection con=null;
		PreparedStatement pstmt=null;
		try{
			con=DbcpBean.getConn();
			String sql="update gj_mem_c set mem_c_pwd=?,mem_c_name=?,mem_c_bossname=?,mem_c_addr=?,mem_c_homepage=?,"
					+"mem_c_intro=?,mem_c_sawon=?,mem_c_jabon=?,mem_c_machul=?,mem_c_img=?,mem_c_simg=?"
					+ " where mem_c_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,mem.getMem_c_pwd());
			pstmt.setString(2,mem.getMem_c_name());
			pstmt.setString(3,mem.getMem_c_bossname());
			pstmt.setString(4,mem.getMem_c_addr());
			pstmt.setString(5,mem.getMem_c_homepage());
			pstmt.setString(6,mem.getMem_c_intro());
			pstmt.setString(7,mem.getMem_c_sawon());
			pstmt.setString(8,mem.getMem_c_jabon());
			pstmt.setString(9,mem.getMem_c_machul());
			pstmt.setString(10,mem.getMem_c_img());
			pstmt.setString(11,mem.getMem_c_simg());
			pstmt.setString(12,mem.getMem_c_id());
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt,con);
		}
	}
	
	public int delete(String mem_c_id){
		Connection con=null;
		PreparedStatement pstmt=null;
		try{
			con=DbcpBean.getConn();
			String sql="delete from gj_mem_c where mem_c_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,mem_c_id);
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt,con);
		}
	}
}
