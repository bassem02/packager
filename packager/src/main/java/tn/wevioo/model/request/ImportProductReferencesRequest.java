package tn.wevioo.model.request;

import java.util.List;

public class ImportProductReferencesRequest {
	List<ProductRequest> requests;

	public List<ProductRequest> getRequests() {
		return requests;
	}

	public void setRequests(List<ProductRequest> requests) {
		this.requests = requests;
	}

}
