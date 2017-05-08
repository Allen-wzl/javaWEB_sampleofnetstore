<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%@include file="/header.jsp"%>
	
	
	
 	     <img src="${pageContext.request.contextPath}/images/${book.path}/${book.filename}" alt="${book.filename}"/><br/>
 	     ${book }<br/>
 	     <a href="${pageContext.request.contextPath }/client/ClientServlet?op=buy&bookId=${book.id}" >放入购物车</a>
 	     <a href="javascript:window.history.back()" >返回</a>
    	  	    	  
  </body>
</html>
