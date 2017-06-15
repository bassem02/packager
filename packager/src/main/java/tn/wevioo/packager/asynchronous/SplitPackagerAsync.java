package tn.wevioo.packager.asynchronous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.wevioo.packager.controller.PackagerManagementController;
import tn.wevioo.packager.model.request.SplitPackagerRequest;

@Component
public class SplitPackagerAsync extends AbstractAsyncAction {

	private SplitPackagerRequest request;

	@Autowired
	private PackagerManagementController packagerManagementController;

	protected void doAction() throws Exception {
		packagerManagementController.splitPackager(request);
	}

	public SplitPackagerAsync(String ticketIdentifier, SplitPackagerRequest request) {
		super(ticketIdentifier);
		this.request = request;
	}

	public SplitPackagerRequest getRequest() {
		return request;
	}

	public void setRequest(SplitPackagerRequest request) {
		this.request = request;
	}

	public SplitPackagerAsync() {
	}
}
