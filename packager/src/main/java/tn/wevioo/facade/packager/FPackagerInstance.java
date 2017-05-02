package tn.wevioo.facade.packager;

import java.util.ArrayList;
import java.util.List;

import nordnet.drivers.contract.types.State;
import tn.wevioo.facade.product.FProductInstance;

/**
 * The class FPackagerInstance allows providing information to the Packager's
 * users about a full packager instance. All the provided information is
 * guaranteed to have been retrieved instantly.
 * 
 * @author THUGUERRE
 * @since 2.0.0
 */
public class FPackagerInstance {

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
	 * This packager instance's current state.
	 */
	private State currentState;

	/**
	 * All the product instances this packager instance owns.
	 */
	private List<FProductInstance> products = new ArrayList<FProductInstance>();

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

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public State getCurrentState() {
		return this.currentState;
	}

	public List<FProductInstance> getProducts() {
		return products;
	}

	public void setProducts(List<FProductInstance> products) {
		this.products = products;
	}

}