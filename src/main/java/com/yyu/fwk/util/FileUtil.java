package com.yyu.fwk.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import org.apache.commons.io.FileUtils;


public class FileUtil {
	
    /**
     * remove <code>len</code> bytes after <code>pos</code>.
     * @param fileName
     * @param pos bytes after <code>pos</code> would be removed
     * @param len <code>len</code> bytes would be removed
     * @throws IOException
     */
    public static void remove(String fileName, long pos, long len) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
        try {
            if (pos < 0 || pos > raf.length()) {
                throw new RuntimeException("parameter 'skip' invalid.");
            }
            if ((raf.length() - pos) <= len) {
                raf.setLength(pos);
            } else {
                for (long i = pos, j = pos + len; j < raf.length(); i++, j++) {
                    raf.seek(j);
                    byte temp = raf.readByte();
                    raf.seek(i);
                    raf.writeByte(temp);
                }
                raf.setLength(raf.length() - len);
            }
        } finally {
            raf.close();
        }
    }
    
    /**
     * insert <code>b</code> after <code>pos</code>
     * @param fileName
     * @param pos new bytes would be inserted after <code>pos</code>
     * @param b the bytes which would be inserted
     * @throws IOException
     */
    public static void insert(String fileName, long pos, byte[] b) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
        try {
            if (pos < 0 || pos > raf.length()) {
                throw new RuntimeException("parameter 'skip' invalid.");
            }
            if (b == null || b.length == 0) {
                return;
            }
            raf.setLength(raf.length() + b.length);
            for (long i = raf.length() - 1; i > b.length + pos - 1; i--) {
                raf.seek(i - b.length);
                byte temp = raf.readByte();
                raf.seek(i);
                raf.writeByte(temp);
            }
            raf.seek(pos);
            raf.write(b);
        } finally {
            raf.close();
        }
    }
    
    /**
     * replace the exactly bytes after <code>pos</code> by <code>b</code> 
     * @param fileName
     * @param pos the bytes after <code>pos</code> would be replaced
     * @param b the bytes after <code>pos</code> would be replaced with <code>b</code> in same length
     * @throws IOException
     */
    public static void replace(String fileName, long pos, byte[] b) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
        try {
            if (pos < 0 || pos > raf.length()) {
                throw new RuntimeException("parameter 'skip' invalid.");
            }
            if (b == null || b.length == 0) {
                return;
            }
            raf.seek(pos);
            raf.write(b);
        } finally {
            raf.close();
        }
    }
    
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
