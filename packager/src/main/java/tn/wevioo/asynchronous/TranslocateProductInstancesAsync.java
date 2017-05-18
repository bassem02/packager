package tn.wevioo.asynchronous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.wevioo.controller.PackagerManagementController;
import tn.wevioo.model.request.PackagerTransformationRequest;

@Component
public class TranslocateProductInstancesAsync extends AbstractAsyncAction {

	@Autowired
	private PackagerManagementController packagerManagementController;

	private PackagerTransformationRequest request;

	public TranslocateProductInstancesAsync(String ticketIdentifier, PackagerTransformationRequest request) {
		super(ticketIdentifier);
		this.request = request;
	}

	protected void doAction() throws Exception {
		packagerManagementController.translocateProductInstances(request);
	}

	public PackagerTransformationRequest getRequest() {
		return request;
	}

	public void setRequest(PackagerTransformationRequest request) {
		this.request = request;
	}

	public TranslocateProductInstancesAsync() {
	}
}
