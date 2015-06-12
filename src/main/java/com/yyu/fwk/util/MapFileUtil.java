package com.yyu.fwk.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 将Map保存到文件中的工具类
 * 
 * @author liugang
 * 
 */
public class MapFileUtil {

    private static Log log = LogFactory.getLog(MapFileUtil.class);

    public final static String DEFAULT_ENCODING = "gbk";

    public static void writeToFile(List<String> header, List<Map<String, String>> data, String filename, boolean append) {
        BufferedWriter writer = generateWriterForFile(filename, append);
        writer = writeToFileWithReturnedWriter(header, data, writer, append);
        try {
            writer.close();
        } catch (IOException e) {
            log.error("cannot close the BufferedWriter", e);
        }
    }
    
    public static BufferedWriter generateWriterForFile(String filename, boolean append){
        BufferedWriter writer = null;
        try{
            File file = new File(filename);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, append), DEFAULT_ENCODING));
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return writer;
    }
    
    /**
     * if you invoke this method, you HAVE TO close the returned writer by yourself.
     * @author yuyang
     * @param header
     * @param data
     * @param filename
     * @param append
     * @return BufferedWriter
     */
    public static BufferedWriter writeToFileWithANewReturnedWriter(List<String> header, List<Map<String, String>> data, String filename, boolean append) {
        BufferedWriter writer = generateWriterForFile(filename, append);
        return writeToFileWithReturnedWriter(header, data, writer, append);
    }
    
    /**
     * if you invoke this method, you HAVE TO close the returned writer by yourself.
     * @author yuyang
     * @param header
     * @param data
     * @param filename
     * @param append
     * @return BufferedWriter
     */
    public static BufferedWriter writeToFileWithReturnedWriter(List<String> header, List<Map<String, String>> data, BufferedWriter writer, boolean append) {
        try {
            if (!append) {
                // 写入头
                for (int i = 0; i < header.size(); i++) {
                    writer.write(header.get(i));
                    if (i < header.size() - 1)
                        writer.write('\t');
                    else writer.write('\n');
                }
            }

            // 写入数据
            for (int i = 0; i < data.size(); i++) {
                Map<String, String> d = data.get(i);
                for (int j = 0; j < header.size(); j++) {
                    String value = d.get(header.get(j));
                    if (value == null || "".equals(value.trim()))
                        value = "-";

                    value = value.replace("\t", "").trim();

                    writer.write(value);
                    if (j < header.size() - 1)
                        writer.write('\t');
                    else writer.write('\n');
                }
            }

            writer.flush();
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return writer;
    }
}
