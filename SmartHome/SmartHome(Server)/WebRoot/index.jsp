<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
  </head>
  <body align="center">
	<h1>智能家居平台</h1><hr>
	<c:if test="${sessionScope.user==null}">
		欢迎来到智能家居管理平台!
		<a href="${pageContext.request.contextPath }/login.jsp">请先登录</a>
	</c:if>
	<c:if test="${sessionScope.user!=null}">
		欢迎回来!${sessionScope.user.username }!<br>
		<a href="${pageContext.request.contextPath }/servlet/ShowHomeServlet">查看监控情况</a><br>
		<a href="${pageContext.request.contextPath }/servlet/RecListServlet">查看异常日志</a><br>
		<a href="${pageContext.request.contextPath }/servlet/DoorGuardServlet">查看门禁</a><br>
	  	<a href="${pageContext.request.contextPath }/servlet/LogoutServlet">注销</a><br>
	</c:if>
  </body>
</html>

