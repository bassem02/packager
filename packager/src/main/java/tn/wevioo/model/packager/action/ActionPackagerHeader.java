package tn.wevioo.model.packager.action;

import tn.wevioo.entities.Retailer;

/**
 * The class ActionPackagerHeader allows storing in database packager
 * information related to a packager action. The data contained in this class
 * are not dependent from the packager they come from, which can be modified
 * after the action has been stored.
 * 
 * @author vberezan
 * @since 2.5.2
 */
public class ActionPackagerHeader {

	/**
	 * Unique auto-incremental database identifier.
	 */
	private Long id;

	/**
	 * Retailer packager identifier corresponding to the packager instance
	 * related to the action.
	 */
	private String retailerPackagerId;

	/**
	 * Packager model key of the packager instance related to the action.
	 */
	private String packagerModelKey;

	/**
	 * The retailer to which is linked the current packager. This attribute maps
	 * the database field id_retailer.
	 */
	private Retailer retailer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRetailerPackagerId() {
		return retailerPackagerId;
	}

	public void setRetailerPackagerId(String retailerPackagerId) {
		this.retailerPackagerId = retailerPackagerId;
	}

	public String getPackagerModelKey() {
		return packagerModelKey;
	}

	public void setPackagerModelKey(String packagerModelKey) {
		this.packagerModelKey = packagerModelKey;
	}

	public Retailer getRetailer() {
		return retailer;
	}

	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((packagerModelKey == null) ? 0 : packagerModelKey.hashCode());
		result = prime * result + ((retailer == null) ? 0 : retailer.hashCode());
		result = prime * result + ((retailerPackagerId == null) ? 0 : retailerPackagerId.hashCode());
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
		ActionPackagerHeader other = (ActionPackagerHeader) obj;
		if (packagerModelKey == null) {
			if (other.packagerModelKey != null) {
				return false;
			}
		} else if (!packagerModelKey.equals(other.packagerModelKey)) {
			return false;
		}
		if (retailer == null) {
			if (other.retailer != null) {
				return false;
			}
		} else if (!retailer.equals(other.retailer)) {
			return false;
		}
		if (retailerPackagerId == null) {
			if (other.retailerPackagerId != null) {
				return false;
			}
		} else if (!retailerPackagerId.equals(other.retailerPackagerId)) {
			return false;
		}
		return true;
	}

}
