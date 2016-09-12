package software.BusinessLogic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import software.DataManager.HibernateManager;
import software.DomainModel.SoftwareArchitectureSpecificationEntity.ANDFork;
import software.DomainModel.SoftwareArchitectureSpecificationEntity.ANDJoin;
import software.DomainModel.SoftwareArchitectureSpecificationEntity.Architecture;
import software.DomainModel.SoftwareArchitectureSpecificationEntity.ArchitectureElement;
import software.DomainModel.SoftwareArchitectureSpecificationEntity.CompositeComponent;
import software.DomainModel.SoftwareArchitectureSpecificationEntity.EndPoint;
import software.DomainModel.SoftwareArchitectureSpecificationEntity.ORFork;
import software.DomainModel.SoftwareArchitectureSpecificationEntity.ORJoin;
import software.DomainModel.SoftwareArchitectureSpecificationEntity.Path;
import software.DomainModel.SoftwareArchitectureSpecificationEntity.PathElement;
import software.DomainModel.SoftwareArchitectureSpecificationEntity.Responsibility;
import software.DomainModel.SoftwareArchitectureSpecificationEntity.SimpleComponent;
import software.DomainModel.SoftwareArchitectureSpecificationEntity.SpecificationParameter;
import software.DomainModel.SoftwareArchitectureSpecificationEntity.StartPoint;

/**
 * This class is responsible for the management package: Software Architecture
 * Specification
 * 
 * @author: FEM
 * @version: 23/08/2016
 */
public class SoftwareArchitectureSpecificationManager extends HibernateManager implements TreeSelectionListener {

	/**
	 * Attributes
	 */
	private software.DomainModel.AnalysisEntity.System system;
	Document doc;
	private DefaultTreeModel model;
	private Architecture arch;
	private JTree tree;
	private StartPoint startPoint;
	private Set<ArchitectureElement> archElements = new HashSet<ArchitectureElement>();
	private Set<PathElement> pathElements = new HashSet<PathElement>();
	private Set<Responsibility> responsibilities = new HashSet<Responsibility>();
	private CompositeComponent child;
	

	/**
	 * Getters and Setters
	 */
	public void setSystem(software.DomainModel.AnalysisEntity.System psystem) {
		this.system = psystem;
	}

	public software.DomainModel.AnalysisEntity.System getSystem() {
		return this.system;
	}

