package com.yyu.fwk.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class CSVUtil {
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
}
