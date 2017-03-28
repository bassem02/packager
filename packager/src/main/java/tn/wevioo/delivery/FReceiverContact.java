package tn.wevioo.delivery;

/**
 * The class FReceiverContact allows giving information about the contact who will receive the delivery.
 * 
 * @author mleahu
 * @since 2.0.0
 */
public class FReceiverContact {

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
	private FDeliveryAddress address;

	/**
	 * Receiver civility.
	 */
	private FReceiverCivility civility;

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

	public FDeliveryAddress getAddress() {
		return address;
	}

	public void setAddress(FDeliveryAddress address) {
		this.address = address;
	}

	public FReceiverCivility getCivility() {
		return civility;
	}

	public void setCivility(FReceiverCivility civility) {
		this.civility = civility;
	}

}
