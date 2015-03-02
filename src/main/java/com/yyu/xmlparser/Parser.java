package com.yyu.xmlparser;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public interface Parser {
    public boolean hasNext();
    public Map<String, String> next();
    public Collection<String> getKeys();
}