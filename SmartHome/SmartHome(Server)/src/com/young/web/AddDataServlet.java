package com.young.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.young.domain.DoorGuard;
import com.young.domain.ExceptionRecord;
import com.young.factory.BasicFactory;
import com.young.service.DoorService;
import com.young.service.RecordService;

@SuppressWarnings("serial")
public class AddDataServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			RecordService rs = BasicFactory.getFactory().getService(
					RecordService.class);// 处理异常记录的接口
			DoorService ds = BasicFactory.getFactory().getService(
					DoorService.class);// 处理门禁记录的接口

			PrintWriter out = response.getWriter();
			ServletContext context = this.getServletContext();// 获取ServletContext域，此域可以在所有Servlet共享数据
			// 获取Android端http请求的数据
			String card_id = request.getParameter("card_id");
			String temp_str = request.getParameter("temperature");
			String shake = request.getParameter("shake");
			// 在终端打印一下，作测试用。成品不需要此段代码
			System.out.println(card_id);
			System.out.println(temp_str);
			System.out.println(shake);
			out.write("ok");
			if (temp_str != null && !("".equals(temp_str))) {// 如果温度数据不是空的则予以处理
				double temperature = Double.parseDouble(temp_str);
				context.setAttribute("temperature", temperature);// 存入ServletContext域中带到网页上显示
				if (temperature > 30.0) {// 温度过高时存入异常记录
					ExceptionRecord er = new ExceptionRecord();
					er.setEvent("温度过高");
					er.setUser_id(1);
					rs.addRec(er);// 添加异常记录到数据库
				}
			}
			context.setAttribute("shake", shake);
			if (shake != null && !("".equals(shake))) {// 有震动数据时存入异常记录
				ExceptionRecord er = new ExceptionRecord();
				er.setEvent("房子强烈摇晃");
				er.setUser_id(1);
				rs.addRec(er);// 添加异常记录到数据库
			}
			if (card_id != null && !("".equals(card_id))) {// 处理门禁记录
				if (!ds.isRecExisted(card_id)) {
					DoorGuard dg = new DoorGuard();
					dg.setCard_id(card_id);
					dg.setOpenstate(0);// 设置开门状态，开门为1，不开为0
					dg.setAgreestate(0);// 设置同意状态，同意为1，忽略为0
					dg.setTime(new java.sql.Timestamp(System
							.currentTimeMillis()));// 设置访问时间
					dg.setUser_id(1);
					ds.addDoor(dg);// 添加到数据库
				} else {
					ds.updateTime(card_id,
							new java.sql.Timestamp(System.currentTimeMillis()));
					DoorGuard dG = ds.findDoorByCardId(card_id);
					if (dG.getOpenstate() == 1) {// 若用户同意开门，发送开门信号
						out.write("open");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
