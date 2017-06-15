package tn.wevioo.packager.asynchronous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.wevioo.packager.controller.PackagerManagementController;
import tn.wevioo.packager.model.request.PackagerRequest;

@Component
public class ResetPackagerAsync extends AbstractAsyncAction {
	/**
	 * The request to give to the method resetPackager.
	 * 
	 */

	@Autowired
	private PackagerManagementController packagerManagementController;

	private PackagerRequest request;

	public ResetPackagerAsync(String ticketIdentifier, PackagerRequest request) {
		super(ticketIdentifier);
		this.request = request;
	}

	protected void doAction() throws Exception {
		packagerManagementController.resetPackager(request);
	}

	public PackagerRequest getRequest() {
		return request;
	}

	public void setRequest(PackagerRequest request) {
		this.request = request;
	}

	public ResetPackagerAsync() {
	}
}
