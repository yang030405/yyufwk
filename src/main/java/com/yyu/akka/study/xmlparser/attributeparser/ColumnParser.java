package com.yyu.akka.study.xmlparser.attributeparser;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

import com.yyu.xmlparser.Parser;

public class ColumnParser implements Parser {
    private XMLStreamReader reader;
    
    public ColumnParser(XMLStreamReader reader) {
        this.reader = reader;
    }
    
    @Override
    public boolean hasNext() {
        return "column".equalsIgnoreCase(reader.getLocalName()) && reader.isStartElement();
    }

    @Override
    public Map<String, String> next() {
        Map<String, String> fieldInfo = new HashMap<String, String>();
        int attributeCount = reader.getAttributeCount();
        for (int i = 0;i < attributeCount; i++) {
            QName attributeName = reader.getAttributeName(i);
            if ("name".equals(attributeName.getLocalPart())) {
                String columnName = reader.getAttributeValue(i);
                fieldInfo.put("columnName", columnName);
            }
            if ("length".equals(attributeName.getLocalPart())) {
                String columnLength = reader.getAttributeValue(i);
                fieldInfo.put("columnLength", columnLength);
            }
        }
        return fieldInfo;
    }
    
    @Override
    public Collection<String> getKeys() {
        Set<String> keys = new HashSet<String>();
        keys.add("columnName");
        keys.add("columnLength");
        return keys;
    }
}