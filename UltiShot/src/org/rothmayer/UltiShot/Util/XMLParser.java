package org.rothmayer.UltiShot.Util;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

public class XMLParser {
	
	DocumentBuilderFactory factory;
	DocumentBuilder builder;
	StringBuilder xmlStringBuilder;
	ByteArrayInputStream input;
	Document doc;
	
	public XMLParser() throws ParserConfigurationException, SAXException, IOException{
		factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		xmlStringBuilder = new StringBuilder();
		xmlStringBuilder.append("<?xml version=\"1.0\"?> <class> </class>");
		input =  new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
		doc = builder.parse(input);
	}

}
