package tn.wevioo.tools.xml.handler;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import nordnet.architecture.exceptions.explicit.MalformedXMLException;
import nordnet.architecture.exceptions.implicit.NullException;

public class XmlHandler {
	private static final Log LOGGER = LogFactory.getLog(XmlHandler.class);

	public static final String XSI_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";

	public static final String XSI_NILLABLE = "nil";

	public XmlHandler() {
	}

	public Document createEmptyDocument() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Creating the XML factory...");
			}
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = dbf.newDocumentBuilder();

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Creating and returning the empty document...");
			}
			return documentBuilder.newDocument();
		} catch (ParserConfigurationException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			throw new IllegalStateException(e.getMessage());
		}
	}

	public Element createElement(Document parent, String nodeName) {
		return createElement(parent, nodeName, "");
	}

	public Element createElement(Document parent, String nodeName, String nodeValue) {
		return createElement(parent, nodeName, nodeValue);
	}

	public Element createElement(Document parent, String nodeName, Object nodeValue) {
		return createElement(parent, nodeName, nodeValue, null);
	}

	public Element createElement(Document parent, String nodeName, Object nodeValue, String nodePrefix) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (parent == null) {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn("The parent can not be null.");
			}
			return null;
		}

		if ((nodeName == null) || (nodeName.trim().length() == 0)) {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn("The node name can not be null or empty.");
			}
			return null;
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Creating the node [" + nodeName + "]...");
		}
		Element node = parent.createElement(nodeName);

		if (nodeValue != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Setting the node value [" + nodeValue + "]...");
			}
			node.setTextContent(nodeValue.toString());
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The node value is null.");
				LOGGER.debug("Setting the 'nil' attribute to the element...");
			}
			node.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "nil", Boolean.TRUE.toString());
		}

		if (nodePrefix != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Setting the node prefix [" + nodePrefix + "]...");
			}
			node.setPrefix(nodePrefix);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Returning created node...");
		}
		return node;
	}

	public Node getFirstChildByName(Node parent, String childName) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (parent == null) {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn("The received node is null.");
				LOGGER.warn("Returning null...");
			}
			return null;
		}

		if (childName == null) {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn("The received child name is null.");
				LOGGER.warn("Returning null...");
			}
			return null;
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Getting the node children...");
		}
		NodeList children = parent.getChildNodes();

		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Browsing all the children...");
		Node currentChild;
		Integer localInteger1;
		for (Integer index = Integer.valueOf(0); index.intValue() < children
				.getLength(); localInteger1 = index = Integer.valueOf(index.intValue() + 1)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Getting the current child...");
			}
			currentChild = children.item(index.intValue());

			if ((currentChild.getNodeName() != null) && (currentChild.getNodeName().equals(childName))) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("The child corresponds. Returning it...");
				}
				return currentChild;
			}
			// currentChild = index;
		}

		if (LOGGER.isWarnEnabled()) {
			LOGGER.warn("No child has been found.");
			LOGGER.warn("Returning null...");
		}
		return null;
	}

	public Node getFirstElementChild(Node parent) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (parent == null) {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn("The parent can not be null.");
			}
			return null;
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Getting the children of the received parent...");
		}
		NodeList children = parent.getChildNodes();

		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Browsing all the children...");
		Node currentNode;
		Integer localInteger1;
		for (Integer index = Integer.valueOf(0); index.intValue() < children
				.getLength(); localInteger1 = index = Integer.valueOf(index.intValue() + 1)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Getting current node...");
			}
			currentNode = children.item(index.intValue());

			if (currentNode.getNodeType() == 1) {
				return currentNode;
			}
			// currentNode = index;
		}

		if (LOGGER.isWarnEnabled()) {
			LOGGER.warn("No element node has been found.");
			LOGGER.warn("Returning null...");
		}
		return null;
	}

	public Node getFirstNotEmptyElementChild(Node parent) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		NodeList nodeList = parent.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentNode = nodeList.item(i);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("New element child [" + currentNode.getNodeName() + "]");
			}
			if ((currentNode.getNodeName() != null) && (currentNode.getNodeName().length() > 0)
					&& (!currentNode.getNodeName().startsWith("#"))) {

				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("The node is valid. Returning it.");
				}
				return currentNode;
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("No node has been found. Returning null.");
		}
		return null;
	}

	public String getNodeAsString(Node node) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (node == null) {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn("The received node is null.");
				LOGGER.warn("Returning null...");
			}
			return null;
		}

		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Instantiating the XML transformer...");
			}
			Transformer identityTransformer = TransformerFactory.newInstance().newTransformer();
			identityTransformer.setOutputProperty("omit-xml-declaration", "yes");

			StringWriter sw = new StringWriter();

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Creating source and result variables...");
			}
			Source source = new DOMSource(node);
			Result result = new StreamResult(sw);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Transforming the source into the result...");
			}
			identityTransformer.transform(source, result);

			String resultString = sw.getBuffer().toString();

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Returning [" + resultString + "].");
			}
			return resultString;
		} catch (TransformerConfigurationException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			throw new IllegalStateException(e.getMessage(), e);
		} catch (TransformerException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public String getNodeChildrenAsString(Node node) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (node == null) {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn("The received node is null.");
				LOGGER.warn("Returning null...");
			}
			return null;
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Getting the node children...");
		}
		NodeList nodeList = node.getChildNodes();
		Integer length = Integer.valueOf(nodeList.getLength());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(length + " children.");
		}

		StringBuffer resultBuffer = new StringBuffer();

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Browsing all the children...");
		}
		for (int i = 0; i < length.intValue(); i++) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Getting the current child...");
			}
			Node childNode = nodeList.item(i);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Getting the child as string...");
			}
			resultBuffer.append(getNodeAsString(childNode));
		}

		String result = resultBuffer.toString();

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Returning [" + result + "]...");
		}
		return result;
	}

	public Document parse(String pXMLContent) throws MalformedXMLException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
			LOGGER.debug("XML to parse :\n[" + pXMLContent + "].");
		}

		if ((pXMLContent == null) || (pXMLContent.trim().length() == 0)) {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn("The XML content can not be null or empty.");
			}
			throw new NullException(NullException.NullCases.NULL_EMPTY, "XML content");
		}

		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Transforming and returning the XML content into DOM document...");
			}
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			return dbf.newDocumentBuilder().parse(new ByteArrayInputStream(pXMLContent.getBytes()));
		} catch (Exception ex) {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn("The XML content does not respect a valid structure.");
			}
			throw new MalformedXMLException(MalformedXMLException.MalformedCases.INVALID_STRUCTURE, pXMLContent, ex);
		}
	}

	public String removeXMLDeclaration(String xmlStream) throws MalformedXMLException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (xmlStream == null) {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn("The received XML stream is null.");
				LOGGER.warn("Returning null...");
			}
			return null;
		}

		if (xmlStream.trim().startsWith("<?")) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The XML stream starts with an XML declaration.");
				LOGGER.debug("Computiong the index of start and end of the XML declaration...");
			}

			int endIndex = xmlStream.indexOf("?>") + 1;

			if (endIndex == 0) {
				if (LOGGER.isWarnEnabled()) {
					LOGGER.warn("The XML declaration is malformed : correctly started, but no end found.");
				}
				throw new MalformedXMLException(MalformedXMLException.MalformedCases.INVALID_STRUCTURE, xmlStream);
			}

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Removing the XML declaration from the received XML stream...");
			}
			xmlStream = xmlStream.substring(endIndex + 1);

		} else if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("There is no XML declaration as the beginning of the XML stream.");
		}

		if (LOGGER.isInfoEnabled()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Result [" + xmlStream + "].");
			}
			LOGGER.info("Returning XML without XML declaration...");
		}
		return xmlStream;
	}
}
