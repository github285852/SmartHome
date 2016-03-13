package com.young.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.young.domain.ExceptionRecord;
import com.young.factory.BasicFactory;
import com.young.service.RecordService;

@SuppressWarnings("serial")
public class RecListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RecordService service = BasicFactory.getFactory().getService(
				RecordService.class);
		// 1.调用Service中查询所有异常记录的方法,查找到所有异常记录
		List<ExceptionRecord> list = service.getAllRec();
		// 2.将查找到的信息存入request域,请求转发到recordList.jsp页面进行展示
		request.setAttribute("list", list);
		request.getRequestDispatcher("/recordList.jsp").forward(request,
				response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
