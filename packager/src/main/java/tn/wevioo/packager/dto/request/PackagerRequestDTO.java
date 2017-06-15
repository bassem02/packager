package tn.wevioo.packager.dto.request;

import java.util.ArrayList;
import java.util.List;

public class PackagerRequestDTO {

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
	private List<ProductRequestDTO> products = new ArrayList<ProductRequestDTO>();

	/**
	 * The delivery request of this packager.
	 */
	private DeliveryRequestDTO deliveryRequest;

	public List<ProductRequestDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductRequestDTO> products) {
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

	public DeliveryRequestDTO getDeliveryRequest() {
		return deliveryRequest;
	}

	public void setDeliveryRequest(DeliveryRequestDTO deliveryRequest) {
		this.deliveryRequest = deliveryRequest;
	}

}
