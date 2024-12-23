package DomQueryG0P9OJ;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import org.xml.sax.SAXException;

public class DomQueryG0P9OJ {

  public static void main(String[] args) {
    try {
      // xml fajl beolvasasa es dom objektum letrehozasa
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      Document document = documentBuilder.parse("XMLG0P9OJ.xml");

      // dom objektum normalizalasa
      document.getDocumentElement().normalize();

      // xpath inicializalasa
      XPath xPath = XPathFactory.newInstance().newXPath();

      // lekerdezesek
      String expression = "";


      // kulonbozo xpath lekerdezesek es kifejezesek

      //expression = "/gepjarmu/tulajdonos";
      //expression = "/gepjarmu/gyartas[@gyartasID='10B']";
      //expression = "/gepjarmu/motor[last()]";
      //expression = "/gepjarmu/karosszeria[szin='fekete']";
      //expression = "/gepjarmu/gyartas[evszam>2013]";
      expression = "/gepjarmu/birtoklas";
      NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);

      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        System.out.println("\nAktualis elem: " + node.getNodeName());

        // gepjarmu elem kiirasa
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("munkagep")) {
          Element element = (Element) node;

          System.out.println("Rendszam: " + element.getAttribute("rendszam"));
          System.out.println("Tulaj jogsi: " + element.getElementsByTagName("tulajID").item(0).getTextContent());
          System.out.println("Alvaz szama: " + element.getElementsByTagName("jarmuID").item(0).getTextContent());
          System.out.println("Motor szama: " + element.getElementsByTagName("motorID").item(0).getTextContent());
          System.out.println("Gyartasi szam: " + element.getElementsByTagName("gyartasID").item(0).getTextContent());
        }


        // tulajdonos elem kiirasa
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("tulajdonos")) {
          Element element = (Element) node;

          System.out.println("Jogositvany szama: " + element.getAttribute("jogositvany_szama"));
          System.out.println("Keresztnev: " + element.getElementsByTagName("knev").item(0).getTextContent());
          System.out.println("Vezeteknev: " + element.getElementsByTagName("vnev").item(0).getTextContent());
        }


        // motor elem kiirasa
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("motor")) {
          Element element = (Element) node;

          System.out.println("Motor szama: " + element.getAttribute("motorszam"));
          System.out.println("Uzemanyag: " + element.getElementsByTagName("meghajtas").item(0).getTextContent());
          System.out.println("Loero: " + element.getElementsByTagName("loero").item(0).getTextContent());
          System.out.println("Nyomatek: " + element.getElementsByTagName("nyomatek").item(0).getTextContent());
        }


        // karosszeria elem kiirasa
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("karosszeria")) {
          Element element = (Element) node;

          System.out.println("Alvazszam: " + element.getAttribute("alvazszam"));
          System.out.println("Felulet: " + element.getElementsByTagName("felulet").item(0).getTextContent());
          System.out.println("Engedely: " + element.getElementsByTagName("engedely").item(0).getTextContent());
          System.out.println("Elsodleges szin: " + element.getElementsByTagName("szin").item(0).getTextContent());
        }


        // gyartas elem kiirasa
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("gyartas")) {
          Element element = (Element) node;

          System.out.println("ID: " + element.getAttribute("gyartasID"));
          System.out.println("Marka: " + element.getElementsByTagName("marka").item(0).getTextContent());
          System.out.println("Modell: " + element.getElementsByTagName("modell").item(0).getTextContent());
          System.out.println("Gyartasi ev: " + element.getElementsByTagName("evszam").item(0).getTextContent());
        }


        // birtoklasok kiirasa
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("birtoklas")){
          Element element = (Element) node;

          System.out.println("Rendszam: " + element.getAttribute("rendszam"));
          System.out.println("Jogositvany szama: " + element.getAttribute("jogositvany_szama"));
          System.out.println("Birtoklas kezdete: " + element.getElementsByTagName("birtoklas_kezdete").item(0).getTextContent());
        }
      }

    } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
      e.printStackTrace();
    }
  }
}