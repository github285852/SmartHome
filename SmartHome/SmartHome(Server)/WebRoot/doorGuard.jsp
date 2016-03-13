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
  	<h1>门禁记录</h1><hr>
  	<form action="${pageContext.request.contextPath }/servlet/FindDoorByDateServlet" method="POST">
  		请输入日期:<input type="text" name="from" value="${param.from }"/>
  		至<input type="text" name="to" value="${param.to }"/>	
		<input type="submit" value="查询记录 "/>
  	</form>
  	<form action="${pageContext.request.contextPath}/servlet/BatchDelDoorServlet" method="POST">
	  	<table border="1" width="100%">
	  		<tr>
	  			<th><input type="checkbox" onclick="checkAll(this)"/>全选</th>
	  			<th>卡号</th>
	  			<th>访问时间</th>
	  			<th>开门情况</th>
	  			<th>是否同意?</th>
	  			<th>删除记录</th>
	  		</tr>
	  		<c:forEach items="${requestScope.list}" var="door">
		  		<tr>
		  			<td><input type="checkbox" name="delId" value="${door.id }" /></td>
		  			<td><c:out value="${door.card_id }"/></td>
		  			<td><c:out value="${door.time }"/></td>
		  			<c:if test="${door.openstate == 0 }">
		  				<td><font color="red">未开门</font></td>
		  			</c:if>
		  			<c:if test="${door.openstate != 0 }">
		  				<td><font color="blue">已开门</font></td>
		  			</c:if>
		  			<td>
		  				<c:if test="${door.agreestate == 0 }">
			  				<a href="${pageContext.request.contextPath }/servlet/AgreeToOpenServlet?id=${door.id }">同意</a>/
			  				<a href="${pageContext.request.contextPath }/servlet/IgnoreServlet?id=${door.id }">忽略</a>
		  				</c:if>
		  				<c:if test="${door.agreestate != 0}">
		  					<c:if test="${door.agreestate == 1 }">
		  						<font color="red">未同意</font>
		  					</c:if>
			  				<c:if test="${door.agreestate == 2 }">
		  						<font color="blue">已同意</font>
		  					</c:if>
		  				</c:if>
		  			</td>
		  			<td><a href="${pageContext.request.contextPath }/servlet/DelDoorServlet?id=${door.id }">删除</a></td>
		  		</tr>
	  		</c:forEach>
	  	</table>
  		<input type="submit" value="批量删除"/>
  		<a href="${pageContext.request.contextPath }/index.jsp">返回主页</a>
  	</form>
  </body>
</html>
