<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.net.URLDecoder"%>
<%@ taglib uri="http://www.younger.com/UserTag" prefix="UserTag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript">
  		function changeImg(img){
  			img.src = img.src+"?time="+new Date().getTime();
  		}
  		function checkForm(){
  			var canSub = true;
  			//非空校验
  			canSub = checkNull("username","用户名不能为空!") && canSub;
  			canSub = checkNull("password","密码不能为空!") && canSub;
  			canSub = checkNull("valistr","验证码不能为空!") && canSub;
  			return canSub;
  		
  		}
  		function checkNull(name,msg){
  			document.getElementById(name+"_msg").innerHTML = "";
  			var objValue = document.getElementsByName(name)[0].value;
  			if(objValue == null || objValue == ""){
				document.getElementById(name+"_msg").innerHTML = "<font color='red'>"+msg+"</font>";
  				return false;
  			}
  			return true;
  		}
  	</script>
  </head>
  <body>
  	<div align="center">
 	<h1>智能监控系统_登录</h1><hr>
 	<font color="red">${msg }${priviError }</font>
 	<form action="${pageContext.request.contextPath }/servlet/LoginServlet" method="POST" onsubmit="return checkForm()">
		<table>
			<tr>
				<td>用户名:</td>
				<td><input type="text" name="username" value="<UserTag:URLDecoder content="${cookie.remname.value }" encode="utf-8"/>"/></td>
				<td id="username_msg">${msgofuser }</td>
			</tr>
			<tr>
				<td>密码:</td>
				<td><input type="password" name="password"/></td>
				<td id="password_msg">${msgofpwd }</td>
			</tr>
			<tr>
  				<td>验证码</td>
  				<td><input type="text" name="valistr" /></td>
  				<td id="valistr_msg">${msgofvali }</td>
  			</tr>
  			<tr>
				<td><input type="submit" value="登录"/></td>
				<td>
					<img id="image" src="${pageContext.request.contextPath }/servlet/ValiImg" onclick="changeImg(this)" />
					<a href="javascript:changeImg(image)" >看不清?换一张</a>
				</td>
			</tr>	
		</table>
		<input type="checkbox" value="ok" name="remname" 
					<c:if test="${cookie.remname!=null }">
						checked="checked" 
					</c:if>
				/>记住用户名
		<input type="checkbox" name="autologin" value="true"/>一周内自动登陆<br>
 	</form>
 	</div>
  </body>
</html>
