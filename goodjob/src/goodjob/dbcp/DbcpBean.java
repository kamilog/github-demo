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
	- dbms와 접속된 커넥션들을 여러개 만들어놓고(컨넥션풀) DB와 접속할때 사용중이지 않은 컨넥션객체를
	   얻어와 사용하고 작업이 끝나면 컨넥션객체를 다시 컨넥션풀에 반환한다.
	- 성능향상을 위해 사용한다.   

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
