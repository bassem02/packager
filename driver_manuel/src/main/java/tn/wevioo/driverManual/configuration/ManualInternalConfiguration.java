package tn.wevioo.driverManual.configuration;

import nordnet.drivers.configuration.DriverInternalConfiguration;
import nordnet.drivers.contract.exceptions.DriverException;

/**
 * The class {@link ManualInternalConfiguration} provides an implementation of
 * the {@link DriverInternalConfiguration} to the Manual Driver. The class is
 * instanced by Spring with singleton scope.
 */
public class ManualInternalConfiguration extends PropertyFileDriverInternalConfiguration {

	/**
	 * Default constructor.
	 * 
	 * @throws DriverException
	 *             custom exception.
	 */
	public ManualInternalConfiguration() throws DriverException {
		super();
	}

}
