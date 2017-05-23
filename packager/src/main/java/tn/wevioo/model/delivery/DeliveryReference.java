package tn.wevioo.model.delivery;

/**
 * The class DeliveryReference models all the deliverable references, which are
 * known by the NetDelivery system, and which can be linked to certain products.
 * <p>
 * When adding a new delivery reference into the packager database, the
 * administrator must ensure the regarded reference is well known and spelled,
 * as in the NetDeliverySystem.
 * <p>
 */
public class DeliveryReference {

	/**
	 * Unique auto-incremental database identifier.
	 */
	private Long id;

	/**
	 * The reference which is known by the NetDelivery system.
	 */
	private String referenceName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((referenceName == null) ? 0 : referenceName.hashCode());
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
		DeliveryReference other = (DeliveryReference) obj;
		if (referenceName == null) {
			if (other.referenceName != null) {
				return false;
			}
		} else if (!referenceName.equals(other.referenceName)) {
			return false;
		}
		return true;
	}

}
