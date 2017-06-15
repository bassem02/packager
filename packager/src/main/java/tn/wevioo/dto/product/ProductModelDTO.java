package tn.wevioo.dto.product;

/**
 * This class represents the Facade object for Product model.
 */
public class ProductModelDTO {

	/**
	 * The attribute key contains the key under which is known the product model
	 * from the outside projects. This key is supposed to be unique and could be
	 * considered as another primary key of the model.
	 * <p>
	 * The value of this attribute could be set to null if the product model is
	 * desactivated from the database (desactivated does not mean deleted).
	 */
	private String key;

	/**
	 * The attribute key contains the key under which is known the product model
	 * from the outside projects. This key is supposed to be unique and could be
	 * considered as another primary key of the model.
	 * <p>
	 * The value of this attribute could be set to null if the product model is
	 * desactivated from the database (desactivated does not mean deleted).
	 */
	private String name;

	/**
	 * This attribute defines the maximal number of product instances the
	 * regarded packager model must contain. Of course, these product instances
	 * respect the specified product model. If null, the number of instances is
	 * unlimited.
	 */
	private Integer maximumInstances;

	/**
	 * This attribute defines the minimal number of product instance the
	 * regarded packager model must contain. Of course, these product instances
	 * respect the specified product model.
	 */
	private Integer minimumInstances;

	/**
	 * Key getter.
	 * 
	 * @return The key.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Key setter.
	 * 
	 * @param key
	 *            The key.
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Name getter.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Name setter.
	 * 
	 * @param name
	 *            The name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Maximum instances getter.
	 * 
	 * @return The maximum instances number.
	 */
	public Integer getMaximumInstances() {
		return maximumInstances;
	}

	/**
	 * Maximum instances setter.
	 * 
	 * @param maximumInstances
	 *            The maximum instances number.
	 */
	public void setMaximumInstances(Integer maximumInstances) {
		this.maximumInstances = maximumInstances;
	}

	/**
	 * Minimum instances getter.
	 * 
	 * @return The minimum instances number.
	 */
	public Integer getMinimumInstances() {
		return minimumInstances;
	}

	/**
	 * MinimumInstances setter.
	 * 
	 * @param minimumInstances
	 *            The minimum instances number.
	 */
	public void setMinimumInstances(Integer minimumInstances) {
		this.minimumInstances = minimumInstances;
	}

}
