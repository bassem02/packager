package tn.wevioo.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductInstanceHeaderDTO {
	private int productId;

	private String productModel;

	private String providerProductId;

	private List<ProductInstanceReferenceDTO> productInstanceReferencesDTO = new ArrayList<ProductInstanceReferenceDTO>();

	private List<ProductInstanceDiagnosticDTO> productInstanceDiagnosticsDTO = new ArrayList<ProductInstanceDiagnosticDTO>();

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductModel() {
		return productModel;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}

	public String getProviderProductId() {
		return providerProductId;
	}

	public void setProviderProductId(String providerProductId) {
		this.providerProductId = providerProductId;
	}

	public List<ProductInstanceReferenceDTO> getProductInstanceReferencesDTO() {
		return productInstanceReferencesDTO;
	}

	public void setProductInstanceReferencesDTO(List<ProductInstanceReferenceDTO> productInstanceReferencesDTO) {
		this.productInstanceReferencesDTO = productInstanceReferencesDTO;
	}

	public List<ProductInstanceDiagnosticDTO> getProductInstanceDiagnosticsDTO() {
		return productInstanceDiagnosticsDTO;
	}

	public void setProductInstanceDiagnosticsDTO(List<ProductInstanceDiagnosticDTO> productInstanceDiagnosticsDTO) {
		this.productInstanceDiagnosticsDTO = productInstanceDiagnosticsDTO;
	}

}
