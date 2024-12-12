package DomWriteG0P9OJ;

import java.io.File;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DomWriteG0P9OJ {

    public static void main(String[] args) throws Exception {
        // letrehozunk egy uj dokumentumot
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        // gyoker elem letrehozasa
        Element gepjarmuElement = doc.createElement("gepjarmu");
        gepjarmuElement.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema-instance");
        gepjarmuElement.setAttribute("xs:noNamespaceSchemaLocation", "XMLSchemaG0P9OJ.xsd");
        doc.appendChild(gepjarmuElement);

        // munkagepek
        addMunkagep(doc, gepjarmuElement, "AB-CD-123", "HU345678", "100FDSAF008F1G", "0FSFDSFFF70000019", "15B",
                "FSDAG34");
        addMunkagep(doc, gepjarmuElement, "AA-BB-720", "HU346544", "100FDFGDS58F1G", "0FSFDSCCC70000019", "10B",
                "HSDAG34");
        addMunkagep(doc, gepjarmuElement, "KD-QS-510", "RO378544", "540FDFGDS58F1G", "3HSFDSCCC70000019", "98C",
                null);

        // tulajdonosok
        addTulajdonos(doc, gepjarmuElement, "HU345678", "Kovacs", "Arpad");
        addTulajdonos(doc, gepjarmuElement, "HU346544", "Szabo", "Peter");
        addTulajdonos(doc, gepjarmuElement, "RO378544", "Elekes", "Tibor");

        // motorok
        addMotor(doc, gepjarmuElement, "100FDSAF008F1G", "dizel", "68", "160 Nm");
        addMotor(doc, gepjarmuElement, "100FDFGDS58F1G", "benzin", "75", "107 Nm");
        addMotor(doc, gepjarmuElement, "540FDFGDS58F1G", "elektromos", "150", "310 Nm");

        // karosszeriak
        addKarosszeria(doc, gepjarmuElement, "0FSFDSFFF70000019", "matt", "nincs", "feher", "szurke");
        addKarosszeria(doc, gepjarmuElement, "0FSFDSCCC70000019", "teljes", "van", "feher", null);
        addKarosszeria(doc, gepjarmuElement, "3HSFDSCCC70000019", "reszleges", "nincs", "zold", "fekete", "szurke");

        // gyartasi infok
        addGyartas(doc, gepjarmuElement, "15B", "Hyundai", "R25Z-9AK", "2020");
        addGyartas(doc, gepjarmuElement, "10B", "JCB", "1CX", "2008");
        addGyartas(doc, gepjarmuElement, "98C", "JCB", "4CX", "2019");

        // birtoklasi kapcsolatok
        addBirtoklas(doc, gepjarmuElement, "AB-CD-123", "HU345678", "2023-01-01");
        addBirtoklas(doc, gepjarmuElement, "AA-BB-720", "HU346544", "2022-11-04");
        addBirtoklas(doc, gepjarmuElement, "KD-QS-510", "RO378544", "2021-05-18");

        // fajl mentese
        String fileName = "XMLG0P9OJ2.xml";
        saveXML(doc, fileName);

        // XML kiíratása a konzolra
        printXML(doc);
    }


    // munkagep elem hozzadasa az xml-hez
    private static void addMunkagep(Document doc, Element parent, String rendszam, String tulajJogsi,
            String motorSzam, String alvazSzam, String gyartasSorszam, String cascoAzon) {
        Element munkagepElement = doc.createElement("munkagep");
        munkagepElement.setAttribute("rendszam", rendszam);

        addChildElement(doc, munkagepElement, "tulajID", tulajJogsi);
        addChildElement(doc, munkagepElement, "motorID", motorSzam);
        addChildElement(doc, munkagepElement, "jarmuID", alvazSzam);
        addChildElement(doc, munkagepElement, "gyartasID", gyartasSorszam);

        if (cascoAzon != null) {
            addChildElement(doc, munkagepElement, "biztosit", cascoAzon);
        }

        parent.appendChild(munkagepElement);
    }


    // tulajdonos elem hozzadasa az xml-hez
    private static void addTulajdonos(Document doc, Element parent, String jogositvanySzama, String vnev,
            String knev) {
        Element tulajdonosElement = doc.createElement("tulajdonos");
        tulajdonosElement.setAttribute("jogositvany_szama", jogositvanySzama);

        Element nevElement = doc.createElement("nev");
        addChildElement(doc, nevElement, "vnev", vnev);
        addChildElement(doc, nevElement, "knev", knev);

        tulajdonosElement.appendChild(nevElement);
        parent.appendChild(tulajdonosElement);
    }


    // motor elem hozzadasa az xml-hez
    private static void addMotor(Document doc, Element parent, String motorSzam, String meghajtas, String loero,
            String nyomatek) {
        Element motorElement = doc.createElement("motor");
        motorElement.setAttribute("motorszam", motorSzam);

        addChildElement(doc, motorElement, "meghajtas", meghajtas);
        addChildElement(doc, motorElement, "loero", loero);
        addChildElement(doc, motorElement, "nyomatek", nyomatek);

        parent.appendChild(motorElement);
    }


    // karosszeria elem hozzadasa az xml-hez
    private static void addKarosszeria(Document doc, Element parent, String alvazSzam, String felulet, String engedely,
            String... szinek) {
        Element karosszeriaElement = doc.createElement("karosszeria");
        karosszeriaElement.setAttribute("alvazszam", alvazSzam);

        addChildElement(doc, karosszeriaElement, "felulet", felulet);
        addChildElement(doc, karosszeriaElement, "engedely", engedely);

        for (String szin : szinek) {
            addChildElement(doc, karosszeriaElement, "szin", szin);
        }

        parent.appendChild(karosszeriaElement);
    }


    // gyartas elem hozzadasa az xml-hez
    private static void addGyartas(Document doc, Element parent, String gyartasID, String marka, String modell,
            String gyartasiEv) {
        Element gyartasElement = doc.createElement("gyartas");
        gyartasElement.setAttribute("gyartasID", gyartasID);

        addChildElement(doc, gyartasElement, "marka", marka);
        addChildElement(doc, gyartasElement, "modell", modell);
        addChildElement(doc, gyartasElement, "evszam", gyartasiEv);

        parent.appendChild(gyartasElement);
    }


    // birtoklas kapcsolat hozzadasa az xml-hez
    private static void addBirtoklas(Document doc, Element parent, String rendszam, String jogositvanySzama,
            String idoKezdete) {
        Element birtoklasElement = doc.createElement("birtoklas");
        birtoklasElement.setAttribute("rendszam", rendszam);
        birtoklasElement.setAttribute("jogositvany_szama", jogositvanySzama);

        addChildElement(doc, birtoklasElement, "birtoklas_kezdete", idoKezdete);

        parent.appendChild(birtoklasElement);
    }


    // gyerek elem hozzadasa az xml-hez
    private static void addChildElement(Document doc, Element parent, String name, String value) {
        Element childElement = doc.createElement(name);
        childElement.appendChild(doc.createTextNode(value));
        parent.appendChild(childElement);
    }


    // xml dokumentum mentese fajlba
    private static void saveXML(Document doc, String fileName) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fileName));
        transformer.transform(source, result);
    }


    // xml kiiratasa konzolra
    private static void printXML(Document doc) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");


    // stringwriter letrehozasa
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);


    // dom forras
        DOMSource source = new DOMSource(doc);


    // strinwriter-be transzformalas
        transformer.transform(source, result);


    // konzolra iratas
        System.out.println("XML dokumentum:");
        System.out.println(writer.toString());
    }
}