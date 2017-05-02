package tn.wevioo.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PackagerInstanceHeaderDTO {
	private String retailerPackagerId;

	private String packagerModel;

	private List<ProductInstanceHeaderDTO> products = new ArrayList<ProductInstanceHeaderDTO>();

	private Date creationDate;

	public String getRetailerPackagerId() {
		return retailerPackagerId;
	}

	public void setRetailerPackagerId(String retailerPackagerId) {
		this.retailerPackagerId = retailerPackagerId;
	}

	public String getPackagerModel() {
		return packagerModel;
	}

	public void setPackagerModel(String packagerModel) {
		this.packagerModel = packagerModel;
	}

	public List<ProductInstanceHeaderDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductInstanceHeaderDTO> products) {
		this.products = products;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
