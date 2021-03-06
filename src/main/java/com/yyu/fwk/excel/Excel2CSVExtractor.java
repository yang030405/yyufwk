package com.yyu.fwk.excel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yyu.fwk.excel.exception.UnsupportExcelVersionException;
import com.yyu.fwk.util.BeanUtil;
import com.yyu.fwk.util.CSVUtil;
import com.yyu.fwk.util.FileUtil;

public class Excel2CSVExtractor {

	private String excelFilePath;
	
	public Excel2CSVExtractor(String excelFilePath){
		if(BeanUtil.isBlank(excelFilePath)){
			throw new NullPointerException("the file path of the Excel can not be null.");
		}
		this.excelFilePath = excelFilePath;
	}
	
	/**
	 * the excel will be converted to a folder with the excel file's name, fills in with all CSV files
	 * those converted from the sheets in excel file.
	 */
	public List<String> extract() throws Exception, UnsupportExcelVersionException {
		ExcelVersion excelVersion = getExcelVersion(excelFilePath);
		if(ExcelVersion.UnsupportedVersion == excelVersion){
			throw new UnsupportExcelVersionException("file[" + excelFilePath + "] is not a supported excel file.");
		}
		List<String> csvPathes = new ArrayList<String>();
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(excelFilePath)));
		
		Workbook wb;
		if(ExcelVersion.Excel2003 == excelVersion){
			wb = new HSSFWorkbook(in);
		}else{
			wb = new XSSFWorkbook(in);
		}
		createFolder(excelFilePath);
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			Sheet sheet = wb.getSheetAt(sheetIndex);
			String sheetName = sheet.getSheetName();
			if(BeanUtil.isBlank(sheetName)){
				continue;
			}
			String filename = processCSVFilePath(excelFilePath, sheetName);
			List<String> titles = getSheetTitles(sheet);
			List<String[]> values = getSheetValues(sheet);
			CSVUtil.writeToCSV(filename, titles, values);
			csvPathes.add(filename);
		}
		in.close();
		return csvPathes;
	}
	
	private void createFolder(String excelFilePath){
		String folderName = excelFilePath.replaceAll(".xlsx", "").replaceAll(".xls", "");
		File csvFolder = new File(folderName);
		if(csvFolder.exists()){
			FileUtil.deleteFolder(csvFolder);
		}
		if(!csvFolder.exists()){
			csvFolder.mkdirs();
		}
	}
	
	private String processCSVFilePath(String excelFilePath, String sheetName){
		String folderName = excelFilePath.replaceAll(".xlsx", "").replaceAll(".xls", "");
		return folderName + "/" + sheetName + ".csv";
	}
	
	private List<String> getSheetTitles(Sheet sheet){
		List<String> titles = new ArrayList<String>();
		Row row = sheet.getRow(0);
		if (BeanUtil.isBlank(row)) {
			return titles;
		}
		
		for (int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
			Cell cell = row.getCell(columnIndex);
			if (cell != null) {
				// no matter what type of the cell is, we get its string value as title.
				String title = cell.getStringCellValue();
				titles.add(title);
			}
		}
		return titles;
	}
	
	private List<String[]> getSheetValues(Sheet sheet){
		List<String[]> listValues = new ArrayList<String[]>();
		String sheetName = sheet.getSheetName();
		if(BeanUtil.isBlank(sheet) || BeanUtil.isBlank(sheetName)){
			return listValues;
		}
		
		//ignore line number 0, because it is a line for title
		for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			if (row == null) {
				continue;
			}
			int columnSize = row.getLastCellNum() + 1;
			String[] values = new String[columnSize];
			Arrays.fill(values, "");
			boolean hasValue = false;
			for (int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
				String value = getValueOfCell(row.getCell(columnIndex));
				values[columnIndex] = rightTrim(value);
				hasValue = true;
			}
			if (hasValue) {
				listValues.add(values);
			}
		}
		return listValues;
	}
	
	private String getValueOfCell(Cell cell){
		String value = "";
		if(BeanUtil.isBlank(cell)){
			return value;
		}

		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				value = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					if (date != null) {
						value = new SimpleDateFormat("yyyy-MM-dd").format(date);
					}
					else {
						value = "";
					}
				}
				else {
					value = new DecimalFormat("0").format(cell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_FORMULA:
				// if the value is generated by a formula, we set it to null.
				if (!cell.getStringCellValue().equals("")) {
					value = cell.getStringCellValue();
				}
				else {
					value = cell.getNumericCellValue() + "";
				}
				break;
			case Cell.CELL_TYPE_BLANK:
				break;
			case Cell.CELL_TYPE_ERROR:
				value = "";
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				value = (cell.getBooleanCellValue() == true ? "Y" : "N");
				break;
			default:
				value = "";
		}
		return value;
	}
	
	private String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		str=str.replaceAll("\r","").replaceAll("\r\n","").replaceAll("\n","").replaceAll("\\s+","");
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}
	
	private enum ExcelVersion{
		Excel2003, Excel2007, UnsupportedVersion
	}
	
	private ExcelVersion getExcelVersion(String excelFilePath){
		if(excelFilePath.matches("^.+(xls)$")){
			return ExcelVersion.Excel2003;
		}else if(excelFilePath.matches("^.+(xlsx)$")){
			return ExcelVersion.Excel2007;
		}else{
			return ExcelVersion.UnsupportedVersion;
		}
	}
}
