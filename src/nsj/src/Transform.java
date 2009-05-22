import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.sax.SAXSource;
//import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
//import org.xml.sax.InputSource;
//import org.xml.sax.XMLFilter;
//import org.xml.sax.XMLReader;

import com.ns.log.Log;

class Transform {
	File file = new File("e:\\test2.xslt");
	public Transformer getTransformer() throws Exception {
		TransformerFactory tfactory = TransformerFactory.newInstance();
		Source xslt = new StreamSource(file);
		Transformer transformer = tfactory.newTransformer(xslt);
		return transformer;
	}

	public Transformer getTransformer(File xsltFile) throws Exception {
		TransformerFactory factory = TransformerFactory.newInstance();
		Source xslt = new StreamSource(xsltFile);
		Templates template = factory.newTemplates(xslt);
		return template.newTransformer();
	}

	public void transform(File source_file) throws Exception {
		StreamSource xml_source = new StreamSource(source_file);
		StringWriter result = new StringWriter();
		StreamResult xml_result = new StreamResult(result);

		try {
			getTransformer().setParameter("paramAddressVisible", "yes");
			getTransformer().transform(xml_source, xml_result);
		} catch (Exception e) {
			Log.error(e);
		}

		OutputStreamWriter wt = new OutputStreamWriter(System.out);

		wt.write(result.toString());
		wt.close();

		File result_file = new File("e:\\result.xdp");
		FileWriter writer = new FileWriter(result_file);
		writer.write(result.toString());
		writer.close();
	}
	/*
	public void a() {
		XMLReader xmlReader = new MyCSVReader();

		//	   Create XSLT filters and set its parents
		SAXTransformerFactory stf =
			(SAXTransformerFactory) TransformerFactory.newInstance();
		XMLFilter[] xmlFilters = new XMLFilter[xsltFiles.length];
		for (int i = 0; i < xmlFilters.length; i++) {
			xmlFilters[i] = stf.newXMLFilter(new StreamSource(xsltFiles[i]));
			// Set the parent for the filter
			if (i == 0)
				xmlFilters[i].setParent(xmlReader);
			else
				xmlFilters[i].setParent(xmlFilters[i - 1]);
		}

		//	   Define input and output streams
		BufferedReader br =
			new BufferedReader(new FileReader(xmlTransDoc.getFile()));
		InputSource inputSource = new InputSource(br);
		String outputFileName = outputPath + xmlTransDoc.getFile().getName();
		BufferedOutputStream bos =
			new BufferedOutputStream(
				new FileOutputStream(new File(outputFileName)));

		//	   Set up the transformer to process the SAX events generated
		//	   by the last filter in the chain
		Transformer transformer = stf.newTransformer();
		SAXSource source =
			new SAXSource(xmlFilters[xmlFilters.length - 1], inputSource);
		StreamResult result = new StreamResult(bos);

		//	   Transform
		transformer.transform(source, result);

	}
	*/
	public void test(String[] args) throws Exception {
		TransformerFactory tfactory = TransformerFactory.newInstance();
		Transformer transformer =
			tfactory.newTransformer(new StreamSource(args[0]));
		DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = dfactory.newDocumentBuilder();

		Document doc = parser.parse(args[1]);

		Element domElem = doc.getDocumentElement();

		// workaround
		// StringWriter out = new StringWriter();
		// Transformer id = tfactory.newTransformer();
		// id.transform(new DOMSource(domElem),new StreamResult(out));
		// String xml = out.toString();
		// transformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(System.out));
		transformer.transform(
			new DOMSource(domElem),
			new StreamResult(System.out));
		transformer.clearParameters();

	}

	public static void main(String[] args) throws Exception {
		new Transform().transform(new File("e:\\test.xdp"));
	}
}