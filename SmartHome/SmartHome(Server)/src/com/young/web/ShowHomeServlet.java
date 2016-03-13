package com.young.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ShowHomeServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		ServletContext context = this.getServletContext();
		// int temperature = 30;
		// out.write("�¶ȣ�" + temperature + "�� " + " ");
		// if (temperature >= 50) {
		// out.write("<font color='red'>" + "�¶ȹ��" + "</font>");
		// }
		if (context.getAttribute("temperature") != null) {
			double temperature = (Double) context.getAttribute("temperature");
			out.write("温度：" + temperature + "℃ " + "<br>");
			if (temperature >= 30.0) {
				out.write("<font size='20' color='red'>" + "温度过高"
						+ "</font><br>");
			}
		}
		if (context.getAttribute("shake") != null) {
			out.write("<font size='20' color='red'>" + "地震" + "</font>");
		}
		// if (!("".equals(temperature))) {
		//
		// if (temperature >= 50.0) {
		// out.write("<font color='red'>" + "�¶ȹ��" + "</font>");
		// }
		// }
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
