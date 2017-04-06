package tn.wevioo.dto;

import java.util.Date;

public class PackagerInstanceDTO {

	private Integer idPackagerInstance;
	private String retailerPackagerId;
	private Date creationDate;
	private Date lastUpdate;
	private String toto;
	private PackagerModelDTO packagerModel;
	private RetailerDTO retailer;

	public Integer getIdPackagerInstance() {
		return idPackagerInstance;
	}

	public void setIdPackagerInstance(Integer idPackagerInstance) {
		this.idPackagerInstance = idPackagerInstance;
	}

	public String getRetailerPackagerId() {
		return retailerPackagerId;
	}

	public void setRetailerPackagerId(String retailerPackagerId) {
		this.retailerPackagerId = retailerPackagerId;
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

	public String getToto() {
		return toto;
	}

	public void setToto(String toto) {
		this.toto = toto;
	}

	public PackagerModelDTO getPackagerModel() {
		return packagerModel;
	}

	public void setPackagerModel(PackagerModelDTO packagerModel) {
		this.packagerModel = packagerModel;
	}

	public RetailerDTO getRetailer() {
		return retailer;
	}

	public void setRetailer(RetailerDTO retailer) {
		this.retailer = retailer;
	}

}
