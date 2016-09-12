package software.Security;

import java.io.File;
import java.io.IOException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import software.DataManager.DOM;

//http://www.software-architect.net/articles/using-strong-encryption-in-java/introduction.html

/**
 * This class initialize Hibernate
 *
 * @author: FEM
 * @version: 08/09/2016
 */

public class Encryption {

	private String password = "postgres";
	private String key = "QWaaaaaahSkaaaaa";

	private void readDocument() {
		File fXmlFile = new File(
				"C:/Users/Usuario-Pc/git/project/Project/src/software/DataManager/DatabaseConnection.xml");

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
						password = eElement.getElementsByTagName("password").item(0).getTextContent();
					}
				}

			} catch (SAXException | IOException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

	}

	public void run() {
		this.readDocument();
		try {

			// Create key and cipher
			Key aesKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES");

			// encrypt the text
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] encrypted = cipher.doFinal(password.getBytes());
			System.err.println(new String(encrypted));

			// decrypt the text
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			String decrypted = new String(cipher.doFinal(encrypted));
			System.err.println(decrypted);

			DOM dom = new DOM();
			dom.writeUserName(new String(encrypted));

			/*byte[] enc = cipher.doFinal(dom.readPassword().getBytes());
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			String dec = new String(cipher.doFinal(encrypted));
			System.err.println(dec);*/

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Encryption app = new Encryption();
		app.run();
	}

}