package tn.wevioo.dto;

import java.util.Date;

public class ProductModelDTO {

	private Integer idProductModel;
	private String retailerKey;
	private String oldRetailerKey;
	private Date creationDate;
	private Date lastUpdate;
	private String productDriverFactoryBeanName;
	private String defaultTemplateUrlV2;
	private String defaultConfigurationUri;

	public Integer getIdProductModel() {
		return idProductModel;
	}

	public void setIdProductModel(Integer idProductModel) {
		this.idProductModel = idProductModel;
	}

	public String getRetailerKey() {
		return retailerKey;
	}

	public void setRetailerKey(String retailerKey) {
		this.retailerKey = retailerKey;
	}

	public String getOldRetailerKey() {
		return oldRetailerKey;
	}

	public void setOldRetailerKey(String oldRetailerKey) {
		this.oldRetailerKey = oldRetailerKey;
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

	public String getProductDriverFactoryBeanName() {
		return productDriverFactoryBeanName;
	}

	public void setProductDriverFactoryBeanName(String productDriverFactoryBeanName) {
		this.productDriverFactoryBeanName = productDriverFactoryBeanName;
	}

	public String getDefaultTemplateUrlV2() {
		return defaultTemplateUrlV2;
	}

	public void setDefaultTemplateUrlV2(String defaultTemplateUrlV2) {
		this.defaultTemplateUrlV2 = defaultTemplateUrlV2;
	}

	public String getDefaultConfigurationUri() {
		return defaultConfigurationUri;
	}

	public void setDefaultConfigurationUri(String defaultConfigurationUri) {
		this.defaultConfigurationUri = defaultConfigurationUri;
	}

}
