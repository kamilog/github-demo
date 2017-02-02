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
		String name=dto.getFile_c_name();//������ ���ϸ�
		String sname=dto.getFile_c_sname();//���� ����� ���ϸ�
		
		//////////�ٿ�ε�â���� �����ϱ� //////////
		//������ 8��Ʈ�� ������ �����ͷ� �����ϱ�(������ �ٿ�ε�â�� ���.)
		response.setContentType("application/octet-stream");
		//�ٿ�ε庸���� ���� ����(�ѱ����ϸ��� ������ �ʵ���)
		name=URLEncoder.encode(name,"utf-8");
		//�����ΰ�� +�� ��ȯ�Ȱ��� �ٽ� �������� �ٲٱ�
		name=name.replaceAll("\\+","%20");
		//���ϸ��� ������ �ʵ��� ���ڵ��� ���ϸ����� �ٿ�ε�â�� ���̱�
		response.setHeader("Content-Disposition","attachment;filename="+name);
		
		//////////�����ٿ�ε��ϱ�(������ ������ Ŭ���̾�Ʈ�� �����ϱ�) //////////
		OutputStream os=response.getOutputStream();//Ŭ���̾�Ʈ�� ����ϱ� ���� ��Ʈ��
		String path=getServletContext().getRealPath("/upload"); //ServletContext = application
		String file=path+"\\"+sname;//�ٿ�ε��� ����
		FileInputStream fis=new FileInputStream(file);
		//��������� ���� ���۱���� �ִ� ��Ʈ������ ��ȯ
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
