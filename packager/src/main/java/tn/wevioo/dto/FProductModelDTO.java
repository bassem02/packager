package tn.wevioo.dto;

public class FProductModelDTO {

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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMaximumInstances() {
		return maximumInstances;
	}

	public void setMaximumInstances(Integer maximumInstances) {
		this.maximumInstances = maximumInstances;
	}

	public Integer getMinimumInstances() {
		return minimumInstances;
	}

	public void setMinimumInstances(Integer minimumInstances) {
		this.minimumInstances = minimumInstances;
	}

}
