package tn.wevioo.packager.dto.product;

/**
 * The class FProductProperties allows retrieving the current properties of an
 * existent product instance. All the provided information is guaranteed to have
 * been retrieved quasi-instantly. However, a possible delta is possible,
 * regarding the cache system.
 * 
 */
public class ProductPropertiesDTO extends ProductInstanceDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Product properties.
	 */
	private String properties;

	/**
	 * Properties getter.
	 * 
	 * @return The properties.
	 */
	public String getProperties() {
		return properties;
	}

	/**
	 * Properties setter.
	 * 
	 * @param properties
	 *            The properties.
	 */
	public void setProperties(String properties) {
		this.properties = properties;
	}

}
