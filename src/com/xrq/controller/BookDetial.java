package com.xrq.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xrq.model.*; 

/**
 * 实现书籍详细信息页的显示
 */
@WebServlet("/BookDetial")
public class BookDetial extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BookDetial() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = (String)request.getParameter("id");
		DataBean dat1=null;
		if(id!=null)  //if a specific book was clicked, a id will be post to this servlet
		{
			 dat1 = new DBOperation().getdata(id);
			 request.setAttribute("dat1", dat1);
			 request.getRequestDispatcher("detail.jsp").forward(request, response);
		}	
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
