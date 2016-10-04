
package com.ramanuj.emi.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ramanuj.utilities.WorkbookBuilder;



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
		WorkbookBuilder wb = new WorkbookBuilder();
		String headerLine[] = { "Month", "Opening Balance", "Interest Paid", "Principal Paid", "Net Overdue", "EMI",
				"Closing Balance" };

		InputStream in = wb.mkXlsx("AmortizationSchedule", headerLine, table);
	
	
		
		resp.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"","AmortizationSchedule.xlsx");
		resp.setHeader(headerKey, headerValue);
		OutputStream outStream = resp.getOutputStream();
		
		byte[] buffer = new byte[4096];
		int bytesRead = -1;
		
		while ((bytesRead = in.read(buffer)) != -1) {
		outStream.write(buffer, 0, bytesRead);
		System.out.println(buffer.length);
		}
		
		in.close();
		outStream.close();
	}
	
	
}