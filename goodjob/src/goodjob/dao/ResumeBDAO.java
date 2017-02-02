package goodjob.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import goodjob.dbcp.DbcpBean;
import goodjob.dto.GJ_EXPINFO_DTO;
import goodjob.dto.GJ_HAK_DTO;
import goodjob.dto.GJ_LAN_CER_DTO;
import goodjob.dto.GJ_LAN_DTO;
import goodjob.dto.GJ_LICENSE_DTO;
import goodjob.dto.GJ_MEM_RESUME_DTO;
import goodjob.dto.GJ_P_FILE_DTO;
import goodjob.dto.GJ_TEMP_DTO;

public class ResumeBDAO {
	
	public ArrayList<GJ_TEMP_DTO> searchMajor(String major){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GJ_TEMP_DTO> list = new ArrayList<>();
		try{
			String sql = "select major_code \"CODE\", major_name \"NAME\" from gj_major where major_name like ?";
			con = DbcpBean.getConn();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+major+"%");
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_TEMP_DTO dto = new GJ_TEMP_DTO(rs.getInt("CODE"),rs.getString("NAME"));
				list.add(dto);
			}
			return list;
		}catch(SQLException se){
			System.out.println(se.getMessage()+"라라라라");
			
			return null;
		}
		finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	
	//--  자격증 검색
	public ArrayList<GJ_TEMP_DTO> searchLicense(String license_name){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GJ_TEMP_DTO> list = new ArrayList<>();
		try{
			String sql = "select license_c_code \"CODE\", license_name \"NAME\" from gj_license_c where license_name like ?";
			con = DbcpBean.getConn();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+license_name+"%");
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_TEMP_DTO dto = new GJ_TEMP_DTO(rs.getInt("CODE"),rs.getString("NAME"));
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
	
	
	//--  언어 검색
	public ArrayList<GJ_TEMP_DTO> searchLanCer(String lan_cer_name){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GJ_TEMP_DTO> list = new ArrayList<>();
		try{
			String sql = "select lan_cer_c_code \"CODE\", lan_cer_name \"NAME\" from gj_lan_cer_c where lan_cer_name like ?";
			
			con = DbcpBean.getConn();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+lan_cer_name+"%");
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_TEMP_DTO dto = new GJ_TEMP_DTO(rs.getInt("CODE"),rs.getString("NAME"));
				
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
	
	
	//--  언어 검색
	public ArrayList<GJ_TEMP_DTO> searchLan(String lan_name){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GJ_TEMP_DTO> list = new ArrayList<>();
		try{
			String sql = "select lan_c_code \"CODE\", lan_name \"NAME\" from gj_lan_c where lan_name like ?";
			con = DbcpBean.getConn();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+lan_name+"%");
			rs = pstmt.executeQuery();
			while(rs.next()){
				GJ_TEMP_DTO dto = new GJ_TEMP_DTO(rs.getInt("CODE"),rs.getString("NAME"));
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
	
	
	public int insert(GJ_MEM_RESUME_DTO resume){
		Connection  con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int n=-1;
		
		try{
			con=DbcpBean.getConn();
			String sql="insert into gj_mem_resume values(gj_mem_resume_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, resume.getMem_pr_title());
			pstmt.setString(2, resume.getMem_pr_marry());
			pstmt.setString(3, resume.getMem_pr_byoung());
			pstmt.setString(4, resume.getMem_pr_bohoon());
			pstmt.setString(5, resume.getMem_pr_boho());
			pstmt.setString(6, resume.getMem_pr_handy());
			
			pstmt.setString(7, resume.getMem_pr_gyoung());
			pstmt.setString(8, resume.getMem_pr_exp());
			pstmt.setString(9, resume.getMem_pr_prize());
			pstmt.setString(10, resume.getMem_pr_wishsal());
			pstmt.setString(11, resume.getMem_pr_resume());
						
			pstmt.setString(12, resume.getMem_pr_img());
			pstmt.setString(13, resume.getMem_pr_simg());
			pstmt.setBoolean(14, resume.isMem_pr_check());
			pstmt.setString(15, resume.getMem_p_id());
			pstmt.setInt(16, resume.getJik2_code());
						
			pstmt.executeUpdate();
			pstmt.close();
								
			
			
			sql="select gj_mem_resume_seq.currval from dual";
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
			DbcpBean.close(rs,pstmt,con);
		}
	}
	
	
	public int insertHak2(String hak_name, String hak_uniname, String hakdate){
		Connection  con=null;
		PreparedStatement pstmt1=null;
		PreparedStatement pstmt2=null;
		ResultSet rs=null;
		try{
			con=DbcpBean.getConn();
			String sql1="select gj_mem_resume_seq.currval from dual";
			String sql2="insert into gj_hak values(gj_hak_seq.nextval,?,?,?,gj_mem_resume_seq.currval,null)";
			pstmt1=con.prepareStatement(sql1);
			pstmt2=con.prepareStatement(sql2);
			
			pstmt2.setString(1, hak_name);
			pstmt2.setString(2, hak_uniname);
			pstmt2.setString(3, hakdate);
					
			rs=pstmt1.executeQuery();
			rs.next();
			System.out.println(rs.getInt(1));
			int n=pstmt2.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt2,con);
			DbcpBean.close(null, pstmt1, null);
		}
	} 
	public int insertHak(GJ_HAK_DTO hak){
		Connection  con=null;
		PreparedStatement pstmt1=null;
		PreparedStatement pstmt2=null;
		ResultSet rs=null;
		try{
			con=DbcpBean.getConn();
			String sql1="select gj_mem_resume_seq.currval from dual";
			String sql2="insert into gj_hak values(gj_hak_seq.nextval,?,?,?,gj_mem_resume_seq.currval,?)";
			pstmt1=con.prepareStatement(sql1);
			pstmt2=con.prepareStatement(sql2);
			
			pstmt2.setString(1, hak.getHak_type());
			pstmt2.setString(2, hak.getHak_name());
			pstmt2.setString(3, hak.getHak_date());
			pstmt2.setInt(4, hak.getMajor_code());
			
			System.out.println(hak.getHak_code());
				
			
			rs=pstmt1.executeQuery();
			rs.next();
			System.out.println(rs.getInt(1));
			int n=pstmt2.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt2,con);
			DbcpBean.close(null, pstmt1, null);
		}
	}
	
	public int insertLan(GJ_LAN_DTO lan){
		Connection  con=null;
		PreparedStatement pstmt=null;
		try{
			con=DbcpBean.getConn();
			String sql="insert into gj_lan values(gj_lan_seq.nextval,?,?,gj_mem_resume_seq.currval)";
			pstmt=con.prepareStatement(sql);
			
			pstmt.setInt(1, lan.getLan_c_code());
			pstmt.setString(2, lan.getLan_grade());
			
			System.out.println(lan.getLan_c_code());
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt,con);
		}
	}
	
	public int insertLancer(GJ_LAN_CER_DTO lancer){
		Connection  con=null;
		PreparedStatement pstmt=null;
		try{
			con=DbcpBean.getConn();
			String sql="insert into gj_lan_cer values(gj_lan_cer_seq.nextval,?,?,?,gj_mem_resume_seq.currval)";
			pstmt=con.prepareStatement(sql);
			
			pstmt.setInt(1, lancer.getLan_cer_c_code());
			pstmt.setString(2, lancer.getLan_cer_level());
			pstmt.setString(3, lancer.getLan_cer_date());
						
			System.out.println(lancer.getLan_cer_c_code());
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt,con);
		}
	}
	
	public int insertLicense(GJ_LICENSE_DTO license){
		Connection  con=null;
		PreparedStatement pstmt=null;
		try{
			con=DbcpBean.getConn();
			String sql="insert into gj_license values(gj_license_seq.nextval,?,?,gj_mem_resume_seq.currval)";
			pstmt=con.prepareStatement(sql);
			
			pstmt.setInt(1, license.getLicense_c_code());
			pstmt.setString(2, license.getLicense_date());
						
			System.out.println(license.getLicense_c_code());
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt,con);
		}
	}
	
	public int insertExpinfo(GJ_EXPINFO_DTO exp){
		Connection  con=null;
		PreparedStatement pstmt=null;
		try{
			con=DbcpBean.getConn();
			String sql="insert into gj_expinfo values(gj_expinfo_seq.nextval,?,?,?,?,gj_mem_resume_seq.currval)";
			pstmt=con.prepareStatement(sql);
			
			pstmt.setString(1, exp.getExpinfo_date());
			pstmt.setString(2, exp.getExpinfo_cname());
			pstmt.setString(3, exp.getExpinfo_grade());
			pstmt.setString(4, exp.getExpinfo_detail());
						
			int n=pstmt.executeUpdate();
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt,con);
		}
	}
	
	public GJ_MEM_RESUME_DTO get_mem_resume(int mem_pr_num){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		GJ_MEM_RESUME_DTO mm=null;
		try{
			String sql="select * from gj_mem_resume where mem_pr_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, mem_pr_num);
			rs=pstmt.executeQuery();
			while(rs.next()){
				int num=rs.getInt("mem_pr_num");
				String title=rs.getString("mem_pr_title");
				String marry=rs.getString("mem_pr_marry");
				String byoung=rs.getString("mem_pr_byoung");
				String bohoon=rs.getString("mem_pr_bohoon");
				String boho=rs.getString("mem_pr_boho");
				String handy=rs.getString("mem_pr_handy");
				String gyoung=rs.getString("mem_pr_gyoung");
				String exp=rs.getString("mem_pr_exp");
				String prize=rs.getString("mem_pr_prize");
				String wishsal=rs.getString("mem_pr_wishsal");
				String resume=rs.getString("mem_pr_resume");
				Date date=rs.getDate("mem_pr_date");
				String img=rs.getString("mem_pr_img");
				String simg=rs.getString("mem_pr_simg");
				boolean check=rs.getBoolean("mem_pr_check");
				String mem_c_id=rs.getString("mem_p_id");
				int jik2_code=rs.getInt("jik2_code");
				mm=new GJ_MEM_RESUME_DTO(num,title,marry,byoung,bohoon,boho,handy,gyoung,exp,prize,wishsal,resume,date,
										img,simg,check,mem_c_id,jik2_code);
			}
			return mm;
			
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	
	public String get_jik2_name(int mem_pr_num){
	      Connection con=DbcpBean.getConn();
	      PreparedStatement pstmt=null;
	      ResultSet rs=null;
	      try{
	         String sql="select jik2_name from gj_mem_resume r, gj_jik2 j where r.jik2_code=j.jik2_code and mem_pr_num=?";
	         pstmt=con.prepareStatement(sql);
	         pstmt.setInt(1, mem_pr_num);
	         rs=pstmt.executeQuery();
	         String jik2_name="";
	         while(rs.next()){
	        	 jik2_name=rs.getString("jik2_name");
	         }
	         return jik2_name;
	      }catch(SQLException se){
	         System.out.println(se.getMessage());
	         return null;
	      }finally{
	         DbcpBean.close(rs, pstmt, con);
	      }
	   }
	
	public GJ_HAK_DTO get_hak(int mem_pr_num){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		PreparedStatement pstmt2=null;
		ResultSet rs=null;
		GJ_HAK_DTO mm=null;
		try{
			String sql="select * from gj_hak where mem_pr_num=?";
			String sql2="select major_name from gj_major m, gj_hak h where m.major_code=h.major_code and mem_pr_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt2=con.prepareStatement(sql2);
			pstmt.setInt(1, mem_pr_num);
			pstmt2.setInt(1, mem_pr_num);
			rs=pstmt.executeQuery();
			while(rs.next()){
				int hak_code=rs.getInt("hak_code");
				String hak_type=rs.getString("hak_type");
				String hak_name=rs.getString("hak_name");
				String hak_date=rs.getString("hak_date");
				int num=rs.getInt("mem_pr_num");
				int major_code=rs.getInt("major_code");
				mm=new GJ_HAK_DTO(hak_code,hak_type,hak_name,hak_date,num,major_code);
			}
			return mm;
			
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	
	public String get_hakdate(int mem_pr_num){
	      Connection con=DbcpBean.getConn();
	      PreparedStatement pstmt=null;
	      ResultSet rs=null;
	      try{
	         String sql="select hak_date from gj_hak where mem_pr_num=?";
	         pstmt=con.prepareStatement(sql);
	         pstmt.setInt(1, mem_pr_num);
	         rs=pstmt.executeQuery();
	         String hak_date="";
	         while(rs.next()){
	        	 hak_date=rs.getString("hak_date");
	         }
	         return hak_date;
	      }catch(SQLException se){
	         System.out.println(se.getMessage());
	         return null;
	      }finally{
	         DbcpBean.close(rs, pstmt, con);
	      }
	}
	
	public String get_expdate(int mem_pr_num){
	      Connection con=DbcpBean.getConn();
	      PreparedStatement pstmt=null;
	      ResultSet rs=null;
	      try{
	         String sql="select expinfo_date from gj_expinfo where mem_pr_num=?";
	         pstmt=con.prepareStatement(sql);
	         pstmt.setInt(1, mem_pr_num);
	         rs=pstmt.executeQuery();
	         String expinfo_date="";
	         while(rs.next()){
	        	 expinfo_date=rs.getString("expinfo_date");
	         }
	         return expinfo_date;
	      }catch(SQLException se){
	         System.out.println(se.getMessage());
	         return null;
	      }finally{
	         DbcpBean.close(rs, pstmt, con);
	      }
	}
	
	public String get_major_name(int mem_pr_num){
	      Connection con=DbcpBean.getConn();
	      PreparedStatement pstmt=null;
	      ResultSet rs=null;
	      try{
	         String sql="select major_name from gj_major m, gj_hak h where m.major_code=h.major_code and mem_pr_num=?";
	         pstmt=con.prepareStatement(sql);
	         pstmt.setInt(1, mem_pr_num);
	         rs=pstmt.executeQuery();
	         String major_name="";
	         while(rs.next()){
	        	 major_name=rs.getString("major_name");
	         }
	         return major_name;
	      }catch(SQLException se){
	         System.out.println(se.getMessage());
	         return null;
	      }finally{
	         DbcpBean.close(rs, pstmt, con);
	      }
	   }
	
	public GJ_LICENSE_DTO get_license(int mem_pr_num){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		GJ_LICENSE_DTO mm=null;
		try{
			String sql="select * from gj_license where mem_pr_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, mem_pr_num);
			rs=pstmt.executeQuery();
			while(rs.next()){
				int license_code=rs.getInt("license_code");
				int license_c_code=rs.getInt("license_c_code");
				String license_date=rs.getString("license_date");
				int num=rs.getInt("mem_pr_num");
				mm=new GJ_LICENSE_DTO(license_code,license_c_code,license_date,num);
			}
			return mm;
			
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	
	public String get_license_name(int mem_pr_num){
	      Connection con=DbcpBean.getConn();
	      PreparedStatement pstmt=null;
	      ResultSet rs=null;
	      try{
	         String sql="select license_name from gj_license l, gj_license_c c where l.license_c_code=c.license_c_code and mem_pr_num=?";
	         pstmt=con.prepareStatement(sql);
	         pstmt.setInt(1, mem_pr_num);
	         rs=pstmt.executeQuery();
	         String license_name="";
	         while(rs.next()){
	        	 license_name=rs.getString("license_name");
	         }
	         return license_name;
	      }catch(SQLException se){
	         System.out.println(se.getMessage());
	         return null;
	      }finally{
	         DbcpBean.close(rs, pstmt, con);
	      }
	   }
	
	public GJ_EXPINFO_DTO get_expinfo(int mem_pr_num){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		GJ_EXPINFO_DTO mm=null;
		try{
			String sql="select * from gj_expinfo where mem_pr_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, mem_pr_num);
			rs=pstmt.executeQuery();
			while(rs.next()){
				int expinfo_code=rs.getInt("expinfo_code");
				String expinfo_date=rs.getString("expinfo_date");
				String expinfo_cname=rs.getString("expinfo_cname");
				String expinfo_grade=rs.getString("expinfo_grade");
				String expinfo_detail=rs.getString("expinfo_detail");
				int num=rs.getInt("mem_pr_num");
				mm=new GJ_EXPINFO_DTO(expinfo_code,expinfo_date,expinfo_cname,expinfo_grade,expinfo_detail,num);
			}
			return mm;
			
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	
	public GJ_LAN_CER_DTO get_lan_cer(int mem_pr_num){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		GJ_LAN_CER_DTO mm=null;
		try{
			String sql="select * from gj_lan_cer where mem_pr_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, mem_pr_num);
			rs=pstmt.executeQuery();
			while(rs.next()){
				int lan_cer_code=rs.getInt("lan_cer_code");
				int lan_cer_c_code=rs.getInt("lan_cer_c_code");
				String lan_cer_level=rs.getString("lan_cer_level");
				String lan_cer_date=rs.getString("lan_cer_date");
				int num=rs.getInt("mem_pr_num");
				mm=new GJ_LAN_CER_DTO(lan_cer_code,lan_cer_c_code,lan_cer_level,lan_cer_date,num);
			}
			return mm;
			
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	
	public String get_lan_cer_name(int mem_pr_num){
	      Connection con=DbcpBean.getConn();
	      PreparedStatement pstmt=null;
	      ResultSet rs=null;
	      try{
	         String sql="select lan_cer_name from gj_lan_cer l, gj_lan_cer_c c where l.lan_cer_c_code=c.lan_cer_c_code and mem_pr_num=?";
	         pstmt=con.prepareStatement(sql);
	         pstmt.setInt(1, mem_pr_num);
	         rs=pstmt.executeQuery();
	         String lan_cer_name="";
	         while(rs.next()){
	        	 lan_cer_name=rs.getString("lan_cer_name");
	         }
	         return lan_cer_name;
	      }catch(SQLException se){
	         System.out.println(se.getMessage());
	         return null;
	      }finally{
	         DbcpBean.close(rs, pstmt, con);
	      }
	   }
	
	public GJ_LAN_DTO get_lan(int mem_pr_num){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		GJ_LAN_DTO mm=null;
		try{
			String sql="select * from gj_lan where mem_pr_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, mem_pr_num);
			rs=pstmt.executeQuery();
			while(rs.next()){
				int lan_code=rs.getInt("lan_code");
				int lan_c_code=rs.getInt("lan_c_code");
				String lan_grade=rs.getString("lan_grade");
				int num=rs.getInt("mem_pr_num");
				mm=new GJ_LAN_DTO(lan_code,lan_c_code,lan_grade,num);
			}
			return mm;
			
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return null;
		}finally{
			DbcpBean.close(rs, pstmt, con);
		}
	}
	
	public String get_lan_name(int mem_pr_num){
	      Connection con=DbcpBean.getConn();
	      PreparedStatement pstmt=null;
	      ResultSet rs=null;
	      try{
	         String sql="select lan_name from gj_lan_c c, gj_lan l where c.lan_c_code=l.lan_c_code and mem_pr_num=?";
	         pstmt=con.prepareStatement(sql);
	         pstmt.setInt(1, mem_pr_num);
	         rs=pstmt.executeQuery();
	         String lan_name="";
	         while(rs.next()){
	        	 lan_name=rs.getString("lan_name");
	         }
	         return lan_name;
	      }catch(SQLException se){
	         System.out.println(se.getMessage());
	         return null;
	      }finally{
	         DbcpBean.close(rs, pstmt, con);
	      }
	   }
	
	public int update(GJ_MEM_RESUME_DTO resume){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		try{
			con=DbcpBean.getConn();
			String sql="update GJ_MEM_RESUME set MEM_PR_TITLE=?,MEM_PR_MARRY=?,MEM_PR_BYOUNG=?,MEM_PR_BOHOON=?,MEM_PR_BOHO=?,"
					+ "MEM_PR_HANDY=?,MEM_PR_GYOUNG=?,MEM_PR_EXP=?,MEM_PR_PRIZE=?,MEM_PR_WISHSAL=?,MEM_PR_RESUME=?,"
					+ "MEM_PR_CHECK=?,JIK2_CODE=? WHERE MEM_PR_NUM=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,resume.getMem_pr_title());
			pstmt.setString(2,resume.getMem_pr_marry());
			pstmt.setString(3,resume.getMem_pr_byoung());
			pstmt.setString(4, resume.getMem_pr_bohoon());
			pstmt.setString(5, resume.getMem_pr_boho());
			pstmt.setString(6, resume.getMem_pr_handy());
			pstmt.setString(7, resume.getMem_pr_gyoung());
			pstmt.setString(8, resume.getMem_pr_exp());
			pstmt.setString(9, resume.getMem_pr_prize());
			pstmt.setString(10, resume.getMem_pr_wishsal());
			pstmt.setString(11, resume.getMem_pr_resume());
			pstmt.setBoolean(12, resume.isMem_pr_check());
			pstmt.setInt(13, resume.getJik2_code());
			pstmt.setInt(14, resume.getMem_pr_num());
			
			int n=pstmt.executeUpdate();
			
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt,con);
		}
	}
	
	public int updateHak(GJ_HAK_DTO hak){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		try{
			con=DbcpBean.getConn();
			String sql="update gj_hak set hak_type=?,hak_name=?,hak_date=?,major_code=? where mem_pr_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,hak.getHak_type());
			pstmt.setString(2,hak.getHak_name());
			pstmt.setString(3,hak.getHak_date());
			pstmt.setInt(4, hak.getMajor_code());
			pstmt.setInt(5, hak.getMem_pr_num());
			
			int n=pstmt.executeUpdate();
			
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt,con);
		}
	}
	
	public int updateLicense(GJ_LICENSE_DTO license){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		try{
			con=DbcpBean.getConn();
			String sql="update gj_license set license_c_code=?,license_date=? where mem_pr_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,license.getLicense_c_code());
			pstmt.setString(2,license.getLicense_date());
			pstmt.setInt(3,license.getMem_pr_num());
			
			int n=pstmt.executeUpdate();
			
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt,con);
		}
	}
	
	public int updateExpinfo(GJ_EXPINFO_DTO expinfo){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		try{
			con=DbcpBean.getConn();
			String sql="update gj_expinfo set expinfo_date=?,expinfo_cname=?,expinfo_grade=?,expinfo_detail=? where mem_pr_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,expinfo.getExpinfo_date());
			pstmt.setString(2,expinfo.getExpinfo_cname());
			pstmt.setString(3,expinfo.getExpinfo_grade());
			pstmt.setString(4, expinfo.getExpinfo_detail());
			pstmt.setInt(5,expinfo.getMem_pr_num());
			
			int n=pstmt.executeUpdate();
			
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt,con);
		}
	}
	
	public int updateLancer(GJ_LAN_CER_DTO lancer){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		try{
			con=DbcpBean.getConn();
			String sql="update gj_lan_cer set lan_cer_c_code=?,lan_cer_level=?,lan_cer_date=? where mem_pr_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,lancer.getLan_cer_c_code());
			pstmt.setString(2,lancer.getLan_cer_level());
			pstmt.setString(3,lancer.getLan_cer_date());
			pstmt.setInt(4,lancer.getMem_pr_num());
			
			int n=pstmt.executeUpdate();
			
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt,con);
		}
	}
	
	public int updateLan(GJ_LAN_DTO lan){
		Connection con=DbcpBean.getConn();
		PreparedStatement pstmt=null;
		try{
			con=DbcpBean.getConn();
			String sql="update gj_lan set lan_c_code=?,lan_grade=? where mem_pr_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,lan.getLan_c_code());
			pstmt.setString(2,lan.getLan_grade());
			pstmt.setInt(3,lan.getMem_pr_num());
			
			int n=pstmt.executeUpdate();
			
			return n;
		}catch(SQLException se){
			System.out.println(se.getMessage());
			return -1;
		}finally{
			DbcpBean.close(null,pstmt,con);
		}
	}
	
	public int fileinsert(GJ_P_FILE_DTO file){//파일업로드
		Connection con=null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmt1=null;
		ResultSet rs=null;
		try{
			con=DbcpBean.getConn();
			String sql="select gj_mem_resume_seq.currval from dual";
			String sql1="insert into gj_p_file values(gj_p_file_seq.nextval,?,?,sysdate,gj_mem_resume_seq.currval)";
			pstmt=con.prepareStatement(sql);
			pstmt1=con.prepareStatement(sql1);
			pstmt1.setString(1,file.getFile_p_name());
			pstmt1.setString(2,file.getFile_p_sname());
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
}
