package com.ramanuj.utilities;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

public class WorkbookBuilder {

	private SXSSFWorkbook workbook;

	public InputStream mkXlsx(String workbookName, String[] headerString, ArrayList<ArrayList<String>> table) {
		InputStream in=null;
		Path workbookpath = null;
		
		try {
			workbook = new SXSSFWorkbook(100);
			Sheet sheet = workbook.createSheet();
			workbook.setSheetName(0, workbookName);

			/* Header Style- Start */

			XSSFCellStyle headerStyle = (XSSFCellStyle) workbook.createCellStyle();
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

			XSSFCellStyle tableStyle = (XSSFCellStyle) workbook.createCellStyle();
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

			FileSystem fs = Jimfs.newFileSystem(Configuration.unix());
			Path emi = fs.getPath("/emi");
			Files.createDirectory(emi);
			workbookpath = emi.resolve(workbookName + ".xlsx");

			Path filepath = Files.createFile(workbookpath);
			OutputStream out = Files.newOutputStream(filepath, StandardOpenOption.WRITE);
			System.out.println("Folder path " + emi.toString());
			System.out.println(workbookpath.getFileName().toString());

			try {

				workbook.write(out);
				out.close();
				workbook.dispose();
				in = Files.newInputStream(filepath, StandardOpenOption.READ);	
			} catch (FileNotFoundException f) {
				f.printStackTrace();
				System.err.println("Check file path : - Refer file system documents.");
			}

		}
	

		catch(Exception e){
			e.printStackTrace();
		}
		
			System.out.println(workbookpath.toUri().toString());
			return in;
			

}}
