package DomModifyG0P9OJ;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomModifyG0P9OJ {

    public static void main(String argv[]) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        File inputFile = new File("XMLG0P9OJ.xml");

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();


        // masodik munkagep attributumanak modositasa
        Node jarmu = doc.getElementsByTagName("munkagep").item(1);
        NamedNodeMap attr = jarmu.getAttributes();
        Node nodeAttr = attr.getNamedItem("rendszam");
        nodeAttr.setTextContent("AA-BB-123");
        System.out.println("munkagep attributum modositva");


        // elso tulajdonos attributumanak modositasa
        Node tulaj = doc.getElementsByTagName("tulajdonos").item(0);
        attr = tulaj.getAttributes();
        nodeAttr = attr.getNamedItem("jogositvany_szama");
        nodeAttr.setTextContent("BB112233");
        System.out.println("tulajdonos attributum modositva");


        // osszes egedely beallitasa van-ra
        NodeList nodes = doc.getElementsByTagName("karosszeria");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                NodeList childNodes = node.getChildNodes();

                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node childNode = childNodes.item(j);
                    if (childNode.getNodeName().equals("egedely")) {
                        childNode.setTextContent("van");
                    }
                }
            }
        }
        System.out.println("egedely modositasok kesz");


        // osszes benzines motor atallitasa dizelre
        nodes = doc.getElementsByTagName("motor");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                NodeList childNodes = node.getChildNodes();

                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node childNode = childNodes.item(j);
                    if (childNode.getNodeName().equals("uzemanyag") && childNode.getTextContent().equals("benzin")) {
                        childNode.setTextContent("dizel");
                    }
                }
            }
        }
        System.out.println("benzin motorok atallitva dizelre");


        // gyartasi_ev element atnevezese evjaratra
        nodes = doc.getElementsByTagName("gyartasi_ev");
        for (int i = 0; i < nodes.getLength(); i++) {
            doc.renameNode(nodes.item(i), null, "evjarat");
        }
        System.out.println("gyartasi_ev atnevezeve evjaratra");


        // birtoklasok kezdeti datumnak a modositasa mindenhol egy evvel hamarabbra
        nodes = doc.getElementsByTagName("birtoklas_kezdete");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                node.setTextContent(decrementYear(node.getTextContent()));
            }
        }
        System.out.println("birtoklas_kezdete modositasa kesz");


        // modositott xml dokumentum elmentese
        writeXml(doc, new File("XMLG0P9OJModify.xml"));
    }


    private static void writeXml(Document doc, File output) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(doc);

        StreamResult file = new StreamResult(output);

        transformer.transform(source, file);
    }


    private static String decrementYear(String date) {
        int year = Integer.parseInt(date.substring(0, 4)) - 1;
        String monthDay = date.substring(4);
        return year + monthDay;
    }
}
