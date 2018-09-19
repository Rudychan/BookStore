package com.xrq.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
		String addToCart = request.getParameter("detailJump");
		String minus = request.getParameter("minus");
		HttpSession se2 = request.getSession();
		HttpSession se1 = request.getSession();

		Cart c1 = (Cart)request.getSession().getAttribute("cart");
		if(c1==null)  //session���ڣ����ߵ�һ�Ρ�ÿ�ζ���session����ض��Ĺ��ﳵ
		{//	û�о��½�һ�����ﳵ
			System.out.println("�״ν����ﳵ��session����");
			 c1 = new Cart();
			 se1.setAttribute("cart", c1);
			 se1.setMaxInactiveInterval(60);
		}
		if(minus!=null)
		{
			String num = c1.getNum(bookId);
			if(Integer.parseInt(num)>1)
			{ 
				int tmp = Integer.parseInt(num)-1;
				c1.addItem(bookId,tmp+""); 
			}
			else {
				c1.delItem(bookId);
			}
			String keySet = c1.displayItem();
			ArrayList<DataBean> al = new DBOperation().displayCart(keySet,c1);
			request.setAttribute("cart", al);
			request.getRequestDispatcher("cart.jsp").forward(request, response);
		}
		if(add!=null)  //�����Ʒ�����ﳵ
		{
			String num = c1.getNum(bookId);
			if (num==null) {
				c1.addItem(bookId, "1"); 
			}else
			{
				int tmp = Integer.parseInt(num)+1;
				c1.addItem(bookId,tmp+""); 
				System.out.println("new num: "+tmp);
			}
			if(addToCart!=null)  //������ҳ���빺�ﳵ
				response.sendRedirect("detial?id="+bookId);
			else   //�ڹ��ﳵ����ҳ���Ĺ��ﳵ��Ʒ����
			{	
				String keySet = c1.displayItem();
				ArrayList<DataBean> al = new DBOperation().displayCart(keySet,c1);
				se2.setAttribute("cartList", al);
				se2.setMaxInactiveInterval(60);
				request.getRequestDispatcher("cart.jsp").forward(request, response);
			}
		}
		if(display!=null)  //��ʾ���ﳵҳ��
		{
			String keySet = c1.displayItem();
			ArrayList<DataBean> al = new DBOperation().displayCart(keySet,c1);
	
			se2.setAttribute("cartList", al);
			se2.setMaxInactiveInterval(60);
			request.getRequestDispatcher("cart.jsp").forward(request, response);
		}
		
		if(del!=null)  //ɾ�����ﳵ��ĳ����Ʒ
		{
			c1.delItem(bookId);
			String keySet = c1.displayItem();
			ArrayList<DataBean> al = new DBOperation().displayCart(keySet,c1);
			se2.setAttribute("cartList", al);
			se2.setMaxInactiveInterval(60);
			request.getRequestDispatcher("cart.jsp").forward(request, response);
		}
		
		if(delAll!=null)  //��չ��ﳵ����
		{
			c1.delAll();
			String keySet = c1.displayItem();
			ArrayList<DataBean> al = new DBOperation().displayCart(keySet,c1);
			se2.setAttribute("cartList", al);
			se2.setMaxInactiveInterval(60);
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
