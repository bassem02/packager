package tn.wevioo.facade.delivery;

/**
 * The class FDeliveryAddress allows giving the address where delivering the different elements.
 * 
 * @author mleahu
 * @since 2.0.0
 */
public class FDeliveryAddress {
	/**
	 * Initial Value: Mandatory first line of the address.
	 */
	private String address1;

	/**
	 * Initial Value:
	 * <p>
	 * Optional second line of the address.
	 */
	private String address2;

	/**
	 * Initial Value:
	 * <p>
	 * Optional third line of the address.
	 */
	private String address3;

	/**
	 * Initial Value:
	 * <p>
	 * Mandatory zip code of address.
	 */
	private String zipCode;

	/**
	 * Initial Value:
	 * <p>
	 * Mandatory city of the address.
	 */
	private String city;

	/**
	 * Initial Value:
	 * <p>
	 * Optional country of the address.
	 */
	private String country;

	/**
	 * Initial Value:
	 * <p>
	 * Optional cedex code of the address.
	 */
	private String cedexCode;

	/**
	 * Initial Value:
	 * <p>
	 * Optional cedex label of the address.
	 */
	private String cedexLabel;

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCedexCode() {
		return cedexCode;
	}

	public void setCedexCode(String cedexCode) {
		this.cedexCode = cedexCode;
	}

	public String getCedexLabel() {
		return cedexLabel;
	}

	public void setCedexLabel(String cedexLabel) {
		this.cedexLabel = cedexLabel;
	}
}
