package goodjob.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import goodjob.dao.CDAO;
import goodjob.dao.ResumeBDAO;
import goodjob.dao.SearchKDAO;
import goodjob.dto.GJ_C_FILE_DTO;
import goodjob.dto.GJ_DETAIL_RESUME;
import goodjob.dto.GJ_EXPINFO_DTO;
import goodjob.dto.GJ_EXPINFO_L;
import goodjob.dto.GJ_HAK_DTO;
import goodjob.dto.GJ_HAK_L;
import goodjob.dto.GJ_JIK2_DTO;
import goodjob.dto.GJ_LAN_CER_C_DTO;
import goodjob.dto.GJ_LAN_CER_DTO;
import goodjob.dto.GJ_LAN_CER_L;
import goodjob.dto.GJ_LAN_DTO;
import goodjob.dto.GJ_LICENSE_DTO;
import goodjob.dto.GJ_LICENSE_L;
import goodjob.dto.GJ_MEM_P_DTO;
import goodjob.dto.GJ_MEM_RESUME_DTO;
import goodjob.dto.GJ_P_FILE_DTO;
import goodjob.dto.GJ_P_FILE_L;
import goodjob.dto.GJ_TEMP_DTO;

@WebServlet("/Bang.do")
public class ControllerResume_B extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String cmd=request.getParameter("cmd");
		if(cmd.equals("insert")){
			insert(request,response);
		}else if(cmd.equals("update1")){
			update1(request,response);
		}else if(cmd.equals("update2")){
			update2(request,response);
		}else if(cmd.equals("detail")){
			detailResume(request,response);
		}else if(cmd.equals("getLan2")){  //외국어명 검색..0117
			getLan2(request, response);
		}else if(cmd.equals("getLanCer")){  //공인어학점수명 검색..0117
			getLanCer(request, response);
		}else if(cmd.equals("getLicense")){  //공인어학점수명 검색..0117
			getLicense(request, response);
		}else if(cmd.equals("getMajor")){  //공인어학점수명 검색..0117
			getMajor(request, response);
		}
	}
	
	
	private void getMajor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml;charset=utf-8");
		String major_name = request.getParameter("major_name");
		PrintWriter pw = response.getWriter();
		ResumeBDAO dao = new ResumeBDAO();
		ArrayList<GJ_TEMP_DTO> list = dao.searchMajor(major_name);
		pw.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		pw.println("<result>");
		for(GJ_TEMP_DTO temp : list){
			pw.println("<major_code>");
			pw.println(temp.getCode());
			System.out.println(temp.getCode()+"철영이형컨트롤러 코드");
			pw.println("</major_code>");	
			pw.println("<major_name>");
			pw.println(temp.getStr());
			pw.println("</major_name>");	
		}
		pw.println("</result>");
		pw.close();
	}
	
	private void getLicense(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml;charset=utf-8");
		String license_name = request.getParameter("license_name");
		PrintWriter pw = response.getWriter();
		ResumeBDAO dao = new ResumeBDAO();
		ArrayList<GJ_TEMP_DTO> list = dao.searchLicense(license_name);
		pw.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		pw.println("<result>");
		for(GJ_TEMP_DTO temp : list){	
			pw.println("<license_c_code>");
			pw.println(temp.getCode());
			System.out.println(temp.getCode()+"철영이형컨트롤러 라이센스코드");
			pw.println("</license_c_code>");
			pw.println("<license_name>");
			pw.println(temp.getStr());
			pw.println("</license_name>");	
		}
		pw.println("</result>");
		pw.close();
	}
	
	private void getLanCer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml;charset=utf-8");
		String lan_cer_name = request.getParameter("lan_cer_name");
		PrintWriter pw = response.getWriter();
		ResumeBDAO dao = new ResumeBDAO();
		ArrayList<GJ_TEMP_DTO> list = dao.searchLanCer(lan_cer_name);
		pw.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		pw.println("<result>");
		for(GJ_TEMP_DTO temp : list){	
			pw.println("<lan_cer_c_code>");
			pw.println(temp.getCode());
			System.out.println(temp.getCode()+"철영이형컨트롤러 랜써코드");
			pw.println("</lan_cer_c_code>");
			pw.println("<lan_cer_name>");
			pw.println(temp.getStr());
			pw.println("</lan_cer_name>");	
		}
		pw.println("</result>");
		pw.close();
	}
	
	private void getLan2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml;charset=utf-8");
		String lan_name = request.getParameter("lan_name");
		PrintWriter pw = response.getWriter();
		ResumeBDAO dao = new ResumeBDAO();
		ArrayList<GJ_TEMP_DTO> list = dao.searchLan(lan_name);
		pw.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		pw.println("<result>");
		for(GJ_TEMP_DTO temp : list){	
			pw.println("<lan_c_code>");
			pw.println(temp.getCode());
			System.out.println(temp.getCode()+"철영이형컨트롤러 랜코드");
			pw.println("</lan_c_code>");
			pw.println("<lan_name>");
			pw.println(temp.getStr());
			pw.println("</lan_name>");	
		}
		pw.println("</result>");
		pw.close();
	}
	
	private void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uploadPath=getServletContext().getRealPath("/upload");//실제저장된경로
		System.out.println(uploadPath);//저장된경로 출력
		MultipartRequest mr=new MultipartRequest(
				request, //request객체
				uploadPath, //파일을 저장할 경로
				1024*1024*5, //최대 업로드 사이즈(byte단위 - 5mb)
				"utf-8", //인코딩타입
				new DefaultFileRenamePolicy()//동일한 파일명이 존재시 파일끝에 숫자붙이기
			);
		
		
		String title=mr.getParameter("resume_title");
		
		String major_code1=mr.getParameter("major_code");
		String lan_code1=mr.getParameter("resume_lan_code");
		String lan_cer_c_code1=mr.getParameter("resume_lan_cer_code");
		String license_c_code1=mr.getParameter("resume_license_code");
		
		
		String jik1=mr.getParameter("jik2_code");
		int jik2=Integer.parseInt(jik1);
		
		String resume_id=mr.getParameter("resume_id");
		
		String marry=mr.getParameter("resume_marry");
		String byoung=mr.getParameter("resume_byoung");
		String bohoon=mr.getParameter("resume_bohoon");
		String boho=mr.getParameter("resume_boho");
		String handy=mr.getParameter("resume_handy");
		String img=mr.getOriginalFileName("img");//전송된 파일명
		String simg=mr.getFilesystemName("img");//실제 저장된 파일명
		
		String hak=mr.getParameter("resume_hak");
		String uniname=mr.getParameter("resume_uniname");
		String unimajor=mr.getParameter("resume_unimajor");
		String hakdate1=mr.getParameter("resume_hakdate1");
		String hakdate2=mr.getParameter("resume_hakdate2");
		String hakdate=hakdate1+"~"+hakdate2;
		
		
		String gyoung=mr.getParameter("resume_gyoung");
		String gyoungexp=mr.getParameter("resume_exp");
		String prize=mr.getParameter("resume_prize");
		
		String resume=mr.getParameter("resume_resume");
		
		String lan=mr.getParameter("resume_lan");
		String lanlev=mr.getParameter("resume_lanlev");
		
		String lancer=mr.getParameter("resume_lan_cer");
		String lancerlev=mr.getParameter("resume_lan_cerlev");
		String lancerdate=mr.getParameter("resume_lan_cerdate");
		
		String license=mr.getParameter("resume_license");
		String licensegigwan=mr.getParameter("resume_licensegigwan");
		String license_date=mr.getParameter("resume_licensedate");
		
		
		System.out.println(jik1);
				
		String wishsal=mr.getParameter("resume_wishsal");
		
		String check_temp=mr.getParameter("resume_check");
		//boolean check=check1.charAt(0);
		
		boolean check=false;
		
        if(check_temp.charAt(0)!='0'){
           check=true;
        }
		
		String expinfo_date1=mr.getParameter("resume_expinfo_date1");
		String expinfo_date2=mr.getParameter("resume_expinfo_date2");
		String expinfo_date=expinfo_date1+"년"+expinfo_date2+"개월";
		
		String expinfo_cname=mr.getParameter("resume_expinfo_cname");
		String expinfo_grade=mr.getParameter("resume_expinfo_grade");
		String expinfo_detail=mr.getParameter("resume_expinfo_detail");
		
		//파일업로드
		String file1=mr.getOriginalFileName("file1");//전송된 파일명
		String sfile1=mr.getFilesystemName("file1");//실제 저장된 파일명
					
		
		
		
		String id=mr.getParameter("id");
		
		System.out.println(id);
		
		System.out.println(title);
		
		System.out.println(resume_id);
		
		//DB에 저장하기
		GJ_MEM_RESUME_DTO dto=new GJ_MEM_RESUME_DTO(0,title,marry,byoung,bohoon,boho,handy,gyoung,gyoungexp,prize,wishsal,resume,null,img,simg,check,id,jik2);
		
		SearchKDAO dao1=new SearchKDAO();
		ResumeBDAO dao=new ResumeBDAO();
		int n1=dao.insert(dto);
		
		
		GJ_EXPINFO_DTO dto6=new GJ_EXPINFO_DTO(0,expinfo_date,expinfo_cname,expinfo_grade,expinfo_detail,0);
		dao.insertExpinfo(dto6);
		
		GJ_P_FILE_DTO dto1=new GJ_P_FILE_DTO(0,file1,sfile1,null,0);
		int n2=dao.fileinsert(dto1);
		ArrayList<GJ_P_FILE_L> filelist = dao1.getPFilelist_insert();
		
		
	
		if(major_code1 ==null){
			dao.insertHak2(hak, uniname, hakdate);
		}else if(major_code1.length() == 0){
			
			dao.insertHak2(hak, uniname, hakdate);
		}else{
			int major_code=Integer.parseInt(major_code1);
			GJ_HAK_DTO dto2=new GJ_HAK_DTO(0,hak,uniname,hakdate,0,major_code);
			dao.insertHak(dto2);
		}
		
		if(lan_code1 != null){
			if(lan_code1.length() != 0){
				
				int lan_c_code=Integer.parseInt(lan_code1);
				GJ_LAN_DTO dto3=new GJ_LAN_DTO(0,lan_c_code,lanlev,0);
				dao.insertLan(dto3);
			}
		}
			
		if(lan_cer_c_code1 != null){
			if(lan_cer_c_code1.length() != 0){
				int lan_cer_c_code=Integer.parseInt(lan_cer_c_code1);
				GJ_LAN_CER_DTO dto4=new GJ_LAN_CER_DTO(0,lan_cer_c_code,lancerlev,lancerdate,0);
				dao.insertLancer(dto4);
			}
		}
		
		if(license_c_code1 != null){
			if(license_c_code1.length() != 0){
				int license_c_code=Integer.parseInt(license_c_code1);
				GJ_LICENSE_DTO dto5=new GJ_LICENSE_DTO(0,license_c_code,license_date,0);
				dao.insertLicense(dto5);
			}
		}
		
		
		
		
		
		
		
		

		
		// 결과를 가지고 뷰페이지로 이동하기
		request.setAttribute("filelist", filelist);
		if(n1>0){
			request.setAttribute("result","success");
		}else{
			request.setAttribute("result","fail");
		}
		request.getRequestDispatcher("/park_board.do?cmd=resumelist&mem_p_id="+id).forward(request,response);
	}
	
	private void detailResume(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String num = request.getParameter("num");

		SearchKDAO dao = new SearchKDAO();
		
		if(num == null){
			System.out.println("잘못된 접근입니다.");
			return;
		}
		
		GJ_DETAIL_RESUME dto = dao.getDetailResume(Integer.parseInt(num));
				
		ArrayList<GJ_HAK_L> haklist = dao.getHak(Integer.parseInt(num));
		ArrayList<GJ_EXPINFO_L> explist = dao.getExplist(Integer.parseInt(num));
		ArrayList<GJ_LAN_CER_L> lancerlist = dao.getLancerlist(Integer.parseInt(num));
		ArrayList<GJ_LICENSE_L> liclist = dao.getLiclist(Integer.parseInt(num));
		ArrayList<GJ_P_FILE_L> filelist = dao.getPFilelist(Integer.parseInt(num));
		
		request.setAttribute("num", num);
		request.setAttribute("dto", dto);
		request.setAttribute("haklist", haklist);
		request.setAttribute("explist", explist);
		request.setAttribute("lancerlist", lancerlist);
		request.setAttribute("liclist", liclist);
		request.setAttribute("filelist", filelist);
		//request에 담았으니 forward 방식으로 보낸다.
		
		request.getRequestDispatcher("/homepage_psn/resume_detail.jsp").forward(request, response);
		
	}
	
		
	private void update1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ResumeBDAO dao=new ResumeBDAO();
		int num=Integer.parseInt(req.getParameter("num"));
		
		GJ_MEM_RESUME_DTO dto=dao.get_mem_resume(num);
		req.setAttribute("dto",dto);
		
		GJ_HAK_DTO dto2=dao.get_hak(num);
		req.setAttribute("dto2", dto2);
		
		//학력정보 데이트값 스플릿
		String Hakdate=dao.get_hakdate(num);
		String[] array = Hakdate.split("~");
	    String hak_date1 = array[0];
	    String hak_date2 = array[1];
		req.setAttribute("hakdate1", hak_date1);
		req.setAttribute("hakdate2", hak_date2);
		
		String major_name=dao.get_major_name(num);
		req.setAttribute("major_name", major_name);
		
		GJ_LICENSE_DTO dto3=dao.get_license(num);
		req.setAttribute("dto3", dto3);
		
		String license_name=dao.get_license_name(num);
		req.setAttribute("license_name", license_name);
		
		GJ_EXPINFO_DTO dto4=dao.get_expinfo(num);
		req.setAttribute("dto4", dto4);
		
		//경력사항 년월 스플릿
		String expinfo_date=dao.get_expdate(num);
		String[] array2 = expinfo_date.split("년");
		String expinfo_dateY = array2[0];
		String expinfo_date2 = array2[1];
		String[] array3 = expinfo_date2.split("개");
		String expinfo_dateM = array3[0];
		req.setAttribute("expinfo_dateY", expinfo_dateY);
		req.setAttribute("expinfo_dateM", expinfo_dateM);
		
		
		GJ_LAN_CER_DTO dto5=dao.get_lan_cer(num);
		req.setAttribute("dto5", dto5);
		
		String lan_cer_name=dao.get_lan_cer_name(num);
		req.setAttribute("lan_cer_name", lan_cer_name);
		
		GJ_LAN_DTO dto6=dao.get_lan(num);
		req.setAttribute("dto6", dto6);
		
		String lan_name=dao.get_lan_name(num);
		req.setAttribute("lan_name", lan_name);
		
		String jik2_name=dao.get_jik2_name(num);
		req.setAttribute("jik2_name", jik2_name);
		
		req.getRequestDispatcher("/homepage_psn/resume_update.jsp").forward(req,resp);
	}
	
	private void update2(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				
		int num=Integer.parseInt(req.getParameter("num"));
		
		System.out.println(num+"<-update2 controller num 넘어오나 체크");
		
		String title=req.getParameter("resume_title");
		String marry=req.getParameter("resume_marry");
		String byoung=req.getParameter("resume_byoung");
		String bohoon=req.getParameter("resume_bohoon");
		String boho=req.getParameter("resume_boho");
		String handy=req.getParameter("resume_handy");
		String gyoung=req.getParameter("resume_gyoung");
		String exp=req.getParameter("resume_exp");
		String prize=req.getParameter("resume_prize");
		String wishsal=req.getParameter("resume_wishsal");
		String resume=req.getParameter("resume_resume");
		String check_temp=req.getParameter("resume_check");
		boolean check=false;
		if(check_temp.charAt(0)!='0'){
			check=true;
		}
		String id=req.getParameter("id");
		int jik2_code=Integer.parseInt(req.getParameter("jik2_code"));
		
		String hak_type=req.getParameter("resume_hak");
		String hak_date1=req.getParameter("resume_hakdate1");
		String hak_date2=req.getParameter("resume_hakdate2");
		String hak_date=hak_date1+"~"+hak_date2;
		String hak_name=req.getParameter("resume_uniname");
		int major_code=Integer.parseInt(req.getParameter("major_code"));
		
		int license_c_code=Integer.parseInt(req.getParameter("resume_license_code"));
		String license_date=req.getParameter("resume_licensedate");
		
		String expinfo_date1=req.getParameter("resume_expinfo_date1");
		String expinfo_date2=req.getParameter("resume_expinfo_date2");
		String expinfo_date=expinfo_date1+"년"+expinfo_date2+"개월";
		String expinfo_cname=req.getParameter("resume_expinfo_cname");
		String expinfo_grade=req.getParameter("resume_expinfo_grade");
		String expinfo_detail=req.getParameter("resume_expinfo_detail");
		
		int lan_cer_c_code=Integer.parseInt(req.getParameter("resume_lan_cer_code"));
		String lan_cer_level=req.getParameter("resume_lan_cerlev");
		String lan_cer_date=req.getParameter("resume_lan_cerdate");
		
		int lan_c_code=Integer.parseInt(req.getParameter("resume_lan_code"));
		String lan_grade=req.getParameter("resume_lanlev");
		
		
		ResumeBDAO dao=new ResumeBDAO();
		GJ_MEM_RESUME_DTO dto=new GJ_MEM_RESUME_DTO(num,title,marry,byoung,bohoon,boho,handy,gyoung,exp,prize,wishsal,resume,
				null,null,null,check,id,jik2_code);
		
		int n=dao.update(dto);
		
		GJ_HAK_DTO dto2=new GJ_HAK_DTO(0,hak_type,hak_name,hak_date,num,major_code);
		n=dao.updateHak(dto2);
		
		GJ_LICENSE_DTO dto3=new GJ_LICENSE_DTO(0,license_c_code,license_date,num);
		n=dao.updateLicense(dto3);
		
		GJ_EXPINFO_DTO dto4=new GJ_EXPINFO_DTO(0,expinfo_date,expinfo_cname,expinfo_grade,expinfo_detail,num);
		n=dao.updateExpinfo(dto4);
		
		GJ_LAN_CER_DTO dto5=new GJ_LAN_CER_DTO(0,lan_cer_c_code,lan_cer_level,lan_cer_date,num);
		n=dao.updateLancer(dto5);
		
		GJ_LAN_DTO dto6=new GJ_LAN_DTO(0,lan_c_code,lan_grade,num);
		n=dao.updateLan(dto6);
		
		if(n>0){
			req.setAttribute("result","success1");
		}else{
			req.setAttribute("result","fail1");
		}
		req.getRequestDispatcher("/homepage_psn/layout.jsp").forward(req,resp);
	}
		
}
