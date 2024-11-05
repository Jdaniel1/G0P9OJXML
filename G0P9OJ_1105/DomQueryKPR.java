package domG0P9OJ1105;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class DomQueryKPR {
	public static void main(String argv[]) {
		
		try {
			File inputFile = new File("hallgatokquerry.xml");
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(inputFile);
			
			doc.getDocumentElement().normalize();
			
			System.out.print("Gyökér elem: ");
			
			System.out.println(doc.getDocumentElement().getNodeName());
			
			NodeList nList = doc.getElementsByTagName("hallgato");
		}
	}
}
