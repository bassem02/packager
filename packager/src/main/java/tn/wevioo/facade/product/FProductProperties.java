package tn.wevioo.facade.product;

/**
 * The class FProductProperties allows retrieving the current properties of an existent product instance. All the
 * provided information is guaranteed to have been retrieved quasi-instantly. However, a possible delta is possible,
 * regarding the cache system.
 * 
 * @author vberezan
 * @since 2.0.0
 */
public class FProductProperties extends FProductInstance {

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
