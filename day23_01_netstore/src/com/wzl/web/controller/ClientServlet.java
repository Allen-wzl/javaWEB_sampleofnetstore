package com.wzl.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wzl.commons.Page;
import com.wzl.constant.Constants;
import com.wzl.domain.Book;
import com.wzl.domain.Category;
import com.wzl.domain.Customer;
import com.wzl.domain.Order;
import com.wzl.domain.OrderItem;
import com.wzl.service.BusinessService;
import com.wzl.service.impl.BusinessServiceImpl;
import com.wzl.util.IdGenertor;
import com.wzl.util.WebUtil;
import com.wzl.web.beans.Cart;
import com.wzl.web.beans.CartItem;

public class ClientServlet extends HttpServlet {

	private BusinessService s= new BusinessServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		if("showIndex".equals(op)){
			showIndex(request,response);
		}else if("showCategoryBooks".equals(op)){
			showCategoryBooks(request,response);
		}else if("showBookDetail".equals(op)){
			showBookDetail(request,response);
		}else if("buy".equals(op)){
			buy(request,response);
		}else if("changeNum".equals(op)){
			changeNum(request,response);
			
		}else if("delOneItem".equals(op)){
			delOneItem(request,response);
			
		}else if("delAllItems".equals(op)){
			delAllItems(request,response);
		}else if("registCustomer".equals(op)){
			registCustomer(request,response);
			
		}else if("customerLogin".equals(op)){
			customerLogin(request,response);
		}else if("customerLogout".equals(op)){
			customerLogout(request,response);
		}else if("genOrder".equals(op)){
			genOrder(request,response);
		}else if("showCustomerOrders".equals(op)){
			showCustomerOrders(request,response);
		}
	}
	//显示客户订单
	private void showCustomerOrders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException  {
		HttpSession session = request.getSession();
		 Customer c=(Customer) session.getAttribute(Constants.CUSTOEMR_LOGIN_FIAG);
		    //没有登录
		 if(c==null){
			 response.sendRedirect(request.getContextPath()+"/login.jsp");
			 return;
		 }
		//登录:
		 List<Order> os= s.findCustomerOrder(c);
		 request.setAttribute("os",os);
		 request.getRequestDispatcher("/showCustomerOrders.jsp").forward(request, response);
		 
		 
		 
		 
	}
	//生成订单;
	private void genOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//验证用户是否登录：
		HttpSession session = request.getSession();
		 Customer c=(Customer) session.getAttribute(Constants.CUSTOEMR_LOGIN_FIAG);
		    //没有登录
		 if(c==null){
			 response.sendRedirect(request.getContextPath()+"/login.jsp");
			 return;
		 }
		//登录:
		    //取出购物车的信息------Order
			//取出购物项的信息------OrderItem
		 Cart cart=(Cart)session.getAttribute(Constants.HTTPSESSION_CART);
		  if(cart==null){
			  response.getWriter().write("会话超时");
			  return;
		  }
		  Order order= new Order();
		  order.setOrdernum(IdGenertor.genOrdernum());
		  order.setQuantity(cart.getTotalQuantity());
		  order.setMoney(cart.getTotalMoney());
		  order.setCustomer(c);
		  //订单项
		  List<OrderItem> oItems= new ArrayList<OrderItem>();
		  for(Map.Entry<String,CartItem> me:cart.getItems().entrySet()){
			  CartItem cItem= me.getValue();//购物项
			  OrderItem oItem= new OrderItem();
			  oItem.setId(IdGenertor.genGUID());
			  oItem.setBook(cItem.getBook());
			  oItem.setPrice(cItem.getMoney());
			  oItem.setQuantity(cItem.getQuantity());
			  oItems.add(oItem);
			  
		  }
		  //建立订单项和订单的关系
		  order.setItems(oItems);
		//保存订单:跳转到在线支付
		  s.genOrder(order);
		  request.setAttribute("order", order);	
		  request.getRequestDispatcher("/pay.jsp").forward(request, response);
		
	}
	//客户注销
	private void customerLogout(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		request.getSession().removeAttribute(Constants.CUSTOEMR_LOGIN_FIAG);
		response.sendRedirect(request.getContextPath());	
	}
	//客户登录
	private void customerLogin(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		String username= request.getParameter("username");
		String password= request.getParameter("password");
		Customer c=s.customerLogin(username, password);
		request.getSession().setAttribute(Constants.CUSTOEMR_LOGIN_FIAG,c);
		response.sendRedirect(request.getContextPath());	
		
	}
	//用户注册
	private void registCustomer(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Customer c = WebUtil.fillBean(request, Customer.class);
		s.addCustomer(c);
		response.getWriter().write("注册成功,2秒后转向主页");
		response.setHeader("Refresh", "2;URL="+request.getContextPath());
	}
	private void delAllItems(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		request.getSession().removeAttribute(Constants.HTTPSESSION_CART);
		response.sendRedirect(request.getContextPath()+"/showCart.jsp");
		
	}
	//删除一个购物项
	private void delOneItem(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String bookId= request.getParameter("bookId");
		//取出购物车
		Cart cart = (Cart)request.getSession().getAttribute(Constants.HTTPSESSION_CART);
		CartItem item=cart.getItems().get(bookId);
		cart.getItems().remove(bookId);
		
		response.sendRedirect(request.getContextPath()+"/showCart.jsp");
	}
	//修改购物项的数量
	private void changeNum(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String num=request.getParameter("num");
		String bookId= request.getParameter("bookId");
		//取出购物车
		Cart cart = (Cart)request.getSession().getAttribute(Constants.HTTPSESSION_CART);
		CartItem item=cart.getItems().get(bookId);
		item.setQuantity(Integer.parseInt(num));
		response.sendRedirect(request.getContextPath()+"/showCart.jsp");
	}
	//把书籍放入购物车
	private void buy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String bookId=request.getParameter("bookId");
		Book book= s.findBookById(bookId);
		HttpSession session = request.getSession();
		Cart cart=(Cart)session .getAttribute(Constants.HTTPSESSION_CART);
		if(cart==null){
			cart= new Cart();
			session.setAttribute(Constants.HTTPSESSION_CART, cart);
			
		}
		//必定有购物车
		cart.addBook(book);
		 //返回主页
		response.getWriter().write("购买成功,2秒后转向主页");
		response.setHeader("Refresh", "2;URL="+request.getContextPath());
	}
