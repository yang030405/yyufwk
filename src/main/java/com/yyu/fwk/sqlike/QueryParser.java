package com.yyu.fwk.sqlike;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParser {
    
    private static Pattern replaceSpacePattern = Pattern.compile(" ");
    private static Pattern selectPattern = Pattern.compile("select(.*)from");
    private static Pattern fromPattern = Pattern.compile("from(.*)(?=where)|from(.*)(?=group)|from(.*)(?=having)|from(.*)(?=order)|from(.*)(?=limit)");
    private static Pattern wherePattern = Pattern.compile("where(.*)(?=group)|where(.*)(?=having)|where(.*)(?=order)|where(.*)(?=limit)|where(.*)");
    private static Pattern groupPattern = Pattern.compile("group(.*)(?=having)|group(.*)(?=order)|group(.*)(?=limit)|group(.*)");
    private static Pattern havingPattern = Pattern.compile("having(.*)(?=order)|having(.*)(?=limit)|having(.*)");
    private static Pattern orderPattern = Pattern.compile("order(.*)(?=limit)|order(.*)");
    private static Pattern limitPattern = Pattern.compile("limit(.*)");
    
    private QueryParser() {}
    
    public static Query parse(String sql) {
        String select = getOriginalValueFromSQL(sql, selectPattern);
        String from   = getOriginalValueFromSQL(sql, fromPattern);
        String where  = getOriginalValueFromSQL(sql, wherePattern);
        String group  = getOriginalValueFromSQL(sql, groupPattern);
        String having = getOriginalValueFromSQL(sql, havingPattern);
        String order  = getOriginalValueFromSQL(sql, orderPattern);
        String limit  = getOriginalValueFromSQL(sql, limitPattern);
        
        Query q = new Query();
        q = appendSelect(q, select);
        
        return new Query();
    }
    
    private static Query appendSelect(Query q, String select) {
        return q;
    }
    
    static Pattern replacePattern = Pattern.compile("\n|\r|\t");
    private static String getOriginalValueFromSQL(String sql, Pattern pattern) {
        sql = replacePattern.matcher(sql).replaceAll("");
        
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
        return value.toLowerCase();
    }
}
