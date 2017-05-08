package com.wzl.filter;
//只拦截后台资源的访问
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wzl.domain.Function;
import com.wzl.domain.Role;
import com.wzl.domain.User;
import com.wzl.service.BusinessService;
import com.wzl.service.impl.BusinessServiceImpl;

public class PrivilegeFilter implements Filter {
	private BusinessService s = new BusinessServiceImpl();
	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request;
		HttpServletResponse response;
		
		try{
			request =(HttpServletRequest)req;
			response = (HttpServletResponse)resp;
			
		}catch(Exception e){
			 throw new RuntimeException("non-HTTP request  or response");
		}
		
		
		//检测用户有没有登录
		 HttpSession session = request.getSession();
		 User user=  (User)session .getAttribute("user");
		//没有登录:转向登录页面
		  if(user==null){
			  request.getRequestDispatcher("/passport/adminLogin.jsp").forward(request, response);
			  return;
		  }
		//登录:
		
		   //把当前用户能访问的功能存起来：Set<Function>
		   Set<Function> funs= new HashSet<Function>();
		   //查询对应的角色
		   List<Role> roles= s.findRolesByUser(user);
		   //遍历角色:查询功能
		    for(Role r:roles){
		   
		  List<Function> functions= s.findFunctionByRole(r);
		  funs.addAll(functions);
		    }
		    //得到当前用户访问的uri地址
		    String uri= request.getRequestURI();
		    String queryString = request.getQueryString();
		    if(queryString!=null){
		    	uri=uri+"?"+queryString;
		    	
		    }
		    uri= uri.replace(request.getContextPath(),"");
		    
		    boolean hasPermission = false;//是否有权限
		   //对比是否在权限范围内
		     for(Function f:funs){
		    	 if(uri.equals(f.getUri())){
		    		 hasPermission=true;
		    		 break;
		    	 }
		     }
		  			//不在:提示没有权限
		     if(!hasPermission){
		    	 response.getWriter().write("您没有权限");
		    	 return;
		     }
		    //放行	
		     chain.doFilter(request,response);
}	    
		  
		  
	

	public void init(FilterConfig arg0) throws ServletException {

	}

}
