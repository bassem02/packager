package tn.wevioo.dto.delivery;

import java.util.Date;
import java.util.List;

/**
 * The class FDeliveryDemand provides all the information about the delivery demand which have been asked through the
 * packager project, on packager instances.
 * <p>
 * This class has been designed in order to be able to merge delivery demands which have been successfully asked to the
 * NetDelivery system, with them which have been declined and the other ones which have failed.
 * 
 * @author mleahu
 * @since 2.0.0
 */
public class DeliveryDemandDTO {
	/**
	 * The unique identifier into the NetDelivery system.
	 */
	private String netDeliveryId;

	/**
	 * All the references which have been asked to be sent.
	 */
	private List<String> references;

	/**
	 * The mail template which have been used to send the equipments to the customers.
	 */
	private String template;
	/**
	 * Has the delivery demand been declined by the user ? If yes, no delivery demand can be found in the NetDelivery
	 * system.
	 */
	private Boolean declined;

	/**
	 * Has the delivery demand been asked to the NetDelivery system ? True if the delivery has been successfully asked,
	 * false if the demand has failed and will be re-attempted later. Always false, if the demand has been declined.
	 */
	private Boolean asked;

	/**
	 * The date when the delivery demand has been asked to the NetDelivery demand. Null if the demand has been declined
	 * or if it has not been asked yet.
	 */
	private Date askDate;

	/**
	 * The state the current delivery demand is in.
	 */
	private String state;

	/**
	 *Delivery properties.
	 */
	private List<DeliveryPropertyDTO> properties;

	/**
	 * Delivery receiver.
	 */
	private ReceiverContactDTO receiver;

	/**
	 * The identifier of the customer for which the delivery is asked. This value comes from the NetDelivery system.
	 */
	public String customerId;

	public String getNetDeliveryId() {
		return netDeliveryId;
	}

	public void setNetDeliveryId(String netDeliveryId) {
		this.netDeliveryId = netDeliveryId;
	}

	public java.util.List<String> getReferences() {
		return references;
	}

	public void setReferences(java.util.List<String> references) {
		this.references = references;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Boolean getDeclined() {
		return declined;
	}

	public void setDeclined(Boolean declined) {
		this.declined = declined;
	}

	public Boolean getAsked() {
		return asked;
	}

	public void setAsked(Boolean asked) {
		this.asked = asked;
	}

	public Date getAskDate() {
		return askDate;
	}

	public void setAskDate(Date askDate) {
		this.askDate = askDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<DeliveryPropertyDTO> getProperties() {
		return properties;
	}

	public void setProperties(List<DeliveryPropertyDTO> properties) {
		this.properties = properties;
	}

	public ReceiverContactDTO getReceiver() {
		return receiver;
	}

	public void setReceiver(ReceiverContactDTO receiver) {
		this.receiver = receiver;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}
