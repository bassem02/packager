package tn.wevioo.driverManual.tools.xml;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import nordnet.architecture.exceptions.explicit.MalformedXMLException;
import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.explicit.ResourceAccessException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.utils.ErrorCode;

@Deprecated
public final class XMLValidator {

	private static final Log LOGGER = LogFactory.getLog(XMLValidator.class);

	private static final XMLValidator INSTANCE = new XMLValidator();

	private static final String XSD_ATTRIBUTE_NAME = "xsi:noNamespaceSchemaLocation";

	private SAXParserFactory saxParserFactory;

	private XMLValidator() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Instantiating the SAX Parser Factory...");
		}
		saxParserFactory = SAXParserFactory.newInstance();
		saxParserFactory.setNamespaceAware(true);
		saxParserFactory.setValidating(false);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("XMLValidator successfully instantiated.");
		}
	}

	public static XMLValidator getInstance() {
		return INSTANCE;
	}

	private void isWellFormedXML(String pXMLContent) throws MalformedXMLException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method.");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Calling the parsing method in order to validate the structure...");
		}
		XMLHelper.getInstance().parse(pXMLContent);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("The XML content has a valid structure.");
		}
	}

	private void isXSDValidated(String pXMLContent, String pSchemaURI)
			throws ResourceAccessException, MalformedXMLException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if ((null == pXMLContent) || (pXMLContent.trim().length() == 0)) {
			throw new NullException(NullException.NullCases.NULL_EMPTY, "XML");
		}

		if ((null == pSchemaURI) || (pSchemaURI.trim().length() == 0)) {
			throw new NullException(NullException.NullCases.NULL_EMPTY, "schema URI");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("The parameter are valid.");
		}

		StringReader srXML = new StringReader(pXMLContent);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Getting XSD schema from the received URI...");
		}
		Schema schema = SchemaFactoryHelper.getInstance().getSchemaFromURI(pSchemaURI);

		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Creating a validator from the schema...");
			}
			Validator validator = schema.newValidator();

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Validating the XML content from the schema...");
			}
			validator.validate(new StreamSource(srXML));
		} catch (SAXException ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("The XML content does not respect the schema.");
			}
			throw new MalformedXMLException(MalformedXMLException.MalformedCases.INVALID_SCHEMA, pXMLContent, ex);
		} catch (IOException ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("An exception has occured while trying to read the XML content.", ex);
			}
			throw new MalformedXMLException(MalformedXMLException.MalformedCases.INVALID_SCHEMA, pXMLContent, ex);

		} finally {
			srXML.close();
		}
	}

	public void validate(String pXMLContent, String pSchemaURI) throws MalformedXMLException, ResourceAccessException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Validating the XML content structure...");
		}
		isWellFormedXML(pXMLContent);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("The XML content is well formed.");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Validating the XML content against the schema....");
		}
		isXSDValidated(pXMLContent, pSchemaURI);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("The XML content validates the schema...");
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("The XML content is totally valid.");
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Ending method.");
			}
		}
	}

	public String extractXSDUri(String pXMLContent) throws MalformedXMLException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method.");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Parsing XML...");
		}
		Document xml = XMLHelper.getInstance().parse(pXMLContent);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Extracting and returning the XSD from the parsed XML...");
		}
		return extractXSDUriFromDocument(xml);
	}

	private String extractXSDUriFromDocument(Node pXMLStream) throws MalformedXMLException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if ((pXMLStream instanceof Document)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Processing a Document...");
				LOGGER.debug("Getting document's children...");
			}

			NodeList children = pXMLStream.getChildNodes();

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(children.getLength() + " children.");
				LOGGER.debug("Browsing children...");
			}

			int currentIndex = 0;
			if (currentIndex < children.getLength()) {
				return extractXSDUriFromDocument(children.item(currentIndex));
			}
		} else if ((pXMLStream instanceof Element)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Processing the element [" + ((Element) pXMLStream).getNodeName() + "]...");
			}
			Element currentNode = (Element) pXMLStream;
			if (currentNode.getAttributes() != null) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("The element has " + currentNode.getAttributes().getLength() + " attributes.");
				}
				if ((currentNode.getAttribute("xsi:noNamespaceSchemaLocation") != null)
						&& (currentNode.getAttribute("xsi:noNamespaceSchemaLocation").trim().length() != 0)) {

					if (LOGGER.isInfoEnabled()) {
						LOGGER.info("The XSD has been found : ["
								+ currentNode.getAttribute("xsi:noNamespaceSchemaLocation") + "]");
					}
					return currentNode.getAttribute("xsi:noNamespaceSchemaLocation");
				}
			}
		}

		if (LOGGER.isErrorEnabled()) {
			LOGGER.error("No XSD URI has been found in the XML content.");
		}
		NotFoundException nfe = new NotFoundException(new ErrorCode("0.2.1.3.2"),
				new Object[] { "XSD URI", "attribute", "xsi:noNamespaceSchemaLocation" });

		throw new MalformedXMLException(MalformedXMLException.MalformedCases.INVALID_STRUCTURE,
				XMLHelper.getInstance().getNodeAsString(pXMLStream), nfe);
	}
}
