package com.young.filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrivilegeFilter implements Filter {
	private List<String> uri_list = new ArrayList<String>();

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String uri = req.getRequestURI();
		// System.out.println(uri);
		// chain.doFilter(request, response);
		if (uri_list.contains(uri)) {
			// 说明当前资源需要权限
			if (req.getSession(false) == null
					|| req.getSession().getAttribute("user") == "null") {
				resp.getWriter().write("当前资源需要权限,请先登录!");
				return;
			} else {
				chain.doFilter(request, response);
			}
		} else {
			// 不需要权限
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ServletContext context = filterConfig.getServletContext();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					context.getRealPath("WEB-INF/Privilege.txt")));
			String line = null;
			while ((line = reader.readLine()) != null) {
				uri_list.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
