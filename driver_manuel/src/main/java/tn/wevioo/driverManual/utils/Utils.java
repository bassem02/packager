package tn.wevioo.driverManual.utils;

import org.apache.commons.lang.math.RandomUtils;

public class Utils {

	/**
	 * Generate an identifier of 8 characters.
	 * 
	 * @return the enduser.
	 */
	public static String getIdentifer() {
		String identifier = String.valueOf(RandomUtils.nextInt(99999999));
		return normalize(identifier);
	}

	/**
	 * To clear all 0 cahracter located at the left side of the identifier . eg. 000123 after denormalize becomes 123
	 * 
	 * @param customerKey
	 *            the customer key
	 * @return the string
	 */
	public static String deNormalize(final String identifier) {

		if (!identifier.trim().startsWith("0")) {
			return identifier;
		}
		return deNormalize(identifier.substring(1, identifier.length()));

	}

	/**
	 * Transform an identifier that length is less than 8 characters to a String of 8 characters by adding 0 character
	 * to the left side until the length reaches 8 characters. eg: 12365 becomes after normalize : 00012365
	 * 
	 * @param identifier
	 *            the identifier
	 * @return the string
	 */
	public static String normalize(final String identifier) {

		if (identifier.length() == 8) {
			return identifier;
		}
		return normalize("0" + identifier);

	}

}
