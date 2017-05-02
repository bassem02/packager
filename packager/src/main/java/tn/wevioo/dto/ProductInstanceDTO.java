package tn.wevioo.dto;

import java.util.Date;

public class ProductInstanceDTO {

	private Integer idProductInstance;
	private ProductModelDTO productModel;
	private String providerProductId;
	private Date creationDate;
	private Date lastUpdate;
	private String lastKnownState;
	private Date lastKnownStateUpdate;

	public ProductModelDTO getProductModel() {
		return productModel;
	}

	public void setProductModel(ProductModelDTO productModel) {
		this.productModel = productModel;
	}

	public Integer getIdProductInstance() {
		return idProductInstance;
	}

	public void setIdProductInstance(Integer idProductInstance) {
		this.idProductInstance = idProductInstance;
	}

	public String getProviderProductId() {
		return providerProductId;
	}

	public void setProviderProductId(String providerProductId) {
		this.providerProductId = providerProductId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getLastKnownState() {
		return lastKnownState;
	}

	public void setLastKnownState(String lastKnownState) {
		this.lastKnownState = lastKnownState;
	}

	public Date getLastKnownStateUpdate() {
		return lastKnownStateUpdate;
	}

	public void setLastKnownStateUpdate(Date lastKnownStateUpdate) {
		this.lastKnownStateUpdate = lastKnownStateUpdate;
	}

}
