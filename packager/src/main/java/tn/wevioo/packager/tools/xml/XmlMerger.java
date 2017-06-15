package tn.wevioo.packager.tools.xml;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlMerger {

	public static String merge(String priorityXml, String secondaryXml)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {

		String resultXml = "";

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);

		DocumentBuilder db = dbf.newDocumentBuilder();

		InputSource priorityIs = new InputSource();
		priorityIs.setCharacterStream(new StringReader(priorityXml));

		InputSource secondaryIs = new InputSource();
		secondaryIs.setCharacterStream(new StringReader(secondaryXml));

		Document priorityDoc = db.parse(priorityIs);
		Document secondaryDoc = db.parse(secondaryIs);

		NodeList priorityNode = priorityDoc.getElementsByTagName("manual:productProperties");

		NodeList secondaryNode = secondaryDoc.getElementsByTagName("manual:productProperties");

		Element priorityElement = (Element) priorityNode.item(0);
		Element secondaryElement = (Element) secondaryNode.item(0);

		if (priorityElement.getElementsByTagName("manual:hexacle").item(0) == null) {
			if (secondaryElement.getElementsByTagName("manual:hexacle").item(0) != null) {
				Element hexacle = priorityDoc.createElement("manual:hexacle");
				hexacle.appendChild(priorityDoc.createTextNode(getCharacterDataFromElement(
						(Element) secondaryElement.getElementsByTagName("manual:hexacle").item(0))));
				priorityElement.appendChild(hexacle);
			}
		}

		if (priorityElement.getElementsByTagName("manual:idClient").item(0) == null) {
			if (secondaryElement.getElementsByTagName("manual:idClient").item(0) != null) {
				Element idClient = priorityDoc.createElement("manual:idClient");
				idClient.appendChild(priorityDoc.createTextNode(getCharacterDataFromElement(
						(Element) secondaryElement.getElementsByTagName("manual:idClient").item(0))));
				priorityElement.appendChild(idClient);
			}
		}

		if (priorityElement.getElementsByTagName("manual:infoCompl").item(0) == null) {
			if (secondaryElement.getElementsByTagName("manual:infoCompl").item(0) != null) {
				Element infoCompl = priorityDoc.createElement("manual:infoCompl");
				infoCompl.appendChild(priorityDoc.createTextNode(getCharacterDataFromElement(
						(Element) secondaryElement.getElementsByTagName("manual:infoCompl").item(0))));
				priorityElement.appendChild(infoCompl);
			}
		}

		if (priorityElement.getElementsByTagName("manual:typeProduct").item(0) == null) {
			if (secondaryElement.getElementsByTagName("manual:typeProduct").item(0) != null) {
				Element typeProduct = priorityDoc.createElement("manual:typeProduct");
				typeProduct.appendChild(priorityDoc.createTextNode(getCharacterDataFromElement(
						(Element) secondaryElement.getElementsByTagName("manual:typeProduct").item(0))));
				priorityElement.appendChild(typeProduct);
			}
		}

		priorityDoc.removeChild((Element) priorityNode.item(0));
		priorityDoc.appendChild(priorityElement);

		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource domSource = new DOMSource(priorityDoc);
		transformer.transform(domSource, result);
		writer.flush();
		return writer.toString();

	}

	public static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}
}
