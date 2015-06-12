package com.yyu.fwk.sqlike;

import java.util.HashMap;
import java.util.Map;

public class Query {
    
    private String[] select;
    private String from;
    private String where;
    private String[] group;
    private String having;
    private Map<String, String> order = new HashMap<String, String>();
    private Integer limit = -1;
}
