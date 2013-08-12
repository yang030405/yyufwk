package com.yyu.fwk.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.commons.io.FileUtils;

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
}
