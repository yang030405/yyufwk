package com.yyu.fwk.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.yyu.fwk.common.MapWriter;


public class FileUtil {
	
	public static void deleteFolder(File folder){
		if(folder.exists() && folder.isDirectory()){
			_deleteFolder(folder);
		}else if(!folder.isDirectory()){
			throw new RuntimeException("path [" + folder.getPath() + "] is not a folder path.");
		}
	}

	private static void _deleteFolder(File folder){
		File[] files = folder.listFiles();
		if(files.length > 0){
			for(File file : files){
				if(file.isFile()){
					file.delete();
				}
				if(file.isDirectory()){
					_deleteFolder(file);
				}
				if(file.isDirectory() && file.listFiles().length == 0){
					file.delete();
				}
			}
		}
		if(folder.listFiles().length == 0){
			folder.delete();
		}
	}
	
	public static void writeToCSV(String filename, List<String> titles, List<String[]> listValues) {
		try {
			File file = new File(filename);
			if (!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			FileOutputStream out = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(out, "gbk");
			BufferedWriter bw = new BufferedWriter(osw);
			String strKey = "";
			for (String keyName : titles) {
				if (strKey == "") {
					strKey = keyName;
				} else {
					strKey = strKey + "\t" + keyName;
				}
			}
			bw.write(strKey + "\r\n");// this is head

			for (String[] resultString : listValues) {
				String wriValue = "";
				for (String strValue : resultString) {
					if (wriValue == "") {
						wriValue = strValue;
					} else {
						wriValue = wriValue + "\t" + strValue;
					}
				}
				bw.write(wriValue + "\r\n");
			}
			bw.close();
			osw.close();
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static void copyFile(String sourceFilePath, String targetFilePath) throws IOException {
		File targetFile = new File(targetFilePath);
		if (targetFile.getParentFile() != null && !targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}
		File sourceFile = new File(sourceFilePath);
		FileUtils.copyFile(sourceFile, targetFile);
	}
	
	public static void writeToFile(InputStream inputStream, OutputStream outputStream) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(inputStream);
		BufferedOutputStream bos = new BufferedOutputStream(outputStream);
		try {
			int i = 0;
			while ((i = bis.read()) != -1) {
				bos.write(i);
			}
		}
		finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}
		}
	}
	
	public static String generateFilePathWithUUID(){
		String dateStr = DateFormatUtils.format(new Date(), "yyyy-MM-dd");		
		String newFilePath = "/" + dateStr + "/" + UUID.randomUUID().toString() + ".csv";
		return newFilePath;
	}
	
	/**
	 * 根据指定块大小对文件进行切分
	 * @author yuyang
	 * @param csvFilePath 被拆分的文件路径，该文件必须是一个以 "\t" 作为分隔符的csv文件
	 * @param bitSize 该值为字节长度，且仅仅作为参考，而并非最终切分出来的文件大小
	 * @param comparator 如果不为空，则在拆分时直接对文件进行排序
	 * @return 返回一个数组，其中包含了已经切分好的文件路径
	 */
	public static List<String> splitCSVFile(String csvFilePath, long bitSize, Comparator<Map<String, String>> comparator){
		List<String> splitedFilePaths = new ArrayList<String>();
		File sourceFile = new File(csvFilePath);
		if(BeanUtil.isBlank(csvFilePath) || !sourceFile.exists()){
			return splitedFilePaths;
		}
		String splitToFolder = sourceFile.getParentFile().getPath() + "/";
		
		List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
		CsvFileParser parser = new CsvFileParser(csvFilePath);
		String[] header = parser.getHeader();
		String splitedFileName = splitToFolder + generateFilePathWithUUID();
		splitedFilePaths.add(splitedFileName);
		long fileLength = 0L;
		do{
			if(parser.hasNext()){
				datas.add(parser.next());
				fileLength += parser.lineLength();
			}
			
			if(fileLength >= bitSize || !parser.hasNext()){
				if(comparator != null){
					Collections.sort(datas, comparator);
				}
				MapFileUtil.writeToFile(Arrays.asList(header), datas, splitedFileName, false);
				datas.clear();
				fileLength = 0L;
				splitedFileName = splitToFolder + generateFilePathWithUUID();
				splitedFilePaths.add(splitedFileName);
			}
		}while(parser.hasNext());
		
		return splitedFilePaths;
	}
	
