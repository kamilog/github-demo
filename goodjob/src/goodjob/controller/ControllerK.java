package goodjob.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import goodjob.dao.AdminPDAO;
import goodjob.dao.BoardPDAO;
import goodjob.dao.MyRecruitKDAO;
import goodjob.dao.SearchKDAO;
import goodjob.dto.GJ_BOARD_FREE_DTO;
import goodjob.dto.GJ_BOARD_NEWS_DTO;
import goodjob.dto.GJ_BOARD_NOTICE_DTO;
import goodjob.dto.GJ_C_FILE_L;
import goodjob.dto.GJ_DETAIL_RECRUIT;
import goodjob.dto.GJ_DETAIL_RESUME;
import goodjob.dto.GJ_EXPINFO_L;
import goodjob.dto.GJ_HAK_L;
import goodjob.dto.GJ_LAN_CER_L;
import goodjob.dto.GJ_LAN_L;
import goodjob.dto.GJ_LICENSE_L;
import goodjob.dto.GJ_MY_RECRUIT_DTO;
import goodjob.dto.GJ_MY_RESUME;
import goodjob.dto.GJ_P_FILE_L;
import goodjob.dto.GJ_RECRUIT_KIM;
import goodjob.dto.GJ_RECRUIT_S;
import goodjob.dto.GJ_REC_JIWON;
import goodjob.dto.GJ_RESUME_KIM;
import goodjob.dto.GJ_RESUME_S;
 
