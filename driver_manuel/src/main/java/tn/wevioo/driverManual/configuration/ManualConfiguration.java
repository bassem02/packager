package tn.wevioo.driverManual.configuration;

import nordnet.drivers.configuration.DriverConfiguration;
import nordnet.drivers.configuration.impl.PropertyFileDriverConfiguration;
import nordnet.drivers.contract.exceptions.DriverException;

/**
 * The class {@link ManualConfiguration} provides an implementation of the
 * {@link DriverConfiguration} to the Manual driver. The class is instanced by
 * Spring with singleton scope.
 */
public class ManualConfiguration extends PropertyFileDriverConfiguration {

	/**
	 * Default constructor.
	 * 
	 * @throws DriverException
	 *             custom exception.
	 */
	public ManualConfiguration() throws DriverException {
		super();
	}

}
