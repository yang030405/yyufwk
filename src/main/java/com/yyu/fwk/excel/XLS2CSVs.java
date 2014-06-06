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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.yyu.fwk.util.BeanUtil;
import com.yyu.fwk.util.CSVUtil;

public class XLS2CSVs {

	public static List<String> extract(String excelFilePath) throws Exception {
		List<String> csvPathes = new ArrayList<String>();
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(excelFilePath)));
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			HSSFSheet sheet = wb.getSheetAt(sheetIndex);
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
	
	private static String processCSVFilePath(String excelFilePath, String sheetName){
		String folderName = excelFilePath.replaceAll(".xlsx", "").replaceAll(".xls", "");
		File csvFolder = new File(folderName);
		if(csvFolder.exists()){
			csvFolder.mkdirs();
		}
		return folderName + "/" + sheetName + ".csv";
	}
	
	private static List<String> getSheetTitles(HSSFSheet sheet){
		List<String> titles = new ArrayList<String>();
		HSSFRow row = sheet.getRow(0);
		if (BeanUtil.isBlank(row)) {
			return titles;
		}
		
		for (int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
			HSSFCell cell = row.getCell(columnIndex);
			if (cell != null) {
				// no matter what type of the cell is, we get its string value as title.
				String title = cell.getStringCellValue();
				titles.add(title);
			}
		}
		return titles;
	}
	
	private static List<String[]> getSheetValues(HSSFSheet sheet){
		List<String[]> listValues = new ArrayList<String[]>();
		String sheetName = sheet.getSheetName();
		if(BeanUtil.isBlank(sheet) || BeanUtil.isBlank(sheetName)){
			return listValues;
		}
		
		// 第0行为标题，不取
		for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			HSSFRow row = sheet.getRow(rowIndex);
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
	
	private static String getValueOfCell(HSSFCell cell){
		String value = "";
		if(BeanUtil.isBlank(cell)){
			return value;
		}
		
		// 注意：一定要设成这个，否则可能会出现乱码
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				value = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
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
			case HSSFCell.CELL_TYPE_FORMULA:
				// 导入时如果为公式生成的数据则无值
				if (!cell.getStringCellValue().equals("")) {
					value = cell.getStringCellValue();
				}
				else {
					value = cell.getNumericCellValue() + "";
				}
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				value = "";
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				value = (cell.getBooleanCellValue() == true ? "Y" : "N");
				break;
			default:
				value = "";
		}
		return value;
	}
	
	private static String rightTrim(String str) {
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
}
