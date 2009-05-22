import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;

public class MyXMLReader extends DefaultHandler {
	java.util.Stack tags = new java.util.Stack();
	public MyXMLReader() {
		super();
	}
	public static void main(String args[]) {
		
		long lasting = System.currentTimeMillis();
		try {
			SAXParserFactory sf = SAXParserFactory.newInstance();
			SAXParser sp = sf.newSAXParser();
			MyXMLReader reader = new MyXMLReader();
			sp.parse(new InputSource("data_10k.xml"), reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(
			"运行时间：" + (System.currentTimeMillis() - lasting) + " 毫秒");
	}
	
	public void characters(char ch[], int start, int length)
		throws SAXException {
		String tag = (String) tags.peek();
		if (tag.equals("NO")) {
			System.out.print("车牌号码：" + new String(ch, start, length));
		}
		if (tag.equals("ADDR")) {
			System.out.println(" 地址:" + new String(ch, start, length));
		}

	}
	
	public void startElement(
		String uri,
		String localName,
		String qName,
		Attributes attrs) {
		tags.push(qName);
	}
}
