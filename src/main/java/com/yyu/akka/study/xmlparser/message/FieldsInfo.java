package com.yyu.akka.study.xmlparser.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FieldsInfo {
    private List<Map<String, String>> fieldsInfo = new ArrayList<Map<String, String>>();
    
    public List<Map<String, String>> getFieldsInfo() {
        return this.fieldsInfo;
    }
}
