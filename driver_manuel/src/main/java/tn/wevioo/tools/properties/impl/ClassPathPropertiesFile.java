package tn.wevioo.tools.properties.impl;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.utils.ErrorCode;

public class ClassPathPropertiesFile extends SystemPropertyFile {
	private static final Log LOGGER = LogFactory.getLog(ClassPathPropertiesFile.class);

	public ClassPathPropertiesFile(String pAddress) throws NotFoundException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting method.");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Verifying the address is not null or empty...");
		}
		if ((pAddress == null) || (pAddress.trim().length() == 0)) {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn("The properties file address can not be null or empty.");
			}
			throw new NullException(NullException.NullCases.NULL_EMPTY, "properties file address");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Getting the input stream on the properties file...");
		}
		InputStream inputFile = obtainThePropertiesFileFromJbossConfiguration(pAddress);
		if (inputFile == null) {
			inputFile = getClass().getClassLoader().getResourceAsStream(pAddress);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Verifying the input stream is not null...");
		}
		if (null == inputFile) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("The properties file [" + pAddress + "] has not been found.");
			}
			throw new NotFoundException(new ErrorCode("0.1.1.2.1"), new Object[] { pAddress });
		}

		super.setPropertyFile(inputFile);
	}

	private InputStream obtainThePropertiesFileFromJbossConfiguration(String pAddress) {
		String jbossConfig = System.getProperty("jboss.server.config.url");
		if (jbossConfig == null) {
			return null;
		}
		// String applicationConfigPath =
		// SpringBeanFactory.getInstance().getApplicationConfigPath();
		String applicationConfigPath = "applicationConfigPath";
		if (applicationConfigPath == null) {
			return null;
		}

		return getClass().getClassLoader()
				.getResourceAsStream(applicationConfigPath + System.getProperty("file.separator") + pAddress);
	}

	public Properties getPropertyFile() {
		return super.getPropertyFile();
	}
}
