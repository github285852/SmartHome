package com.young.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.young.factory.BasicFactory;
import com.young.service.DoorService;

@SuppressWarnings("serial")
public class DelDoorServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DoorService service = BasicFactory.getFactory().getService(
				DoorService.class);
		// 1.获取要删除的记录id
		String id = request.getParameter("id");
		// 2.调用Servcie中根据id删除记录的方法
		service.delDoorByID(id);
		// 3.请求转发到记录列表页面
		request.getRequestDispatcher("/servlet/DoorGuardServlet").forward(
				request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
