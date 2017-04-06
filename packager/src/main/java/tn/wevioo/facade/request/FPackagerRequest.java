package tn.wevioo.facade.request;

import java.util.ArrayList;
import java.util.List;

public class FPackagerRequest {

	/**
	 * The retailer packager identifier on which performing the action.
	 */
	private String retailerPackagerId;

	/**
	 * The packager model key to which the corresponding packager instance must be after the action has been performed.
	 */
	private String model;

	/**
	 * All the product requests to apply to perform the action.
	 */
	private List<FProductRequest> products = new ArrayList<FProductRequest>();

	/**
	 * The delivery request of this packager.
	 */
	private FDeliveryRequest deliveryRequest;

	public List<FProductRequest> getProducts() {
		return products;
	}

	public void setProducts(List<FProductRequest> products) {
		this.products = products;
	}

	public void setRetailerPackagerId(String retailerPackagerId) {
		this.retailerPackagerId = retailerPackagerId;
	}

	public String getRetailerPackagerId() {
		return this.retailerPackagerId;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModel() {
		return this.model;
	}

	public FDeliveryRequest getDeliveryRequest() {
		return deliveryRequest;
	}

	public void setDeliveryRequest(FDeliveryRequest deliveryRequest) {
		this.deliveryRequest = deliveryRequest;
	}

}
