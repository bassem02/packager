package tn.wevioo.packager.model.request;

import java.util.List;

public class UpdatePackagerStatesRequest {

	List<PackagerRequest> requests;

	public List<PackagerRequest> getRequests() {
		return requests;
	}

	public void setRequests(List<PackagerRequest> requests) {
		this.requests = requests;
	}

}
