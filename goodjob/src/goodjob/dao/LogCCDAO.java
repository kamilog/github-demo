package goodjob.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import goodjob.dbcp.DbcpBean;

public class LogCCDAO {
	// 1. ��ü �ڽ��� static����� �����Ѵ�.
	private static LogCCDAO instance=new LogCCDAO();
	
	// 2. �����ڸ� private�� ����� �ܺο����� �������� ���ϰ� �Ѵ�.
	private LogCCDAO(){}
	
	// 3. 1���� ������ ��ü�� �����ϴ� static�޼ҵ带 �����Ѵ�
	public static LogCCDAO getInstance(){
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
			String sql="select * from gj_mem_c where mem_c_id=? and mem_c_pwd=?";
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