@WebServlet("/Kim.do")
public class ControllerK extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String cmd=request.getParameter("cmd");
		if(cmd.equals("searchResume")){  // 이력서 리스트
			searchResume(request, response); 
		}else if(cmd.equals("categoryResume")){  // 이력서 - 외국어 리스트
			categoryResume(request, response); 
		}else if(cmd.equals("getLan")){  // 이력서 - 외국어 리스트
			getLan(request, response); 
		}else if(cmd.equals("getLic")){
			getLic(request, response); //  이력서 - 자격증
		}else if(cmd.equals("getMajor")){
			getMajor(request, response); //cmd=getMajor&major_name=
		}else if(cmd.equals("getLicense")){
			getLicense(request, response); //Kim.do?cmd=getLicense&license_name=
		}else if(cmd.equals("detailResume")){
			detailResume(request, response); //Kim.do?cmd=detailResume&num=?
		}else if(cmd.equals("searchRecruit")){
			searchRecruit(request, response); 
		}else if(cmd.equals("categoryRecruit")){
			categoryRecruit(request, response);
		}else if(cmd.equals("detailRecruit")){
			detailRecruit(request, response);
		}else if(cmd.equals("realtimeResume")){
			realtimeResume(request, response);
		}else if(cmd.equals("realtimeRecruit")){
			realtimeRecruit(request, response);
		}else if(cmd.equals("getJiwon")){
			getJiwon(request, response);
		}else if(cmd.equals("getVolunteer")){
			getVolunteer(request, response);
		}else if(cmd.equals("getMyRecruit")){
			getMyRecruit(request, response);
		}else if(cmd.equals("outJiwon")){
			outJiwon(request, response);
		}else if(cmd.equals("goMyVolunteer")){
			goMyVolunteer(request, response);
		}else if(cmd.equals("searchAll")){
			searchAll(request, response);
		}else if(cmd.equals("searchAll1")){
			searchAll1(request, response);
		}
		else if(cmd.equals("noitce_board_detail")){
			noitce_board_detail(request, response);
		}
		else if(cmd.equals("comu_freeboard_detail")){
			comu_freeboard_detail(request, response);
		}
		else if(cmd.equals("news_board_detail")){
			news_board_detail(request, response);
		}
		else if(cmd.equals("joinRec")){
			joinRec(request, response);
		}else if(cmd.equals("insertRec")){
			insertRec(request, response);
		}
		
	}
	
	private void insertRec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String rec_num = request.getParameter("rec_num");
		String res_num = request.getParameter("res_num");
		
		
		
		MyRecruitKDAO dao = new MyRecruitKDAO();
		int n = dao.insertRec(Integer.parseInt(rec_num), Integer.parseInt(res_num));
		
		response.sendRedirect("/homepage_psn/layout.jsp?spage=/Kim.do?cmd=searchRecruit");
	}
	
	private void joinRec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String rec_num = request.getParameter("rec_num");
		
		if(id==null){
			System.out.println("해당 아이디의 이력서를 찾을수 없습니다.");
			response.sendRedirect("/homepage_psn/layout.jsp?spage=main.jsp");
		}else if(id.length()==0){
			System.out.println("id를 불러올 수 없습니다");
			response.sendRedirect("/homepage_psn/layout.jsp?spage=main.jsp");
		}
		if(rec_num==null){
			System.out.println("해당 채용공고를 찾을수 없습니다.");
			response.sendRedirect("/homepage_psn/layout.jsp?spage=main.jsp");
		}
		
		MyRecruitKDAO dao = new MyRecruitKDAO();
		ArrayList<GJ_MY_RESUME> list = dao.myResumeList(id);
		request.setAttribute("resumeList", list);
		request.setAttribute("rec_num", Integer.parseInt(rec_num));
		request.getRequestDispatcher("/homepage_psn/layout.jsp?spage=/kim/joinRecruit.jsp").forward(request, response);
	}
	
	private void news_board_detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int board_news_num=Integer.parseInt(request.getParameter("board_news_num"));
		AdminPDAO dao=new AdminPDAO();
		
		GJ_BOARD_NEWS_DTO dto=new GJ_BOARD_NEWS_DTO();
			dto=dao.news_detail(board_news_num);
			
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("/kim/news_board_detail.jsp").forward(request, response);
	}
	
	private void comu_freeboard_detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int board_free_num=Integer.parseInt(request.getParameter("board_free_num"));
		AdminPDAO dao=new AdminPDAO();
		GJ_BOARD_FREE_DTO dto=new GJ_BOARD_FREE_DTO();
		dto=dao.board_free_detail(board_free_num);
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("/kim/comu_freeboard_detail.jsp").forward(request, response);
	}
	private void noitce_board_detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int board_notice_num=Integer.parseInt(request.getParameter("board_notice_num"));
		AdminPDAO dao=new AdminPDAO();
		
		GJ_BOARD_NOTICE_DTO dto=new GJ_BOARD_NOTICE_DTO();
			dto=dao.notice_detail(board_notice_num);
			
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("/kim/notice_board_detail.jsp").forward(request, response);
	}
	private void searchAll1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String findall = request.getParameter("findall");
		if(findall==null){
			findall = "";
		}
		
		MyRecruitKDAO dao = new MyRecruitKDAO();
		ArrayList<GJ_BOARD_NOTICE_DTO> noticeList = dao.noticeList(findall);
		ArrayList<GJ_BOARD_NEWS_DTO> newsList = dao.newsList(findall);
		ArrayList<GJ_BOARD_FREE_DTO> freeList = dao.freeList(findall);
		ArrayList<GJ_RESUME_KIM> resumeList = dao.resumeList(findall);
		ArrayList<GJ_RECRUIT_KIM> recruitList = dao.recruitList(findall);
		
		request.setAttribute("noticeList", noticeList);
		request.setAttribute("newsList", newsList);
		request.setAttribute("freeList", freeList);
		request.setAttribute("resumeList", resumeList);
		request.setAttribute("recruitList", recruitList);
		
		//request에 담았으니 forward 방식으로 보낸다.
		request.getRequestDispatcher("/homepage_com/layout.jsp?spage=/kim/searchAll.jsp").forward(request, response);
	}
	
	private void searchAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String findall = request.getParameter("findall");
		if(findall==null){
			findall = "";
		}
		
		MyRecruitKDAO dao = new MyRecruitKDAO();
		ArrayList<GJ_BOARD_NOTICE_DTO> noticeList = dao.noticeList(findall);
		ArrayList<GJ_BOARD_NEWS_DTO> newsList = dao.newsList(findall);
		ArrayList<GJ_BOARD_FREE_DTO> freeList = dao.freeList(findall);
		ArrayList<GJ_RESUME_KIM> resumeList = dao.resumeList(findall);
		ArrayList<GJ_RECRUIT_KIM> recruitList = dao.recruitList(findall);
		
		request.setAttribute("noticeList", noticeList);
		request.setAttribute("newsList", newsList);
		request.setAttribute("freeList", freeList);
		request.setAttribute("resumeList", resumeList);
		request.setAttribute("recruitList", recruitList);
		
		//request에 담았으니 forward 방식으로 보낸다.
		request.getRequestDispatcher("/homepage_psn/layout.jsp?spage=/kim/searchAll.jsp").forward(request, response);
	}
	//개인회원 로그인시 자신이 지원한 채용공고 지원취소
		private void outJiwon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String res_num = request.getParameter("res_num");
			String rec_num = request.getParameter("rec_num");
			String id = request.getParameter("id");
			if(id==null){
				System.out.println("해당 아이디의 이력서를 찾을수 없습니다.");
				response.sendRedirect("/homepage_psn/layout.jsp?spage=main.jsp");
			}else if(id.length()==0){
				System.out.println("id를 불러올 수 없습니다");
				response.sendRedirect("/homepage_psn/layout.jsp?spage=main.jsp");
			}
			
			if(res_num==null){
				System.out.println("해당 아이디의 이력서를 찾을수 없습니다.");
				response.sendRedirect("/homepage_psn/layout.jsp?spage=main.jsp");
			}
			if(rec_num==null){
				System.out.println("해당 아이디의 채용정보를 찾을수 없습니다.");
				response.sendRedirect("/homepage_psn/layout.jsp?spage=main.jsp");
			}
			
			MyRecruitKDAO dao = new MyRecruitKDAO();
			int n = dao.outJiwon(Integer.parseInt(res_num), Integer.parseInt(rec_num));
			if(n<0){
				System.out.println("해당 채용정보에서 지원취소 할 수 없습니다.");
				response.sendRedirect("/homepage_psn/layout.jsp?spage=main.jsp");
			}else{
				ArrayList<GJ_MY_RECRUIT_DTO> my_recruit_list = dao.my_recruit_list(id);
				request.setAttribute("my_recruit_list", my_recruit_list);
				
				//request에 담았으니 forward 방식으로 보낸다.
				request.getRequestDispatcher("/homepage_psn/layout.jsp?spage=/kim/listCooper.jsp").forward(request, response);
			}
			
		}
		
	//개인회원 로그인시 자신이 지원한 채용공고 출력
	private void getMyRecruit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if(id==null){
			System.out.println("해당 아이디의 이력서를 찾을수 없습니다.");
			response.sendRedirect("/homepage_psn/layout.jsp?spage=main.jsp");
		}else if(id.length()==0){
			System.out.println("id를 불러올 수 없습니다");
			response.sendRedirect("/homepage_psn/layout.jsp?spage=main.jsp");
		}
		
		MyRecruitKDAO dao = new MyRecruitKDAO();
		ArrayList<GJ_MY_RECRUIT_DTO> my_recruit_list = dao.my_recruit_list(id);
		
		request.setAttribute("my_recruit_list", my_recruit_list);
		
		
		//request에 담았으니 forward 방식으로 보낸다.
		request.getRequestDispatcher("/homepage_psn/layout.jsp?spage=/kim/listCooper.jsp").forward(request, response);
	}
	private void goMyVolunteer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String num = request.getParameter("num");
		if(num==null){
			System.out.println("해당 번호의 이력서를 찾을수 없습니다.");
			response.sendRedirect("/homepage_com/layout.jsp?spage=main.jsp");
		}else if(num.length()==0){
			System.out.println("id를 불러올 수 없습니다");
			response.sendRedirect("/homepage_com/layout.jsp?spage=main.jsp");
		}
		
		request.setAttribute("num", num);
		
		
		//request에 담았으니 forward 방식으로 보낸다.
		request.getRequestDispatcher("/kim/myVolunteer.jsp").forward(request, response);
	}
		
	private void getVolunteer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if(id==null){
			System.out.println("id를 불러올 수 없습니다");
			response.sendRedirect("/homepage_com/layout.jsp?spage=main.jsp");
		}else if(id.length()==0){
			System.out.println("id를 불러올 수 없습니다");
			response.sendRedirect("/homepage_com/layout.jsp?spage=main.jsp");
		}
		SearchKDAO dao = new SearchKDAO();
		ArrayList<GJ_REC_JIWON> recruit_list = dao.listJiwonJa(id);
		request.setAttribute("recruit_list", recruit_list);
		
		
		//request에 담았으니 forward 방식으로 보낸다.
		request.getRequestDispatcher("/homepage_com/layout.jsp?spage=/kim/listVolunteer.jsp").forward(request, response);
	}
	private void getJiwon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml;charset=utf-8");
		String num = request.getParameter("num");
		if(num == null){
			System.out.println("잘못된 접근입니다. - getJiwon");
			return;
		}
		
		
		PrintWriter pw = response.getWriter();
		SearchKDAO dao = new SearchKDAO();
		ArrayList<GJ_RESUME_S> list = dao.jiwonJa(Integer.parseInt(num));
		
		
		pw.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		pw.println("<result>");
		
		for(GJ_RESUME_S temp : list){
			pw.println("<data>");
			pw.println("<res_num>");
			pw.println(temp.getNum());
			pw.println("</res_num>");
			pw.println("<res_name>");
			pw.println(temp.getName());
			pw.println("</res_name>");
			pw.println("<res_gender>");
			pw.println(temp.getGender());
			pw.println("</res_gender>");
			pw.println("<res_age>");
			pw.println(temp.getAge());
			pw.println("</res_age>");
			pw.println("<res_title>");
			pw.println(temp.getTitle());
			pw.println("</res_title>");
			pw.println("<res_jik>");
			pw.println(temp.getJik());
			pw.println("</res_jik>");
			pw.println("<res_gyoung>");
			pw.println(temp.getGyoung());
			pw.println("</res_gyoung>");
			pw.println("<res_lan_cnt>");
			pw.println(temp.getLan_cnt());
			pw.println("</res_lan_cnt>");
			pw.println("<res_lic_cnt>");
			pw.println(temp.getLic_cnt());
			pw.println("</res_lic_cnt>");
			pw.println("<res_resume_date>");
			pw.println(temp.getResume_date());
			pw.println("</res_resume_date>");
			pw.println("</data>");
			
		}
		
		pw.println("</result>");
		pw.close();
	}
	
	private void realtimeResume(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml;charset=utf-8");
		String search = request.getParameter("search");
		String tempPage = request.getParameter("tempPage");
		if(search == null){
			search = "";
		}
		if(tempPage == null){
			tempPage = "1";
		}
		if(tempPage.length() ==0){
			tempPage = "1";
		}
		
		int pageNum  = Integer.parseInt(tempPage);
		int startRow=(pageNum-1)*5+1;//시작행번호
		int endRow=startRow+4;//끝행번호
		
		PrintWriter pw = response.getWriter();
		SearchKDAO dao = new SearchKDAO();
		ArrayList<GJ_RESUME_S> list = dao.searchResume(search, startRow, endRow);
		int pageCount=(int)Math.ceil( dao.countResume(search) / 5.0); // 5.0으로 나누어서 페이지 카운터
		
		pageNum++;
		if(pageNum > pageCount){
			pageNum = 1;
		}
		
		pw.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		pw.println("<result>");
		pw.println("<tempPage>");
		pw.println(pageNum+"");
		pw.println("</tempPage>");
		for(GJ_RESUME_S temp : list){
			pw.println("<data>");
			pw.println("<res_num>");
			pw.println(temp.getNum());
			pw.println("</res_num>");
			pw.println("<res_name>");
			pw.println(temp.getName());
			pw.println("</res_name>");
			pw.println("<res_gender>");
			pw.println(temp.getGender());
			pw.println("</res_gender>");
			pw.println("<res_age>");
			pw.println(temp.getAge());
			pw.println("</res_age>");
			pw.println("<res_title>");
			pw.println(temp.getTitle());
			pw.println("</res_title>");
			pw.println("<res_jik>");
			pw.println(temp.getJik());
			pw.println("</res_jik>");
			pw.println("<res_gyoung>");
			pw.println(temp.getGyoung());
			pw.println("</res_gyoung>");
			pw.println("<res_lan_cnt>");
			pw.println(temp.getLan_cnt());
			pw.println("</res_lan_cnt>");
			pw.println("<res_lic_cnt>");
			pw.println(temp.getLic_cnt());
			pw.println("</res_lic_cnt>");
			pw.println("<res_resume_date>");
			pw.println(temp.getResume_date());
			pw.println("</res_resume_date>");
			pw.println("</data>");
		}
		
		pw.println("</result>");
		pw.close();
	}
	
	
	private void realtimeRecruit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml;charset=utf-8");
		String search = request.getParameter("search");
		String tempPage = request.getParameter("tempPage");
		if(search == null){
			search = "";
		}
		if(tempPage == null){
			tempPage = "1";
		}
		if(tempPage.length() ==0){
			tempPage = "1";
		}
		
		int pageNum  = Integer.parseInt(tempPage);
		int startRow=(pageNum-1)*5+1;//시작행번호
		int endRow=startRow+4;//끝행번호
		
		PrintWriter pw = response.getWriter();
		SearchKDAO dao = new SearchKDAO();
		ArrayList<GJ_RECRUIT_S> list = dao.searchRecruit(search, startRow, endRow);
		int pageCount=(int)Math.ceil( dao.countRecruit(search) / 5.0); // 5.0으로 나누어서 페이지 카운터
		
		pageNum++;
		if(pageNum > pageCount){
			pageNum = 1;
		}
		
		pw.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		pw.println("<result>");
		pw.println("<tempPage>");
		pw.println(pageNum+"");
		pw.println("</tempPage>");
		for(GJ_RECRUIT_S temp : list){
			pw.println("<data>");
			pw.println("<rec_num>");
			pw.println(temp.getNum());
			pw.println("</rec_num>");
			pw.println("<rec_name>");
			pw.println(temp.getName());
			pw.println("</rec_name>");
			pw.println("<rec_title>");
			pw.println(temp.getTitle());
			pw.println("</rec_title>");
			pw.println("<rec_gyoung>");
			pw.println(temp.getGyoung());
			pw.println("</rec_gyoung>");
			pw.println("<rec_hak>");
			pw.println(temp.getHak());
			pw.println("</rec_hak>");
			pw.println("<rec_jobtype>");
			pw.println(temp.getJobtype());
			pw.println("</rec_jobtype>");
			pw.println("<rec_addr>");
			pw.println(temp.getAddr());
			pw.println("</rec_addr>");
			pw.println("<rec_sal>");
			pw.println(temp.getSal());
			pw.println("</rec_sal>");
			pw.println("<rec_rtime>");
			pw.println(temp.getRtime());
			pw.println("</rec_rtime>");
			pw.println("</data>");
		}
		
		pw.println("</result>");
		pw.close();
	}
	
	private void searchRecruit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		String spageNum=request.getParameter("pageNum");
		String search=request.getParameter("search"); 
		String cmd = request.getParameter("cmd");
		String select=request.getParameter("select");
		String order=request.getParameter("order");
		if(search == null){
			search = "";
		}
		if(select ==null){
			select="gc.mem_c_name";
		}
		if(order ==null){
			order="asc";
		}
		
		int pageNum=1; //페이지 번호가 없으면(파라미터가 넘어오지 않으면) 1페이지를 보여준다
		if(spageNum!=null){
			pageNum=Integer.parseInt(spageNum);
		}
		//1페이지면 1~10, 2페이지면 11~20까지 보여주기 위한 변수와 수식
		int startRow=(pageNum-1)*5+1;//시작행번호
		int endRow=startRow+4;//끝행번호

		SearchKDAO dao = new SearchKDAO();
		ArrayList<GJ_RECRUIT_S> list = //dao.searchRecruit(search, startRow, endRow);
				dao.searchRecruit(search, startRow, endRow, select, order);
	
		ArrayList<String> lanList = dao.allLan();
		/////////////////////////////////////////////////////////////
		//전체페이지갯수 구하기
		int pageCount=(int)Math.ceil(dao.countRecruit(search)/5.0); //페이지는 정수값뿐이니 올림 뒤 형변환후 넣어줬음
		
		//시작페이지 구하기
		int startPage=((pageNum-1)/4*4)+1;//정수끼리 계산이기 때문에 0.1~0.9 전부 0이다 즉 1~10페이지의 시작페이지는 언제나 1이다
		//끝페이지 구하기
		int endPage=startPage+3;
	
		// 여기까지만 있다면 전체 페이지가 10의 배수가 아닐때 비어있는 페이지가 출력된다(ex 전체 페이지가 25이지만 끝페이지는 30까지 된다)
		// 그렇기 때문에 아래 if문 추가!
		if(endPage>pageCount){
			endPage=pageCount;
		}
		request.setAttribute("select", select);
		request.setAttribute("order", order);
		request.setAttribute("cmd",cmd);
		request.setAttribute("list", list);
		request.setAttribute("lanList", lanList);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("search", search);
		
		//request에 담았으니 forward 방식으로 보낸다.
		request.getRequestDispatcher("/homepage_psn/layout.jsp?spage=/kim/searchRecruit.jsp").forward(request, response);
	}
	
	private void categoryRecruit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String spageNum=request.getParameter("pageNum");
		String cmd = request.getParameter("cmd");
		String jik = request.getParameter("jik");
		String exp = request.getParameter("exp");
		String hak = request.getParameter("hak");
		String jobtype = request.getParameter("jobtype");
		String sal = request.getParameter("sal");
		String area = request.getParameter("area");
		String temp_lanCnt = request.getParameter("lanCnt");
		
		String select = request.getParameter("select");
		String order = request.getParameter("order");
		if(select==null){
			select="gc.mem_c_name";		
		}
		if(order==null){
			order="asc";		
		}
		if(temp_lanCnt == null){
			temp_lanCnt = "1";
		}
		int lanCnt = Integer.parseInt(temp_lanCnt);
		String[] lan_n_list = new String[lanCnt];
		String[] lan_g_list = new String[lanCnt];
				
		for( int i=0; i< lanCnt; i++){
			String lan_n = request.getParameter("lan_n"+(i+1));
			String lan_g = request.getParameter("lan_g"+(i+1));
			if(lan_n == null || lan_g == null){
				lan_n = "";
				lan_g = "";
			}
			lan_n_list[i] = lan_n;
			lan_g_list[i] = lan_g;
		}
		
		String license = request.getParameter("license");
		String word = request.getParameter("word");
		
		if(word == null){
			word = "";
		}
		if(jik == null){
			jik = "";
		}
		if(exp == null){
			exp = "";
		}
		if(hak == null){
			hak = "";
		}
		if(sal == null){
			sal = "";
		}
		if(jobtype == null){
			jobtype = "";
		}
		if(area == null){
			area = "";
		}
		if(license == null){
			license = "";
		}
		
		int pageNum=1; //페이지 번호가 없으면(파라미터가 넘어오지 않으면) 1페이지를 보여준다
		if(spageNum!=null){
			pageNum=Integer.parseInt(spageNum);
		}
		//1페이지면 1~10, 2페이지면 11~20까지 보여주기 위한 변수와 수식
		int startRow=(pageNum-1)*5+1;//시작행번호
		int endRow=startRow+4;//끝행번호
		
		SearchKDAO dao = new SearchKDAO();
		ArrayList<GJ_RECRUIT_S> list = //dao.categoryRecruit(exp, area, sal, jobtype, jik, hak, lan_n_list, lan_g_list, license, word, startRow, endRow);
				dao.categoryRecruit(select, order, exp, area, sal, jobtype, jik, hak, lan_n_list, lan_g_list, license, word, startRow, endRow);
		ArrayList<String> lanList = dao.allLan();
		/////////////////////////////////////////////////////////////
		//전체페이지갯수 구하기
		int pageCount=(int)Math.ceil(dao.countCGRecruit(exp, area, sal, jobtype, jik, hak, lan_n_list, lan_g_list, license, word)/5.0); //페이지는 정수값뿐이니 올림 뒤 형변환후 넣어줬음
		
		//시작페이지 구하기
		int startPage=((pageNum-1)/4*4)+1;//정수끼리 계산이기 때문에 0.1~0.9 전부 0이다 즉 1~10페이지의 시작페이지는 언제나 1이다
		//끝페이지 구하기
		int endPage=startPage+3;
	
		// 여기까지만 있다면 전체 페이지가 10의 배수가 아닐때 비어있는 페이지가 출력된다(ex 전체 페이지가 25이지만 끝페이지는 30까지 된다)
		// 그렇기 때문에 아래 if문 추가!
		if(endPage>pageCount){
			endPage=pageCount;
		}
		request.setAttribute("cmd",cmd);
		request.setAttribute("list", list);
		request.setAttribute("lanList", lanList);  // 카테고리 검색시 외국어 default option 값
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("pageNum", pageNum);
		
		//jik, exp, hak, major, lan_list, license, gender, age1, age2, word
		// 검색값 저장
		request.setAttribute("jik",jik);
		request.setAttribute("exp",exp);
		request.setAttribute("hak",hak);
		request.setAttribute("sal",sal);
		request.setAttribute("lanCnt",temp_lanCnt);   //카테고리 검색시 외국어 관련 select 태그 수
		request.setAttribute("lan_n_list",lan_n_list);  //카테고리 검색에서 저장된 외국어 명 갯수
		request.setAttribute("lan_g_list",lan_g_list);  //카테고리 검색에서 저장된 외국어 등급 갯수
		
		request.setAttribute("license",license);
		request.setAttribute("area",area);
		request.setAttribute("jobtype",jobtype);
		request.setAttribute("word", word);
		
		request.setAttribute("select", select);
		request.setAttribute("order", order);

		
		//request에 담았으니 forward 방식으로 보낸다.
		request.getRequestDispatcher("/homepage_psn/layout.jsp?spage=/kim/searchRecruit.jsp").forward(request, response);
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
		int jiwon_cnt = dao.jiwonCnt(Integer.parseInt(num));
		//ArrayList<GJ_C_FILE_L> filelist = dao.getCFilelist(Integer.parseInt(num));
		HttpSession session=request.getSession();
		
		request.setAttribute("num", num);
		request.setAttribute("dto", dto);
		request.setAttribute("jiwon_cnt", jiwon_cnt);
		
		//request.setAttribut("filelist", filelist);
		//request에 담았으니 forward 방식으로 보낸다.
		MyRecruitKDAO dao1 = new MyRecruitKDAO();
		if(session !=null){
			String id = (String)session.getAttribute("id");
			boolean check_psn = dao1.checkPSN(id);
			if(check_psn){
				boolean check_recruit = dao1.checkMyRecruit(id, Integer.parseInt(num));
				request.setAttribute("check_recruit", check_recruit);
				request.getRequestDispatcher("/kim/detailRecruit2.jsp").forward(request, response);	
			}else{ //기업회원의 경우
				request.getRequestDispatcher("/kim/detailRecruit.jsp").forward(request, response);
			}
		}else{  //로그인 안했을 경우
			request.getRequestDispatcher("/kim/detailRecruit.jsp").forward(request, response);
		}
		
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////이력서 검색 관련 부분
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
		//ArrayList<GJ_P_FILE_L> filelist = dao.getPFilelist(Integer.parseInt(num));
		
		request.setAttribute("num", num);
		request.setAttribute("dto", dto);
		request.setAttribute("haklist", haklist);
		request.setAttribute("explist", explist);
		request.setAttribute("lancerlist", lancerlist);
		request.setAttribute("liclist", liclist);
		//request.setAttribut("filelist", filelist);
		//request에 담았으니 forward 방식으로 보낸다.
		
		request.getRequestDispatcher("/kim/detailResume.jsp").forward(request, response);
		
	}
	
	private void getLicense(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml;charset=utf-8");
		String license_name = request.getParameter("license_name");
		PrintWriter pw = response.getWriter();
		SearchKDAO dao = new SearchKDAO();
		ArrayList<String> list = dao.searchLicense(license_name);
		pw.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		pw.println("<result>");
		for(String temp : list){	
			pw.println("<license_name>");
			pw.println(temp);
			pw.println("</license_name>");	
		}
		pw.println("</result>");
		pw.close();
	}
	
	private void getMajor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml;charset=utf-8");
		String major_name = request.getParameter("major_name");
		PrintWriter pw = response.getWriter();
		SearchKDAO dao = new SearchKDAO();
		ArrayList<String> list = dao.searchMajor(major_name);
		pw.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		pw.println("<result>");
		for(String temp : list){	
			pw.println("<major_name>");
			pw.println(temp);
			pw.println("</major_name>");	
		}
		pw.println("</result>");
		pw.close();
	}
	
	private void getLan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter pw = response.getWriter();
		int num = Integer.parseInt(request.getParameter("num"));
		SearchKDAO dao = new SearchKDAO();
		ArrayList<GJ_LAN_L> list = dao.listLan(num);
		pw.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		pw.println("<result>");
		for(GJ_LAN_L temp : list){
			pw.println("<data>");
			pw.println("<lan_name>");
			pw.println(temp.getLan_name());
			pw.println("</lan_name>");
			pw.println("<lan_grade>");
			pw.println(temp.getLan_grade());
			pw.println("</lan_grade>");
			pw.println("</data>");
		}
		pw.println("</result>");
		pw.close();
	}
	private void getLic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter pw = response.getWriter();
		int num = Integer.parseInt(request.getParameter("num"));
		SearchKDAO dao = new SearchKDAO();
		ArrayList<GJ_LICENSE_L> list = dao.listLic(num);
		pw.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		pw.println("<result>");
		for(GJ_LICENSE_L temp : list){
			pw.println("<data>");
			pw.println("<lic_name>");
			pw.println(temp.getLicense_name());
			pw.println("</lic_name>");
			pw.println("<lic_date>");
			pw.println(temp.getLicense_date());
			pw.println("</lic_date>");
			pw.println("</data>");
		}
		pw.println("</result>");
		pw.close();
	}
	private void searchResume(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String spageNum=request.getParameter("pageNum");
		String word=request.getParameter("search"); 
		String cmd = request.getParameter("cmd");
		String mem_c_id=request.getParameter("mem_c_id");

		String select=request.getParameter("select");
		String order=request.getParameter("order");
		System.out.println("select : "+select);
		System.out.println("order : "+order);
		if(select ==null){
			select="mem_pr_date";
		}
		if(order ==null){
			order="asc";
		}
		if(word == null){
			word = "";
		}
		
		int pageNum=1; //페이지 번호가 없으면(파라미터가 넘어오지 않으면) 1페이지를 보여준다
		if(spageNum!=null){
			pageNum=Integer.parseInt(spageNum);
		}
		//1페이지면 1~10, 2페이지면 11~20까지 보여주기 위한 변수와 수식
		int startRow=(pageNum-1)*5+1;//시작행번호
		int endRow=startRow+4;//끝행번호

		SearchKDAO dao = new SearchKDAO();
		ArrayList<GJ_RESUME_S> //list = dao.searchResume(word, startRow, endRow);
		list = dao.searchResume(select, order, word, startRow, endRow);
		ArrayList<String> lanList = dao.allLan();
		
		BoardPDAO dao2=new BoardPDAO();
		ArrayList<Integer> list_star=dao2.resume_star(mem_c_id);
		
		/////////////////////////////////////////////////////////////
		//전체페이지갯수 구하기
		int pageCount=(int)Math.ceil(dao.countResume(word)/5.0); //페이지는 정수값뿐이니 올림 뒤 형변환후 넣어줬음
		
		//시작페이지 구하기
		int startPage=((pageNum-1)/4*4)+1;//정수끼리 계산이기 때문에 0.1~0.9 전부 0이다 즉 1~10페이지의 시작페이지는 언제나 1이다
		//끝페이지 구하기
		int endPage=startPage+3;
	
		// 여기까지만 있다면 전체 페이지가 10의 배수가 아닐때 비어있는 페이지가 출력된다(ex 전체 페이지가 25이지만 끝페이지는 30까지 된다)
		// 그렇기 때문에 아래 if문 추가!
		if(endPage>pageCount){
			endPage=pageCount;
		}
		request.setAttribute("select", select);
		request.setAttribute("order", order);
		request.setAttribute("cmd",cmd);
		request.setAttribute("list", list);
		request.setAttribute("list_star", list_star);
		request.setAttribute("lanList", lanList);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("search", word);
		
		//request에 담았으니 forward 방식으로 보낸다.
		request.getRequestDispatcher("/homepage_com/layout.jsp?spage=/kim/searchResume.jsp").forward(request, response);
	}
	
	private void categoryResume(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String spageNum=request.getParameter("pageNum");
		String cmd = request.getParameter("cmd");
		String jik = request.getParameter("jik");
		String exp = request.getParameter("exp");
		String hak = request.getParameter("hak");
		String major = request.getParameter("major");
		String temp_lanCnt = request.getParameter("lanCnt");
		
		String select = request.getParameter("select");
		String order = request.getParameter("order");
		String mem_c_id = request.getParameter("mem_c_id");
		
		if(select==null){
			select="mem_pr_date";
		}
		if(order==null){
			order="asc";
		}
		
		if(temp_lanCnt == null){
			temp_lanCnt = "1";
		}
		int lanCnt = Integer.parseInt(temp_lanCnt);
		String[] lan_n_list = new String[lanCnt];
		String[] lan_g_list = new String[lanCnt];
				
		for( int i=0; i< lanCnt; i++){
			String lan_n = request.getParameter("lan_n"+(i+1));
			String lan_g = request.getParameter("lan_g"+(i+1));
			if(lan_n == null || lan_g == null){
				lan_n = "";
				lan_g = "";
			}
			lan_n_list[i] = lan_n;
			lan_g_list[i] = lan_g;
		}
		
		String license = request.getParameter("license");
		String gender = request.getParameter("gender");
		String age1 = request.getParameter("age1");
		String age2 = request.getParameter("age2");
		String word = request.getParameter("word");
		
		if(word == null){
			word = "";
		}
		if(jik == null){
			jik = "";
		}
		if(exp == null){
			exp = "";
		}
		if(hak == null){
			hak = "";
		}
		if(major == null){
			major = "";
		}
		if(license == null){
			license = "";
		}
		if(gender == null){
			gender = "";
		}
		if(age1 == null && age2 == null){
			age1 = "";
			age2 = "";
		}
		
		int pageNum=1; //페이지 번호가 없으면(파라미터가 넘어오지 않으면) 1페이지를 보여준다
		if(spageNum!=null){
			pageNum=Integer.parseInt(spageNum);
		}
		//1페이지면 1~10, 2페이지면 11~20까지 보여주기 위한 변수와 수식
		int startRow=(pageNum-1)*5+1;//시작행번호
		int endRow=startRow+4;//끝행번호

		SearchKDAO dao = new SearchKDAO();
		ArrayList<GJ_RESUME_S> list = //dao.categoryResume(jik, exp, hak, major, lan_n_list, lan_g_list, license, gender, age1, age2, word, startRow, endRow);
		dao.categoryResume(select, order, jik, exp, hak, major, lan_n_list, lan_g_list, license, gender, age1, age2, word, startRow, endRow);
		
		BoardPDAO dao2 = new BoardPDAO();
		ArrayList<Integer> list_star =dao2.resume_star(mem_c_id);
		
		
		ArrayList<String> lanList = dao.allLan();
		/////////////////////////////////////////////////////////////
		//전체페이지갯수 구하기
		int pageCount=(int)Math.ceil(dao.countCGResume(jik, exp, hak, major, lan_n_list, lan_g_list, license, gender, age1, age2, word)/5.0); //페이지는 정수값뿐이니 올림 뒤 형변환후 넣어줬음
		
		//시작페이지 구하기
		int startPage=((pageNum-1)/4*4)+1;//정수끼리 계산이기 때문에 0.1~0.9 전부 0이다 즉 1~10페이지의 시작페이지는 언제나 1이다
		//끝페이지 구하기
		int endPage=startPage+3;
	
		// 여기까지만 있다면 전체 페이지가 10의 배수가 아닐때 비어있는 페이지가 출력된다(ex 전체 페이지가 25이지만 끝페이지는 30까지 된다)
		// 그렇기 때문에 아래 if문 추가!
		if(endPage>pageCount){
			endPage=pageCount;
		}
		request.setAttribute("cmd",cmd);
		request.setAttribute("list", list);
		request.setAttribute("lanList", lanList);  // 카테고리 검색시 외국어 default option 값
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("pageNum", pageNum);
		
		//jik, exp, hak, major, lan_list, license, gender, age1, age2, word
		// 검색값 저장
		request.setAttribute("jik",jik);
		request.setAttribute("exp",exp);
		request.setAttribute("hak",hak);
		request.setAttribute("major",major);
		request.setAttribute("lanCnt",temp_lanCnt);   //카테고리 검색시 외국어 관련 select 태그 수
		request.setAttribute("lan_n_list",lan_n_list);  //카테고리 검색에서 저장된 외국어 명 갯수
		request.setAttribute("lan_g_list",lan_g_list);  //카테고리 검색에서 저장된 외국어 등급 갯수
		
		request.setAttribute("license",license);
		request.setAttribute("gender",gender);
		request.setAttribute("age1",age1);
		request.setAttribute("age2",age2);
		request.setAttribute("word", word);

		request.setAttribute("select", select);
		request.setAttribute("order", order);
		request.setAttribute("list_star", list_star);
		
		
		//request에 담았으니 forward 방식으로 보낸다.
		request.getRequestDispatcher("/homepage_com/layout.jsp?spage=/kim/searchResume.jsp").forward(request, response);
	}
}
