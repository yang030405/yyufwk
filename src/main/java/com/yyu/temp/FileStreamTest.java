package com.yyu.temp;

import com.yyu.fwk.util.FileUtil;

public class FileStreamTest {
    
    public static void main(String[] args) throws Exception {
        String filePath = "/Users/yangyu/Desktop/test.txt";
        
        String value = "cc\n";
        
        FileUtil.replace(filePath, 3, value.getBytes());
        
        System.out.println("done");
    }
}