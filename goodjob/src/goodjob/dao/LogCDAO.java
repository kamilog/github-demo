package goodjob.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import goodjob.dbcp.DbcpBean;
import goodjob.dto.GJ_MEM_P_DTO;

public class LogCDAO {
	// 1. ��ü �ڽ��� static����� �����Ѵ�.
	private static LogCDAO instance=new LogCDAO();
	
	// 2. �����ڸ� private�� ����� �ܺο����� �������� ���ϰ� �Ѵ�.
	private LogCDAO(){}
	
	// 3. 1���� ������ ��ü�� �����ϴ� static�޼ҵ带 �����Ѵ�
	public static LogCDAO getInstance(){
		return instance;
	}
	public int isMember(HashMap<String,String> map){
		//map�� ��� ���� ��������
		String id=map.get("id");
		String pwd=map.get("pwd");
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			con=DbcpBean.getConn();
			String sql="select * from gj_mem_p where mem_p_id=? and mem_p_pwd=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,id);
			pstmt.setString(2,pwd);
			rs=pstmt.executeQuery();
			if(rs.next()){
				return 1;
			}else{
				return 0;
			}
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
}
