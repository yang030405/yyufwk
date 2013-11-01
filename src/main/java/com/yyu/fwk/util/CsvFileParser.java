package com.yyu.fwk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.csvreader.CsvReader;

public class CsvFileParser implements Iterator<Map<String, String>> {
	
	private String tempFileName;
	
	private static Log log = LogFactory.getLog(CsvFileParser.class);
	
	public static final String EMPTY_TAG = "-";
	
	private String[] header;
	
	private CsvReader csvReader;
	
	private long lineNum = 0L;
	
	private long lineLength = 0L;
	
	private boolean hasNext = false;
	
	private Map<String, String> currentValue;
	
	public String[] getHeader() {
		return header;
	}

	public CsvFileParser(String fileName) {		
		File file = new File(fileName);
		this.tempFileName = fileName;
		try {
			//File file=new File(fileName);
			csvReader = new CsvReader(new FileInputStream(file), ',', Charset.forName("utf-8"));
			
			//初始化头
			csvReader.readHeaders();
			header = csvReader.getHeaders();
			hasNext = csvReader.readRecord();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	@Override
	public boolean hasNext() {		
		return hasNext;
	}

	@Override
	public Map<String, String> next() {
		lineNum++;
		Map<String, String> result = new HashMap<String, String>();
		
		try{
			long tempLineLength = 0;
			String[] dataArray = csvReader.getValues();
			if(dataArray.length != header.length){
				log.warn("lineNum[" + lineNum + "] >> dataArray.length = [" + dataArray.length + "] and header.length = [" + header.length + "] of file[" + this.tempFileName + "]");
			}
			for (int i = 0; i < header.length; i++) {
				if("-".equals(dataArray[i]))
					result.put(header[i], null);
				else{
					result.put(header[i], dataArray[i]);
					tempLineLength += dataArray[i].getBytes().length;
				}
			}
			hasNext = csvReader.readRecord();
			lineLength = tempLineLength;
			currentValue = result;
			return result;
		}catch(Exception e){
			throw new RuntimeException("lineNum[" + lineNum + "] >> an error happen while reading value in file[" + tempFileName + "].", e);
		}
	}

	public Long lineLength(){
		return lineLength;
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException("Remove unsupported on CsvFileParser");
		
	}
	
	public Map<String, String> getCurrentValue(){
		return this.currentValue;
	}
	
	public void close(){
		csvReader.close();
	}

}
