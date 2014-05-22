package com.yyu.temp;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import com.yyu.fwk.common.Filter;
import com.yyu.fwk.text.TikaUtil;

public class TextExtractor {
    
    public static void main(String[] args) throws Exception {
        LineSkipingFilter filter = new LineSkipingFilter();
        filter.addSkipLine("各类书籍资料 http://shop58547591.taobao.com/   ");
        filter.addSkipLine("定制电子书--您只需要告诉我们书名 ");
        filter.addSkipLine("最低价格  最优服务   ");
        
//        String pdfPath = "/Users/yangyu/Downloads/Battle Hymn of The Tiger Mother.pdf";
//        String txtPath = "/Users/yangyu/Downloads/Battle Hymn of The Tiger Mother.txt";
//        TikaUtil.extractTextFromFile(new File(pdfPath), new File(txtPath), filter);
        
        String pdfPath = "/Users/yangyu/Downloads/The Art of Deception.pdf";
        String txtPath = "/Users/yangyu/Downloads/The Art of Deception.txt";
        TikaUtil.extractTextFromFile(new File(pdfPath), new File(txtPath), filter);
        System.out.println("done");
    }
}

class LineSkipingFilter implements Filter<String> {

    private Set<String> skipLines = new HashSet<String>();
    
    public void addSkipLine(String skipLine) {
        skipLines.add(skipLine);
    }
    
    @Override
    public boolean filter(String line) {
        return skipLines.contains(line);
    }
}