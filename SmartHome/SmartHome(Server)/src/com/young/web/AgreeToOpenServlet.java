package com.young.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.young.factory.BasicFactory;
import com.young.service.DoorService;

@SuppressWarnings("serial")
public class AgreeToOpenServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DoorService service = BasicFactory.getFactory().getService(
				DoorService.class);
		String id = request.getParameter("id");
		service.openDoor(id);
		response.sendRedirect(request.getContextPath()
				+ "/servlet/DoorGuardServlet");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
