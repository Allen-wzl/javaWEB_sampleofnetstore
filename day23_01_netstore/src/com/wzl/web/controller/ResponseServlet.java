package com.wzl.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wzl.domain.Order;
import com.wzl.service.BusinessService;
import com.wzl.service.impl.BusinessServiceImpl;
import com.wzl.util.PaymentUtil;
//接受相应数据，处理订单；
public class ResponseServlet extends HttpServlet {
	private BusinessService s= new BusinessServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			String p1_MerId= request.getParameter("p1_MerId");
			String  r0_Cmd= request.getParameter("r0_Cmd");
			String  r1_Code= request.getParameter("r1_Code");//支付结果 1.成功 
			String  r2_TrxId= request.getParameter("r2_TrxId");
			String  r3_Amt= request.getParameter("r3_Amt");
			String  r4_Cur= request.getParameter("r4_Cur");
			String  r5_Pid= request.getParameter("r5_Pid");
			String  r6_Order= request.getParameter("r6_Order");//订单号
			String  r7_Uid= request.getParameter("r7_Uid");
			String  r8_MP= request.getParameter("r8_MP");
			String  r9_BType= request.getParameter("r9_BType");//1.重定向 2.点对点
			String  hmac= request.getParameter("hmac");
			//验证数据的正确性
		boolean b= 	PaymentUtil.verifyCallback(hmac, 
					p1_MerId, 
					r0_Cmd, 
					r1_Code, 
					r2_TrxId, 
					r3_Amt, 
					r4_Cur,
					r5_Pid, 
					r6_Order, 
					r7_Uid, 
					r8_MP, 
					r9_BType, 
					"69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
		if(b){
			//数据ok
			 if("1".equals(r1_Code)){
				 //交易成功
				 if("2".equals(r9_BType)){
					 response.getWriter().write("success");
				 }
				 //更改订单状态
				 Order order= s.findOrderByNum(r6_Order);
				 order.setStatus(1);
				 s.changeOrderStatus(order);
				 response.getWriter().write("支付成功！");
			 }else{
				 response.getWriter().write("交易失败，请重新付款");
			 }
		}else{
			
			response.getWriter().write("返回的交易信息有误!");
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

}
