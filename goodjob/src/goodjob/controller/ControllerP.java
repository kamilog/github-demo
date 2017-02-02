package goodjob.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import goodjob.dao.AdminPDAO;
import goodjob.dbcp.DbcpBean;
import goodjob.dto.GJ_BOARD_FREE_DTO;
import goodjob.dto.GJ_BOARD_NEWS_DTO;
import goodjob.dto.GJ_BOARD_NOTICE_DTO;
import goodjob.dto.GJ_C_GRADE_DTO;
import goodjob.dto.GJ_C_RECRUIT_DTO;
import goodjob.dto.GJ_MEM_C_DTO;
import goodjob.dto.GJ_MEM_P_DTO;
import goodjob.dto.GJ_MEM_RECRUIT_DTO;
import goodjob.dto.GJ_RECRUIT_DETAIL_DTO;

@WebServlet("/park.do")
public class ControllerP extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String cmd=request.getParameter("cmd");
		if(cmd.equals("notice_insert")){
			notice_insert(request,response);
		}else if(cmd.equals("notice_search")){
			notice_search(request,response);
		}else if(cmd.equals("search_c")){
			search_c(request,response);
		}else if(cmd.equals("search_p")){
			search_p(request,response);
		}else if(cmd.equals("notice_update")){
			notice_update(request,response);
		}else if(cmd.equals("notice_detail")){
			notice_detail(request,response);
		}else if(cmd.equals("list_c_detail")){
			list_c_detail(request,response);
		}else if(cmd.equals("list_p_detail")){
			list_p_detail(request,response);
		}else if(cmd.equals("delete_c")){
			delete_c(request,response);
		}else if(cmd.equals("delete_p")){
			delete_p(request,response);
		}else if(cmd.equals("grade_update")){
			grade_update(request,response);
		}else if(cmd.equals("board_free")){
			board_free(request,response);
		}else if(cmd.equals("board_free_detail")){
			board_free_detail(request,response);
		}else if(cmd.equals("delete_free")){
			delete_free(request,response);
		}else if(cmd.equals("board_recruit")){
			board_recruit(request,response);
		}else if(cmd.equals("board_recruit_detail")){
			board_recruit_detail(request,response);
		}else if(cmd.equals("news_insert")){
			news_insert(request,response);
		}else if(cmd.equals("news_search")){
			news_search(request,response);
		}else if(cmd.equals("news_detail")){
			news_detail(request,response);
		}else if(cmd.equals("news_update")){
			news_update(request,response);
		}
	}
	private void grade_update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String mem_c_id=request.getParameter("mem_c_id");
		int gm=Integer.parseInt(request.getParameter("grade_money"));
		String grade="";
		
		if(gm>=300){
			grade="4";			
		}else if(gm>=200){
			grade="3";
		}else if(gm>=100){
			grade="2";
		}else{
			grade="1";
		}
		String grade_money=""+gm;

		AdminPDAO dao=new AdminPDAO();

		int n=dao.grade_update(grade,grade_money, mem_c_id);
		System.out.println(n+"rorororo");
		if(n>0){
			request.getRequestDispatcher("/park.do?cmd=list_c_detail&mem_c_id="+mem_c_id).forward(request, response);
		}else{
			request.setAttribute("result", "수정실패");
			request.getRequestDispatcher("/admin/notice_result.jsp").forward(request, response);
		}
	}
	private void notice_insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String board_notice_title=request.getParameter("board_notice_title");
		String board_notice_contents=request.getParameter("board_notice_contents");
		
		GJ_BOARD_NOTICE_DTO dto=new GJ_BOARD_NOTICE_DTO(0, board_notice_title, board_notice_contents, null);
		AdminPDAO dao=new AdminPDAO();
		int n=dao.insert(dto);
		if(n>0){
			request.getRequestDispatcher("/park.do?cmd=notice_search&select=board_notice_num&search=").forward(request, response);
		}else{
			request.setAttribute("result", "공지사항등록실패");
			request.getRequestDispatcher("/admin/notice_result.jsp").forward(request, response);
		}
	}
	private void news_insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String board_news_title=request.getParameter("board_news_title");
		String board_news_contents=request.getParameter("board_news_contents");
		
		GJ_BOARD_NEWS_DTO dto=new GJ_BOARD_NEWS_DTO(0, board_news_title, board_news_contents,null);
		AdminPDAO dao=new AdminPDAO();
		int n=dao.news_insert(dto);
		if(n>0){
			request.setAttribute("result", "공지사항등록성공");
			request.getRequestDispatcher("/admin/notice_result.jsp").forward(request, response);
		}else{
			request.setAttribute("result", "공지사항등록실패");
			request.getRequestDispatcher("/admin/notice_result.jsp").forward(request, response);
		}
	}
	private void news_update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int board_news_num=Integer.parseInt(request.getParameter("board_news_num"));
		String board_news_title=request.getParameter("board_news_title");
		String board_news_contents=request.getParameter("board_news_contents");
		System.out.println("보드뉴스넘 업뎃 : "+board_news_num);

		AdminPDAO dao=new AdminPDAO();
		int n=dao.news_update(board_news_num, board_news_title, board_news_contents);
		if(n>0){
			request.getRequestDispatcher("/park.do?cmd=news_search&select=board_news_num&search=").forward(request, response);
		}else{
			request.setAttribute("result", "공지사항수정실패");
			request.getRequestDispatcher("/admin/result.jsp").forward(request, response);
		}
	}
	private void news_detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int board_news_num=Integer.parseInt(request.getParameter("board_news_num"));
		AdminPDAO dao=new AdminPDAO();
		GJ_BOARD_NEWS_DTO  dto=new GJ_BOARD_NEWS_DTO();
			dto=dao.news_detail(board_news_num);
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("/admin/news_detail.jsp").forward(request, response);
	}
	private void notice_update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int board_notice_num=Integer.parseInt(request.getParameter("board_notice_num"));
		String board_notice_title=request.getParameter("board_notice_title");
		String board_notice_contents=request.getParameter("board_notice_contents");
		

		AdminPDAO dao=new AdminPDAO();
		int n=dao.notice_update(board_notice_num, board_notice_title, board_notice_contents);
		if(n>0){
			request.getRequestDispatcher("/park.do?cmd=notice_search&select=board_notice_num&search=").forward(request, response);
		}else{
			request.setAttribute("result", "공지사항수정실패");
			request.getRequestDispatcher("/admin/result.jsp").forward(request, response);
		}
	}
	private void notice_detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int board_notice_num=Integer.parseInt(request.getParameter("board_notice_num"));
		AdminPDAO dao=new AdminPDAO();
		GJ_BOARD_NOTICE_DTO dto=new GJ_BOARD_NOTICE_DTO();
			dto=dao.notice_detail(board_notice_num);
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("/admin/notice_detail.jsp").forward(request, response);
	}
	private void delete_p(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String mem_p_id=request.getParameter("mem_p_id");
		
		
		GJ_MEM_P_DTO dto=new GJ_MEM_P_DTO();
		AdminPDAO dao=new AdminPDAO();
		int n=dao.delete_p(mem_p_id);
		if(n>0){
			request.setAttribute("result", "삭제성공");
			request.getRequestDispatcher("/park.do?cmd=search_p&select=mem_p_id&search=").forward(request, response);
		}else{
			request.setAttribute("result", "삭제실패");
			request.getRequestDispatcher("/admin/notice_result.jsp").forward(request, response);
		}
	}
	private void delete_c(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String mem_c_id=request.getParameter("mem_c_id");
		
		GJ_BOARD_NOTICE_DTO dto=new GJ_BOARD_NOTICE_DTO();
		AdminPDAO dao=new AdminPDAO();
		int n=dao.delete_c(mem_c_id);
		
		if(n>0){
			request.getRequestDispatcher("/admin/notice_result.jsp").forward(request, response);
			request.setAttribute("result", "삭제성공");
		}else{
			request.setAttribute("result", "삭제실패");
			request.getRequestDispatcher("/admin/notice_result.jsp").forward(request, response);
		}
	}
	private void list_c_detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String mem_c_id=request.getParameter("mem_c_id");
		//
		AdminPDAO dao=new AdminPDAO();
		GJ_MEM_C_DTO dto=new GJ_MEM_C_DTO();
		GJ_C_GRADE_DTO dto2=new GJ_C_GRADE_DTO();
		dto=dao.list_c_detail(mem_c_id);
		dto2=dao.grade_c(mem_c_id);
		request.setAttribute("dto", dto);
		request.setAttribute("dto2", dto2);
		request.getRequestDispatcher("/admin/list_c_detail.jsp").forward(request, response);
	}
	private void list_p_detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String mem_p_id=request.getParameter("mem_p_id");
		AdminPDAO dao=new AdminPDAO();
		GJ_MEM_P_DTO dto=new GJ_MEM_P_DTO();
		dto=dao.list_p_detail(mem_p_id);
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("/admin/list_p_detail.jsp").forward(request, response);
	}
	private void board_recruit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String spageNum=request.getParameter("pageNum");
		int pageNum=1;  
		if(spageNum!=null){
			pageNum=Integer.parseInt(spageNum);
		}
		int startNum=(pageNum-1)*10+1; 
		int endNum=startNum+9; 
		String select=request.getParameter("select");
		String search=request.getParameter("search");
		//String mem_c_id=request.getParameter("mem_c_id");
		
		AdminPDAO dao=new AdminPDAO();
		ArrayList<GJ_C_RECRUIT_DTO> list=dao.board_recruit(select, search, startNum, endNum);
		
		int pageCount=(int)Math.ceil(dao.count_recruit(select, search)/10.0);
		
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
	//	request.setAttribute("mem_c_name", mem_c_name);
		request.getRequestDispatcher("/admin/board_recruit.jsp").forward(request, response);
	}
	private void notice_search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		request.getRequestDispatcher("/admin/notice_list.jsp").forward(request, response);
	}
	private void news_search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		request.getRequestDispatcher("/admin/news_list.jsp").forward(request, response);
	}
	private void search_c(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		ArrayList<GJ_MEM_C_DTO> list=dao.search_c(select, search, startNum, endNum);
		
		int pageCount=(int)Math.ceil(dao.searchCount_c(select, search)/10.0);
		
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
		request.getRequestDispatcher("/admin/list_c.jsp").forward(request, response);
	}
	private void search_p(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
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
		ArrayList<GJ_MEM_P_DTO> list=dao.search_p(select, search, startNum, endNum);
		
		int pageCount=(int)Math.ceil(dao.searchCount_p(select,search)/10.0);
		
		
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
		request.getRequestDispatcher("/admin/list_p.jsp").forward(request, response);
	}
	private void board_free(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		request.getRequestDispatcher("/admin/board_free.jsp").forward(request, response);
	}
	private void board_free_detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int board_free_num=Integer.parseInt(request.getParameter("board_free_num"));
		AdminPDAO dao=new AdminPDAO();
		GJ_BOARD_FREE_DTO dto=new GJ_BOARD_FREE_DTO();
		dto=dao.board_free_detail(board_free_num);
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("/admin/board_free_detail.jsp").forward(request, response);
	}
	private void delete_free(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int board_free_num=Integer.parseInt(request.getParameter("board_free_num"));
		
		GJ_BOARD_FREE_DTO dto=new GJ_BOARD_FREE_DTO();
		AdminPDAO dao=new AdminPDAO();
		int n=dao.delete_free(board_free_num);
		
		if(n>0){
			request.setAttribute("result", "삭제성공");
			request.getRequestDispatcher("/park.do?cmd=board_free&select=board_free_num&search=").forward(request, response);
		}else{
			request.setAttribute("result", "삭제실패");
			request.getRequestDispatcher("/admin/notice_result.jsp").forward(request, response);
		}
	}
	private void board_recruit_detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int mem_cr_num=Integer.parseInt(request.getParameter("mem_cr_num"));
		AdminPDAO dao=new AdminPDAO();
		GJ_RECRUIT_DETAIL_DTO dto=new GJ_RECRUIT_DETAIL_DTO();
		dto=dao.board_recruit_detail(mem_cr_num);
		
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("/admin/board_recruit_detail.jsp").forward(request, response);
	}
}
	