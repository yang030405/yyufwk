package com.yyu.fwk.util;

import org.junit.Test;

import com.yyu.fwk.excel.Excel2CSVExtractor;

public class Excel2CSVTest {

	@Test
	public void testExtractExcel() throws Exception {
//		String excelFilePath = "/Users/apple/Downloads/beforechange.xls";
		String excelFilePath = "/Users/apple/Downloads/beforechange_2.xls";
		Excel2CSVExtractor excel2CSV = new Excel2CSVExtractor(excelFilePath);
		System.out.println(excel2CSV.extract());
		System.out.println("done");
	}
}
