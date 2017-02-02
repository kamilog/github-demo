package goodjob.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import goodjob.dao.AdminPDAO;
import goodjob.dao.BoardPDAO;
import goodjob.dao.SearchKDAO;
import goodjob.dto.GJ_BAG_DTO;
import goodjob.dto.GJ_BOARD_FREE_DTO;
import goodjob.dto.GJ_BOARD_NEWS_DTO;
import goodjob.dto.GJ_BOARD_NOTICE_DTO;
import goodjob.dto.GJ_DETAIL_RECRUIT;
import goodjob.dto.GJ_DETAIL_RESUME;
import goodjob.dto.GJ_EXPINFO_L;
import goodjob.dto.GJ_HAK_L;
import goodjob.dto.GJ_LAN_CER_L;
import goodjob.dto.GJ_LICENSE_L;
import goodjob.dto.GJ_MEM_RESUME_DTO;
import goodjob.dto.GJ_RECRUIT_DETAIL_DTO;
import goodjob.dto.GJ_RESUME_S;
@WebServlet("/park_board.do")
public class ControllerBoard_P extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String cmd=request.getParameter("cmd");
		if(cmd.equals("comu_freeboard")){
			comu_freeboard(request,response);
		}else if(cmd.equals("comu_freeboard_detail")){
			comu_freeboard_detail(request,response);
		}else if(cmd.equals("comu_freeboard_write")){
			comu_freeboard_write(request,response);
		}else if(cmd.equals("notice_board")){
			notice_board(request,response);
		}else if(cmd.equals("notice_board2")){
			notice_board2(request,response);
		}else if(cmd.equals("noitce_board_detail")){
			noitce_board_detail(request,response);
		}else if(cmd.equals("login")){
			login(request,response);
		}else if(cmd.equals("recommend")){
			recommend(request,response);
		}else if(cmd.equals("like_resume_comment")){
			like_resume_comment(request,response);
		}else if(cmd.equals("like_resume")){
			like_resume(request,response);
		}else if(cmd.equals("wish_resume")){
			wish_resume(request,response);
		}else if(cmd.equals("detailResume_2")){
			detailResume_2(request,response);
		}else if(cmd.equals("delete_bag")){
			delete_bag(request,response);
		}else if(cmd.equals("news_board")){
			news_board(request,response);
		}else if(cmd.equals("news_board2")){
			news_board2(request,response);
		}else if(cmd.equals("news_board_detail")){
			news_board_detail(request,response);
		}else if(cmd.equals("primium_recruit")){
			primium_recruit(request,response);
		}else if(cmd.equals("resumelist")){
			resumelist(request,response);
		}else if(cmd.equals("div_notice")){
	        div_notice(request,response);
	    }else if(cmd.equals("div_news")){
	        div_news(request,response);
	    }
	}
	private void comu_freeboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String spageNum=request.getParameter("pageNum");
		int pageNum=1;  
		if(spageNum!=null){
			pageNum=Integer.parseInt(spageNum);
		}
		int startNum=(pageNum-1)*10+1; 
		int endNum=startNum+9; 
		String select=request.getParameter("select");
		String search=request.getParameter("search");

		AdminPDAO dao=new AdminPDAO();
		ArrayList<GJ_BOARD_FREE_DTO> list=dao.board_free(select, search, startNum, endNum);
		
		int pageCount=(int)Math.ceil(dao.count_board_free(select, search)/10.0);
		
		int startPage=((pageNum-1)/10*10)+1; 
		
		int endPage=startPage+9;
		
		if(endPage>pageCount){
			endPage=pageCount;
		}
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage",endPage);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("list", list);
		request.setAttribute("select", select);
		request.setAttribute("search", search);
		request.getRequestDispatcher("/homepage_psn/layout.jsp?spage=/community/comu_freeboard.jsp").forward(request, response);
	}
	
	private void comu_freeboard_detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int board_free_num=Integer.parseInt(request.getParameter("board_free_num"));
		AdminPDAO dao=new AdminPDAO();
		GJ_BOARD_FREE_DTO dto=new GJ_BOARD_FREE_DTO();
		dto=dao.board_free_detail(board_free_num);
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("/homepage_psn/layout.jsp?spage=/community/comu_freeboard_detail.jsp").forward(request, response);
	}
	
	private void comu_freeboard_write(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String board_free_title=request.getParameter("board_free_title");
		String board_free_contents=request.getParameter("board_free_contents");
		String mem_p_id=request.getParameter("mem_p_id");
		
		
		
	//	GJ_BOARD_FREE_DTO dto=new GJ_BOARD_FREE_DTO(0, board_free_title, board_free_contents, null, 0, 0, 0, mem_p_id);
		BoardPDAO dao=new BoardPDAO();
		int n=dao.comu_freeboard_write(board_free_title, board_free_contents, mem_p_id);
		
		request.getRequestDispatcher("/park_board.do?cmd=comu_freeboard&select=board_free_num&search=").forward(request, response);		
	}
	private void notice_board(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String spageNum=request.getParameter("pageNum");
		int pageNum=1;  
		if(spageNum!=null){
			pageNum=Integer.parseInt(spageNum);
		}
		int startNum=(pageNum-1)*10+1; 
		int endNum=startNum+9; 
		String select=request.getParameter("select");
		String search=request.getParameter("search");

		AdminPDAO dao=new AdminPDAO();
		ArrayList<GJ_BOARD_NOTICE_DTO> list=dao.notice_search(select, search, startNum, endNum);
		
		int pageCount=(int)Math.ceil(dao.searchCount_list(select, search)/10.0);
		
		int startPage=((pageNum-1)/10*10)+1; 
		
		int endPage=startPage+9;
		
		if(endPage>pageCount){
			endPage=pageCount;
		}
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage",endPage);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("list", list);
		request.setAttribute("select", select);
		request.setAttribute("search", search);
		request.getRequestDispatcher("/homepage_psn/layout.jsp?spage=/community/notice_board.jsp").forward(request, response);
	}
	private void notice_board2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String spageNum=request.getParameter("pageNum");
		int pageNum=1;  
		if(spageNum!=null){
			pageNum=Integer.parseInt(spageNum);
		}
		int startNum=(pageNum-1)*10+1; 
		int endNum=startNum+9; 
		String select=request.getParameter("select");
		String search=request.getParameter("search");

		AdminPDAO dao=new AdminPDAO();
		ArrayList<GJ_BOARD_NOTICE_DTO> list=dao.notice_search(select, search, startNum, endNum);
		
		int pageCount=(int)Math.ceil(dao.searchCount_list(select, search)/10.0);
		
		int startPage=((pageNum-1)/10*10)+1; 
		
		int endPage=startPage+9;
		
		if(endPage>pageCount){
			endPage=pageCount;
		}
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage",endPage);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("list", list);
		request.setAttribute("select", select);
		request.setAttribute("search", search);
		request.getRequestDispatcher("/homepage_com/layout.jsp?spage=/community/notice_board.jsp").forward(request, response);
	}
	private void noitce_board_detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int board_notice_num=Integer.parseInt(request.getParameter("board_notice_num"));
		AdminPDAO dao=new AdminPDAO();
		
		GJ_BOARD_NOTICE_DTO dto=new GJ_BOARD_NOTICE_DTO();
			dto=dao.notice_detail(board_notice_num);
			
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("/homepage_psn/layout.jsp?spage=/community/notice_board_detail.jsp").forward(request, response);
	}
	
	private void news_board(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String spageNum=request.getParameter("pageNum");
		int pageNum=1;  
		if(spageNum!=null){
			pageNum=Integer.parseInt(spageNum);
		}
		int startNum=(pageNum-1)*10+1; 
		int endNum=startNum+9; 
		String select=request.getParameter("select");
		String search=request.getParameter("search");

		AdminPDAO dao=new AdminPDAO();
		ArrayList<GJ_BOARD_NEWS_DTO> list=dao.news_search(select, search, startNum, endNum);
		
		int pageCount=(int)Math.ceil(dao.searchCount_news(select, search)/10.0);
		
		int startPage=((pageNum-1)/10*10)+1; 
		
		int endPage=startPage+9;
		
		if(endPage>pageCount){
			endPage=pageCount;
		}
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage",endPage);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("list", list);
		request.setAttribute("select", select);
		request.setAttribute("search", search);
		request.getRequestDispatcher("/homepage_psn/layout.jsp?spage=/community/news_board.jsp").forward(request, response);
	}
	private void news_board2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String spageNum=request.getParameter("pageNum");
		int pageNum=1;  
		if(spageNum!=null){
			pageNum=Integer.parseInt(spageNum);
		}
		int startNum=(pageNum-1)*10+1; 
		int endNum=startNum+9; 
		String select=request.getParameter("select");
		String search=request.getParameter("search");

		AdminPDAO dao=new AdminPDAO();
		ArrayList<GJ_BOARD_NEWS_DTO> list=dao.news_search(select, search, startNum, endNum);
		
		int pageCount=(int)Math.ceil(dao.searchCount_news(select, search)/10.0);
		
		int startPage=((pageNum-1)/10*10)+1; 
		
		int endPage=startPage+9;
		
		if(endPage>pageCount){
			endPage=pageCount;
		}
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage",endPage);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("list", list);
		request.setAttribute("select", select);
		request.setAttribute("search", search);
		request.getRequestDispatcher("/homepage_com/layout.jsp?spage=/community/news_board.jsp").forward(request, response);
	}
	private void news_board_detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int board_news_num=Integer.parseInt(request.getParameter("board_news_num"));
		AdminPDAO dao=new AdminPDAO();
		
		GJ_BOARD_NEWS_DTO dto=new GJ_BOARD_NEWS_DTO();
			dto=dao.news_detail(board_news_num);
			
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("/homepage_psn/layout.jsp?spage=/community/news_board_detail.jsp").forward(request, response);
	}
	
	
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String mem_p_id=request.getParameter("mem_p_id");
		
		System.out.println(mem_p_id);
		int jik2=0;
		BoardPDAO dao=new BoardPDAO();
		ArrayList<GJ_RECRUIT_DETAIL_DTO> list_c=new ArrayList<>();
		ArrayList<GJ_MEM_RESUME_DTO> list_p=new ArrayList<>();
		list_c=dao.login_c_reco(mem_p_id,jik2);
		list_p=dao.resume_title(mem_p_id);
		request.setAttribute("list_p", list_p);
		request.setAttribute("list_c", list_c);
		request.setAttribute("mem_p_id",mem_p_id);
