package tn.wevioo.model.request;

import java.util.List;

public class DeletePackagersRequest {

	List<PackagerRequest> requests;

	Boolean ignoreProviderException;

	Boolean ignoreUncanceledState;

	public List<PackagerRequest> getRequests() {
		return requests;
	}

	public void setRequests(List<PackagerRequest> requests) {
		this.requests = requests;
	}

	public Boolean getIgnoreProviderException() {
		return ignoreProviderException;
	}

	public void setIgnoreProviderException(Boolean ignoreProviderException) {
		this.ignoreProviderException = ignoreProviderException;
	}

	public Boolean getIgnoreUncanceledState() {
		return ignoreUncanceledState;
	}

	public void setIgnoreUncanceledState(Boolean ignoreUncanceledState) {
		this.ignoreUncanceledState = ignoreUncanceledState;
	}

}
