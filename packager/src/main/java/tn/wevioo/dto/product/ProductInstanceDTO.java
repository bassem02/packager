package tn.wevioo.dto.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nordnet.drivers.contract.types.State;

/**
 * The class FProductInstance allows providing information to the Packager's
 * users about a product instance. All the provided information is guaranteed to
 * have been retrieved instantly.
 */
public class ProductInstanceDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The product instance identifier under which the current product instance
	 * is known into the packager system.
	 */
	private Long productId;

	/**
	 * The identifier under which the current product instance is known into the
	 * provider system. This information should not be used into process system.
	 */
	private String providerProductId;

	/**
	 * This product instance's current state.
	 */
	private State currentState;

	/**
	 * The product instance's product model's key.
	 */
	private String productModel;

	/**
	 * The product instance's customer identifiers. These identifiers allows the
	 * user to connect to its product (if required). They could be a license
	 * key, a login and passwords...
	 */
	private String customerProductIdentifiers;

	/**
	 * Product instance references.
	 */
	private List<ProductInstanceReferenceDTO> references = new ArrayList<ProductInstanceReferenceDTO>();

	/**
	 * Product instance diagnostics.
	 */
	private List<ProductInstanceDiagnosticDTO> diagnostics = new ArrayList<ProductInstanceDiagnosticDTO>();

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getProductId() {
		return this.productId;
	}

	public void setProviderProductId(String providerProductId) {
		this.providerProductId = providerProductId;
	}

	public String getProviderProductId() {
		return this.providerProductId;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public State getCurrentState() {
		return this.currentState;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}

	public String getProductModel() {
		return this.productModel;
	}

	public void setCustomerProductIdentifiers(String customerProductIdentifiers) {
		this.customerProductIdentifiers = customerProductIdentifiers;
	}

	public String getCustomerProductIdentifiers() {
		return this.customerProductIdentifiers;
	}

	public List<ProductInstanceReferenceDTO> getReferences() {
		return references;
	}

	public void setReferences(List<ProductInstanceReferenceDTO> references) {
		this.references = references;
	}

	public List<ProductInstanceDiagnosticDTO> getDiagnostics() {
		return diagnostics;
	}

	public void setDiagnostics(List<ProductInstanceDiagnosticDTO> diagnostics) {
		this.diagnostics = diagnostics;
	}

}