//查看书籍详细内容
   private void showBookDetail(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String bookId=request.getParameter("bookId");
		Book book= s.findBookById(bookId);
		request.setAttribute("book",book);
		request.getRequestDispatcher("/bookDetail.jsp").forward(request,response);
	}
	//按照分类查询分页信息
	private void showCategoryBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//查询所有分类
		List<Category> cs=s.findAllCategories();
		request.setAttribute("cs", cs);
		
		//查询所有书籍：分页
		String num=request.getParameter("num");
		String categoryId = request.getParameter("categoryId");
		Page page=s.findBookPageRecords(num,categoryId);
		 page.setUrl("/client/ClientServlet?op=showCategoryBooks&categoryId="+categoryId);
		 request.setAttribute("page",page);
		 request.getRequestDispatcher("/listBooks.jsp").forward(request, response);
	}

	//展现主页
	private void showIndex(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		//查询所有分类
		List<Category> cs=s.findAllCategories();
		request.setAttribute("cs", cs);
		
		//查询所有书籍：分页
		String num=request.getParameter("num");
		
	  Page page=s.findBookPageRecords(num);
	  page.setUrl("/client/ClientServlet?op=showIndex");
	  request.setAttribute("page",page);
	  request.getRequestDispatcher("/listBooks.jsp").forward(request, response);
		//
		
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

}
