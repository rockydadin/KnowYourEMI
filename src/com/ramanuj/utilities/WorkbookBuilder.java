package com.ramanuj.utilities;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class WorkbookBuilder {

	private SXSSFWorkbook workbook;

	public String mkXlsx(String workbookName, String filepath,
			String[] headerString, ArrayList<ArrayList<String>> table) {
		
		File finalfile=null;

		try {
			workbook = new SXSSFWorkbook(100);
			Sheet sheet = workbook.createSheet();
			workbook.setSheetName(0, workbookName);

			/* Header Style- Start */

			XSSFCellStyle headerStyle = (XSSFCellStyle) workbook
					.createCellStyle();
			XSSFFont headerLineFont = (XSSFFont) workbook.createFont();
			/*
			 * below lines to change font
			 * headerLineFont.setFontName("Maiandra GD");
			 */

			headerLineFont.setFontHeight(12);
			XSSFColor headerFontColor = new XSSFColor(Color.WHITE);
			headerLineFont.setColor(headerFontColor);
			headerLineFont.setBold(true);
			headerStyle.setFont(headerLineFont);
			byte[] color = { (byte) 79, (byte) 129, (byte) 189 };
			XSSFColor headerFillColor = new XSSFColor(color);
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerStyle.setFillForegroundColor(headerFillColor);
			headerStyle.setBorderBottom((short) 1);
			headerStyle.setBorderTop((short) 1);
			headerStyle.setBorderRight((short) 1);
			headerStyle.setBorderLeft((short) 1);

			/* Header Style- End */

			/* Table Style- Start */

			XSSFCellStyle tableStyle = (XSSFCellStyle) workbook
					.createCellStyle();
			tableStyle.setBorderBottom((short) 1);
			tableStyle.setBorderTop((short) 1);
			tableStyle.setBorderRight((short) 1);
			tableStyle.setBorderLeft((short) 1);
			tableStyle.setAlignment(HorizontalAlignment.RIGHT);

			/* Table Style- End */

			Row headerRow = sheet.createRow(0);

			for (int cellnum = 0; cellnum < headerString.length; cellnum++) {
				Cell cell = headerRow.createCell(cellnum);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(headerString[cellnum]);

			}

			int rownum = 1;
			ArrayList<String> allLobNames = new ArrayList<String>();
			for (ArrayList<String> sb : table) {
				Row row = sheet.createRow(rownum);

				int cellnum = 0;
				int columnOfLobName = 3;

				int iterationCounter = 0;
				for (String s : sb) {
					Cell cell = row.createCell(cellnum);
					cell.setCellStyle(tableStyle);
					cell.setCellValue(s.toString());
					if (iterationCounter == columnOfLobName) {
						allLobNames.add(s.toString());
					}
					cellnum++;
					iterationCounter++;
				}

				rownum++;
			}

			for (int cellnum = 0; cellnum < headerString.length; cellnum++) {
				sheet.autoSizeColumn(cellnum);
				int currentWidth = sheet.getColumnWidth(cellnum);
				double desiredWidth = (headerString[cellnum].length() + 5) * 256;
				int desiredWidthTruncted = (int) desiredWidth;
				if (currentWidth < desiredWidthTruncted) {
					sheet.setColumnWidth(cellnum, desiredWidthTruncted);
				}

			}

			/*CellRangeAddress cra = new CellRangeAddress(0, rownum - 1, 0,
					headerString.length - 1);
			sheet.setAutoFilter(cra);
			
			String timeNow = DateFormatUtils.format(new Date().getTime(),"dd-MM-yyyy HH-mm-ss");
			timeNow = "_" + timeNow;
			*/

			FileOutputStream out;
			File outputFile = new File(filepath);
			finalfile = new File(outputFile.getAbsolutePath()+File.separator+ workbookName + ".xlsx");
			
			try{
			out = new FileOutputStream(finalfile);
			workbook.write(out);
			out.close();
			workbook.dispose();
			}
			catch(FileNotFoundException f){
				System.err.println("Check file path : - If you are running on windows Server. change file-path in config.properties file.");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return finalfile.getAbsolutePath();

	}

}
