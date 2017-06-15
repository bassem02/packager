package tn.wevioo.packager.dto.request;

/**
 * The class FProductReferenceRequest represents the Facade Object for an
 * ProductReference request.
 */
public class ProductReferenceRequestDTO {

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
