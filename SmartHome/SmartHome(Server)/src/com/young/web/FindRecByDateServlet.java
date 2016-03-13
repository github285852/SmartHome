package com.young.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.young.domain.ExceptionRecord;
import com.young.domain.User;
import com.young.factory.BasicFactory;
import com.young.service.RecordService;

@SuppressWarnings("serial")
public class FindRecByDateServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		RecordService service = BasicFactory.getFactory().getService(
				RecordService.class);
		User user = (User) request.getSession().getAttribute("user");
		int id = user.getId();
		List<ExceptionRecord> list = service.findRecByDateAndUserId(from, to,
				id);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/recordList.jsp").forward(request,
				response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
