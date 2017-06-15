package tn.wevioo.packager.dto.packager.action;

/**
 * The class ActionPackagerHeader allows storing in database packager
 * information related to a packager action. The data contained in this class
 * are not dependent from the packager they come from, which can be modified
 * after the action has been stored.
 */
public class ActionPackagerHeaderDTO {

	/**
	 * Retailer packager identifier corresponding to the packager instance
	 * related to the action.
	 */
	private String retailerPackagerId;

	/**
	 * Packager model key of the packager instance related to the action.
	 */
	private String packagerModelKey;

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

}
