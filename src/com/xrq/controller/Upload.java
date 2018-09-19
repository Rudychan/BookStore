package com.xrq.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import com.xrq.model.DBOperation;
import com.xrq.model.DataBean;

/**
 * Servlet implementation class Upload
 */
@WebServlet("/Upload")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Upload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DataBean dat = new DataBean();
		
		//����һ������������
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024*1024); //��������С
		factory.setRepository(new File("D:\\YOHO\\"));  //�ļ�����Ŀ¼
		//�ļ��ϴ�������
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(1024*1024 );   //�ϴ��������ֵ��Ĭ��1M
		upload.setFileSizeMax(1024*1024);   //�����ļ����ֵ
		upload.setHeaderEncoding("utf-8");
		
		try {
			//�ѱ��������request����һ��list����
			List<FileItem> itemList = upload.parseRequest(new ServletRequestContext(request));
			for(FileItem item:itemList)
			{
				//true��class�����ļ��������������ַ�������Ҫ���ļ���һ���request�ֿ�
				if(item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("utf-8");
					System.out.println("��ͨ������ֵ�� "+name +" --> "+value);
					if(name.equals("bookId"))  dat.setId(Integer.parseInt(value));
					if(name.equals("bookName")) dat.setBookName(value);
					if(name.equals("bookPrice")) dat.setBookPrice(value);
					if(name.equals("bookAmount")) dat.setBookAmount(value);
					if(name.equals("bookPublisher")) dat.setBookPublisher(value);
					if(name.equals("bookType")) dat.setBookType(value);
					if(name.equals("bookIntro")) dat.setBookIntro(value);
					
				}else
					//��request���ļ�ʱ��������Ƭ��
				{
					String fileName = item.getName();  //�ļ���
					System.out.println("�ļ��� " +fileName); 
					dat.setBookCover(fileName);
					//���ر���ǩname���Ե�ֵ
				/*	String namede=item.getFieldName();
					System.out.println(namede);*/
					
					InputStream is = item.getInputStream();  //��������ʽ�����ļ���������
					FileOutputStream fos = new FileOutputStream("E:\\eclipse_javaee_workspace\\BookStore\\WebContent\\images\\"+fileName);
					byte[] buff = new byte[1024];
					int len=0;
					while((len=is.read(buff))>0)
					{
						fos.write(buff); 
					}
						fos.close();
					}
				
			}
	    	new DBOperation().insert(dat);
			request.getRequestDispatcher("index.jsp").forward(request, response);
			 
			
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
