package com.yyu.xmlparser.hbm;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

import com.yyu.xmlparser.Parser;

public class PropertyParser implements Parser {

    private XMLStreamReader reader;
    
    public PropertyParser(XMLStreamReader reader) {
        this.reader = reader;
    }
    
    @Override
    public boolean hasNext() {
        return "property".equalsIgnoreCase(reader.getLocalName()) && reader.isStartElement();
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
            if ("type".equals(attributeName.getLocalPart())) {
                String type = reader.getAttributeValue(i);
                fieldInfo.put("javaType", type);
            }
        }
        return fieldInfo;
    }

    @Override
    public Collection<String> getKeys() {
        Set<String> keys = new HashSet<String>();
        keys.add("javaName");
        keys.add("javaType");
        return keys;
    }
}
