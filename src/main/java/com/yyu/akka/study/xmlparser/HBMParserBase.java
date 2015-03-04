package com.yyu.akka.study.xmlparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.yyu.xmlparser.Parser;

public abstract class HBMParserBase {
    
    private String xmlFilePath;
    private XMLStreamReader reader;
    private Set<String> titles = new HashSet<String>();
    
    public HBMParserBase(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
    }
    
    public List<Map<String, String>> getFiledsInfo() throws Exception {
        initReader();
        List<Map<String, String>> fieldsInfo = new ArrayList<Map<String, String>>();
        Map<String, String> fieldInfos = new HashMap<String, String>();
        while (reader.hasNext()) {
            skipElements();
            if (isStartElements()) {
                fieldInfos = new HashMap<String, String>();
            }
            fieldInfos.putAll(readToMap());
            if (isEndElements()) {
                fieldsInfo.add(fieldInfos);
            }
            reader.next();
        }
        closeReader();
        return fieldsInfo;
    }
    
    public abstract List<Parser> getParsers();
    
//    public abstract List<String> getElementsNames();
    
    public List<String> getElementsNames() {
        List<String> elementNames = new ArrayList<String>();
        elementNames.add("property");
        elementNames.add("many-to-one");
        return elementNames;
    }
    
    public List<String> getNeedSkipElementNames() {
        return null;
    }
    
    public XMLStreamReader getReader() {
        return reader;
    }
    
    public Set<String> getTitles() {
        return titles;
    }
    
    private void skipElements() throws Exception {
        List<String> skipElementNames = getNeedSkipElementNames();
        if (skipElementNames != null && !skipElementNames.isEmpty()) {
            for (String skipElementName : skipElementNames) {
                if (isObjectStart(skipElementName)) {
                    do {
                        reader.next();
                    } while (!isObjectEnd(skipElementName));
                    reader.next();
                }
            }
        }
    }
    
    private boolean isStartElements() throws Exception {
        List<String> elementNames = getElementsNames();
        if (elementNames != null && !elementNames.isEmpty()) {
            for (String elementName : elementNames) {
                if (isObjectStart(elementName)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isEndElements() throws Exception {
        List<String> elementNames = getElementsNames();
        if (elementNames != null && !elementNames.isEmpty()) {
            for (String elementName : elementNames) {
                if (isObjectEnd(elementName)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isObjectStart(String elementName) throws Exception {
        gotoNextValidTag();
        return elementName.equalsIgnoreCase(reader.getLocalName()) && reader.isStartElement();
    }
    
    private boolean isObjectEnd(String elementName) throws Exception {
        gotoNextValidTag();
        return elementName.equalsIgnoreCase(reader.getLocalName()) && reader.isEndElement();
    }
    
    private void initReader() throws Exception {
        File xmlFile = new File(xmlFilePath);
        InputStream is = new FileInputStream(xmlFile);
        XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
        reader = xmlFactory.createXMLStreamReader(is);
    }
    
    private void closeReader() throws Exception {
        reader.close();
    }
    
    private Parser getSpecificParser() {
        for (Parser parser : getParsers()) {
            if (parser.hasNext()) {
                return parser;
            }
        }
        return null;
    }
    
    private Map<String, String> readToMap() throws Exception {
        gotoNextValidTag();
        Map<String, String> fieldInfo = new HashMap<String, String>();
        Parser parser = getSpecificParser();
        if (parser != null) {
            fieldInfo.putAll(parser.next());
            titles.addAll(parser.getKeys());
        }
        gotoNextValidTag();
        return fieldInfo;
    }
    
    private void gotoNextValidTag() throws XMLStreamException {
        while (!reader.isStartElement() && !reader.isEndElement() && reader.hasNext()) {
            reader.next();
        }
    }
}
