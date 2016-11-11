package Configuration;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.runtime.Platform;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import Presentation.controllerReports.ReportsPPController;

public class Installation {
	private static final String PATH = Platform.getInstallLocation().getURL().getPath() + "plugins/UCM2DEVS";

	public void exists() throws Exception {
		File directory = new File(PATH);

		if (!directory.exists()) {
			// create directories
			directory.mkdir();

			File configuration = new File(PATH + "/Configuration");
			configuration.mkdir();
			this.generateXML();

			File run = new File(PATH + "/Run");
			run.mkdir();
			for (int i = 1; i < 11; i++) {
				File runIterator = new File(PATH + "/Run/Run" + i);
				runIterator.mkdir();
			}

			File simulator = new File(PATH + "/Simulator");
			simulator.mkdir();
			
			File simulatorsrc = new File(PATH + "/Simulator/src");
			simulatorsrc.mkdir();

			// File simulatorFile = new File ("C:/Users/Micaela/Dropbox/PROYECTO
			// FINAL/UCM2DEVS/Simulator");
			// File simulatorFile = new File
			// (Installation.class.getProtectionDomain().getCodeSource()
			// .getLocation().getPath() + "Configuration/Simulator");
			// CopiarDirectorio(simulatorFile,simulator);
			extractJar(Installation.class.getProtectionDomain().getCodeSource()
			 .getLocation().getPath() + "src/Configuration/project.jar", simulatorsrc);
		}

	}

	public static void extractJar(String jarFile, java.io.File directory) throws IOException {
		java.util.jar.JarInputStream jarInput = new java.util.jar.JarInputStream(new FileInputStream(jarFile));
		java.util.jar.JarEntry jarEntry = null;
		while ((jarEntry = jarInput.getNextJarEntry()) != null) {
			java.io.File file = new java.io.File(directory, jarEntry.getName());
			if (jarEntry.isDirectory()) {
				if (!file.exists())
					file.mkdirs();
			} else {
				java.io.File dir = new java.io.File(file.getParent());
				if (!dir.exists())
					dir.mkdirs();
				byte[] bytes = new byte[1024];
				java.io.InputStream inputStream = new BufferedInputStream(jarInput);
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				int read = -1;
				while ((read = inputStream.read(bytes)) != -1) {
					fileOutputStream.write(bytes, 0, read);
				}
				fileOutputStream.close();
			}
		}
	}

	/*
	 * private static void CopiarDirectorio(File dirOrigen, File dirDestino)
	 * throws Exception { try { if (dirOrigen.isDirectory()) { if
	 * (!dirDestino.exists()) dirDestino.mkdir();
	 * 
	 * String[] hijos = dirOrigen.list(); for (int i = 0; i < hijos.length; i++)
	 * { CopiarDirectorio(new File(dirOrigen, hijos[i]), new File(dirDestino,
	 * hijos[i])); } // end for } else { Copiar(dirOrigen, dirDestino); } // end
	 * if } catch (Exception e) { throw e; } // end try } // end
	 * CopiarDirectorio
	 * 
	 * private static void Copiar(File dirOrigen, File dirDestino) throws
	 * Exception {
	 * 
	 * InputStream in = new FileInputStream(dirOrigen); OutputStream out = new
	 * FileOutputStream(dirDestino);
	 * 
	 * byte[] buffer = new byte[1024]; int len;
	 * 
	 * try { // recorrer el array de bytes y recomponerlo while ((len =
	 * in.read(buffer)) > 0) { out.write(buffer, 0, len); } // end while
	 * out.flush(); } catch (Exception e) { throw e; } finally { in.close();
	 * out.close(); } // end ty } // end Copiar
	 * 
	 * private static void Copiar(String dirOrigen, String dirDestino) throws
	 * Exception { InputStream in = new FileInputStream(dirOrigen); OutputStream
	 * out = new FileOutputStream(dirDestino);
	 * 
	 * byte[] buffer = new byte[1024]; int len;
	 * 
	 * try { // recorrer el array de bytes y recomponerlo while ((len =
	 * in.read(buffer)) > 0) { out.write(buffer, 0, len); } // end while
	 * out.flush(); } catch (Exception e) { throw e; } finally { in.close();
	 * out.close(); } // end ty } // end Copiar
	 */
	private void generateXML() {
		try {
			String nameXML = "databaseconfiguration";

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, nameXML, null);
			document.setXmlVersion("1.0");

			// Main Node
			Element root = document.getDocumentElement();

			Element itemUsername = document.createElement("username");
			Text nodeUsernameValue = document.createTextNode("postgres");
			itemUsername.appendChild(nodeUsernameValue);

			root.appendChild(itemUsername);

			Element itemPassword = document.createElement("password");
			Text nodePasswordValue = document.createTextNode("3325230E8CFE8DA79488EE7BE3CE4AE8");
			itemPassword.appendChild(nodePasswordValue);

			root.appendChild(itemPassword);

			Element itemPortnumber = document.createElement("portnumber");
			Text nodePortnumberValue = document.createTextNode("5432");
			itemPortnumber.appendChild(nodePortnumberValue);

			root.appendChild(itemPortnumber);

			Element itemDatabase = document.createElement("database");
			Text nodeDatabaseValue = document.createTextNode("ProyectoFinal");
			itemDatabase.appendChild(nodeDatabaseValue);

			root.appendChild(itemDatabase);

			// Generate XML
			Source source = new DOMSource(document);
			// Indicamos donde lo queremos almacenar
			Result result = new StreamResult(new java.io.File(PATH + "/Configuration/" + nameXML + ".xml")); // nombre
			// del
			// archivo
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);
		} catch (ParserConfigurationException | TransformerFactoryConfigurationError | TransformerException e) {
			System.err.println(e);
		}
	}

}
