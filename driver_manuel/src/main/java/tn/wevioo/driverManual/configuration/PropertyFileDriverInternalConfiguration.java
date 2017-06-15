package tn.wevioo.driverManual.configuration;

import java.util.ArrayList;
import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.drivers.configuration.DriverInternalConfiguration;
import nordnet.drivers.contract.exceptions.DriverException;
import tn.wevioo.driverManual.tools.properties.PropertiesFile;
import tn.wevioo.driverManual.tools.properties.impl.ClassPathPropertiesFile;

/**
 * The class PropertyFileDriverInternalConfiguration, providing an abstract
 * implementation of the interface DriverInternalConfiguration, has for aim to
 * provide an easy access to the internal configuration property file. This file
 * is stored inside the CLASSPATH drivers.
 * <p>
 * The name of this property file is defined by the concatenation of the driver
 * key (obtained calling the method getDriverKey on the driver configuration)
 * and the extension defined by this class (refer to the owned attributes). This
 * file is provided by driver itself, storing it in its classpath's root.
 * <p>
 * This class is designed to be inherited from the specific driver internal
 * configuration classes, which should be loaded as a singleton Spring bean. For
 * example, the AstraSatelliteDriverInternalConfiguration can inherit this
 * class, defining the specific internal properties for the driver ASTRA
 * Satellite.
 */
public abstract class PropertyFileDriverInternalConfiguration implements DriverInternalConfiguration {

	/**
	 * The attribute internalConfigurationFile contains the internal
	 * configuration of the current drivers. It is not static, as it is not
	 * shared by all the drivers. However, it is supposed to be loaded only
	 * once, as the class embedding it is supposed to be singleton pattern
	 * designed.
	 */
	private PropertiesFile internalConfigurationFile = null;

	/**
	 * This attribute defines the name of the property containing the XML node
	 * prefix for the driver.
	 */
	public static final String XML_NODE_PREFIX_PROPERTY = "properties.prefix";

	/**
	 * This attribute defines the name of the property defining if the activate
	 * properties are required or not.
	 */
	public static final String ACTIVATE_PROPERTIES_REQUIRED_PROPERTY = "properties.activate.required";

	/**
	 * This attribute defines the name of the property defining if the cancel
	 * properties are required or not.
	 */
	public static final String CANCEL_PROPERTIES_REQUIRED_PROPERTY = "properties.cancel.required";

	/**
	 * This attribute defines the name of the property defining if the create
	 * properties are required or not.
	 */
	public static final String CREATE_PRODUCT_PROPERTIES_REQUIRED_PROPERTY = "properties.create.required";

	/**
	 * This attribute defines the name of the property defining if the delete
	 * properties are required or not.
	 */
	public static final String DELETE_PROPERTIES_REQUIRED_PROPERTY = "properties.delete.required";

	/**
	 * This attribute defines the name of the property defining if the
	 * reactivate properties are required or not.
	 */
	public static final String REACTIVATE_PROPERTIES_REQUIRED_PROPERTY = "properties.reactivate.required";

	/**
	 * This attribute defines the name of the property defining if the reset
	 * properties are required or not.
	 */
	public static final String RESET_PROPERTIES_REQUIRED_PROPERTY = "properties.reset.required";

	/**
	 * This attribute defines the name of the property defining if the suspend
	 * properties are required or not.
	 */
	public static final String SUSPEND_PROPERTIES_REQUIRED_PROPERTY = "properties.suspend.required";

	/**
	 * This attribute defines the name of the property containing the name of
	 * the JAXB package.
	 */
	public static final String JAXB_PACKAGE_NAME_PROPERTY = "properties.jaxb.package";

	/**
	 * This attribute defines the name of the property containing the list of
	 * the Dozer mapping file names.
	 */
	public static final String DOZER_MAPPING_FILE_NAMES_PROPERTY = "converter.mapping.file.names";

	/**
	 * This attribute defines the name of the property containing the driver
	 * key.
	 */
	public static final String DRIVER_KEY_PROPERTY = "driver.key";

	/**
	 * Driver version.
	 */
	public static final String DRIVER_VERSION_PROPERTY = "driver.version";

