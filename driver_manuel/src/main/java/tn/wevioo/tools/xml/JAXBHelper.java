package tn.wevioo.tools.xml;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import nordnet.architecture.exceptions.explicit.ResourceAccessException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.utils.ErrorCode;

@Deprecated
public final class JAXBHelper {
	private static final Log LOGGER = LogFactory.getLog(JAXBHelper.class);

	private static JAXBHelper instance = null;

	public JAXBHelper() {
	}

	public JAXBContext createContext(String packageName) throws ResourceAccessException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if ((packageName == null) || (packageName.trim().length() == 0)) {
			throw new NullException(NullException.NullCases.NULL_EMPTY, "package name");
		}

		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Creating context from [" + packageName + "].");
				LOGGER.debug("Instantiating and returning the JAXBContext...");
			}
			return JAXBContext.newInstance(packageName.trim());
		} catch (JAXBException ex) {
			throw new ResourceAccessException(new ErrorCode("0.1.1.12.1"), ex);
		}
	}

	public JAXBContext createContext(Class jaxClass) throws ResourceAccessException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (jaxClass == null) {
			throw new NullException(NullException.NullCases.NULL, "JAXB Class");
		}

		return createContext(jaxClass.getPackage().getName());
	}

	public JAXBContext createContext(Object jaxObject) throws ResourceAccessException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (jaxObject == null) {
			throw new NullException(NullException.NullCases.NULL, "JAXB Object");
		}

		return createContext(jaxObject.getClass().getPackage().getName());
	}

	public Unmarshaller createUnmarshaller(Class jaxClass) throws ResourceAccessException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Creating the context...");
		}
		JAXBContext context = createContext(jaxClass);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Creating and returning the unmarshaller...");
		}
		return createUnmarshaller(context);
	}

	public Unmarshaller createUnmarshaller(JAXBContext context) throws ResourceAccessException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Calling the right method with null schema...");
		}
		return createUnmarshaller(context, (Schema) null);
	}

	public Unmarshaller createUnmarshaller(JAXBContext context, String schemaURL) throws ResourceAccessException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (schemaURL == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Calling the right method with null schema...");
			}
			return createUnmarshaller(context, (URL) null);
		}

		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Calling the right method...");
			}
			return createUnmarshaller(context, new URL(schemaURL));
		} catch (MalformedURLException ex) {
			throw new ResourceAccessException(new ErrorCode("0.1.1.3.2"), new Object[] { schemaURL });
		}
	}

	public Unmarshaller createUnmarshaller(JAXBContext context, URL schemaURL) throws ResourceAccessException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (schemaURL == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Calling the right method with null schema...");
			}
			return createUnmarshaller(context, (Schema) null);
		}

		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Getting the schema from the given URL...");
			}
			SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = schemaFactory.newSchema(schemaURL);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Calling the right method...");
			}
			return createUnmarshaller(context, schema);
		} catch (SAXException ex) {
			throw new ResourceAccessException(new ErrorCode("0.1.1.3.1"), new Object[] { schemaURL.getPath() });
		}
	}

	public Unmarshaller createUnmarshaller(JAXBContext context, Schema schema) throws ResourceAccessException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (context == null) {
			throw new NullException(NullException.NullCases.NULL, "context");
		}

		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Creating the unmarshaller...");
			}
			Unmarshaller result = context.createUnmarshaller();

			if (schema != null) {
				result.setSchema(schema);
			}

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Returning result...");
			}
			return result;
		} catch (JAXBException ex) {
			throw new ResourceAccessException(new ErrorCode("0.2.1.2.3"), ex);
		}
	}

	public Marshaller createMarshaller(Class jaxClass) throws ResourceAccessException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Creating the context...");
		}
		JAXBContext context = createContext(jaxClass);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Creating and returning the marshaller...");
		}
		return createMarshaller(context);
	}

	public Marshaller createMarshaller(JAXBContext context) throws ResourceAccessException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Calling the right method with null schema...");
		}
		return createMarshaller(context, (Schema) null);
	}

	public Marshaller createMarshaller(JAXBContext context, String schemaURL) throws ResourceAccessException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (schemaURL == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Calling the right method with null schema...");
			}
			return createMarshaller(context, (URL) null);
		}

		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Calling the right method...");
			}
			return createMarshaller(context, new URL(schemaURL));
		} catch (MalformedURLException ex) {
			throw new ResourceAccessException(new ErrorCode("0.1.1.3.2"), new Object[] { schemaURL });
		}
	}

	public Marshaller createMarshaller(JAXBContext context, URL schemaURL) throws ResourceAccessException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (schemaURL == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Calling the right method with null schema...");
			}
			return createMarshaller(context, (Schema) null);
		}

		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Getting the schema from the given URL...");
			}
			SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = schemaFactory.newSchema(schemaURL);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Calling the right method...");
			}
			return createMarshaller(context, schema);
		} catch (SAXException ex) {
			throw new ResourceAccessException(new ErrorCode("0.1.1.3.1"), new Object[] { schemaURL.getPath() });
		}
	}

	public Marshaller createMarshaller(JAXBContext context, Schema schema) throws ResourceAccessException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (context == null) {
			throw new NullException(NullException.NullCases.NULL, "context");
		}

		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Creating the marshaller...");
			}
			Marshaller result = context.createMarshaller();

			if (schema != null) {
				result.setSchema(schema);
			}

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Returning result...");
			}
			return result;
		} catch (JAXBException ex) {
			throw new ResourceAccessException(new ErrorCode("0.2.1.2.3"), ex);
		}
	}

	public static JAXBHelper getInstance() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method...");
		}

		if (instance == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Instantiating a new JAXBHelper...");
			}
			instance = new JAXBHelper();
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Returning the existing singleton instance...");
		}
		return instance;
	}
}
