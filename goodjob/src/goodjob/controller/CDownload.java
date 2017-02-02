package goodjob.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import goodjob.dao.SearchKDAO;
import goodjob.dto.GJ_C_FILE_DTO;

@WebServlet("/cdownload.do")
public class CDownload extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int file_c_code=Integer.parseInt(request.getParameter("file_c_code"));
		SearchKDAO dao=new SearchKDAO();
		GJ_C_FILE_DTO dto=dao.detail(file_c_code);
		String name=dto.getFile_c_name();//전송한 파일명
		String sname=dto.getFile_c_sname();//실제 저장된 파일명
		
		//////////다운로드창으로 응답하기 //////////
		//응답을 8비트로 나열된 데이터로 응답하기(무조건 다운로드창이 뜬다.)
		response.setContentType("application/octet-stream");
		//다운로드보여질 정보 설정(한글파일명이 깨지지 않도록)
		name=URLEncoder.encode(name,"utf-8");
		//공백인경우 +로 변환된것을 다시 공백으로 바꾸기
		name=name.replaceAll("\\+","%20");
		//파일명이 깨지지 않도록 인코딩된 파일명으로 다운로드창에 보이기
		response.setHeader("Content-Disposition","attachment;filename="+name);
		
		//////////실제다운로드하기(서버측 파일을 클라이언트에 복사하기) //////////
		OutputStream os=response.getOutputStream();//클라이언트에 출력하기 위한 스트림
		String path=getServletContext().getRealPath("/upload"); //ServletContext = application
		String file=path+"\\"+sname;//다운로드할 파일
		FileInputStream fis=new FileInputStream(file);
		//성능향상을 위한 버퍼기능이 있는 스트링으로 변환
		BufferedOutputStream bos=new BufferedOutputStream(os);
		BufferedInputStream bis=new BufferedInputStream(fis);
		
		byte[] b=new byte[1024];
		int n=0;
		while((n=bis.read(b))!=-1){
			bos.write(b,0,n);
		}
		bos.flush();
		bos.close();
		bis.close();
	}
}
