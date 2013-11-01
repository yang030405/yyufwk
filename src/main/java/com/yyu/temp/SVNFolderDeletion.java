package com.yyu.temp;
import java.io.File;

import com.yyu.fwk.util.FileUtil;


public class SVNFolderDeletion {
	public static void main(String[] args) {
		String path = "/Users/apple/Desktop/WebContent";
		deleteSVNFolder(new File(path));
		System.out.println("done");
	}
	
	public static void deleteSVNFolder(File folder){
		File[] files = folder.listFiles();
		if(files.length > 0){
			for(File file : files){
				if(file.isDirectory() && file.getPath().endsWith(".svn")){
					FileUtil.deleteFolder(file);
				}else if(file.isDirectory()){
					deleteSVNFolder(file);
				}
			}
		}
	}
}
