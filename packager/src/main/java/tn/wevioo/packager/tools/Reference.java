package tn.wevioo.packager.tools;

import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.drivers.tools.ReferenceKeys;

/**
 * The class Reference represents a couple key/value defining a reference for a
 * product.
 */
public class Reference {

	/**
	 * Key of the reference.
	 */
	private ReferenceKeys key;

	/**
	 * Value of the reference.
	 */
	private String value;

	/**
	 * Specialized constructor.
	 * 
	 * @param key
	 *            Reference's key. Cannot be null.
	 * @param value
	 *            Reference's value.
	 */

	public Reference() {

	}

	public Reference(ReferenceKeys key, String value) {
		if (key == null) {
			throw new NullException(NullCases.NULL, "Reference's key");
		}
		this.key = key;
		this.value = value;
	}

	public ReferenceKeys getKey() {
		return key;
	}

	public void setKey(ReferenceKeys key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
