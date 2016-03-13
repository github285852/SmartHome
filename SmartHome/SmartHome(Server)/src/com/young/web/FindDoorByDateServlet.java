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
public class FindDoorByDateServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		DoorService service = BasicFactory.getFactory().getService(
				DoorService.class);
		User user = (User) request.getSession().getAttribute("user");
		int id = user.getId();
		List<DoorGuard> list = service.findDoorByDateAndUserId(from, to, id);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/doorGuard.jsp").forward(request,
				response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
