package software.DataManager;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOM {

	private static String PATH = "C:/Users/Usuario-Pc/git/project/Project/src/software/DataManager/DatabaseConnection.xml";

	public static String readPassword() {
		return internalStructureRead("password");
	}

	public static String readUserName() {
		return internalStructureRead("username");
	}

	public static String readPortNumber() {
		return internalStructureRead("portnumber");
	}

	public void writePassword(String password) {
		internalStructureWrite("password", password);
	}

	public void writeUserName(String username) {
		internalStructureWrite("username", username);
	}

	public void writePortNumber(String portnumber) {
		internalStructureWrite("portnumber", portnumber);
	}

	private void internalStructureWrite(String attribute, String value) {
		File fXmlFile = new File(PATH);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc;
			try {
				doc = dBuilder.parse(fXmlFile);
				doc.getDocumentElement().normalize();

				NodeList nList = doc.getElementsByTagName("databaseconfiguration");

				for (int i = 0; i < nList.getLength(); i++) {
					Node nNode = nList.item(i);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						eElement.getElementsByTagName(attribute).item(0).setTextContent(value);
					}
				}

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer;
				try {
					transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(new File(PATH));
					try {
						transformer.transform(source, result);
					} catch (TransformerException e) {
						e.printStackTrace();
					}
				} catch (TransformerConfigurationException e) {
					e.printStackTrace();
				}

			} catch (SAXException | IOException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	private static String internalStructureRead(String attribute) {
		File fXmlFile = new File(PATH);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc;
			try {
				doc = dBuilder.parse(fXmlFile);
				doc.getDocumentElement().normalize();

				NodeList nList = doc.getElementsByTagName("databaseconfiguration");

				for (int i = 0; i < nList.getLength(); i++) {
					Node nNode = nList.item(i);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						return eElement.getElementsByTagName(attribute).item(0).getTextContent();
					}
				}

			} catch (SAXException | IOException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		return "";
	}

}
