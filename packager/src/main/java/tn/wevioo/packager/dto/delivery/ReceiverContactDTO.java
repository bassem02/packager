package tn.wevioo.packager.dto.delivery;

/**
 * The class FReceiverContact allows giving information about the contact who will receive the delivery.
 * 
 * @author mleahu
 * @since 2.0.0
 */
public class ReceiverContactDTO {

	/**
	 * Initial Value:
	 * <p>
	 * Optional first name of the contact.
	 */
	private String firstName;

	/**
	 * Initial Value:
	 * <p>
	 * Mandatory last name of the contact.
	 */
	private String lastName;

	/**
	 * Initial Value:
	 * <p>
	 * Optional company of the contact.
	 */
	private String company;

	/**
	 * Initial Value:
	 * <p>
	 * Optional email of the contact.
	 */
	private String email;

	/**
	 * Receiver address.
	 */
	private DeliveryAddressDTO address;

	/**
	 * Receiver civility.
	 */
	private ReceiverCivilityDTO civility;

	/**
	 * Initial Value:
	 * <p>
	 * Optional phone number of the contact.
	 */
	private String phoneNumber;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public DeliveryAddressDTO getAddress() {
		return address;
	}

	public void setAddress(DeliveryAddressDTO address) {
		this.address = address;
	}

	public ReceiverCivilityDTO getCivility() {
		return civility;
	}

	public void setCivility(ReceiverCivilityDTO civility) {
		this.civility = civility;
	}

}
