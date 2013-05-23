package zoedb.connection;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DBProperties extends DefaultHandler {
	
	private static DBProperties instance;
	private static File propsFile = new File("C:\\Users\\Robert\\Dropbox\\Java Projects\\ZoeDB\\conf", "ZoeDB.properties");
	private HashMap<String,Hashtable<String,String>> props;	
	private String currentConnectionName = "";
	private Hashtable<String,String> currentConnectionProps = null;
	private String tempKey = "";
	private String tempVal = "";

	private DBProperties() {
			props = new HashMap<String,Hashtable<String,String>>();
			parseXMLFile();
	}
	
	public static DBProperties getProperties() {
		if(instance == null) {
			instance = new DBProperties();
		}
		return instance;
	}
	
	public String getProperty(String key) {
		return getProperty("DEFAULT", key);
	}
	
	public String getProperty(String connectionName, String key) {
		return props.get(connectionName).get(key);
	}
	
	private void parseXMLFile() {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			SAXParser sp = spf.newSAXParser();
			sp.parse(propsFile, this);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startElement(String uri, String localName,
							 String qName, Attributes attributes) throws SAXException {
		if(qName.equalsIgnoreCase("connection")) {
			currentConnectionName = attributes.getLength() == 0 ? "DEFAULT" : attributes.getValue("name");
			currentConnectionProps = new Hashtable<String,String>();
		} else {
			this.tempVal = "";
			this.tempKey = qName;
		}
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		String s = new String(ch, start, length);
		if(s.equals("\n") || s.equals("\t")) {
			// do nothing
		} else {
			this.tempVal = new String(ch, start, length);
		}
	}
	
	public void endElement(String uri, String localName,
						   String qName) throws SAXException {
		if(qName.equalsIgnoreCase("connection")) {
			props.put(currentConnectionName, currentConnectionProps);
		} else {
			currentConnectionProps.put(tempKey, tempVal);
		}
	}
	
}
