package com.xrq.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xrq.model.DBOperation;

/**
 * Servlet implementation class UserVerify
 */
@WebServlet("/UserVerify")
public class UserVerify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     * 
     */
    public UserVerify() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String sessionFlag = (String)request.getSession().getAttribute("login");
		 String jumpFlag =(String)request.getParameter("jumpFlag");  //������¼ҳ��½
		 String payFlag = (String)request.getParameter("payment");  //����ҳ��������
		//sessionflag���ѵ�½��session����jumpflag���������˺�������ת����
		 if(sessionFlag!=null) 
		 {
			 if(payFlag==null)
				 //session����ɵ�½�ģ��Ҳ�����֧��ҳ�����
				 request.getRequestDispatcher("loginResult.jsp").forward(request, response);
			 else 
				 //֧��ҳ����ĵ�½
				 request.getRequestDispatcher("pay.jsp").forward(request, response);
				  
		}else if(jumpFlag!=null || payFlag!=null){
			 //����ҳ��ֱ�ӵ�¼ҳ�����½����
			 //��֤������˺�����
				 String user = (String)request.getParameter("userName");
				 String password = (String)request.getParameter("userPassword");
				 String[] login=null;  //login[0]--password ,login[1]---type
				login = new DBOperation().UserVerify(user);
				if (login[0].equals(password))   //password correct
				 {
					 HttpSession se = request.getSession();
					 se.setMaxInactiveInterval(60);
					 se.setAttribute("login", "true");
					 se.setAttribute("name", user);
					 se.setAttribute("type", login[1]);
					 if(payFlag==null)
						 request.getRequestDispatcher("loginResult.jsp").forward(request, response);
					 else
						 request.getRequestDispatcher("pay.jsp").forward(request, response);
				
				 }else
				 {  //password error
					response.sendRedirect("errorPage.jsp");
				 }
		 }else {
			 //֮ǰδ��¼���Ҵ�����������
			 //ת���¼ҳ
			 request.getRequestDispatcher("login.jsp").forward(request, response);	
		
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
