package com.young.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.young.factory.BasicFactory;
import com.young.service.DoorService;

@SuppressWarnings("serial")
public class BatchDelDoorServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DoorService service = BasicFactory.getFactory().getService(
				DoorService.class);
		// 1.获取所有要删除的记录id
		String[] ids = request.getParameterValues("delId");
		// 2.调用Service中批量删除记录的方法
		service.batchDoorDev(ids);
		// 3.转发到记录列表页面
		request.getRequestDispatcher("/servlet/DoorGuardServlet").forward(
				request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
