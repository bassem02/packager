package tn.wevioo.tools.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import nordnet.architecture.exceptions.explicit.ResourceAccessException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.utils.ErrorCode;

@Deprecated
public class SchemaFactoryHelper {

	private static final Log LOGGER = LogFactory.getLog(SchemaFactoryHelper.class);

	private static final SchemaFactoryHelper INSTANCE = new SchemaFactoryHelper();

	public SchemaFactoryHelper() {
	}

	public static SchemaFactoryHelper getInstance() {
		return INSTANCE;
	}

	public Schema getSchemaFromURI(String pSchemaURI) throws ResourceAccessException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method.");
			LOGGER.debug("Schema URI to get : [" + pSchemaURI + "].");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Verifying the schema URI is not null or empty...");
		}
		if ((null == pSchemaURI) || (pSchemaURI.trim().length() == 0)) {
			throw new NullException(NullException.NullCases.NULL_EMPTY, "schema URI");
		}

		InputStream urlInputStream = null;

		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Opening a stream on the received schema URI...");
			}
			URL url = new URL(pSchemaURI);
			urlInputStream = url.openStream();

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Creating and returning the schema from the input schema...");
			}
			return SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema")
					.newSchema(new StreamSource(urlInputStream));

		} catch (IOException ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("An exception has occured while trying to read the XSD schema.", ex);
			}
			throw new ResourceAccessException(new ErrorCode("0.1.1.3.1"), new Object[] { pSchemaURI }, ex);
		} catch (SAXException ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("An exception has occured while trying to read the XSD schema.", ex);
			}
			throw new ResourceAccessException(new ErrorCode("0.1.3.1.7"), ex);
		} finally {
			try {
				if (null != urlInputStream) {

					urlInputStream.close();
				}
			} catch (IOException e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("An exception has occured while trying to close the input stream on the XSD schema.");
				}
				throw new IllegalStateException(e.getMessage());
			}
		}
	}
}