	/**
	 * 
	 * @return True if there are systems whose state==true, else return false
	 * 
	 */
	public boolean existSystemTrue() {
		if (listSystem().isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @return ComboBoxModel with system names whose state==true
	 */
	public software.DomainModel.AnalysisEntity.System[] getComboModelSystem() { // NOPMD
																				// by
																				// Usuario-Pc
																				// on
																				// 10/06/16
																				// 21:42
		ArrayList<software.DomainModel.AnalysisEntity.System> systems = new ArrayList<software.DomainModel.AnalysisEntity.System>();
		for (software.DomainModel.AnalysisEntity.System auxTipo : this.listSystem()) {
			systems.add(auxTipo);
		}
		software.DomainModel.AnalysisEntity.System[] arraySystem = new software.DomainModel.AnalysisEntity.System[systems
				.size()];
		systems.toArray(arraySystem);
		return arraySystem;
	}

	/**
	 * 
	 * @return List<System> with the system names whose state==true
	 */
	public List<software.DomainModel.AnalysisEntity.System> listSystem() {
		return this.listClass(software.DomainModel.AnalysisEntity.System.class, "systemName", true);
	}

	public Boolean updateSystem() {
		return this.updateObject(this.getSystem());
	}

	public ArrayList<String> getPathUCMs() {
		Iterator it = this.getSystem().getArchitectures().iterator();
		if (it.hasNext()) {
			Architecture a = (Architecture) it.next();
			return a.getPathUCMs();
		} else {
			return null;
		}
	}

	public void setPathUCMs(ArrayList<String> pathUCMs) {
		Iterator it = this.getSystem().getArchitectures().iterator();
		if (it.hasNext()) {
			Architecture a = (Architecture) it.next();
			a.setPathUCMs(pathUCMs);
			createArchitecture(a);
		} else {
			Architecture pa = new Architecture(pathUCMs);
			this.getSystem().getArchitectures().add(pa);
			createArchitecture(pa);
		}
	}

	public Set<Architecture> getArchitectures() {
		return this.getSystem().getArchitectures();
	}

	public Architecture getArch() {
		return arch;
	}

	public void setArch(Architecture arch) {
		this.arch = arch;
	}

	public void createArchitecture(Architecture parch) {
		try {
			this.setArch(parch);
			// lee el archivo xml que se convertir� en �rbol
			File fXmlFile = new File(parch.getPathUCMs().get(0));

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
			Boolean isRootNodeASimpleComponent = true;

			// busca una lista de los elementos con el tag correspondiente
			NodeList contRefsList = doc.getElementsByTagName("contRefs");
			ArchitectureElement rootNode = null;

			DefaultMutableTreeNode parentN = null;
			// busca el padre del �rbol que se crear�
			for (int i = 0; i < contRefsList.getLength(); i++) {
				if (contRefsList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element eContRef = (Element) contRefsList.item(i);
					String parent = eContRef.getAttribute("parent");
					if (parent.equals("")) {
						String id = eContRef.getAttribute("id");
						if (isASimpleComponent(eContRef)){
							rootNode = new SimpleComponent(this.translateNameComponent(id));
							parentN = new DefaultMutableTreeNode(rootNode);
						}else{
							rootNode = new CompositeComponent(this.translateNameComponent(id));
							parentN = new DefaultMutableTreeNode(rootNode);
							isRootNodeASimpleComponent = false;
						}
						//rootNode = new Domain.Component(Integer.parseInt(id), this.translateNameComponent(id));
					}
				}
			}
			archElements.add(rootNode);
			// Definimos el modelo donde se agregaran los nodos
			model = new DefaultTreeModel(parentN);

			// agregamos el modelo al arbol, donde previamente establecimos la
			// raiz
			tree = new JTree(model);

			// definimos los eventos
			tree.getSelectionModel().addTreeSelectionListener(this);

			// Cada Padre crea a su hijo, recursivamente
			creationComponent(parentN, isRootNodeASimpleComponent);

			creationStartPoint();
			
			Set<Path> paths = new HashSet<Path>();
			Path path = new Path();
			path.setPathElements(pathElements);
			paths.add(path);
			this.getArch().setPaths(paths);
			this.getArch().setArchitectureElements(archElements);
			
			updateObject(parch);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Devuelve el nombre correspondiente de un elemento componente con un
	 * determinado id (value)
	 * 
	 * @param id
	 * @return
	 */
	private String translateNameComponent(String id) {
		NodeList componentsList = doc.getElementsByTagName("components");

		for (int i = 0; i < componentsList.getLength(); i++) {

			Node component = componentsList.item(i);

			if (component.getNodeType() == Node.ELEMENT_NODE) {
				Element eComponent = (Element) component;
				if (eComponent.getAttribute("contRefs").equals(id)) {
					return eComponent.getAttribute("name");
				}
			}
		}
		return "";
	}

	/**
	 * Devuelve el nombre correspondiente de un elemento responsabilidad con un
	 * determinado id (value)
	 * 
	 * @param value
	 * @return
	 */
	private String translateNameSimpleElement(String value) {
		NodeList responsibilitiesNode = doc.getElementsByTagName("responsibilities");

		for (int i = 0; i < responsibilitiesNode.getLength(); i++) {

			Node responsibility = responsibilitiesNode.item(i);

			if (responsibility.getNodeType() == Node.ELEMENT_NODE) {
				Element eResponsibility = (Element) responsibility;
				if (eResponsibility.getAttribute("respRefs").equals(value)) {
					return eResponsibility.getAttribute("name");
				}
			}
		}

		NodeList nodesList = doc.getElementsByTagName("nodes");
		for (int i = 0; i < nodesList.getLength(); i++) {

			Node nNode = nodesList.item(i);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String typeNode = eElement.getAttribute("xsi:type");
				if (eElement.getAttribute("id").equals(value) && !typeNode.equals("ucm.map:EndPoint")
						&& !typeNode.equals("ucm.map:StartPoint")) {
					return eElement.getAttribute("name");
				}
			}
		}

		return null;
	}

	private void creationComponent(DefaultMutableTreeNode parentN, boolean pisRootNodeASimpleComponent) {
		NodeList contRefsList = doc.getElementsByTagName("contRefs");
		int index = 0;
		ArchitectureElement dataParent;
		Boolean isRootNodeASimpleComponent = true;

		if (pisRootNodeASimpleComponent){
			dataParent = (SimpleComponent) parentN.getUserObject();
		}else{
			dataParent = (CompositeComponent) parentN.getUserObject();
		}
		for (int j = 0; j < contRefsList.getLength(); j++) {
			Node contRef = contRefsList.item(j);
			if (contRef.getNodeType() == Node.ELEMENT_NODE) {
				Element eContRef = (Element) contRef;
				// busca el elemento del archivo xml que representa al padre del
				// sub�rbol actual en el �rbol
				if (this.translateNameComponent(eContRef.getAttribute("id")).equals(dataParent.getName())) {
					String children = eContRef.getAttribute("children");
					if (!children.equals("")) {
						// crea un vector con los hijos del padre
						String[] childArray = children.split(" ");
						for (int i = 0; i < childArray.length; i++) {

							Element eChild = getNodeOfComponent(childArray[i]);

							String id = eChild.getAttribute("id");
							ArchitectureElement child;
							if (isASimpleComponent(eChild)){
								child = new SimpleComponent(this.translateNameComponent(id));
							}else{
								child = new CompositeComponent(this.translateNameComponent(id));
								isRootNodeASimpleComponent = false;
							}
							archElements.add(child);
							DefaultMutableTreeNode hijoN = new DefaultMutableTreeNode(child);
							model.insertNodeInto(hijoN, parentN, index);
							index++;
							// llama recursivamente, para que si este nodo tiene
							// hijos, los cree
							creationComponent(hijoN, isRootNodeASimpleComponent);
						}
					}

					// si se llega a este punto, es porque el nodo no tiene
					// hijos
					// por lo cual se supone que es un componente simple
					// entonces, se buscan sus responsabilidades
					String nodes = eContRef.getAttribute("nodes");
					if (!nodes.equals("")) {
						String[] node = nodes.split(" ");
						for (int k = 0; k < node.length; k++) {
							// Por cada responsabilidad
							this.creationSimpleElement(node[k], parentN, index);
						}
					}
				}
			}
		}
	}

	private int creationSimpleElement(String idNode, DefaultMutableTreeNode parentNode, int index) {
		NodeList responsibilitiesList = doc.getElementsByTagName("responsibilities");
		NodeList nodesList = doc.getElementsByTagName("nodes");

		Element eNode = this.getNodeOfResponsability(idNode, doc);
		responsibilities.clear();

		for (int i = 0; i < responsibilitiesList.getLength(); i++) {
			Node respNode = responsibilitiesList.item(i);

			if (respNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eResponsability = (Element) respNode;

				if (eResponsability.getAttribute("respRefs").equals(idNode)) {

					Responsibility child = new Responsibility(this.translateNameSimpleElement(idNode));

					NodeList metadatasList = eResponsability.getElementsByTagName("metadata");

					// Busca los metadatos
					for (int k = 0; k < metadatasList.getLength(); k++) {
						Node node = metadatasList.item(k);
						Element metadata = (Element) node;
						String metadataName = metadata.getAttribute("name");

						SpecificationParameter sp = new SpecificationParameter();
						if (metadataName.equals("MeanExecutionTime")) {
							sp.setMeanExecutionTime(Double.parseDouble(metadata.getAttribute("value")));
						} else if (metadataName.equals("MeanDowntime")) {
							sp.setMeanDownTime(Double.parseDouble(metadata.getAttribute("value")));
						} else if (metadataName.equals("MeanRecoveryTime")) {
							sp.setMeanRecoveryTime(Double.parseDouble(metadata.getAttribute("value")));
						} else if (metadataName.equals("MeanTimeBFail")) {
							sp.setMeanTimeBFail(Double.parseDouble(metadata.getAttribute("value")));
						}
						
						child.setSpecificationParameter(sp);						
						responsibilities.add(child);

					}

					DefaultMutableTreeNode hijoN = new DefaultMutableTreeNode(child);

					model.insertNodeInto(hijoN, parentNode, index);
					index++;

					return 0;

				}
			}
			
		}
		SimpleComponent dataParent = (SimpleComponent) parentNode.getUserObject();
		dataParent.setResponsabilities(responsibilities);
		
		for (int i = 0; i < nodesList.getLength(); i++) {
			Node node = nodesList.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eCurrentNode = (Element) node;
				String nodeType = eCurrentNode.getAttribute("xsi:type");
				if (eCurrentNode.getAttribute("id").equals(idNode) && !nodeType.equals("ucm.map:StartPoint")){
					if (nodeType.equals("ucm.map:EndPoint")){
						EndPoint child = new EndPoint(this.translateNameSimpleElement(idNode));
						pathElements.add(child);
					} else if (nodeType.equals("ucm.map:OrFork")){
						ORFork child = new ORFork(this.translateNameSimpleElement(idNode)); 
						ArrayList<Double> pathProbabilities = new ArrayList<Double>();

						NodeList metadatasList = eCurrentNode.getElementsByTagName("metadata");
						// Busca los metadatos
						for (int k = 0; k < metadatasList.getLength(); k++) {
							Node metadataNode = metadatasList.item(k);
							Element metadata = (Element) metadataNode;

							pathProbabilities.add(Double.parseDouble(metadata.getAttribute("value")));
						}
						child.setPathProbabilities(pathProbabilities);
						pathElements.add(child);
						
						DefaultMutableTreeNode hijoN = new DefaultMutableTreeNode(child);

						model.insertNodeInto(hijoN, parentNode, index);
						index++;
					}else if (nodeType.equals("ucm.map:OrJoin")){
						ORJoin child = new ORJoin(this.translateNameSimpleElement(idNode));
						pathElements.add(child);
						
						DefaultMutableTreeNode hijoN = new DefaultMutableTreeNode(child);

						model.insertNodeInto(hijoN, parentNode, index);
						index++;
					}else if (nodeType.equals("ucm.map:AndFork")){
						ANDFork child = new ANDFork(this.translateNameSimpleElement(idNode));
						pathElements.add(child);
						
						DefaultMutableTreeNode hijoN = new DefaultMutableTreeNode(child);

						model.insertNodeInto(hijoN, parentNode, index);
						index++;
					}else if (nodeType.equals("ucm.map:AndJoin")){
						ANDJoin child = new ANDJoin(this.translateNameSimpleElement(idNode));
						pathElements.add(child);
						
						DefaultMutableTreeNode hijoN = new DefaultMutableTreeNode(child);

						model.insertNodeInto(hijoN, parentNode, index);
						index++;
					}

				}
			}

		}

		return 0;

	}
//
	private void creationStartPoint() {

		NodeList nodesList = doc.getElementsByTagName("nodes");

		for (int i = 0; i < nodesList.getLength(); i++) {
			Node node = nodesList.item(i);

			Element eNode = (Element) node;

			if (eNode.getAttribute("xsi:type").equals("ucm.map:StartPoint")) {
				NodeList metadatasList = eNode.getElementsByTagName("metadata");

				Node currentNode = metadatasList.item(0);
				Element metadata = (Element) currentNode;

				startPoint = new StartPoint("start point", Double.parseDouble(metadata.getAttribute("value")));
				
			}

		}
		
		pathElements.add(startPoint);
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
	}

	/**
	 * Obtiene los elementos predecesores de un elemento particular
	 * 
	 * @param idSource
	 * @return
	 */
	private ArrayList<String> getSuccessor(String idSource) {
		ArrayList<String> successor = new ArrayList<String>();
		NodeList listConnections = doc.getElementsByTagName("connections");

		for (int i = 0; i < listConnections.getLength(); i++) {

			Node connection = listConnections.item(i);
			Element eConnection = (Element) connection;

			if (idSource.equals(eConnection.getAttribute("source"))) {
				successor.add(translateNameSimpleElement(eConnection.getAttribute("target")));
			}

		}

		return successor;
	}

	/**
	 * Obtiene los elementos sucesores de un elemento particular
	 * 
	 * @param idTarget
	 * @return
	 */
	private ArrayList<String> getPredecessor(String idTarget) {
		ArrayList<String> predecessor = new ArrayList<String>();
		NodeList listConnections = doc.getElementsByTagName("connections");

		for (int i = 0; i < listConnections.getLength(); i++) {

			Node node = listConnections.item(i);
			Element eNode = (Element) node;

			if (idTarget.equals(eNode.getAttribute("target"))) {
				predecessor.add(translateNameSimpleElement(eNode.getAttribute("source")));
			}
		}

		return predecessor;
	}

	private Element getNodeOfResponsability(String idNode, Document doc) {
		NodeList nodesList = doc.getElementsByTagName("nodes");

		for (int i = 0; i < nodesList.getLength(); i++) {

			Node node = nodesList.item(i);
			Element eNode = (Element) node;

			String id = eNode.getAttribute("id");
			if (id.equals(idNode)) {
				return eNode;
			}

		}
		return null;
	}

	private Element getNodeOfComponent(String id) {
		NodeList contRefsList = doc.getElementsByTagName("contRefs");

		for (int i = 0; i < contRefsList.getLength(); i++) {

			Node contRef = contRefsList.item(i);
			Element eContRef = (Element) contRef;

			if (id.equals(eContRef.getAttribute("id"))) {
				return eContRef;
			}
		}

		return null;
	}

	public boolean isInTree(DefaultMutableTreeNode rootNode, DefaultMutableTreeNode memberNode) {
		java.util.Enumeration e = rootNode.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) e.nextElement();
			if (memberNode == currentNode) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<DefaultMutableTreeNode> getLeafs(DefaultMutableTreeNode node) {
		Boolean terminationCondition = true;

		ArrayList<DefaultMutableTreeNode> leafList = new ArrayList<DefaultMutableTreeNode>();

		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		java.util.Enumeration e = root.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) e.nextElement();
			if (currentNode.isLeaf()) {
				DefaultMutableTreeNode parent = currentNode;

				while (terminationCondition) {
					if (parent != root) {
						if (node.getLevel() != parent.getLevel()) {
							parent = (DefaultMutableTreeNode) parent.getParent();
						} else {
							terminationCondition = false;
						}
					} else {
						terminationCondition = false;
					}
				}

				if (node == parent) {
					leafList.add(currentNode);
				}
				terminationCondition = true;
			}
		}
		return leafList;

	}
	
	public boolean isASimpleComponent(Element pelem){
		if (pelem.getAttribute("children").equals("")){
			return true;
		}else{
			return false;
		}
	}
	
}

