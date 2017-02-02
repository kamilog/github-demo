package goodjob.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import goodjob.dbcp.DbcpBean;

public class CheckadminBDAO {
	
	private static CheckadminBDAO instance=new CheckadminBDAO();
	
	// 2. �����ڸ� private�� ����� �ܺο����� �������� ���ϰ� �Ѵ�.
	private CheckadminBDAO(){}
	
	// 3. 1���� ������ ��ü�� �����ϴ� static�޼ҵ带 �����Ѵ�
	public static CheckadminBDAO getInstance(){
		return instance;
	}
	
	public int isAdmin(HashMap<String,String> map1){
		//map�� ��� ���� ��������
		String id=map1.get("id");
		String pwd=map1.get("pwd");
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			con=DbcpBean.getConn();
			String sql="select * from gj_admin where admin_id=? and admin_pwd=?";
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
