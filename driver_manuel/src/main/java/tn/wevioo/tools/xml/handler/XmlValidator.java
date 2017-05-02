package tn.wevioo.tools.xml.handler;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import nordnet.architecture.exceptions.explicit.MalformedXMLException;
import nordnet.architecture.exceptions.explicit.ResourceAccessException;
import nordnet.architecture.exceptions.implicit.NullException;

public class XmlValidator {
	private static final Log LOGGER = LogFactory.getLog(XmlValidator.class);

	private SAXParserFactory saxParserFactory;

	private XmlHandler xmlHandler;

	private SchemaFactoryHandler schemaFactoryHandler;

	public void setXmlHandler(XmlHandler xmlHandler) {
		this.xmlHandler = xmlHandler;
	}

	public void setSchemaFactoryHandler(SchemaFactoryHandler schemaFactoryHandler) {
		this.schemaFactoryHandler = schemaFactoryHandler;
	}

	public XmlValidator() {
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

	private void isWellFormedXML(String xmlContent) throws MalformedXMLException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method.");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Calling the parsing method in order to validate the structure...");
		}
		xmlHandler.parse(xmlContent);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("The XML content has a valid structure.");
		}
	}

	private void isXSDValidated(String xmlContent, String schemaURI)
			throws ResourceAccessException, MalformedXMLException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if ((null == xmlContent) || (xmlContent.trim().length() == 0)) {
			throw new NullException(NullException.NullCases.NULL_EMPTY, "XML");
		}

		if ((null == schemaURI) || (schemaURI.trim().length() == 0)) {
			throw new NullException(NullException.NullCases.NULL_EMPTY, "schema URI");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("The parameter are valid.");
		}

		StringReader srXML = new StringReader(xmlContent);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Getting XSD schema from the received URI...");
		}
		Schema schema = schemaFactoryHandler.getSchemaFromURI(schemaURI);

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
			throw new MalformedXMLException(MalformedXMLException.MalformedCases.INVALID_SCHEMA, xmlContent, ex);
		} catch (IOException ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("An exception has occured while trying to read the XML content.", ex);
			}
			throw new MalformedXMLException(MalformedXMLException.MalformedCases.INVALID_SCHEMA, xmlContent, ex);

		} finally {
			srXML.close();
		}
	}

	public void validate(String xmlContent, String schemaURI) throws MalformedXMLException, ResourceAccessException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Validating the XML content structure...");
		}
		isWellFormedXML(xmlContent);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("The XML content is well formed.");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Validating the XML content against the schema....");
		}
		isXSDValidated(xmlContent, schemaURI);
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
}
