package goodjob.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import goodjob.dao.RecruitCDAO;
import goodjob.dao.SearchKDAO;
import goodjob.dto.GJ_C_FILE_DTO;
import goodjob.dto.GJ_C_FILE_L;
import goodjob.dto.GJ_DETAIL_RECRUIT;
import goodjob.dto.GJ_MEM_RECRUIT_DTO;

@WebServlet("/chocr.do")
public class ControllerRecruit_C extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String cmd=request.getParameter("cmd");
		if(cmd.equals("insert")){
			insert(request,response);
		}else if(cmd.equals("update")){
			update(request,response);
		}else if(cmd.equals("update1")){
			update1(request,response);
		}else if(cmd.equals("list")){
			list(request,response);
		}else if(cmd.equals("delete")){
			delete(request,response);
		}else if(cmd.equals("detailRecruit")){
			detailRecruit(request,response);
		}
	}
	
	private void insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uploadPath=getServletContext().getRealPath("/upload");
		MultipartRequest mr=new MultipartRequest(
				req, //request객체
				uploadPath, //파일을 저장할 경로
				1024*1024*5, //최대 업로드 사이즈(byte단위 - 5mb)
				"utf-8", //인코딩타입
				new DefaultFileRenamePolicy()//동일한 파일명이 존재시 파일끝에 숫자붙이기
			);
		String title=mr.getParameter("title");
		String gyoung=mr.getParameter("gyoung");
		String sal=mr.getParameter("sal");
		String jobtype=mr.getParameter("jobtype");
		String jobtime=mr.getParameter("jobtime");
		String rtime1=mr.getParameter("rtime1");
		String rtime2=mr.getParameter("rtime2");
		String chadate=mr.getParameter("chadate");
		String workout=mr.getParameter("workout");
		int recruitnumber=Integer.parseInt(mr.getParameter("recruitnumber"));
		String age=mr.getParameter("age");
		String gender=mr.getParameter("gender");
		String document=mr.getParameter("document");
		String howto=mr.getParameter("howto");
		String addr1=mr.getParameter("addr1");
		String addr2=mr.getParameter("addr2");
		String qna=mr.getParameter("qna");
		String qnaname=mr.getParameter("qnaname");
		String hak=mr.getParameter("hak");
		String license=mr.getParameter("license");
		String lan_cer=mr.getParameter("lan_cer");
		String lan=mr.getParameter("lan");
		String check_temp=mr.getParameter("check");
		boolean check=false;
		if(check_temp.charAt(0)!='0'){
			check=true;
		}
		String id=mr.getParameter("id");
		int jik2_code=Integer.parseInt(mr.getParameter("jik2_code"));	
		
		String file1=mr.getOriginalFileName("file1");//전송된 파일명
		String sfile1=mr.getFilesystemName("file1");//실제 저장된 파일명
			
		RecruitCDAO dao=new RecruitCDAO();
		GJ_MEM_RECRUIT_DTO dto=new GJ_MEM_RECRUIT_DTO(0,title,gyoung,sal,jobtype,jobtime,rtime1+"~"+rtime2,chadate,workout,
				recruitnumber,age,gender,document,howto,addr1+" "+addr2,qna,qnaname,hak,license,lan_cer,lan,
				null,check,0,id,jik2_code);
		int n=dao.insert(dto);
		
		GJ_C_FILE_DTO dto1=new GJ_C_FILE_DTO(0,file1,sfile1,null,0);
		int n1=dao.fileinsert(dto1);
				
		if(n<0){
			System.out.println("잘못되었습니다");
		}
		req.getRequestDispatcher("/chocr.do?cmd=detailRecruit&num="+n).forward(req,resp);
	}
	
	private void detailRecruit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String num = request.getParameter("num");

		SearchKDAO dao = new SearchKDAO();
		
		if(num == null){
			System.out.println("잘못된 접근입니다.");
			return;
		}
		dao.upHit(Integer.parseInt(num));
		GJ_DETAIL_RECRUIT dto = dao.getDetailRecruit(Integer.parseInt(num));
		
		ArrayList<GJ_C_FILE_L> filelist = dao.getCFilelist(Integer.parseInt(num));
		
		request.setAttribute("num", num);
		request.setAttribute("dto", dto);
		
		request.setAttribute("filelist", filelist);
		//request에 담았으니 forward 방식으로 보낸다.
		
		request.getRequestDispatcher("/homepage_com/layout.jsp?spage=/homepage_com/recruit_detail.jsp").forward(request, response);
		
	}
	
	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RecruitCDAO dao=new RecruitCDAO();
		int num=Integer.parseInt(req.getParameter("num"));
		GJ_MEM_RECRUIT_DTO dto=dao.all(num);
		String rtime = dto.getMem_cr_rtime();
		String [] array = rtime.split("~");
		String rtime1=array[0];
		String rtime2=array[1];
		//String addr = dto.getMem_cr_addr();
		//String [] array2 = addr.split(")");
		//String addr1=array2[0];
		//String addr2=array2[1];
		req.setAttribute("rtime1",rtime1);
	    req.setAttribute("rtime2",rtime2);
	    //req.setAttribute("addr1",addr1);
	    //req.setAttribute("addr2",addr2);
	   	req.setAttribute("dto",dto);
	   	

		String jik2_name=dao.getJik2_name(dto.getJik2_code());
		req.setAttribute("jik2_name", jik2_name);
	   	
	   	
		req.getRequestDispatcher("/homepage_com/layout.jsp?spage=/homepage_com/recruit_update.jsp").forward(req,resp);
		
	}
	
	private void update1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int num=Integer.parseInt(req.getParameter("num"));
		String title=req.getParameter("title");
		String gyoung=req.getParameter("gyoung");
		String sal=req.getParameter("sal");
		String jobtype=req.getParameter("jobtype");
		String jobtime=req.getParameter("jobtime");
		String rtime1=req.getParameter("rtime1");
		String rtime2=req.getParameter("rtime2");
		String chadate=req.getParameter("chadate");
		String workout=req.getParameter("workout");
		int recruitnumber=Integer.parseInt(req.getParameter("recruitnumber"));
		String age=req.getParameter("age");
		String gender=req.getParameter("gender");
		String document=req.getParameter("document");
		String howto=req.getParameter("howto");
		String addr=req.getParameter("addr");
		String addr1=req.getParameter("addr1");
		String addr2=req.getParameter("addr2");
		if(addr1.length()!=0 || addr2.length()!=0){
			addr=addr1+" "+addr2;
		}
		String qna=req.getParameter("qna");
		String qnaname=req.getParameter("qnaname");
		String hak=req.getParameter("hak");
		String license=req.getParameter("license");
		String lan_cer=req.getParameter("lan_cer");
		String lan=req.getParameter("lan");
		String check_temp=req.getParameter("check");
		boolean check=false;
		if(check_temp.charAt(0)!='0'){
			check=true;
		}
		String id=req.getParameter("id");
		int jik2_code=Integer.parseInt(req.getParameter("jik2_code"));
				
		System.out.println("넘버 : "+recruitnumber);
		System.out.println("직업ㅋ코드 : "+jik2_code);
				
		RecruitCDAO dao=new RecruitCDAO();
		GJ_MEM_RECRUIT_DTO dto=new GJ_MEM_RECRUIT_DTO(num,title,gyoung,sal,jobtype,jobtime,rtime1+"~"+rtime2,chadate,workout,
				recruitnumber,age,gender,document,howto,addr,qna,qnaname,hak,license,lan_cer,lan,
				null,check,0,id,jik2_code);
		int n=dao.update1(dto);
		
		
		if(n>0){
			req.setAttribute("result","success1");
		}else{
			req.setAttribute("result","fail1");
		}
		req.getRequestDispatcher("/homepage_com/layout.jsp").forward(req,resp);
	}
	
	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id=req.getParameter("id");
		RecruitCDAO dao=new RecruitCDAO();
		ArrayList<GJ_MEM_RECRUIT_DTO> list=dao.list(id);
		req.setAttribute("list",list);
		System.out.println(list);
		req.getRequestDispatcher("/homepage_com/layout.jsp?spage=/homepage_com/recruit_list.jsp").forward(req,resp);
	}
	
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RecruitCDAO dao=new RecruitCDAO();
		int num=Integer.parseInt(req.getParameter("num"));
		dao.delete(num);
		resp.sendRedirect("/members/2.jsp");
	}
	
	private void fileinsert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uploadPath=getServletContext().getRealPath("/upload");
		MultipartRequest mr=new MultipartRequest(
				req, //request객체
				uploadPath, //파일을 저장할 경로
				1024*1024*5, //최대 업로드 사이즈(byte단위 - 5mb)
				"utf-8", //인코딩타입
				new DefaultFileRenamePolicy()//동일한 파일명이 존재시 파일끝에 숫자붙이기
			);
		String file1=mr.getOriginalFileName("file1");//전송된 파일명
		String sfile1=mr.getFilesystemName("file1");//실제 저장된 파일명
		int mem_cr_num=Integer.parseInt(mr.getParameter("mem_cr_num"));
		
		RecruitCDAO dao=new RecruitCDAO();
		GJ_C_FILE_DTO dto=new GJ_C_FILE_DTO(0,file1,sfile1,null,mem_cr_num);
		int n=dao.fileinsert(dto);
				
		if(n>0){
			resp.sendRedirect("/members/2.jsp");
		}else{
			resp.sendRedirect("/members/login.jsp");
		}
	}
}
