package com.yyu.fwk.text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import com.yyu.fwk.common.Filter;

public class TikaUtil {
    public static final String LINE_SEPERATOR = System.getProperty("line.separator");
    
    private static final ThreadLocal<Tika> threadTika = new ThreadLocal<Tika>();
    
    public static Tika getTika() {
        Tika tika = threadTika.get();
        if (tika == null) {
            tika = new Tika();
            threadTika.set(tika);
        }
        return tika;
    }
    
    public static void extractTextFromFile(File fromFile, File toText, Filter<String> filter) throws IOException, TikaException {
        Tika t = getTika();
        BufferedReader reader = new BufferedReader(t.parse(fromFile));
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(toText)));
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (filter != null && !filter.filter(line)) {
                writer.write(line + LINE_SEPERATOR);
            }
        }
        reader.close();
        writer.flush();
        writer.close();
    }
    
    public static String getFileMimeType(File file) throws IOException {
        Tika t = getTika();
        return t.detect(file);
    }
}

