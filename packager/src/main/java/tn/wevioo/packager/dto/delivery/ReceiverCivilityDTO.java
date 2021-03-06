package tn.wevioo.packager.dto.delivery;

/**
 * The class FReceiverCivility provides the different available values for a human civility.
 * 
 * @author mleahu
 * @since 2.0.0
 */
public enum ReceiverCivilityDTO {
	/**
	 * All possible receiver civilities.
	 */
	M("M."), MLLE("Mlle"), MME("Mme");

	/**
	 * Civility value.
	 */
	private final String value;

	/**
	 * Constructor.
	 * 
	 * @param v
	 *            The civility as string.
	 */
	ReceiverCivilityDTO(String v) {
		value = v;
	}

	/**
	 * Civility value.
	 * 
	 * @return The civility valye.
	 */
	public String value() {
		return value;
	}

	/**
	 * Returns the receiver civility element from the string value.
	 * 
	 * @param v
	 *            The string value of civility.
	 * @return Enum value representing the string value.
	 */
	public static ReceiverCivilityDTO fromValue(String v) {
		for (ReceiverCivilityDTO c : ReceiverCivilityDTO.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
