package goodjob.dbcp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/*

	DBCP (DataBase Connection Pool)
	- dbms�� ���ӵ� Ŀ�ؼǵ��� ������ ��������(���ؼ�Ǯ) DB�� �����Ҷ� ��������� ���� ���ؼǰ�ü��
	   ���� ����ϰ� �۾��� ������ ���ؼǰ�ü�� �ٽ� ���ؼ�Ǯ�� ��ȯ�Ѵ�.
	- ��������� ���� ����Ѵ�.   

 */
public class DbcpBean {
	public static Connection getConn(){
		try{
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/myoracle");
			Connection conn = ds.getConnection();
			return conn;
		}catch(NamingException ne){
			System.out.println(ne.getMessage());
			return null;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}
	}
	public static void close(ResultSet rs,Statement stmt,Connection con){
		try{
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			if(con!=null) con.close();
		}catch(SQLException se){
			System.out.println(se.getMessage());
		}
	}
}
