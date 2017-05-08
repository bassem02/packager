package tn.wevioo.tools.xml.transformers.impl;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

import ch.elca.el4j.services.xmlmerge.AbstractXmlMergeException;
import ch.elca.el4j.services.xmlmerge.Configurer;
import ch.elca.el4j.services.xmlmerge.XmlMerge;
import ch.elca.el4j.services.xmlmerge.config.ConfigurableXmlMerge;
import ch.elca.el4j.services.xmlmerge.config.PropertyXPathConfigurer;
import nordnet.architecture.exceptions.explicit.MalformedXMLException;
import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.utils.ErrorCode;
import tn.wevioo.tools.properties.PropertiesFile;
import tn.wevioo.tools.properties.impl.SystemPropertyFile;
import tn.wevioo.tools.xml.merger.XmlMerger;

public class XmlMergeImpl implements XmlMerger {
	private static final Log LOGGER = LogFactory.getLog(XmlMergeImpl.class);

	private PropertiesFile configuration = null;

	private XmlMerge engine = null;

	public XmlMergeImpl() {
	}

	public void setConfigurationFile(Resource file) throws NotFoundException {
		try {
			configuration = new SystemPropertyFile(file.getInputStream());
		} catch (IOException ex) {
			throw new NotFoundException(new ErrorCode("0.1.1.2.1"), new Object[] { file.toString() }, ex);
		}
	}

	public String merge(String priorityXml, String secondaryXml)
			throws MalformedXMLException, NotRespectedRulesException {
		if ((priorityXml == null) || (priorityXml.trim().length() == 0)) {
			throw new NullException(NullException.NullCases.NULL_EMPTY, "priority Xml");
		}

		if ((secondaryXml == null) || (secondaryXml.trim().length() == 0)) {
			throw new NullException(NullException.NullCases.NULL_EMPTY, "secondary Xml");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Priority Xml :");
			LOGGER.debug("--------------------------------------------\n" + priorityXml);

			LOGGER.debug("Secondary Xml :");
			LOGGER.debug("--------------------------------------------\n" + secondaryXml);
		}

		try {
			String result = getXmlMergeEngine().merge(new String[] { priorityXml, secondaryXml });

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Merged Xml : ");
				LOGGER.debug("--------------------------------------------\n" + result);
			}

			return result;
		} catch (AbstractXmlMergeException ex) {
			throw new nordnet.architecture.exceptions.implicit.ConfigurationException(new ErrorCode("0.2.1.2.2"), ex);
		}
	}

	protected XmlMerge getXmlMergeEngine() {
		if (engine == null) {
			try {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Loading configuration...");
				}
				Configurer configurer = new PropertyXPathConfigurer(configuration.getAllProperties());

				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Creating XmlMerge engine from configuration...");
				}
				engine = new ConfigurableXmlMerge(configurer);

				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("XmlMerge engine initialized.");
				}
			} catch (ch.elca.el4j.services.xmlmerge.ConfigurationException ex) {
				throw new nordnet.architecture.exceptions.implicit.ConfigurationException(new ErrorCode("0.2.1.4.1"),
						ex);
			}
		}

		return engine;
	}
}
