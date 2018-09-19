package com.xrq.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xrq.model.DBOperation;
import com.xrq.model.DataBean;
import com.xrq.model.OrderBean;

/**
 * Servlet implementation class Order
 */
@WebServlet("/Order")
public class Order extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Order() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String orderId =request.getParameter("orderId");
		String userName = (String)request.getSession().getAttribute("name");
		DBOperation orderOpera = new DBOperation();
		if(orderId==null)
		{ //�����µĶ�������ʾ
		@SuppressWarnings("unchecked")
		String payment = (String)request.getParameter("payment");
		
		ArrayList<DataBean> al = (ArrayList<DataBean>)request.getSession().getAttribute("cartList");
		
		DataBean userData = orderOpera.getUserData(userName); //ͨ���û�����ȡ�û���Ϣid����ַ��
		orderOpera.generateOrder(userData.getUserId(), payment);  //���ɶ������û�id������id(�Զ�����)���ܽ��
		orderId = orderOpera.getOrderId();  //��ȡ������ 
		for(DataBean i:al)  //��ɶ���ϸ�ڣ� �����š���Ʒ���������������ݿ⣩
		{
			orderOpera.InsertorderDetail(orderId, i.getBookName(), i.getBookAmount());
		}
		
		ArrayList<OrderBean> orderDetail = orderOpera.getOrderDetail(orderId);
		OrderBean orderBase = orderOpera.getOrder(orderId);
		//�������ݴ���������ʾ
		request.setAttribute("orderDetail", orderDetail);
		request.setAttribute("orderBase", orderBase);
		request.setAttribute("delivery", userData);
		request.getRequestDispatcher("displayOrder.jsp").forward(request, response);
		}else
		{	//��ʾ���ж���
			
			DataBean userData = orderOpera.getUserData(userName); //ͨ���û�����ȡ�û���Ϣid����ַ��
			ArrayList<OrderBean> orderDetail = orderOpera.getOrderDetail(orderId);
			OrderBean orderBase = orderOpera.getOrder(orderId);
			request.setAttribute("orderDetail", orderDetail);
			request.setAttribute("orderBase", orderBase);
			request.setAttribute("delivery", userData);
			request.getRequestDispatcher("displayOrder.jsp").forward(request, response);
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
