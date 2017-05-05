package tn.wevioo.model.delivery;

import tn.wevioo.model.packager.PackagingConfiguration;

/**
 * The class PackagingDeliveryConfiguration models the configuration for deliveries on packagings : for each product
 * model a packager model packages, a delivery configuration can be set into the database.
 * <p>
 * For example, the packager model "Astra Prestige" proposes two products: a satellite Internet connection and a
 * firewall. Each of these two relations is modelized in the database by a Packaging Configuration. On the one regarding
 * the satellite Internet connection, we will set a satellite aerial and a modem, as delivery configuration. On the
 * other one regarding the firewall, we will set a CD installer. Each of these three references have to be delivered to
 * the final NordNet customer.
 * <p>
 * The class PackagingDeliveryConfiguration modelizes the relationship between the delivery reference and the packaging
 * configuration.
 * 
 * 
 * @author vberezan
 * @since 2.0.0
 */
public class PackagingDeliveryConfiguration {

	/**
	 * Unique auto-incremental database identifier.
	 */
	private Long id;

	/**
	 * The delivery reference.
	 */
	private DeliveryReference deliveryReference;

	/**
	 * The packaging configuration.
	 */
	private PackagingConfiguration packagingConfiguration;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DeliveryReference getDeliveryReference() {
		return deliveryReference;
	}

	public void setDeliveryReference(DeliveryReference deliveryReference) {
		this.deliveryReference = deliveryReference;
	}

	public PackagingConfiguration getPackagingConfiguration() {
		return packagingConfiguration;
	}

	public void setPackagingConfiguration(PackagingConfiguration packagingConfiguration) {
		this.packagingConfiguration = packagingConfiguration;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deliveryReference == null) ? 0 : deliveryReference.hashCode());
		result = prime * result + ((packagingConfiguration == null) ? 0 : packagingConfiguration.hashCode());
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
		PackagingDeliveryConfiguration other = (PackagingDeliveryConfiguration) obj;
		if (deliveryReference == null) {
			if (other.deliveryReference != null) {
				return false;
			}
		} else if (!deliveryReference.equals(other.deliveryReference)) {
			return false;
		}
		if (packagingConfiguration == null) {
			if (other.packagingConfiguration != null) {
				return false;
			}
		} else if (!packagingConfiguration.equals(other.packagingConfiguration)) {
			return false;
		}
		return true;
	}

}
