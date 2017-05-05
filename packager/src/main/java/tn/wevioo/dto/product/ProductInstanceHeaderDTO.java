package tn.wevioo.dto.product;

import java.util.ArrayList;
import java.util.List;

/**
 * The class FProductInstanceHeader allows providing information to the Packager's users about a product instance. This
 * class has been designed in order to be returned quickly by the facade. Consequently, the inner information could have
 * been cached or computed since a long time, and nothing guarantees there is no desynchronization with the provider
 * system. However, the whole system has been designed to minimize this risk.
 * 
 * @author THUGUERRE
 * @since 2.0.0
 */
public class ProductInstanceHeaderDTO {

	/**
	 * The product instance identifier under which the current product instance is known into the packager system.
	 */
	private Long productId;

	/**
	 * The product instance's product model's key.
	 */
	private String productModel;

	/**
	 * The identifier under which the current product instance is known into the provider system. This information
	 * should not be used into process system.
	 */
	private String providerProductId;

	/**
	 * Product instance references.
	 */
	private List<ProductInstanceReferenceDTO> references = new ArrayList<ProductInstanceReferenceDTO>();

	/**
	 * Product instance diagnostics.
	 */
	private List<ProductInstanceDiagnosticDTO> diagnostics = new ArrayList<ProductInstanceDiagnosticDTO>();

	public List<ProductInstanceDiagnosticDTO> getDiagnostics() {
		return diagnostics;
	}

	public void setDiagnostics(List<ProductInstanceDiagnosticDTO> diagnostics) {
		this.diagnostics = diagnostics;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getProductId() {
		return this.productId;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}

	public String getProductModel() {
		return this.productModel;
	}

	public void setProviderProductId(String providerProductId) {
		this.providerProductId = providerProductId;
	}

	public String getProviderProductId() {
		return this.providerProductId;
	}

	public List<ProductInstanceReferenceDTO> getReferences() {
		return references;
	}

	public void setReferences(List<ProductInstanceReferenceDTO> references) {
		this.references = references;
	}

}