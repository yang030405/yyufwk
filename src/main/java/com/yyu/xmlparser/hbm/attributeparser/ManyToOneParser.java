package com.yyu.xmlparser.hbm.attributeparser;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

import com.yyu.xmlparser.Parser;

public class ManyToOneParser implements Parser {

    private XMLStreamReader reader;
    
    public ManyToOneParser(XMLStreamReader reader) {
        this.reader = reader;
    }
    
    @Override
    public boolean hasNext() {
        return "many-to-one".equalsIgnoreCase(reader.getLocalName()) && reader.isStartElement();
    }

    @Override
    public Map<String, String> next() {
        Map<String, String> fieldInfo = new HashMap<String, String>();
        int attributeCount = reader.getAttributeCount();
        for (int i = 0;i < attributeCount; i++) {
            QName attributeName = reader.getAttributeName(i);
            if ("name".equals(attributeName.getLocalPart())) {
                String javaName = reader.getAttributeValue(i);
                fieldInfo.put("javaName", javaName);
            }
        }
        return fieldInfo;
    }

    @Override
    public Collection<String> getKeys() {
        Set<String> keys = new HashSet<String>();
        keys.add("javaName");
        return keys;
    }
}
