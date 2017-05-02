package tn.wevioo.dto;

import java.util.ArrayList;
import java.util.List;

public class FPackagerModelDTO {

	private String key;

	private String name;

	private List<FProductModelDTO> products = new ArrayList<FProductModelDTO>();

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FProductModelDTO> getProducts() {
		return products;
	}

	public void setProducts(List<FProductModelDTO> products) {
		this.products = products;
	}

}
