
package com.ramanuj.emi.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
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
		String path2 = "";
		Double prin = Double.parseDouble(req.getParameter("prin"));		
		Double rate =Double.parseDouble(req.getParameter("rate"));
		Integer month =Integer.parseInt(req.getParameter("month"));
		EMIExcelProcessor e = new EMIExcelProcessor();
		ArrayList<ArrayList<String>> table = e.getpayment(prin, rate, month);
		
		
		
		WorkbookBuilder wb = new WorkbookBuilder();
		String headerLine[] = { "Month", "Opening Balance", "Interest Paid", "Principal Paid", "Net Overdue", "EMI",
				"Closing Balance" };
		String filePath = req.getServletPath().toString();
		System.out.println(filePath);
		
		String pattern = Pattern.quote(System.getProperty("file.separator"));
		String[] splittedFileName = filePath.split(pattern);
		String resultFolder = splittedFileName[0];
		
		File resultFolderOnFileSystem = new File(filePath);
		if (!(resultFolderOnFileSystem.exists())) {
			resultFolderOnFileSystem.mkdir();
			System.out.println("Folder Created: " + resultFolder);
		}
		
		Boolean checkExcel = wb.mkXlsx("AmortizationSchedule", filePath, headerLine, table);
		if (checkExcel) {
			path2 = filePath+"/AmortizationSchedule.xlsx";
		} else {
			path2 = "ERROR";
		}
		
		Gson gson = new Gson();
		String jsonotion = gson.toJson(path2);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		System.out.println(jsonotion);
		resp.getWriter().write(jsonotion);
	}
	
}