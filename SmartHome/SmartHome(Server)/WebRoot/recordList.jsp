<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript">
  		function checkAll(allC){
  			var otherCs = document.getElementsByName("delId");
  			for(var i=0;i<otherCs.length;i++){
  				otherCs[i].checked = allC.checked;
  			}
  		}
  	</script>
  </head>
  <body style="text-align: center;">
  	<h1>异常事件记录</h1><hr>
  	<form action="${pageContext.request.contextPath }/servlet/FindRecByDateServlet" method="POST">
  		请输入日期:<input type="text" name="from" value="${param.from }"/>
  		至<input type="text" name="to" value="${param.to }"/>	
		<input type="submit" value="查询记录 "/>
  	</form>
  	<form action="${pageContext.request.contextPath}/servlet/BatchDelServlet" method="POST">
  	<table border="1" width="100%">
  		<tr>
  			<th><input type="checkbox" onclick="checkAll(this)"/>全选</th>
  			<th>异常事件</th>
  			<th>发生时间</th>
  			<th>删除</th>
  		</tr>
  		<c:forEach items="${requestScope.list}" var="rec">
	  		<tr>
	  			<td><input type="checkbox" name="delId" value="${rec.id }" /></td>
	  			<td><c:out value="${rec.event }"/></td>
	  			<td><c:out value="${rec.time }"/></td>
	  			<td><a href="${pageContext.request.contextPath }/servlet/DelRecServlet?id=${rec.id }">删除</a></td>
	  		</tr>
  		</c:forEach>
  	</table>
  	<input type="submit" value="批量删除"/>
  	<a href="${pageContext.request.contextPath }/index.jsp">返回主页</a>
  	</form>
  </body>
</html>
