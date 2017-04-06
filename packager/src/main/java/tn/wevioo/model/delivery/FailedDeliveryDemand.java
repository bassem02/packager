package tn.wevioo.model.delivery;

import java.util.Date;

import tn.wevioo.entities.PackagerInstance;

/**
 * The class FailedDeliveryDemand models a delivery demand which has not been
 * asked successfully to the Web Service NetDelivery (due to network failure for
 * example) and has to be re-asked later, by processing a dedicated batch.
 * <p>
 * The class FailedDeliveryDemand is supposed to store every required
 * information in order to re-attempt to contact the Web Service NetDelivery
 * successfully. This information corresponds to the original delivery request
 * (see class DeliveryRequest) transformed under an Xml format and stored into
 * the dedicated attribute.
 * <p>
 * In order to prevent endless re-attempts, the original delivery request which
 * has generated this failed delivery demand is supposed to be valid.
 * Consequently, the only reasons for which the delivery demands are considered
 * as failed but retriable are not business but technical: network failure or
 * tieout.
 * <p>
 * When trying to re-attempt again to ask the delivery, the original delivery
 * request is parsed from the database, recomposed into a whole DeliveryRequest
 * object and then used to re-compute the final delivery request which will be
 * asked to the NetDelivery system.
 * 
 * @author vberezan
 * @since 2.0.0
 */
public class FailedDeliveryDemand {

	/**
	 * Identifier.
	 */
	private Long id;

	/**
	 * The original delivery request which has been transformed into the Xml
	 * equivalent stream in order to be stored in the database. In order to
	 * prevent endless re-attempts, the original request is supposed to be
	 * valid, else the process would have not insert it into the database.
	 */
	private String xmlDeliveryRequest;

	/**
	 * The date when the failed delivery demand has been originally inserted
	 * into the database.
	 */
	private Date creationDate;

	/**
	 * The date when the last attempt to ask again the current delivery demand
	 * to the NetDelivery system has been tried.
	 */
	private Date lastAttemptDate;

	/**
	 * The delivery packager instance.
	 */
	private PackagerInstance packagerInstance;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getXmlDeliveryRequest() {
		return xmlDeliveryRequest;
	}

	public void setXmlDeliveryRequest(String xmlDeliveryRequest) {
		this.xmlDeliveryRequest = xmlDeliveryRequest;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastAttemptDate() {
		return lastAttemptDate;
	}

	public void setLastAttemptDate(Date lastAttemptDate) {
		this.lastAttemptDate = lastAttemptDate;
	}

	public PackagerInstance getPackagerInstance() {
		return packagerInstance;
	}

	public void setPackagerInstance(PackagerInstance packagerInstance) {
		this.packagerInstance = packagerInstance;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((packagerInstance == null) ? 0 : packagerInstance.hashCode());
		result = prime * result + ((xmlDeliveryRequest == null) ? 0 : xmlDeliveryRequest.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		FailedDeliveryDemand other = (FailedDeliveryDemand) obj;
		if (creationDate == null) {
			if (other.creationDate != null) {
				return false;
			}
		} else if (!creationDate.equals(other.creationDate)) {
			return false;
		}
		if (packagerInstance == null) {
			if (other.packagerInstance != null) {
				return false;
			}
		} else if (!packagerInstance.equals(other.packagerInstance)) {
			return false;
		}
		if (xmlDeliveryRequest == null) {
			if (other.xmlDeliveryRequest != null) {
				return false;
			}
		} else if (!xmlDeliveryRequest.equals(other.xmlDeliveryRequest)) {
			return false;
		}
		return true;
	}

}
