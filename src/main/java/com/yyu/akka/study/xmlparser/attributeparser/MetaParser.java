package com.yyu.akka.study.xmlparser.attributeparser;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.yyu.xmlparser.Parser;

public class MetaParser implements Parser {
    
    public static final String API_FIELD_NAME = "apiFieldName";
    public static final String API_ACCESS = "apiAccess";
    public static final String API_FIELD_MAX_VERSION = "apiFieldMaxVersion";
    public static final String API_FIELD_MIN_VERSION = "apiFieldMinVersion";
    public static final String API_PERMISSION = "apiPermission";
    
    private XMLStreamReader reader;
    private String apiFieldType;
    private String apiFieldTypeValue;
    
    public MetaParser(XMLStreamReader reader, String apiFieldType) {
        this.reader = reader;
        this.apiFieldType = apiFieldType;
    }

    @Override
    public boolean hasNext() {
        boolean isMeta = "meta".equalsIgnoreCase(reader.getLocalName()) && reader.isStartElement();
        return isMeta && readAttributeValue();
    }
    
    private boolean readAttributeValue() {
        int attributeCount = reader.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            QName attributeName = reader.getAttributeName(i);
            if ("attribute".equalsIgnoreCase(attributeName.getLocalPart())) {
                String apiType = reader.getAttributeValue(i);
                if (apiFieldType.equals(apiType)) {
                    try {
                        apiFieldTypeValue = reader.getElementText();
                    }
                    catch (XMLStreamException e) {
                        e.printStackTrace();
                    }
                    if (apiFieldTypeValue != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Map<String, String> next() {
        Map<String, String> fieldInfo = new HashMap<String, String>();
        fieldInfo.put(apiFieldType, apiFieldTypeValue);
        return fieldInfo;
    }
    
    @Override
    public Collection<String> getKeys() {
        Set<String> keys = new HashSet<String>();
        keys.add(apiFieldType);
        return keys;
    }
}
