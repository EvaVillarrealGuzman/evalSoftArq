package project.chequerUCM;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Chequer {

	String path;
	Document doc;

	public Chequer(String pathName) {
		this.path = pathName;
	}

	public String getPathName() {
		return path;
	}

	public void setPathName(String pathName) {
		this.path = pathName;
	}

	/**
	 * Chequea que el UCM sea válido Si devuelve 0 es porque es válido Si
	 * devuelve 1 es porque existen elementos en el UCM que no están en el
	 * 'dibujo'; Si devuelve 2 es porque existe más de un startPoint o más de un
	 * endPoint; Si devuelve 3 es porque no es correcta la definición de los
	 * metadatos en las responsabilidades; Si devuelve 4 es porque existen
	 * elementos con nombres duplicados; Si devuleve 5 es porque no existen
	 * responsabilidades; Si devuelve 6 es porque no existen componentes
	 * 
	 * @return
	 */
	public int isValid() {

		try {

			File fXmlFile = new File(path);

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;

			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			if (isOneStartPointEndPoint() == 0) {
				if (isResponsibilities() == 0) {
					if (isComponents() == 0) {
						if (isDuplicateName() == 0) {
							if (isInDefinition() == 0) {
								if (isMetadataInResponsibilities() == 0) {
									return 0;
								} else {
									return 3;
								}
							} else {
								return 1;
							}
						} else {
							return 4;
						}
					} else {
						return 6;
					}
				} else {
					return 5;
				}
			} else {
				return 2;
			}

		} catch (

		Exception e) {
			return -1;
		}

	}

	/**
	 * Chequea que exista solo un start point y un solo end point
	 * 
	 * @return
	 */
	private int isOneStartPointEndPoint() {
		Boolean startPointFirst = false;
		Boolean endPointFirst = false;

		NodeList nodesList = doc.getElementsByTagName("nodes");

		for (int i = 0; i < nodesList.getLength(); i++) {

			Node node = nodesList.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eNode = (Element) node;
				// chequea si existe más de un start point
				if (eNode.getAttribute("xsi:type").equals("ucm.map:StartPoint")) {
					if (startPointFirst == false) {
						startPointFirst = true;
					} else {
						return -1;
					}
				}
				// chequea si existe más de un end point
				if (eNode.getAttribute("xsi:type").equals("ucm.map:EndPoint")) {
					if (endPointFirst == false) {
						endPointFirst = true;
					} else {
						return -1;
					}
				}
			}
		}
		// si llega a este punto, es porque existe un solo start point y un end
		// point
		return 0;
	}

	// TODO agregar chequeo de MeanTimeBRequest en start point
	/**
	 * Chequea si los metadatos de cada responsabilidad, están ingresador
	 * correctamente
	 * 
	 * @return
	 */
	private int isMetadataInResponsibilities() {
		Boolean isMeanExecutionTime = false;
		Boolean isMeanRecoveryTime = false;
		Boolean isMeanDowntime = false;
		Boolean isMeanTimeBFail = false;

		NodeList responsibilitiesList = doc.getElementsByTagName("responsibilities");

		for (int i = 0; i < responsibilitiesList.getLength(); i++) {

			Node responsibility = responsibilitiesList.item(i);

			if (responsibility.getNodeType() == Node.ELEMENT_NODE) {
				Element eResponsibility = (Element) responsibility;

				NodeList metadataList = eResponsibility.getElementsByTagName("metadata");

				for (int k = 0; k < metadataList.getLength(); k++) {
					Node metadata = metadataList.item(k);
					Element eMetadata = (Element) metadata;
					String nameMetadata = eMetadata.getAttribute("name");

					if (nameMetadata.equals("MeanExecutionTime")) {
						try {
							String valueMetadata = eMetadata.getAttribute("value");
							double value = Double.parseDouble(valueMetadata);
							if (!isMeanExecutionTime) {
								isMeanExecutionTime = true;
							} else {
								return -1;
							}
						} catch (Exception e) {
							// Si llega acá, es porque el valor ingresado no se
							// puede castear a double
							// por lo tanto, isMeanExecutionTime queda igual a
							// false
						}
					} else if (nameMetadata.equals("MeanRecoveryTime")) {
						try {
							String valueMetadata = eMetadata.getAttribute("value");
							double value = Double.parseDouble(valueMetadata);
							if (!isMeanRecoveryTime) {
								isMeanRecoveryTime = true;
							} else {
								return -1;
							}
						} catch (Exception e) {
							// Si llega acá, es porque el valor ingresado no se
							// puede castear a double
							// por lo tanto, isMeanRecoveryTime queda igual a
							// false
						}
					} else if (nameMetadata.equals("MeanDowntime")) {
						try {
							String valueMetadata = eMetadata.getAttribute("value");
							double value = Double.parseDouble(valueMetadata);
							if (!isMeanDowntime) {
								isMeanDowntime = true;
							} else {
								return -1;
							}
						} catch (Exception e) {
							// Si llega acá, es porque el valor ingresado no se
							// puede castear a double
							// por lo tanto, isMeanDowntime queda igual a false
						}
					} else if (nameMetadata.equals("MeanTimeBFail")) {
						try {
							String valueMetadata = eMetadata.getAttribute("value");
							double value = Double.parseDouble(valueMetadata);
							if (!isMeanTimeBFail) {
								isMeanTimeBFail = true;
							} else {
								return -1;
							}
						} catch (Exception e) {
							// Si llega acá, es porque el valor ingresado no se
							// puede castear a double
							// por lo tanto, isMeanTimeBFail queda igual a false
						}
					}
				}

			}
		}

		if (isMeanTimeBFail && isMeanDowntime && isMeanRecoveryTime && isMeanExecutionTime) {
			return 0;
		}

		return -1;
	}

	/**
	 * Chequea que existan no elementos en el UCM que no se muestren en el
	 * dibujo
	 * 
	 * @return
	 */
	private int isInDefinition() {

		NodeList responsibilitiesList = doc.getElementsByTagName("responsibilities");

		for (int i = 0; i < responsibilitiesList.getLength(); i++) {
			Node responsibility = responsibilitiesList.item(i);

			if (responsibility.getNodeType() == Node.ELEMENT_NODE) {
				Element eResponsibility = (Element) responsibility;

				String respRefs = eResponsibility.getAttribute("respRefs");
				if (respRefs.equals("")) {
					return -1;
				}

			}
		}

		NodeList componentsList = doc.getElementsByTagName("components");

		for (int i = 0; i < componentsList.getLength(); i++) {
			Node component = componentsList.item(i);

			if (component.getNodeType() == Node.ELEMENT_NODE) {
				Element eComponent = (Element) component;

				String contRefs = eComponent.getAttribute("contRefs");
				if (contRefs.equals("")) {
					return -1;
				}

			}
		}
		return 0;
	}

	/**
	 * Chequea que exista al menos una responsabilidad
	 * 
	 * @return
	 */
	private int isResponsibilities() {
		NodeList responsibilitiesList = doc.getElementsByTagName("responsibilities");
		if (responsibilitiesList.getLength() > 0) {
			return 0;
		}
		return -1;
	}

	/**
	 * Chequea que exista al menos un componente
	 * 
	 * @return
	 */
	private int isComponents() {
		NodeList componentsList = doc.getElementsByTagName("components");
		if (componentsList.getLength() > 0) {
			return 0;
		}
		return -1;
	}

	/**
	 * Chequea que no existan nombres duplicados de responsabilidades y de
	 * componentes
	 * 
	 * @return
	 */
	private int isDuplicateName() {
		NodeList responsibilitiesList = doc.getElementsByTagName("responsibilities");

		for (int i = 0; i < responsibilitiesList.getLength(); i++) {
			Node responsibility = responsibilitiesList.item(i);

			if (responsibility.getNodeType() == Node.ELEMENT_NODE) {
				Element eResponsibility = (Element) responsibility;

				String respRefs = eResponsibility.getAttribute("respRefs");
				String[] respRefsList = respRefs.split(" ");
				if (respRefsList.length > 1) {
					return -1;
				}
			}
		}

		NodeList componentsList = doc.getElementsByTagName("components");

		for (int i = 0; i < componentsList.getLength(); i++) {
			Node component = componentsList.item(i);

			if (component.getNodeType() == Node.ELEMENT_NODE) {
				Element eComponent = (Element) component;

				String contRefs = eComponent.getAttribute("contRefs");
				String[] contRefsList = contRefs.split(" ");
				if (contRefsList.length > 1) {
					return -1;
				}

			}
		}
		return 0;
	}

}
