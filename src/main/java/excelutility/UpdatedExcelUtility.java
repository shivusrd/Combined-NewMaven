package excelutility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import baselibrary.Baselibrary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdatedExcelUtility extends Baselibrary {

	public static String getCellData(String filePath, String sheetName, int rowNum, int colNum) {
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = workbook.getSheet(sheetName);
			Row row = sheet.getRow(rowNum);
			Cell cell = row.getCell(colNum);

			if (cell == null) {
				return "";
			}

			switch (cell.getCellType()) {
			case STRING:
				return cell.getStringCellValue();
			case NUMERIC:
				return String.valueOf(cell.getNumericCellValue());
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			default:
				return "";
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void setCellData(String filePath, String sheetName, int rowNum, int colNum, String data) {
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = workbook.getSheet(sheetName);
			Row row = sheet.getRow(rowNum);
			if (row == null) {
				row = sheet.createRow(rowNum);
			}
			Cell cell = row.createCell(colNum);
			cell.setCellValue(data);

			try (FileOutputStream fos = new FileOutputStream(filePath)) {
				workbook.write(fos);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int getRowCount(String filePath, String sheetName) {
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = workbook.getSheet(sheetName);
			return sheet.getLastRowNum() + 1; // +1 to get the actual count
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int getColumnCount(String filePath, String sheetName, int rowNum) {
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = workbook.getSheet(sheetName);
			Row row = sheet.getRow(rowNum);
			if (row != null) {
				return row.getLastCellNum();
			} else {
				return 0;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static List<List<String>> getAllSheetData(String filePath, String sheetName) {
		List<List<String>> data = new ArrayList<>();
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = workbook.getSheet(sheetName);
			int rowCount = sheet.getLastRowNum() + 1;

			for (int i = 0; i < rowCount; i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					List<String> rowData = new ArrayList<>();
					for (int j = 0; j < row.getLastCellNum(); j++) {
						Cell cell = row.getCell(j);
						if (cell == null) {
							rowData.add("");
						} else {
							switch (cell.getCellType()) {
							case STRING:
								rowData.add(cell.getStringCellValue());
								break;
							case NUMERIC:
								rowData.add(String.valueOf(cell.getNumericCellValue()));
								break;
							case BOOLEAN:
								rowData.add(String.valueOf(cell.getBooleanCellValue()));
								break;
							default:
								rowData.add("");
								break;
							}
						}
					}
					data.add(rowData);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

}
