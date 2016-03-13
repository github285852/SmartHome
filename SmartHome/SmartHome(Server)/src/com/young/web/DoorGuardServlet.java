package com.young.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.young.domain.DoorGuard;
import com.young.domain.User;
import com.young.factory.BasicFactory;
import com.young.service.DoorService;

@SuppressWarnings("serial")
public class DoorGuardServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DoorService service = BasicFactory.getFactory().getService(
				DoorService.class);
		// 1.获取当前用户的id
		User user = (User) request.getSession().getAttribute("user");
		int id = user.getId();
		// 2.根据用户id调用service查询方法
		List<DoorGuard> list = service.getAllDoorByUserId(id);
		// 3.存进request域中到页面显示
		request.setAttribute("list", list);
		request.getRequestDispatcher("/doorGuard.jsp").forward(request,
				response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
