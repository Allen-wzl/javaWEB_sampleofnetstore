<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>后台管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"></script>
  </head>
  
  <body>

<h1>管理员登录：</h1>
<form action="${pageContext.request.contextPath }/privilege/PrivilegeServlet" method="post">
	<table border="1" align="center" width="438">
		<tr>
			<td>用户名:</td>
			<td>
			 <input type="text" name="username" />
			</td>
		</tr>
		<tr>
			<td>密码:</td>
			<td>
			<input type="password" name="password" />
			</td>
		</tr>
		<tr>
	       <td colspan="2">
	       <input type="submit" value="登录" />
	       </td>
	       
	     </tr>


	</table>


</form>
</body>
</html>
