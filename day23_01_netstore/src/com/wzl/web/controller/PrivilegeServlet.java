package com.wzl.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wzl.domain.User;
import com.wzl.service.BusinessService;
import com.wzl.service.impl.BusinessServiceImpl;

public class PrivilegeServlet extends HttpServlet {

	private BusinessService s= new BusinessServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 String username= request.getParameter("username");
		 String password= request.getParameter("password");
		 User user=s.login(username,password);
		 if(user==null){
			 response.getWriter().write("错误的用户名或密码,2秒后跳转到登录页面:");
			 response.setHeader("Refresh","2;URL="+request.getContextPath()+"/passport/adminLogin.jsp");
			 return;
		 }
		 request.getSession().setAttribute("user",user);
		 response.sendRedirect(request.getContextPath()+"/manage/index.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

}
