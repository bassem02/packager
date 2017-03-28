package tn.wevioo.request;

import java.util.ArrayList;
import java.util.List;

/**
 * The class FDeliveryRequest allows providing all the required information to deliver different elements which
 * correspond to the regarded packager instance.
 * <p>
 * Depending on the method which is called, the delivery request is mandatory, optional or forbidden. For example, in
 * order to create a new packager instance, depending of the packager model, the delivery request is mandatory or
 * optional. On the other hand, in order to activate a packager instance, the delivery request is forbidden.
 * 
 * @author mleahu
 * @since 2.0.0
 */
public class FDeliveryRequest {

	/**
	 * Initial Value :
	 * <p>
	 * The attribute sendDelivery allows accept or decline the delivery if the value is respectively true or false.
	 */
	private Boolean sendDelivery;

	/**
	 * Delivery properties.
	 */
	private List<tn.wevioo.delivery.FDeliveryProperty> properties = new ArrayList<tn.wevioo.delivery.FDeliveryProperty>();

	/**
	 * Delivery receiver.
	 */
	private tn.wevioo.delivery.FReceiverContact receiver;

	/**
	 * test
	 * The identifier of the customer for which the delivery is asked. This value will be given to the NetDelivery
	 * system.
	 */
	public String customerId;

	/**
	 * In order to complete directive on the delivery request (such as specific cases, as delivery to secondary home,
	 * express deliveries, ...), you can provide here a specific string, which will be taken into account in order to
	 * override default action. The string can be considered as an enumeration value. Please refer to the documentation
	 * of your packager model in order to find the right value.
	 * <p>
	 * If you do not have any complementary directive, please let this attribute null.
	 */
	public String complementaryDirective;

	public Boolean getSendDelivery() {
		return sendDelivery;
	}

	public void setSendDelivery(Boolean sendDelivery) {
		this.sendDelivery = sendDelivery;
	}

	public List<tn.wevioo.delivery.FDeliveryProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<tn.wevioo.delivery.FDeliveryProperty> properties) {
		this.properties = properties;
	}

	public tn.wevioo.delivery.FReceiverContact getReceiver() {
		return receiver;
	}

	public void setReceiver(tn.wevioo.delivery.FReceiverContact receiver) {
		this.receiver = receiver;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getComplementaryDirective() {
		return complementaryDirective;
	}

	public void setComplementaryDirective(String complementaryDirective) {
		this.complementaryDirective = complementaryDirective;
	}

}
