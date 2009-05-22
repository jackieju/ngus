/*
 * Created on 2005-7-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ns.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ns.log.Log;

/**
 * @author I027910
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class XmlUtil {

	static DocumentBuilderFactory factory = null;

	static DocumentBuilder builder = null;

	static {
		// get a DocumentBuilderFactory from the underlying implementation
		factory = DocumentBuilderFactory.newInstance();

		// force vadidating
		factory.setValidating(false);
		factory.setNamespaceAware(false);
		

		// get a DocumentBuilder from the factory
		try {
			builder = factory.newDocumentBuilder();			
		} catch (Exception e) {
			Log.error(e);
		}
		
//		test2();
		
		Log.trace("Init XmlUtil succeeded.");

	}
	
	
	static public Document createDocument(){
		return builder.newDocument();
	}

	static private InputStream getTemplateStream(String templateId) {
		InputStream is = null;
		File f = new File(templateId);
		try {
			is = new FileInputStream(f);
		} catch (Exception e) {
			Log.error(e);
		}
		return is;
	}

	public static Document loadDom(String fileName) {
		Log.trace("template id = " + fileName);
		// get stream
		InputStream is = getTemplateStream(fileName);
		return loadDom(is);
	}
	
	public static Document loadXML(String xml) {
		Log.trace("template id = " + xml);
		// get stream
		InputStream is = new java.io.ByteArrayInputStream(xml.getBytes());
		return loadDom(is);
	}

	public static Document loadDom(InputStream is) {

		if (is == null) {
			Log.error("get template stream failed");
			return null;
		}

		Document doc = null;

		// parse xml
		try {
			doc = builder.parse(is);
		} catch (Exception e) {
			Log.error(e);
			return null;
		}
		return doc;
	}

	public static Element getElementsByTagName(Element currentNode,
			String tagName, int index) {
		NodeList nodes = currentNode.getElementsByTagName(tagName);
		if (nodes == null)
			return null;
		if (nodes.getLength() <= index)
			return null;
		return (Element) nodes.item(index);
	}

	public static Element getElement(Element currentNode, String path) {
		Log.trace("path:" + path);
		String[] tagNames = path.split("[.]");

		Log.trace("names.size = " + tagNames.length);
		Element node = currentNode;
		for (int i = 0; i < tagNames.length; i++) {
			node = getElementsByTagName(node, tagNames[i], 0);
			Log.trace("tagName = " + tagNames[i]);
			if (node == null)
				return null;
		}
		return node;
	}

	static public Element getElement(Element currentNode, String tagName,
			String attrName, String attrValue, int index) {
		NodeList nodes = currentNode.getElementsByTagName(tagName);
		if (nodes == null)
			return null;
		int count = 0;
		for (int i = 0; i < nodes.getLength(); i++) {
			Element ele = (Element) nodes.item(i);
			String value = ele.getAttribute(attrName);
			if (value == null)
				continue;

			if (value.equals(attrValue)) {
				if (count == index)
					return ele;
				else
					count++;
			}
		}
		return null;
	}

	static public boolean writeDomDocumentToFile(Document doc, String fileName)
			throws Exception {
		boolean isOver = false;
		DOMSource doms = new DOMSource(doc);

		File f = new File(fileName);
		StreamResult sr = new StreamResult(f);
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			// Properties properties = t.getOutputProperties();
			// Properties.setProperty (OutputKeys.ENCODING,"GB2312");
			// t.setOutputProperties(properties);
			t.transform(doms, sr);
			isOver = true;
		} catch (TransformerConfigurationException tce) {
			tce.printStackTrace();
			Log.error(tce);
			throw tce;
		} catch (TransformerException te) {
			te.printStackTrace();
			Log.error(te);
			throw te;
		} finally {
			// sr.getWriter().close();
		}
		return isOver;
	}

	static public boolean transform(InputStream xml, InputStream xslt,
			OutputStream o, HashMap params) throws Exception {
		boolean isOver = false;
		StreamSource xml_src = new StreamSource(xml);
		StreamResult sr = new StreamResult(o);
		StreamSource xslt_src = new StreamSource(xslt);

		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Templates template = tf.newTemplates(xslt_src);
			Transformer t = template.newTransformer();

			Iterator it = params.keySet().iterator();
			while (it.hasNext()) {
				String s = (String) it.next();
				t.setParameter(s, params.get(s));
			}
//			StringWriter result = new StringWriter();
//			StreamResult xml_result = new StreamResult(result);
			t.transform(xml_src, new StreamResult(o));

//			File result_file = new File("d:\\o.xml");
//			FileWriter writer = new FileWriter(result_file);
//			writer.write(result.toString());
//			writer.close();

			isOver = true;
		} catch (TransformerConfigurationException tce) {
			tce.printStackTrace();
			Log.error(tce);
			throw tce;
		} catch (TransformerException te) {
			te.printStackTrace();
			Log.error(te);
			throw te;
		} finally {
			// sr.getWriter().close();
		}
		return isOver;
	}
	
	static public String DOMToString(Document doc){
		
		DOMSource doms = new DOMSource( doc);
		Writer out = new StringWriter();
		StreamResult result = new StreamResult( out );
		TransformerFactory tf = TransformerFactory.newInstance();
		try{
		Transformer transformer = tf.newTransformer();

		transformer.transform( doms, result );
		}catch(Exception e){
			Log.error(e);			
		}
		return out.toString();	
	}

	static void testTransform() throws Exception {
		// String xslt = "<xsl:stylesheet version = '1.0'
		// xmlns:xsl='http://www.w3.org/1999/XSL/Transform'
		// xmlns:adobe=\"http://www.xfa.org/schema/xfa-template/2.1/\"
		// xmlns:xhtml=\"http://www.w3.org/1999/xhtml\"> <xsl:param
		// name=\"startDate\" select=\"no\"/><xsl:param name=\"endDate\"
		// select=\"no\"/><!-- Identity transformation --><xsl:template
		// match=\"/ | @*|node()\"> <xsl:copy> <xsl:apply-templates
		// select=\"@*|node()\" /> </xsl:copy> </xsl:template> <xsl:template
		// match=\"sleep[@util=\"${startDate}\"]\"> <xsl:copy> <xsl:attribute
		// name=\"util\"><xsl:value-of select=\"$startDate\"/></xsl:attribute>
		// </xsl:copy> </xsl:template> <xsl:template
		// match=\"sleep[@util=\"${stopDate}\"]\"> <xsl:copy> <xsl:attribute
		// name=\"util\"><xsl:value-of select=\"$stopDate\"/></xsl:attribute>
		// </xsl:copy> </xsl:template> </xsl:stylesheet> ";
		FileInputStream is = new FileInputStream("d:\\a.xsl");
		FileOutputStream os = new FileOutputStream("d:\\o.xml");
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><process-definition xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"http://www.openwfe.org/flowdef_r1.5.0.xsd\" name=\"webActivation\" revision=\"1.0\"><description language=\"default\">worflow for web activation process.</description><sequence><participant ref=\"command-SendMail\" /><sleep util=\"${startDate}\"/><sleep util=\"${endDate}\"/><set field=\"mailTo\" value=\"user-superuser\" /><participant ref=\"user-superuser\" /><participant ref=\"command-activate\" /></sequence></process-definition>";
		HashMap hm = new HashMap();
		hm.put("startDate1", "dhhhh");
		hm.put("endDate1", "a1111");
		transform(new StringBufferInputStream(xml), is, os, hm);

	}

	static void testWriteDomDocumentToFile() throws Exception {
		// get a DocumentBuilderFactory from the underlying implementation
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// force vadidating
		factory.setValidating(true);

		// get a DocumentBuilder from the factory
		DocumentBuilder builder = factory.newDocumentBuilder();

		InputStream is = null;
		File f = new File("d:\\master1.xdp");
		Document doc = builder.parse(f);

		writeDomDocumentToFile(doc, "d:\\write.xdp");
	}

	public static void test1() throws Exception {
		TransformerFactory factory = TransformerFactory.newInstance();
		Source xslt = new StreamSource("d:\\a.xsl");
		Templates template = factory.newTemplates(xslt);
		Transformer transformer = template.newTransformer();

		File source_file = new File("d:\\a.xml");
		StreamSource xml_source = new StreamSource(source_file);
		StringWriter result = new StringWriter();
		StreamResult xml_result = new StreamResult(result);
		transformer.transform(xml_source, xml_result);
		File result_file = new File("d:\\o.xml");
		FileWriter writer = new FileWriter(result_file);
		writer.write(result.toString());
		writer.close();

	}
	
	static public void test2(){
		{
		Document doc = createDocument();
		Element ele = doc.createElement("ngusResponse");
		doc.appendChild(ele);
		Log.log(doc.getClass());
		Log.log(DOMToString(doc));
		}
//		Document doc = XmlUtil.createDocument();
//		Log.log(doc.getClass());
//		Element rootEle = doc.createElement("ngusResponse");
//		rootEle = (Element)(doc.appendChild(rootEle));
		
	}

	public static void main(String[] args) throws Exception {
		 //testTransform();
		//test1();
	 test2();
	 Log.log("ok");
	}
}