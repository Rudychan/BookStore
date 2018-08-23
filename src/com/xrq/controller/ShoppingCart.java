package com.xrq.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xrq.model.Cart;
import com.xrq.model.DBOperation;
import com.xrq.model.DataBean;

/**
 * Servlet implementation class ShoppingCart
 */
@WebServlet("/ShoppingCart")
public class ShoppingCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingCart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String bookId = request.getParameter("id");
		String add = request.getParameter("add");
		String display = request.getParameter("display");
		String del = request.getParameter("del");
		String delAll = request.getParameter("delAll");
		String count = request.getParameter("price");
		String num = request.getParameter("num"); 
		int total=0;
		Cart c1 = (Cart)request.getSession().getAttribute("cart");
		if(c1==null)  //session���ڣ����ߵ�һ�Ρ�ÿ�ζ���session����ض��Ĺ��ﳵ
		{
			System.out.println("���ﳵsession����");
			 c1 = new Cart();
			 request.getSession().setAttribute("cart", c1);
			 request.getSession().setMaxInactiveInterval(30);
		}
		if(add!=null)  //�����Ʒ�����ﳵ
		{
			c1.addItem(bookId, "1"); 
			System.out.println("bookId: "+bookId);
			response.sendRedirect("index.jsp");
		}
		if(display!=null)  //��ʾ���ﳵҳ��
		{
			String keySet = c1.displayItem();
			ArrayList<DataBean> al = new DBOperation().displayCart(keySet);
			request.setAttribute("cart", al);
			request.getRequestDispatcher("cart.jsp").forward(request, response);
		}
		
		if(del!=null)  //ɾ�����ﳵ��ĳ����Ʒ
		{
			c1.delItem(bookId);
			String keySet = c1.displayItem();
			ArrayList<DataBean> al = new DBOperation().displayCart(keySet);
			request.setAttribute("cart", al);
			request.getRequestDispatcher("cart.jsp").forward(request, response);
		}
		
		if(delAll!=null)  //��չ��ﳵ����
		{
			c1.delAll();
			String keySet = c1.displayItem();
			ArrayList<DataBean> al = new DBOperation().displayCart(keySet);
			request.setAttribute("cart", al);
			request.getRequestDispatcher("cart.jsp").forward(request, response);
		}
		if(count!=null) //��Ʒ�������������۸�
		{
			int price = Integer.parseInt(count);
			int number = Integer.parseInt(num);
			if(number>1)
				total = price*(number-1);
			request.setAttribute("total", total+"");
			String keySet = c1.displayItem();
			ArrayList<DataBean> al = new DBOperation().displayCart(keySet);
			request.setAttribute("cart", al);
			request.getRequestDispatcher("cart.jsp").forward(request, response);
			
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