	public static String mergeExternalFiles(List<String> splitedFiles, Comparator<Map<String, String>> comparator){
		//TODO 此处需要增加空值判断
		//TODO 自出需要处理只有一个文件的情况
		
		
		List<Map<String, String>> tempSortHead = new ArrayList<Map<String, String>>();
		Map<Map<String, String>, Integer> minValuePosition = new HashMap<Map<String, String>, Integer>();
		CsvFileParser[] parsers = new CsvFileParser[splitedFiles.size()];
		boolean[] canParserMoveToNext = new boolean[splitedFiles.size()];
		for(int i = 0; i < splitedFiles.size(); i++){
			CsvFileParser parser = new CsvFileParser(splitedFiles.get(i));
			parsers[i] = parser;
			canParserMoveToNext[i] = parser.hasNext();
		}
		MapWriter writeList = new MapWriter("/Users/apple/Desktop/2013-09-22/result.csv", 100000, false);
		Integer minValueParserPosition = -1;
		Map<String, String> minValue = new HashMap<String, String>();
		List<Map<String, String>> sortedResult = new ArrayList<Map<String, String>>();
		while(true){
			for(int i = 0; i < parsers.length; i++){
				if(canParserMoveToNext[i] && parsers[i].hasNext()){
					Map<String, String> data = parsers[i].next();
					tempSortHead.add(data);
					minValuePosition.put(data, i);
					canParserMoveToNext[i] = false;
				}else if(!canParserMoveToNext[i]) {
					Map<String, String> data = parsers[i].getCurrentValue();
					tempSortHead.add(data);
					minValuePosition.put(data, i);
					canParserMoveToNext[i] = parsers[i].hasNext();
				}
			}
			if(tempSortHead.size() == 1){
				while(parsers[minValueParserPosition].hasNext()){
					minValue = parsers[minValueParserPosition].next();
					System.out.println(minValue);
					writeList.write(minValue);
					sortedResult.add(minValue);
				}
				break;
			}
			Collections.sort(tempSortHead, comparator);
			minValue = tempSortHead.remove(0);
			tempSortHead.clear();
			minValueParserPosition = minValuePosition.get(minValue);
			canParserMoveToNext[minValueParserPosition] = true;
			System.out.println(minValue);
			writeList.write(minValue);
			sortedResult.add(minValue);
		}
		writeList.close();
		
		return null;
	}
	
	public static String mergeExternalFiles_V2(List<String> splitedFiles, Comparator<Map<String, String>> comparator){
		//TODO 此处需要增加空值判断
		//TODO 自出需要处理只有一个文件的情况
		
		
		List<Map<String, String>> tempSortHead = new ArrayList<Map<String, String>>();
		CsvFileParser[] parsers = new CsvFileParser[splitedFiles.size()];
		for(int i = 0; i < splitedFiles.size(); i++){
			CsvFileParser parser = new CsvFileParser(splitedFiles.get(i));
			parsers[i] = parser;
		}
		MapWriter writeList = new MapWriter("/Users/apple/Desktop/2013-09-22/result.csv", 100000, false);
		Map<Map<String, String>, Integer> minValuePositionMap = new HashMap<Map<String, String>, Integer>();
		Integer minValueParserPosition = -1;
		Map<String, String> minValue = new HashMap<String, String>();
		boolean firstTime = true;
		while(true){
			
			boolean allEnd = true;
			for(CsvFileParser parser : parsers){
				allEnd = allEnd && !parser.hasNext();
			}
			if(allEnd){
				break;
			}
			
			if(firstTime){
				for(int i = 0; i < parsers.length; i++){
					if(parsers[i].hasNext()){
						Map<String, String> data = parsers[i].next();
						tempSortHead.add(data);
						minValuePositionMap.put(data, i);
					}
				}
				firstTime = false;
			}else{
				if(parsers[minValueParserPosition].hasNext()){
					Map<String, String> data = parsers[minValueParserPosition].next();
					tempSortHead.add(data);
					minValuePositionMap.put(data, minValueParserPosition);
				}else{
					
				}
			}
			Collections.sort(tempSortHead, comparator);
			writeList.write(minValue);
		}
		writeList.close();
		
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		List<String> filePaths = new ArrayList<String>();
		filePaths.add("/Users/apple/Desktop/sort01.txt");
		filePaths.add("/Users/apple/Desktop/sort02.txt");
		filePaths.add("/Users/apple/Desktop/sort03.txt");
		filePaths.add("/Users/apple/Desktop/sort04.txt");
		filePaths.add("/Users/apple/Desktop/sort05.txt");
		mergeExternalFiles_V2(filePaths, new MapSortComparator("num".split(",")));
	}
	
	public static void main1(String[] args) throws Exception {
		List<Integer> all = new ArrayList<Integer>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/apple/Desktop/sort_result.txt"))));
		String line = "";
		while((line = br.readLine()) != null){
			Integer i = Integer.valueOf(line.trim());
			all.add(i);
		}
		Collections.sort(all);
		br.close();
		for(Integer i : all){
			System.out.println(i);
		}
	}
}
