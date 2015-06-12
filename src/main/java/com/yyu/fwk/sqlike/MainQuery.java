package com.yyu.fwk.sqlike;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class MainQuery {

    static Pattern replaceSpacePattern = Pattern.compile(" ");
    static Pattern selectPattern = Pattern.compile("select(.*)from");
    static Pattern fromPattern = Pattern.compile("from(.*)(?=where)|from(.*)(?=group)|from(.*)(?=having)|from(.*)(?=order)|from(.*)(?=limit)");
    static Pattern wherePattern = Pattern.compile("where(.*)(?=group)|where(.*)(?=having)|where(.*)(?=order)|where(.*)(?=limit)|where(.*)");
    static Pattern groupPattern = Pattern.compile("group(.*)(?=having)|group(.*)(?=order)|group(.*)(?=limit)|group(.*)");
    static Pattern havingPattern = Pattern.compile("having(.*)(?=order)|having(.*)(?=limit)|having(.*)");
    static Pattern orderPattern = Pattern.compile("order(.*)(?=limit)|order(.*)");
    static Pattern limitPattern = Pattern.compile("limit(.*)");
    
    public static void main(String[] args) throws Exception {
        String sql = "select a, \nb,\nc, sum(b),  \rd \t"
            + "from file "
            + "where a = b and b <> c and c > d and ( d < e or e<> f) or f = 'a' \n"
            + "   group a,  b\n "
            + " having sum(b) > 3"
            + "   order    c desc, e asc\r"
            + " limit 4";
        
        String select = getOriginalValueFromSQL(sql, selectPattern);
        String from   = getOriginalValueFromSQL(sql, fromPattern);
        String where  = getOriginalValueFromSQL(sql, wherePattern);
        String group  = getOriginalValueFromSQL(sql, groupPattern);
        String having = getOriginalValueFromSQL(sql, havingPattern);
        String order  = getOriginalValueFromSQL(sql, orderPattern);
        String limit  = getOriginalValueFromSQL(sql, limitPattern);
        
        System.out.println("select: " + select);
        System.out.println("  from: " + from);
        System.out.println(" where: " + where);
        System.out.println(" group: " + group);
        System.out.println("having: " + having);
        System.out.println(" order: " + order);
        System.out.println(" limit: " + limit);
    }
    
    static Pattern replacePattern = Pattern.compile("\n|\r|\t");
    private static String putSQLInOneLine(String sql) {
        return replacePattern.matcher(sql).replaceAll("");
    }
//    value = replaceSpacePattern.matcher(value).replaceAll("").toLowerCase();
    
    private static String getOriginalValueFromSQL(String sql, Pattern pattern) {
        sql = putSQLInOneLine(sql);
        
        String value = null;
        Matcher matcher = pattern.matcher(sql);
        if (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                value = matcher.group(i);
                if (value != null) {
                    break;
                }
            }
        }
        return value;
    }
}
