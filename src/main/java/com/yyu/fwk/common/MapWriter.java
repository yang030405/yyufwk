package com.yyu.fwk.common;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yyu.fwk.util.BeanUtil;
import com.yyu.fwk.util.CsvFileParser;
import com.yyu.fwk.util.MapFileUtil;

public class MapWriter {
	private static final Log log = LogFactory.getLog(MapWriter.class);
	
	private List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
	private String destFilePath;
	private int pageSize = 1000;
	private boolean append = false;
	private BufferedWriter outputFileWriter;
	private List<String> header;
	
	public MapWriter(String destFilePath, int pageSize, boolean append){
		this.destFilePath = destFilePath;
		this.pageSize = pageSize;
		this.append = append;
		init();
	}
	
	private void init(){
		validate();
		outputFileWriter = MapFileUtil.generateWriterForFile(destFilePath, append);
		if(append){ // here is the first time that invoke 'append'
			CsvFileParser parser = new CsvFileParser(destFilePath);
			if(BeanUtil.isBlank(parser.getHeader())){
				throw new NullPointerException("cannot get title of file " + destFilePath);
			}
			header = Arrays.asList(parser.getHeader());
			parser.close();
		}
	}
	
	private void validate(){
		if(BeanUtil.isBlank(this.destFilePath)){
			throw new NullPointerException("the DestFilePath cannot be blank.");
		}
	}
	
	public void write(Map<String, String> data){
		if(BeanUtil.isBlank(data)){
			return;
		}
		
		if(BeanUtil.isBlank(header)){
			header = Arrays.asList(data.keySet().toArray(new String[]{}));
		}
		
		dataList.add(data);
		if(dataList.size() >= pageSize){
			MapFileUtil.writeToFileWithReturnedWriter(header, dataList, outputFileWriter, append);
			append = true;
			dataList.clear();
		}
	}
	
	public void close(){
		try {
			MapFileUtil.writeToFileWithReturnedWriter(header, dataList, outputFileWriter, append);
			outputFileWriter.close();
		} catch (IOException e) {
			log.error("cannot close the writer of file " + destFilePath, e);
		}
	}
}
