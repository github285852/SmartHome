package com.young.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.young.util.WebUtils;

public class TestServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (username != null && password != null) {

			// out.write(username + ":" + password);
			System.out.println("”√ªß√˚£∫" + username + "£¨√‹¬Î£∫" + password);

			GsonBuilder bulid = new GsonBuilder();
			Gson gson = bulid.create();
			String str = "I got it";
			String result = gson.toJson(str);
			System.out.println(result);
			WebUtils.writeJson(response, result);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