	/**
	 * The attribute shows if references are changed on activation.
	 */
	public static final String REFERENCES_CHANGED_ON_ACTIVATION_PROPERTY = "references.changed.onActivation";

	/**
	 * The attribute shows if references are changed on cancelation.
	 */
	public static final String REFERENCES_CHANGED_ON_CANCELATION_PROPERTY = "references.changed.onCancelation";

	/**
	 * The attribute shows if references are changed on change properties.
	 */
	public static final String REFERENCES_CHANGED_ON_CHANGE_PROPERTIES_PROPERTY = "references.changed.onChangeProperties";

	/**
	 * The attribute shows if references are changed on reactivation.
	 */
	public static final String REFERENCES_CHANGED_ON_REACTIVATION_PROPERTY = "references.changed.onReactivation";

	/**
	 * The attribute shows if references are changed on reset.
	 */
	public static final String REFERENCES_CHANGED_ON_RESET_PROPERTY = "references.changed.onReset";

	/**
	 * The attribute shows if references are changed on suspend.
	 */
	public static final String REFERENCES_CHANGED_ON_SUSPENSION_PROPERTY = "references.changed.onSuspension";

	/**
	 * Default constructor. This constructor must be used to instantiate a new
	 * configuration loader which is loadable by Spring. Be careful, this
	 * constructor does not constraint you to set a driver configuration, which
	 * is essential to get information in order to load the internal
	 * configuration. You must ensure that your process will then call the
	 * method setDriverConfiguration. Without this second call, no method could
	 * work.
	 * 
	 * @throws DriverException
	 *             Thrown if an error occurs during the process.
	 */
	public PropertyFileDriverInternalConfiguration() throws DriverException {
	}

