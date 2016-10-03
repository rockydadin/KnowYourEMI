package com.ramanuj.emi.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import com.ramanuj.utilities.WorkbookBuilder;

public class ProcessExcelReport {
	
	private Properties prop;
	private InputStream input;
	
	public ProcessExcelReport(){
		try {
			loadProperties();
		} catch (IOException e) {
			System.err.println("Properties File not found.");
		}
	}
	
	private void loadProperties() throws IOException {
		prop = new Properties();
		input = new FileInputStream("D:\\Users\\NIA\\Documents\\GitHub\\KnowYourEMI\\config.properties");
		prop.load(input);
	}

	public String getExcelPath(ArrayList<ArrayList<String>> table) {

		WorkbookBuilder wb = new WorkbookBuilder();
		String headerLine[] = { "Month", "Opening Balance", "Interest Paid", "Principal Paid", "Net Overdue", "EMI",
				"Closing Balance" };
		String filePath = prop.getProperty("filepath");
		Boolean checkExcel = wb.mkXlsx("AmortizationSchedule", filePath, headerLine, table);
		if (checkExcel) {
			return filePath+"\\AmortizationSchedule.xlsx";
		} else {
			return "ERROR";
		}
	}
}