//		request.getRequestDispatcher("/homepage_psn/layout.jsp").forward(request, response);
		
		request.getRequestDispatcher("/c_reco_div.jsp").forward(request, response);
	}
	private void recommend(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mem_p_id=request.getParameter("mem_p_id");
		
		BoardPDAO dao=new BoardPDAO();
		
		ArrayList<GJ_MEM_RESUME_DTO> list_resume=new ArrayList<>();
		
		list_resume=dao.resume_title(mem_p_id);
		
		request.setAttribute("list_resume", list_resume);
		request.setAttribute("mem_p_id",mem_p_id);
		request.getRequestDispatcher("/community/c_reco.jsp").forward(request, response);
	}
	
	private void like_resume_comment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num=Integer.parseInt(request.getParameter("num"));
		String mem_c_id=request.getParameter("mem_c_id");
		request.getRequestDispatcher("/community/bag_popup.jsp").forward(request, response);		
	}
	
	private void like_resume(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num=Integer.parseInt(request.getParameter("num"));
		String mem_c_id=request.getParameter("mem_c_id");
		String bag_comment=request.getParameter("bag_comment");
		BoardPDAO dao=new BoardPDAO();
		GJ_BAG_DTO dto=new GJ_BAG_DTO(0, bag_comment, mem_c_id, num);
		int n=dao.like_resume(dto);
		if(n>0){
			request.setAttribute("result", "완료되었습니다.");
			request.getRequestDispatcher("/community/bag_result.jsp").forward(request, response);		
		}else{
			request.setAttribute("result", "실패햇슴니다.");
		}
	}
	private void wish_resume(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String spageNum=request.getParameter("pageNum");
		String word=request.getParameter("search"); 
		String cmd = request.getParameter("cmd");
		String mem_c_id=request.getParameter("mem_c_id");
		
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
		ArrayList<String> lanList = dao.allLan();
		
		BoardPDAO dao2=new BoardPDAO();
		ArrayList<GJ_RESUME_S> list = dao2.wish_resume(mem_c_id);
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
		request.setAttribute("cmd",cmd);
		request.setAttribute("list", list);
		request.setAttribute("list_star", list_star);
		request.setAttribute("lanList", lanList);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("search", word);
		
		request.getRequestDispatcher("/community/wish_resume.jsp").forward(request, response);
	}
	private void detailResume_2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		request.setAttribute("num", num);
		request.setAttribute("dto", dto);
		request.setAttribute("haklist", haklist);
		request.setAttribute("explist", explist);
		request.setAttribute("lancerlist", lancerlist);
		request.setAttribute("liclist", liclist);
		
		request.getRequestDispatcher("/kim/detailResume_2.jsp").forward(request, response);
		
	}
	private void delete_bag(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String mem_c_id=request.getParameter("mem_c_id");
		int mem_pr_num=Integer.parseInt(request.getParameter("num"));
		BoardPDAO dao=new BoardPDAO();
		int n=dao.delete_bag(mem_c_id, mem_pr_num);
		
		if(n>0){
			request.getRequestDispatcher("/community/bag_result.jsp").forward(request, response);
			request.setAttribute("result", "관심이력서삭제 성공");
		}else{
			request.setAttribute("result", "삭제실패");
			request.getRequestDispatcher("/community/bag_result.jsp").forward(request, response);
		}
	}
	private void primium_recruit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			response.setContentType("text/xml;charset=utf-8");
		BoardPDAO dao=new BoardPDAO();
		ArrayList<GJ_DETAIL_RECRUIT> list=dao.primium_recruit();
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter pw=response.getWriter();
		pw.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		pw.print("<list>");
		for(int i=0;i<list.size();i++){
			GJ_DETAIL_RECRUIT dto=list.get(i);
			pw.println("<data>");
			pw.println("<simg>"+dto.getSimg()+"</simg>");
			pw.println("<title>"+dto.getTitle()+"</title>");
			pw.println("<num>"+dto.getNum()+"</num>");
			pw.println("</data>");
		}
		pw.print("</list>");
		pw.close();
	}private void resumelist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mem_p_id=request.getParameter("mem_p_id");
		BoardPDAO dao=new BoardPDAO();
		ArrayList<GJ_MEM_RESUME_DTO> list_resume=new ArrayList<>();
		list_resume=dao.resume_title(mem_p_id);
		
	//	list_recruit=dao.recommend_c();
		
		request.setAttribute("list_resume", list_resume);
		request.setAttribute("mem_p_id",mem_p_id);
		request.getRequestDispatcher("/homepage_psn/layout.jsp?spage=/homepage_psn/c_reco.jsp").forward(request, response);
	}
	
	   private void div_notice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {         response.setContentType("text/xml;charset=utf-8");
	   BoardPDAO dao=new BoardPDAO();
	   ArrayList<GJ_BOARD_NOTICE_DTO> list=dao.div_notice();
	   response.setContentType("text/xml;charset=utf-8");
	   PrintWriter pw=response.getWriter();
	   pw.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	   pw.print("<list>");
	   for(int i=0;i<list.size();i++){
	      GJ_BOARD_NOTICE_DTO dto=list.get(i);
	      pw.println("<data>");
	      pw.println("<notice_title>"+dto.getBoard_notice_title()+"</notice_title>");
	      pw.println("<notice_num>"+dto.getBoard_notice_num()+"</notice_num>");
	      pw.println("</data>");
	   }
	   pw.print("</list>");
	   pw.close();
	   }
	   private void div_news(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {         response.setContentType("text/xml;charset=utf-8");
	   BoardPDAO dao=new BoardPDAO();
	   ArrayList<GJ_BOARD_NEWS_DTO> list=dao.div_news();
	   response.setContentType("text/xml;charset=utf-8");
	   PrintWriter pw=response.getWriter();
	   pw.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	   pw.print("<list>");
	   for(int i=0;i<list.size();i++){
	      GJ_BOARD_NEWS_DTO dto=list.get(i);
	      pw.println("<data>");
	      pw.println("<news_title>"+dto.getBoard_news_title()+"</news_title>");
	      pw.println("<news_num>"+dto.getBoard_news_num()+"</news_num>");
	      pw.println("</data>");
	   }
	   pw.print("</list>");
	   pw.close();
	   }
}
