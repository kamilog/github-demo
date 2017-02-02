package goodjob.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import goodjob.dao.CDAO;
import goodjob.dao.CheckadminBDAO;
import goodjob.dao.LogCDAO;
import goodjob.dao.MyRecruitKDAO;
import goodjob.dto.GJ_MEM_P_DTO;
import goodjob.dto.GJ_MY_RECRUIT_DTO;

@WebServlet("/cho_psn.do")
public class ControllerC_psn extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String cmd=request.getParameter("cmd");
		if(cmd.equals("insert")){
			insert(request,response);
		}else if(cmd.equals("login")){
			login(request,response);
		}else if(cmd.equals("logout")){
			HttpSession session=request.getSession();
	        session.invalidate();
	        response.sendRedirect("/homepage_psn/layout.jsp");
		}else if(cmd.equals("logout2")){
			HttpSession session=request.getSession();
	        session.invalidate();
	        request.getRequestDispatcher("/intro.jsp").forward(request, response);
		}else if(cmd.equals("update")){
			update(request,response);
		}else if(cmd.equals("update1")){
			update1(request,response);
		}else if(cmd.equals("delete")){
			delete(request,response);
		}else if(cmd.equals("123")){
			System.out.println("1234");
		}else if(cmd.equals("godelete")){
			godelete(request,response);
		}else if(cmd.equals("gohome")){
			gohome(request,response);
		}
	}
	private void gohome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id=req.getParameter("id");
		if(id==null){
			resp.sendRedirect("/intro.jsp");
		}else if(id.length()==0){
			resp.sendRedirect("/intro.jsp");
		}else{
			MyRecruitKDAO dao = new MyRecruitKDAO();
			ArrayList<GJ_MY_RECRUIT_DTO> my_recruit_list = dao.my_recruit_list(id);
			req.setAttribute("my_recruit_list", my_recruit_list);
			
			req.getRequestDispatcher("/homepage_psn/layout.jsp").forward(req, resp);
		}
	}
	private void insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 사용자가 보낸 정보 얻어오기
		req.setCharacterEncoding("utf-8");
		String id=req.getParameter("id");
		String pwd=req.getParameter("pwd");
		String name=req.getParameter("name");
		String birth=req.getParameter("birth");
		String addr1=req.getParameter("addr1");
		String addr2=req.getParameter("addr2");
		String phone=req.getParameter("phone");
		String email=req.getParameter("email");
		String gender=req.getParameter("gender");
		//DB에 저장하기
		GJ_MEM_P_DTO dto=new GJ_MEM_P_DTO(id,pwd,name,birth,addr1+" "+addr2,phone,email,gender,null);
		CDAO dao=new CDAO();
		int n=dao.insert(dto);
		// 결과를 가지고 뷰페이지로 이동하기
		if(n>0){
			req.setAttribute("result","success");
		}else{
			req.setAttribute("result","fail");
		}
		req.getRequestDispatcher("/homepage_psn/layout.jsp").forward(req,resp);
	}
	
	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id=req.getParameter("id");
		String pwd=req.getParameter("pwd");
		
		HashMap<String,String> map=new HashMap<>();
		map.put("id",id);
		map.put("pwd",pwd);
		
		if(id.equals("admin")){
			CheckadminBDAO dao=CheckadminBDAO.getInstance();
			int n=dao.isAdmin(map);
			if(n==1){//해당정보가 존재하는 경우(회원인 경우)
				HttpSession session=req.getSession();//세션 얻어오기
				session.setAttribute("id",id);//세션에 아이디 담기
				resp.sendRedirect("/admin/main.jsp");//세션에 값을 담았으므로 리다이렉트로 보내도 된다
			}else if(n==0){//해당정보가 존재하지 않는 경우
				req.setAttribute("errMsg","아이디 또는 비밀번호가 일치하지 않습니다");
				req.getRequestDispatcher("/homepage_psn/layout.jsp").forward(req, resp);
			}else{//익셉션이 발생한 경우
				resp.sendRedirect("/members/2.jsp");
			}
			
		}else{
		
			//dao 얻어와서 로그인 메소드 호출
			LogCDAO dao=LogCDAO.getInstance();
			int n=dao.isMember(map);
			if(n==1){//해당정보가 존재하는 경우(회원인 경우)
				HttpSession session=req.getSession();//세션 얻어오기
				session.setAttribute("id",id);//세션에 아이디 담기
				MyRecruitKDAO dao1 = new MyRecruitKDAO();
				ArrayList<GJ_MY_RECRUIT_DTO> my_recruit_list = dao1.my_recruit_list(id);
				req.setAttribute("my_recruit_list", my_recruit_list);
				req.getRequestDispatcher("/homepage_psn/layout.jsp").forward(req, resp);//세션에 값을 담았으므로 리다이렉트로 보내도 된다
			}else if(n==0){//해당정보가 존재하지 않는 경우
				req.setAttribute("errMsg","아이디 또는 비밀번호가 일치하지 않습니다");
				req.getRequestDispatcher("/homepage_psn/layout.jsp").forward(req, resp);
			}else{//익셉션이 발생한 경우
				resp.sendRedirect("/members/2.jsp");
			}
		}
	}
	
	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CDAO dao=new CDAO();
		String mem_p_id=req.getParameter("id");
		GJ_MEM_P_DTO dto=dao.all(mem_p_id);
		req.setAttribute("dto",dto);
		req.getRequestDispatcher("/homepage_psn/layout.jsp?spage=update.jsp").forward(req,resp);
	}
	
	private void update1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String id=req.getParameter("id");
		String pwd=req.getParameter("pwd");
		String addr="";
		String addr1=req.getParameter("addr1");
		String addr2=req.getParameter("addr2");
		
		if(addr1.length()!=0 || addr2.length()!=0){
	         addr=addr1+" "+addr2;
	    }
		
		String phone=req.getParameter("phone");
		String email=req.getParameter("email");
					
		GJ_MEM_P_DTO dto=new GJ_MEM_P_DTO(id,pwd,null,null,addr,phone,email,null,null);
		CDAO dao=new CDAO();
		int n=dao.update1(dto);
		if(n>0){
			req.setAttribute("result","success1");
		}else{
			req.setAttribute("result","fail1");
		}
		req.getRequestDispatcher("/homepage_psn/layout.jsp").forward(req,resp);
	}
	
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CDAO dao = new CDAO();
		String mem_p_id=req.getParameter("id");
		String mem_p_pwd=req.getParameter("pwd");
		if(mem_p_id==null){
			mem_p_id="";
		}
		if(mem_p_pwd==null){
			mem_p_pwd="";
		}
		
		HashMap<String, String> logcheck = new HashMap<>();
		logcheck.put("id", mem_p_id);
		logcheck.put("pwd", mem_p_pwd);
		int n= LogCDAO.getInstance().isMember(logcheck);
		if(n==1){
			dao.delete(mem_p_id);
			resp.sendRedirect("/homepage_psn/layout.jsp?spage=/homepage_psn/layout.jsp");
		}else{
			resp.sendRedirect("/homepage_psn/layout.jsp?spage=/homepage_psn/godelete.jsp");
		}
	}
	

	private void godelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		resp.sendRedirect("/homepage_psn/layout.jsp?spage=/homepage_psn/godelete.jsp");
	}
}
