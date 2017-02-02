package goodjob.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import goodjob.dbcp.DbcpBean;
import goodjob.dto.GJ_MEM_P_DTO;

public class CDAO {
	public int insert(GJ_MEM_P_DTO mem){
		Connection con=null;
		PreparedStatement pstmt=null;
		try{
			con=DbcpBean.getConn();
			String sql="insert into gj_mem_p values(?,?,?,?,?,?,?,?,sysdate)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,mem.getMem_p_id());
			pstmt.setString(2,mem.getMem_p_pwd());
			pstmt.setString(3,mem.getMem_p_name());
			pstmt.setString(4,mem.getMem_p_birth());
			pstmt.setString(5,mem.getMem_p_addr());
			pstmt.setString(6,mem.getMem_p_phone());
			pstmt.setString(7,mem.getMem_p_email());
			pstmt.setString(8,mem.getMem_p_gender());
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt,con);
		}
	}
	
	public GJ_MEM_P_DTO all(String mem_p_id){
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		GJ_MEM_P_DTO mm=null;
		try{
			con=DbcpBean.getConn();
			String sql="select * from gj_mem_p where mem_p_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,mem_p_id);
			rs=pstmt.executeQuery();
			if(rs.next()){
				String id=rs.getString("mem_p_id");
				String pwd=rs.getString("mem_p_pwd");
				String name=rs.getString("mem_p_name");
				String birth=rs.getString("mem_p_birth");
				String addr=rs.getString("mem_p_addr");
				String phone=rs.getString("mem_p_phone");
				String email=rs.getString("mem_p_email");
				String gender=rs.getString("mem_p_gender");
				Date date=rs.getDate("mem_p_date");
				 mm=new GJ_MEM_P_DTO(id,pwd,name,birth,addr,phone,email,gender,date);
			}
			return mm;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs,pstmt,con);
		}
	}
	
	public int update1(GJ_MEM_P_DTO mem){
		Connection con=null;
		PreparedStatement pstmt=null;
		try{
			con=DbcpBean.getConn();
			String sql="update gj_mem_p set mem_p_pwd=?,mem_p_addr=?,mem_p_phone=?,mem_p_email=? where mem_p_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,mem.getMem_p_pwd());
			pstmt.setString(2,mem.getMem_p_addr());
			pstmt.setString(3,mem.getMem_p_phone());
			pstmt.setString(4,mem.getMem_p_email());
			pstmt.setString(5,mem.getMem_p_id());
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt,con);
		}
	}
	
	public int delete(String mem_p_id){
		Connection con=null;
		PreparedStatement pstmt=null;
		try{
			con=DbcpBean.getConn();
			String sql="delete from gj_mem_p where mem_p_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,mem_p_id);
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
