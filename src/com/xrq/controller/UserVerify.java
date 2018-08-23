package com.xrq.controller;

import java.io.IOException;
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
		 String loginFlag = (String)request.getSession().getAttribute("login");
		 String jumpFlag =(String)request.getParameter("jumpFlag");  //������¼ҳ��½
		 String payFlag = (String)request.getParameter("payment");  //����ҳ��������
		 String payLogin =(String) request.getParameter("payLogin");  //����ҳ��½��
		 if(loginFlag==null && jumpFlag==null && payFlag==null && payLogin==null) //loginflag���ѵ�½��session����jumpflag���������˺�������ת����
		 {
			 request.getRequestDispatcher("login.jsp").forward(request, response);
		 }
		 else if(loginFlag!=null)
		 {
			 System.out.println("session��¼��½��Ϣ");
			 if(payFlag==null)
				 //session����ɵ�½��
				 request.getRequestDispatcher("loginResult.jsp").forward(request, response);
			 else {
				 request.getRequestDispatcher("pay.jsp").forward(request, response);
			 }
		 }else if(jumpFlag!=null)
		 {
			 //��֤������˺�����
			 String user = (String)request.getParameter("userName");
			 String password = (String)request.getParameter("userPassword");
			 String [] login = new DBOperation().UserVerify(user);
			 if (login[0].equals(password)) 
			 {
				 HttpSession se = request.getSession();
				 se.setMaxInactiveInterval(30);
				 se.setAttribute("login", "true");
				 se.setAttribute("name", user);
				 se.setAttribute("type", login[1]);
				 request.getRequestDispatcher("loginResult.jsp").forward(request, response);
			
			 }else
			 {
				response.sendRedirect("errorPage.jsp");
			 }
		 }
		 else if(payFlag!=null && payLogin==null)  //֮ǰδ��¼���Ҵ�����������
		 {
			 //ת���¼ҳ
			 request.getRequestDispatcher("PayLogin.jsp").forward(request, response);	
		
		 }else if(payLogin!=null)
		 {
			//��֤������˺�����
			 String user = (String)request.getParameter("userName");
			 String password = (String)request.getParameter("userPassword");
			 String [] login = new DBOperation().UserVerify(user);
			 if (login[0].equals(password)) 
			 {
				 HttpSession se = request.getSession();
				 se.setMaxInactiveInterval(30);
				 se.setAttribute("login", "true");
				 se.setAttribute("name", user);
				 se.setAttribute("type", login[1]);
				 request.getRequestDispatcher("pay.jsp").forward(request, response);	 
			 }
			 else
			 {
				 	response.sendRedirect("errorPage.jsp");
			 }
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
