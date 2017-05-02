package tn.wevioo.facade.packager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tn.wevioo.facade.product.FProductInstanceHeader;

/**
 * The class FPackagerInstanceHeader allows providing information to the
 * Packager's users about a packager instance. This class has been designed in
 * order to be returned quickly by the facade. Consequently, the inner
 * information could have been cached or computed since a long time, and nothing
 * guarantees there is no desynchronization with the provider system. However,
 * the whole system has been designed to minimize this risk.
 * 
 * @author THUGUERRE
 * @since 2.0.0
 */
public class FPackagerInstanceHeader {

	/**
	 * The retailer packager identifier under which the related packager
	 * instance has been created.
	 */
	private String retailerPackagerId;

	/**
	 * The packager model key the current packager instance has been created to.
	 */
	private String packagerModel;

	/**
	 * All the product instances this packager instance owns.
	 */
	private List<FProductInstanceHeader> products = new ArrayList<FProductInstanceHeader>();

	/**
	 * The package creation date.
	 */
	private Date creationDate;

	/**
	 * HashCode implementation.
	 * 
	 * @return The hash code.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((packagerModel == null) ? 0 : packagerModel.hashCode());
		result = prime * result + ((retailerPackagerId == null) ? 0 : retailerPackagerId.hashCode());
		return result;
	}

	/**
	 * Equals implementation.
	 * 
	 * @return TRUE if they are equals.
	 * @param obj
	 *            The object to check for equality with this one.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof FPackagerInstanceHeader)) {
			return false;
		}
		FPackagerInstanceHeader other = (FPackagerInstanceHeader) obj;
		if (packagerModel == null) {
			if (other.packagerModel != null) {
				return false;
			}
		} else if (!packagerModel.equals(other.packagerModel)) {
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

	public List<FProductInstanceHeader> getProducts() {
		return products;
	}

	public void setProducts(List<FProductInstanceHeader> products) {
		this.products = products;
	}

	public void setRetailerPackagerId(String retailerPackagerId) {
		this.retailerPackagerId = retailerPackagerId;
	}

	public String getRetailerPackagerId() {
		return this.retailerPackagerId;
	}

	public void setPackagerModel(String packagerModel) {
		this.packagerModel = packagerModel;
	}

	public String getPackagerModel() {
		return this.packagerModel;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}
}