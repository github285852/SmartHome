package com.young.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.young.domain.User;
import com.young.factory.BasicFactory;
import com.young.service.UserService;
import com.young.util.MD5Utils;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserService service = BasicFactory.getFactory().getService(
				UserService.class);
		// 1.获取用户名密码
		String username = request.getParameter("username");
		String password = MD5Utils.md5(request.getParameter("password"));
		// 2.获取验证码
		String valistr1 = request.getParameter("valistr");
		String valistr2 = (String) request.getSession().getAttribute("valistr");
		// 3.校验验证码
		if (valistr1 == null || valistr2 == null || !valistr1.equals(valistr2)) {
			request.setAttribute("msgofvali",
					"<font color='red'>验证码不正确!</font>");
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return;
		}
		// 4.调用Service中根据用户名密码查找用户的方法
		if ((username != null && password != null)
				|| ("".equals(username) && "".equals(password))) {
			User user = service.getUserByNameAndPsw(username, password);
			if (user == null) {
				// 5.如果不正确则提示
				request.setAttribute("msg", "用户名密码不正确!");
				request.getRequestDispatcher("/login.jsp").forward(request,
						response);
				return;
			} else {
				// 6.登录用户重定向到主页
				request.getSession().setAttribute("user", user);

				if ("ok".equals(request.getParameter("remname"))) {
					// 如果用户勾选过记住用户则发送cookie另浏览器保存用户名
					Cookie remNameC = new Cookie("remname", URLEncoder.encode(
							user.getUsername(), "utf-8"));
					remNameC.setPath(request.getContextPath());
					remNameC.setMaxAge(3600 * 24 * 7);
					response.addCookie(remNameC);
				} else {
					// 如果用户没有勾选记住用户名则删除记住用户名的cookie
					Cookie remNameC = new Cookie("remname", "");
					remNameC.setPath(request.getContextPath());
					remNameC.setMaxAge(0);
					response.addCookie(remNameC);
				}
				// 如果用户勾选过一周内自动登陆,发送自动登录cookie
				if ("true".equals(request.getParameter("autologin"))) {
					Cookie autologinC = new Cookie("autologin",
							URLEncoder.encode(
									user.getUsername() + ":"
											+ user.getPassword(), "utf-8"));
					autologinC.setPath(request.getContextPath());
					autologinC.setMaxAge(3600 * 24 * 7);
					response.addCookie(autologinC);
				}
				// 7.重定向到主页
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
