package tn.wevioo.driverManual.utils;

import nordnet.drivers.contract.types.State;
import nordnet.tools.converter.converters.ConverterBeanFactory;

/**
 * Converts a String into an instance of State.
 */
public class StatusConverter extends ConverterBeanFactory {

	/**
	 * Status active.
	 */
	private static final String ACTIVE_STATUS = "ACTIVE";
	/**
	 * status suspend.
	 */
	private static final String SUSPEND_STATUS = "SUSPENDED";
	/**
	 * Status canceled.
	 */
	private static final String CANCELED_STATUS = "CANCELED";
	/**
	 * Status inprogress.
	 */
	private static final String INPROGESS_STATUS = "INPROGRESS";
	/**
	 * Status activable.
	 */
	private static final String ACTIVABLE_STATUS = "ACTIVABLE";

	/**
	 * Converts a String into an instance of State.
	 * 
	 * @param source
	 *            The source object.
	 * @param targetBeanId
	 *            targetBeanId.
	 * @param sourceClass
	 *            The source class name.
	 * @return the generated object.
	 **/

	@Override
	public Object createBean(Object source, Class<?> sourceClass, String targetBeanId) {
		Object destination = null;
		if (source instanceof String) {
			String sourceObj = (String) source;
			if (ACTIVE_STATUS.equals(sourceObj)) {
				destination = State.ACTIVE;
			} else if (SUSPEND_STATUS.equals(sourceObj)) {
				destination = State.SUSPENDED;
			} else if (CANCELED_STATUS.equals(sourceObj)) {
				destination = State.CANCELED;
			} else if (INPROGESS_STATUS.equals(sourceObj)) {
				destination = State.INPROGRESS;
			} else if (ACTIVABLE_STATUS.equals(sourceObj)) {
				destination = State.ACTIVABLE;
			}
		}
		return destination;
	}
}
