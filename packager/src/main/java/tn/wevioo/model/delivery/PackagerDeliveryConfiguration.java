package tn.wevioo.model.delivery;

import tn.wevioo.entities.PackagerModel;

/**
 * The class PackagerDeliveryConfiguration models the configuration for
 * deliveries on packager models : for each packager model, additional delivery
 * references can be added, without any relationship with the packaging
 * configuration (contrary to the class PackagingDeliveryConfiguration).
 * <p>
 * For example, for each packager model in the "prestige" range, an USB key is
 * offered. This does not depend on the packaging configuration, but on the
 * packager model itself.
 */
public class PackagerDeliveryConfiguration {

	/**
	 * Unique identifier of the relation.
	 */
	private Long id;

	/**
	 * The delivery reference.
	 */
	private DeliveryReference deliveryReference;

	/**
	 * The packager model.
	 */
	private PackagerModel packagerModel;

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

	public PackagerModel getPackagerModel() {
		return packagerModel;
	}

	public void setPackagerModel(PackagerModel packagerModel) {
		this.packagerModel = packagerModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deliveryReference == null) ? 0 : deliveryReference.hashCode());
		result = prime * result + ((packagerModel == null) ? 0 : packagerModel.hashCode());
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
		PackagerDeliveryConfiguration other = (PackagerDeliveryConfiguration) obj;
		if (deliveryReference == null) {
			if (other.deliveryReference != null) {
				return false;
			}
		} else if (!deliveryReference.equals(other.deliveryReference)) {
			return false;
		}
		if (packagerModel == null) {
			if (other.packagerModel != null) {
				return false;
			}
		} else if (!packagerModel.equals(other.packagerModel)) {
			return false;
		}
		return true;
	}

}
