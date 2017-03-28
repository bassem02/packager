package tn.wevioo.request;

/**
 * The class FProductReferenceRequest represents the Facade Object for an ProductReference request.
 * 
 * @author vberezan
 * @since 2.6.2
 */
public class FProductReferenceRequest {

	/**
	 * Initial Value: Type of the reference.
	 */
	private String type;

	/**
	 * Initial Value: Value of the reference.
	 */
	private String value;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
