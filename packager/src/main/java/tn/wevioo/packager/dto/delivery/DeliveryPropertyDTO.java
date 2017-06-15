package tn.wevioo.packager.dto.delivery;

/**
 * The class FDeliveryProperty allows providing a couple name/value for a certain property having a particular effect on
 * the final delivery.
 * 
 * @author mleahu
 * @since 2.0.0
 */
public class DeliveryPropertyDTO {

	/**
	 * Initial Value:
	 * <p>
	 * Name of the property.
	 */
	private String name;

	/**
	 * Initial Value:
	 * <p>
	 * Value of the property.
	 */
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
