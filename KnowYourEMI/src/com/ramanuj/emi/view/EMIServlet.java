
package com.ramanuj.emi.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class EMIServlet extends HttpServlet {

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
		EMIProcessor e = new EMIProcessor();
		String json = e.getpayment(prin, rate, month);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(json);
		
	}
	
}