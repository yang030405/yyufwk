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
	
	private static Log log = LogFactory.getLog(CsvFileParser.class);
	
	public static final String EMPTY_TAG = "-";
	
	private CsvReader csvReader;
	
	private String tempFileName;
	private String[] header;
	private long lineNum = 0L;
	private boolean hasNext = false;
	private String charset = "utf-8";
	private char separator = '\t';
	
	public CsvFileParser(String fileName) {		
		init(new File(fileName));
	}
	
	public CsvFileParser(String fileName, String charset, char separator) {
		this.charset = charset;
		this.separator = separator;
		init(new File(fileName));
	}
	
	private void init(File file){
		this.tempFileName = file.getPath();
		try {
			//File file=new File(fileName);
			csvReader = new CsvReader(new FileInputStream(file), separator, Charset.forName(charset));
			
			//初始化头
			csvReader.readHeaders();
			header = csvReader.getHeaders();
			hasNext = csvReader.readRecord();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	public String[] getHeader() {
		return header;
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
			String[] dataArray = csvReader.getValues();
			if(dataArray.length != header.length){
				log.warn("lineNum[" + lineNum + "] >> dataArray.length = [" + dataArray.length + "] and header.length = [" + header.length + "] of file[" + this.tempFileName + "]");
			}
			for (int i = 0; i < header.length; i++) {
				if(EMPTY_TAG.equals(dataArray[i]))
					result.put(header[i], null);
				else
					result.put(header[i], dataArray[i]);
			}
			hasNext = csvReader.readRecord();
			return result;
		}catch(Exception e){
			throw new RuntimeException("lineNum[" + lineNum + "] >> an error happen while reading value in file[" + tempFileName + "].", e);
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Remove unsupported on CsvFileParser");
		
	}
	
	public void close(){
		csvReader.close();
	}

}
