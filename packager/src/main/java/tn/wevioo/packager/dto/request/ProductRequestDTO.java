package tn.wevioo.packager.dto.request;

import java.util.List;

/**
 * The class FProductRequest allows providing action requests on a product
 * instance.
 */
public class ProductRequestDTO {

	/**
	 * The product instance identifier on which performing the action.
	 */
	private Long productId;

	/**
	 * The product model key to which the current product instance must be after
	 * the action has been performed.
	 */
	private String model;

	/**
	 * The Xml properties to use to perform the action.
	 */
	private String properties;

	/**
	 * The provider product identifier to use to perform the action.
	 */
	private String providerProductId;

	/**
	 * Product reference requests.
	 */
	private List<ProductReferenceRequestDTO> references = null;

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getProductId() {
		return this.productId;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModel() {
		return this.model;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getProviderProductId() {
		return providerProductId;
	}

	public void setProviderProductId(String providerProductId) {
		this.providerProductId = providerProductId;
	}

	public String getProperties() {
		return this.properties;
	}

	public List<ProductReferenceRequestDTO> getReferences() {
		return references;
	}

	public void setReferences(List<ProductReferenceRequestDTO> references) {
		this.references = references;
	}

}