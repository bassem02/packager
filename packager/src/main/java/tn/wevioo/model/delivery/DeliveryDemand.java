package tn.wevioo.model.delivery;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import tn.wevioo.entities.PackagerInstance;
import tn.wevioo.entities.ProductInstance;

/**
 * The class DeliveryDemand models a delivery demand which has been asked for a
 * certain packager, and transfered to the Web Service NetDelivery. A delivery
 * allows sending equipments to a final NordNet customer, known has the receiver
 * in the NordNet delivery process.
 * <p>
 * When a DeliveryDemand is stored into the database, this means it has been
 * successfully treated and no more operation is required on it. However, if the
 * attribute declined is set to true, the Packager user has declined the
 * delivery, and this last one has not been transfered to the Web Service
 * NetDelivery. It is stored anyway into the database in order to keep in mind
 * this refusal. In this case, no product instance is linked to the current
 * delivery demand.
 */
public class DeliveryDemand {

	/**
	 * The unique identifier in the database. This identifier is given by the
	 * NetDelivery system, once the delivery demand has been asked to it
	 * successfully. This attribute maps the field 'id' on the database table.
	 */
	private Long netDeliveryId;

	/**
	 * The date when the delivery demand has been inserted into the database.
	 */
	private Date creationDate;

	/**
	 * Unique auto-incremental identifier in the database. This attribute maps
	 * the field 'pack_id' on the table.
	 */
	private Long id;

	/**
	 * Has this delivery demand been declined while proposed ? True if the
	 * delivery demand has been declined and NOT transfered to the Web Service
	 * NetDelivery, false if it has been successfully transfered to it.
	 */
	private Boolean declined;

	/**
	 * The packager instance of delivery demand.
	 */
	private PackagerInstance packagerInstance;

	/**
	 * All delivered products.
	 */
	private Set<ProductInstance> deliveredProducts = new HashSet<ProductInstance>();

	public Long getNetDeliveryId() {
		return netDeliveryId;
	}

	public void setNetDeliveryId(Long netDeliveryId) {
		this.netDeliveryId = netDeliveryId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getDeclined() {
		return declined;
	}

	public void setDeclined(Boolean declined) {
		this.declined = declined;
	}

	public PackagerInstance getPackagerInstance() {
		return packagerInstance;
	}

	public void setPackagerInstance(PackagerInstance packagerInstance) {
		this.packagerInstance = packagerInstance;
	}

	public Set<ProductInstance> getDeliveredProducts() {
		return deliveredProducts;
	}

	public void setDeliveredProducts(Set<ProductInstance> deliveredProducts) {
		this.deliveredProducts = deliveredProducts;
	}

	/**
	 * This method clones the current object but sets a null id in order to be
	 * saved in DB with Hibernate.
	 * 
	 * @return A clone of the current product instance delivery demand.
	 */
	@Override
	public DeliveryDemand clone() {
		DeliveryDemand dd = new DeliveryDemand();

		dd.setCreationDate(this.getCreationDate());
		dd.setDeclined(this.getDeclined());
		dd.setPackagerInstance(this.getPackagerInstance());
		dd.setNetDeliveryId(null);
		dd.setId(null);

		return dd;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((declined == null) ? 0 : declined.hashCode());
		result = prime * result + ((netDeliveryId == null) ? 0 : netDeliveryId.hashCode());
		result = prime * result + ((packagerInstance == null) ? 0 : packagerInstance.hashCode());
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
		DeliveryDemand other = (DeliveryDemand) obj;
		if (creationDate == null) {
			if (other.creationDate != null) {
				return false;
			}
		} else if (!creationDate.equals(other.creationDate)) {
			return false;
		}
		if (declined == null) {
			if (other.declined != null) {
				return false;
			}
		} else if (!declined.equals(other.declined)) {
			return false;
		}
		if (netDeliveryId == null) {
			if (other.netDeliveryId != null) {
				return false;
			}
		} else if (!netDeliveryId.equals(other.netDeliveryId)) {
			return false;
		}
		if (packagerInstance == null) {
			if (other.packagerInstance != null) {
				return false;
			}
		} else if (!packagerInstance.equals(other.packagerInstance)) {
			return false;
		}
		return true;
	}

}
