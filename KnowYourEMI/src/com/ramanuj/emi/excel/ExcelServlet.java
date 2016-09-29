
package com.ramanuj.emi.excel;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class ExcelServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Double prin = Double.parseDouble(req.getParameter("prin"));		
		Double rate =Double.parseDouble(req.getParameter("rate"));
		Integer month =Integer.parseInt(req.getParameter("month"));
		EMIExcelProcessor e = new EMIExcelProcessor();
		ArrayList<ArrayList<String>> table = e.getpayment(prin, rate, month);
		ProcessExcelReport per = new ProcessExcelReport();
		String json = per.getExcelPath(table);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		System.out.println(json);
		resp.getWriter().write(json);
	}
	
}