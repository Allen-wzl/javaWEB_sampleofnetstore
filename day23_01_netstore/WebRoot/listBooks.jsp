<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%@include file="/header.jsp"%>
	<br/>
	<a href="${pageContext.request.contextPath}">所有分类</a>
	<c:forEach  items="${cs }" var="c" >&nbsp;
	 <a href="${pageContext.request.contextPath }/client/ClientServlet?op=showCategoryBooks&categoryId=${c.id}" >${c.name }</a>  
	</c:forEach>
    	<table border="0" width="492"	 align="center" >
    	  	<tr >
    	  	   <c:forEach items="${page.records}"  var="b"> 
    	  	   
    	  	     <td align="center">
    	  	     <img src="${pageContext.request.contextPath}/images/${b.path}/${b.filename}" alt="${b.filename}"/><br/>
    	  	    	 书名:${b.name }<br/>
    	  	    	 作者:${b.author}<br/>
    	  	    	 单价:${b.price }<br/>
    	  	    	 <a href="${pageContext.request.contextPath }/client/ClientServlet?op=showBookDetail&bookId=${b.id}" >去看看</a>
    	  	     
    	  	     </td>
    	  	   
    	  	   </c:forEach>
    	  	</tr>
    	  
    	<tr>
    	   <td colspan="3" align="center">
    	     <%@ include file="/common/page.jsp" %>
    	   </td>
    	   </tr>
    	</table>
  </body>
</html>
