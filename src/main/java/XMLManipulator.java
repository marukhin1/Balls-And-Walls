package main.java;

import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XMLManipulator {
    private String fileName;

    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document document;

    private TransformerFactory transformerFactory;
    private Transformer transformer;
    private DOMSource source;

    private StreamResult consoleSR;
//    private StreamResult fileSR;

    public XMLManipulator(String fileName) throws ParserConfigurationException, TransformerConfigurationException {
        this.fileName = fileName;

        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();

        //Сборка XML (Старый вариант). Сейчас нужен для вывода файла в консоль
        transformerFactory = TransformerFactory.newInstance();
        transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
    }

    public void open() throws SAXException, IOException {
        File file = new File(fileName);
        if (file.exists()) {
            System.out.println("Файл существует");
            document = builder.parse(new File(fileName));
            document.getDocumentElement().normalize();
            source = new DOMSource(document);
        } else {
            System.out.println("Файл не существует");
            document = builder.newDocument();
            Element rootElement = document.createElement("options");
            document.appendChild(rootElement);
            document.getDocumentElement().normalize();
            source = new DOMSource(document);
            record();
        }
    }

    public boolean addElement(String paretn, String tag, String text) throws IOException {
        Element root = document.getDocumentElement();
        Element element = document.createElement(tag);
        element.setTextContent(text);
        NodeList nodeList = document.getElementsByTagName(paretn);
        int sig = 0;
        if (paretn.equals(root.getTagName())){
            root.appendChild(element);
            sig++;
        }
        else if(nodeList.getLength() != 0){
            nodeList.item(0).appendChild(element);
            sig++;
        }
        if (sig != 0){
            document.getDocumentElement().normalize();
            source = new DOMSource(document);
            record();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean editTextElement(String tag, String newText, String option) throws IOException {
        //Element root = document.getDocumentElement();
        NodeList nodeList = document.getElementsByTagName(tag);
        int nodeListLength = nodeList.getLength();
        int sig = 0;
        if (nodeListLength != 0){
            if (option.toLowerCase().equals("first")){
                System.out.println(nodeList.item(0).getTextContent());
                nodeList.item(0).setTextContent(newText);
                System.out.println(nodeList.item(0).getTextContent());
                sig++;
            }
            else if (option.toLowerCase().equals("last")){
                System.out.println(nodeList.item(0).getTextContent());
                nodeList.item(--nodeListLength).setTextContent(newText);
                System.out.println(nodeList.item(0).getTextContent());
                sig++;
            }
            else if (isNumber(option)){
                Integer optionInt = Integer.parseInt(option);
                if((optionInt >= 0) && (optionInt < nodeListLength)){
                    System.out.println(nodeList.item(0).getTextContent());
                    nodeList.item(optionInt).setTextContent(newText);
                    System.out.println(nodeList.item(0).getTextContent());
                    sig++;
                }
            }
            else if (option.toLowerCase().equals("all")){
                for (int i = 0; i < nodeListLength; i++) {
                    System.out.println(nodeList.item(0).getTextContent());
                    nodeList.item(i).setTextContent(newText);
                    System.out.println(nodeList.item(0).getTextContent());
                    sig++;
                }
            }
        }
        if (sig != 0){
            document.getDocumentElement().normalize();
            source = new DOMSource(document);
            record();
            return true;
        }
        else{
            return false;
        }
    }

    public String getTextElement (String tag) {
        NodeList nodeList = document.getElementsByTagName(tag);
        return nodeList.item(0).getTextContent();
    }

    public void record() throws IOException {
//        Запись в XML (Старый вариант).
//        fileSR = new StreamResult(new File(fileName));
//        transformer.transform(source, fileSR);
        DOMImplementation impl = document.getImplementation();
        DOMImplementationLS implLS = (DOMImplementationLS) impl.getFeature("LS", "3.0");
        LSSerializer ser = implLS.createLSSerializer();
        ser.getDomConfig().setParameter("format-pretty-print", true);

        LSOutput out = implLS.createLSOutput();
        out.setEncoding("UTF-8");
        out.setByteStream(Files.newOutputStream(Paths.get(fileName)));
        ser.write(document, out);
    }

    public void print() throws TransformerException {
        System.out.println("\n**********************");
        System.out.println("***" + fileName + "***");
        System.out.println("**********");
        consoleSR = new StreamResult(System.out);
        transformer.transform(source, consoleSR);
        System.out.println("**********************");
    }

    public static boolean isNumber(String str) {
        try{
            Integer i = Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
}