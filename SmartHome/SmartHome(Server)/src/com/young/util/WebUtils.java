package com.young.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class WebUtils {
	// resonpse返回值
	public static void writeJson(HttpServletResponse response, String result) {
		response.setContentType("application/json;charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.write(result);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
