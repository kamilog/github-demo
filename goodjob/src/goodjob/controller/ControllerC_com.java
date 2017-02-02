package goodjob.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import goodjob.dao.CCDAO;
import goodjob.dao.CDAO;
import goodjob.dao.LogCCDAO;
import goodjob.dao.SearchKDAO;
import goodjob.dto.GJ_MEM_C_DTO;
import goodjob.dto.GJ_REC_JIWON;

@WebServlet("/cho_com.do")
public class ControllerC_com extends HttpServlet{
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
	        response.sendRedirect("/homepage_com/layout.jsp");
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
		}else if(cmd.equals("gohome")){
			gohome(request,response);
		}else if(cmd.equals("godelete")){
			godelete(request,response);
		}
	}
	private void godelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		resp.sendRedirect("/homepage_com/layout.jsp?spage=/homepage_com/godelete.jsp");
	}
	private void gohome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id=req.getParameter("id");
		if(id==null){
			resp.sendRedirect("/intro.jsp");
		}else if(id.length()==0){
			resp.sendRedirect("/intro.jsp");
		}else{//�ͼ����� �߻��� ���
			SearchKDAO dao1 = new SearchKDAO();
			ArrayList<GJ_REC_JIWON> recruit_list = dao1.listJiwonJa(id);
			req.setAttribute("recruit_list", recruit_list);
			
			req.getRequestDispatcher("/homepage_com/layout.jsp").forward(req, resp);
		}
	}
	
	
	private void insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uploadPath=getServletContext().getRealPath("/upload");//��������Ȱ��
		System.out.println(uploadPath);//����Ȱ�� ���
		MultipartRequest mr=new MultipartRequest(
				req, //request��ü
				uploadPath, //������ ������ ���
				1024*1024*5, //�ִ� ���ε� ������(byte���� - 5mb)
				"utf-8", //���ڵ�Ÿ��
				new DefaultFileRenamePolicy()//������ ���ϸ��� ����� ���ϳ��� ���ں��̱�
			);
		String id=mr.getParameter("id");
		String pwd=mr.getParameter("pwd");
		String name=mr.getParameter("name");
		String comnum=mr.getParameter("comnum");
		String bossname=mr.getParameter("bossname");
		String addr=mr.getParameter("addr");
		String addr1=mr.getParameter("addr1");
		String addr2=mr.getParameter("addr2");
		if(addr1.length()!=0 || addr2.length()!=0){
			addr=addr1+" "+addr2;
		}
		String homepage=mr.getParameter("homepage");
		String intro=mr.getParameter("intro");
		String year=mr.getParameter("year");
		String sawon=mr.getParameter("sawon");
		String jabon=mr.getParameter("jabon");
		String machul=mr.getParameter("machul");
		String img=mr.getOriginalFileName("img");//���۵� ���ϸ�
		String simg=mr.getFilesystemName("img");//���� ����� ���ϸ�
		int up_code=Integer.parseInt(mr.getParameter("up_code"));
		//db����
		CCDAO dao=new CCDAO();
		
		GJ_MEM_C_DTO dto=new GJ_MEM_C_DTO(id,pwd,name,comnum,bossname,addr,
				homepage,intro,year,sawon,jabon,machul,null,img,simg,up_code);
		int n=dao.insert(dto);
		if(n>0){
			req.setAttribute("result","success");
		}else{
			req.setAttribute("result","fail");
		}
		req.getRequestDispatcher("/homepage_com/layout.jsp").forward(req,resp);
	}
	
	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id=req.getParameter("id");
		String pwd=req.getParameter("pwd");
		
		HashMap<String,String> map=new HashMap<>();
		map.put("id",id);
		map.put("pwd",pwd);
		
		//dao ���ͼ� �α��� �޼ҵ� ȣ��
		LogCCDAO dao=LogCCDAO.getInstance();
		int n=dao.isMember(map);
		if(n==1){//�ش������� �����ϴ� ���(ȸ���� ���)
			HttpSession session=req.getSession();//���� ������
			session.setAttribute("id",id);//���ǿ� ���̵� ���
			SearchKDAO dao1 = new SearchKDAO();
			ArrayList<GJ_REC_JIWON> recruit_list = dao1.listJiwonJa(id);
			req.setAttribute("recruit_list", recruit_list);
			
			req.getRequestDispatcher("/homepage_com/layout.jsp").forward(req, resp);
		}else if(n==0){//�ش������� �������� �ʴ� ���
			req.setAttribute("errMsg","���̵� �Ǵ� ��й�ȣ�� ��ġ���� �ʽ��ϴ�");
			req.getRequestDispatcher("/homepage_com/layout.jsp").forward(req, resp);
		}else{//�ͼ����� �߻��� ���
			resp.sendRedirect("/cmembers/2.jsp");
		}
	}
	
	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CCDAO dao=new CCDAO();
		String mem_c_id=req.getParameter("id");
		GJ_MEM_C_DTO dto=dao.all(mem_c_id);
		req.setAttribute("dto",dto);
		req.getRequestDispatcher("/homepage_com/layout.jsp?spage=update.jsp").forward(req,resp);
	}
	
	private void update1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uploadPath=getServletContext().getRealPath("/upload");
		MultipartRequest mr=new MultipartRequest(
			req, //request��ü
			uploadPath, //������ ������ ���
			1024*1024*5, //�ִ� ���ε� ������(byte���� - 5mb)
			"utf-8", //���ڵ�Ÿ��
			new DefaultFileRenamePolicy()//������ ���ϸ��� ����� ���ϳ��� ���ں��̱�
		);
		String id=mr.getParameter("id");
		String pwd=mr.getParameter("pwd");
		String name=mr.getParameter("name");
		String comnum=mr.getParameter("comnum");
		String bossname=mr.getParameter("bossname");
		String addr=mr.getParameter("addr");
		String addr1=mr.getParameter("addr1");
		String addr2=mr.getParameter("addr2");
		if(addr1.length()!=0 || addr2.length()!=0){
			addr=addr1+" "+addr2;
		}
		String homepage=mr.getParameter("homepage");
		String intro=mr.getParameter("intro");
		String year=mr.getParameter("year");
		String sawon=mr.getParameter("sawon");
		String jabon=mr.getParameter("jabon");
		String machul=mr.getParameter("machul");
		String img=mr.getOriginalFileName("img");//���۵� ���ϸ�
		String simg=mr.getFilesystemName("simg");//���� ����� ���ϸ�
				
		CCDAO dao=new CCDAO();
		//���� ������ִ� ����
		GJ_MEM_C_DTO dto=dao.all(id);
		String img1=dto.getMem_c_img();//�������ϸ�
		String simg1=dto.getMem_c_simg();//�����������ϸ�
		int n=0;
		if(img==null){//������ �������� �ʴ� ���
			GJ_MEM_C_DTO dto1=new GJ_MEM_C_DTO(id,pwd,name,comnum,bossname,addr,
					homepage,intro,year,sawon,jabon,machul,null,img1,simg1,0);
			n=dao.update1(dto1);//DB�� ���� �����ϱ�
		}else{//������ ������ ���
			//������ ���������
			String delFile=uploadPath+"\\"+simg1;
			File f=new File(delFile);
			f.delete();
			GJ_MEM_C_DTO dto2=new GJ_MEM_C_DTO(id,pwd,name,comnum,bossname,addr,
					homepage,intro,year,sawon,jabon,machul,null,img,simg,0);
			n=dao.update1(dto2);//DB�� ���� �����ϱ�
		}
		if(n>0){
			req.setAttribute("result","success1");
		}else{
			req.setAttribute("result","fail1");
		}
		req.getRequestDispatcher("/homepage_com/layout.jsp").forward(req,resp);
	}
	
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CCDAO dao=new CCDAO();
		//������ ���������
		String mem_c_id=req.getParameter("id");
		int n=dao.delete(mem_c_id);
		
		HttpSession session=req.getSession();
        session.invalidate();
        resp.sendRedirect("/homepage_com/layout.jsp");
	}
}