	/**
	 * The method getPropertyValue returns the driver internal configuration
	 * value corresponding to the received key. The value is loaded from the
	 * internal property file, this class references.
	 * 
	 * @param key
	 *            The key for which getting the value from the properties file.
	 *            Cannot be null or empty. A NotFoundException will be thrown if
	 *            no corresponding property is found in the loaded properties
	 *            file.
	 * @return The value from the properties file of the given key.
	 * @throws NotFoundException
	 *             Thrown if the property is not found.
	 */
	protected String getPropertyValue(String key) throws NotFoundException {
		// validation for null or empty key is made on
		// ClassPathPropertiesFile.getProperty() method
		return this.internalConfigurationFile.getProperty(key);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getJaxbPackageName() throws DriverException, NotFoundException {
		return getPropertyValue(JAXB_PACKAGE_NAME_PROPERTY);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getXmlNodePrefix() throws DriverException, NotFoundException {
		return getPropertyValue(XML_NODE_PREFIX_PROPERTY);
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean areActivatePropertiesRequired() throws DriverException {
		String value = null;
		try {
			value = getPropertyValue(ACTIVATE_PROPERTIES_REQUIRED_PROPERTY);
		} catch (NotFoundException e) {
			return true;
		}

		return Boolean.parseBoolean(value);
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean areSuspendPropertiesRequired() throws DriverException {
		String value = null;
		try {
			value = getPropertyValue(SUSPEND_PROPERTIES_REQUIRED_PROPERTY);
		} catch (NotFoundException e) {
			return true;
		}

		return Boolean.parseBoolean(value);
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean areCancelPropertiesRequired() throws DriverException {
		String value = null;
		try {
			value = getPropertyValue(CANCEL_PROPERTIES_REQUIRED_PROPERTY);
		} catch (NotFoundException e) {
			return true;
		}

		return Boolean.parseBoolean(value);
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean areResetPropertiesRequired() throws DriverException {
		String value = null;
		try {
			value = getPropertyValue(RESET_PROPERTIES_REQUIRED_PROPERTY);
		} catch (NotFoundException e) {
			return true;
		}

		return Boolean.parseBoolean(value);
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean areDeletePropertiesRequired() throws DriverException {
		String value = null;
		try {
			value = getPropertyValue(DELETE_PROPERTIES_REQUIRED_PROPERTY);
		} catch (NotFoundException e) {
			return true;
		}

		return Boolean.parseBoolean(value);
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean areReactivatePropertiesRequired() throws DriverException {
		String value = null;
		try {
			value = getPropertyValue(REACTIVATE_PROPERTIES_REQUIRED_PROPERTY);
		} catch (NotFoundException e) {
			return true;
		}

		return Boolean.parseBoolean(value);
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean areCreateProductPropertiesRequired() throws DriverException {
		String value = null;
		try {
			value = getPropertyValue(CREATE_PRODUCT_PROPERTIES_REQUIRED_PROPERTY);
		} catch (NotFoundException e) {
			return true;
		}

		return Boolean.parseBoolean(value);
	}

	/**
	 * The method setPropertyFileName allows setting the internal configuration
	 * property file name to use to load those file. The complete relative class
	 * path file name must be specified thanks to this setter.
	 * <p>
	 * The property file is loaded instantly in this setter.
	 * 
	 * @param fileName
	 *            The relative class path property file complete name. As
	 *            example:
	 *            "configuration/driverInternalConfiguration.properties". Cannot
	 *            be null or empty.
	 * @throws DriverException
	 *             Thrown if an error occurs during the process.
	 * @throws NotFoundException
	 *             Thrown if the property is not found.
	 */
	public void setPropertyFileName(final String fileName) throws DriverException, NotFoundException {
		// Validation for null or empty fileName is made on
		// ClassPathPropertiesFile(String) constructor.
		this.internalConfigurationFile = (PropertiesFile) new ClassPathPropertiesFile(fileName);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDriverKey() throws DriverException, NotFoundException {
		return getPropertyValue(DRIVER_KEY_PROPERTY);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<String> getDozerMappingFileNames() throws DriverException, NotFoundException {
		List<String> toReturn = new ArrayList<String>();
		String fileNames = getPropertyValue(DOZER_MAPPING_FILE_NAMES_PROPERTY).trim();

		if (fileNames.length() > 0) {
			String[] fileNamesArray = fileNames.split(",");
			for (int index = 0; index < fileNamesArray.length; index++) {
				String fileName = fileNamesArray[index].trim();
				if (fileName.length() > 0) {
					toReturn.add(fileName);
				}
			}
		}

		return toReturn;
	}

	/**
	 * The method getInternalConfigurationFile returns the internal
	 * configuration file.
	 * 
	 * @return The properties file which describes the internal configurations.
	 */
	public PropertiesFile getInternalConfigurationFile() {
		return this.internalConfigurationFile;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDriverVersion() throws DriverException, NotFoundException {
		return getPropertyValue(DRIVER_VERSION_PROPERTY);
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean areReferencesChangedOnActivation() throws DriverException {
		String value = null;

		try {
			value = getPropertyValue(REFERENCES_CHANGED_ON_ACTIVATION_PROPERTY);
		} catch (NotFoundException e) {
			return true;
		}

		return Boolean.parseBoolean(value);
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean areReferencesChangedOnCancelation() throws DriverException {
		String value = null;

		try {
			value = getPropertyValue(REFERENCES_CHANGED_ON_CANCELATION_PROPERTY);
		} catch (NotFoundException e) {
			return true;
		}

		return Boolean.parseBoolean(value);
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean areReferencesChangedOnChangeProperties() throws DriverException {
		String value = null;

		try {
			value = getPropertyValue(REFERENCES_CHANGED_ON_CHANGE_PROPERTIES_PROPERTY);
		} catch (NotFoundException e) {
			return true;
		}

		return Boolean.parseBoolean(value);
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean areReferencesChangedOnReactivation() throws DriverException {
		String value = null;

		try {
			value = getPropertyValue(REFERENCES_CHANGED_ON_REACTIVATION_PROPERTY);
		} catch (NotFoundException e) {
			return true;
		}

		return Boolean.parseBoolean(value);
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean areReferencesChangedOnReset() throws DriverException {
		String value = null;

		try {
			value = getPropertyValue(REFERENCES_CHANGED_ON_RESET_PROPERTY);
		} catch (NotFoundException e) {
			return true;
		}

		return Boolean.parseBoolean(value);
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean areReferencesChangedOnSuspension() throws DriverException {
		String value = null;

		try {
			value = getPropertyValue(REFERENCES_CHANGED_ON_SUSPENSION_PROPERTY);
		} catch (NotFoundException e) {
			return true;
		}

		return Boolean.parseBoolean(value);
	}
}