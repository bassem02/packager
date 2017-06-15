package tn.wevioo.packager.model.request;

public class PackagerTransformationRequest extends PackagerRequest {

	/**
	 * The destination model.
	 */
	private String destinationModel;

	/**
	 * The destination retailer packager identifier.
	 */
	private String destinationRetailerPackagerId;

	public String getDestinationModel() {
		return destinationModel;
	}

	public void setDestinationModel(String destinationModel) {
		this.destinationModel = destinationModel;
	}

	public String getDestinationRetailerPackagerId() {
		return destinationRetailerPackagerId;
	}

	public void setDestinationRetailerPackagerId(String destinationRetailerPackagerId) {
		this.destinationRetailerPackagerId = destinationRetailerPackagerId;
	}

}