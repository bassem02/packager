package tn.wevioo.configuration;

import nordnet.drivers.configuration.DriverInternalConfiguration;
import nordnet.drivers.configuration.impl.PropertyFileDriverInternalConfiguration;
import nordnet.drivers.contract.exceptions.DriverException;

/**
 * The class {@link ManualInternalConfiguration} provides an implementation of the {@link DriverInternalConfiguration}
 * to the Manual Driver. The class is instanced by Spring with singleton scope.
 * 
 * @author kad
 * 
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
