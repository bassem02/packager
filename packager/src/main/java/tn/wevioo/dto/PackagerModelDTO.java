package tn.wevioo.dto;

import java.util.Date;

public class PackagerModelDTO {

	private Long idPackagerModel;
	private String retailerKey;
	private String oldRetailerKey;
	private Date creationDate;
	private Date lastUpdate;
	private boolean multithreadedActions;

	public Long getIdPackagerModel() {
		return idPackagerModel;
	}

	public void setIdPackagerModel(Long idPackagerModel) {
		this.idPackagerModel = idPackagerModel;
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

	public boolean isMultithreadedActions() {
		return multithreadedActions;
	}

	public void setMultithreadedActions(boolean multithreadedActions) {
		this.multithreadedActions = multithreadedActions;
	}

}
