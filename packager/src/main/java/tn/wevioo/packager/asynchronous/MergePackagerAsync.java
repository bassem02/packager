package tn.wevioo.packager.asynchronous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.wevioo.packager.controller.PackagerManagementController;
import tn.wevioo.packager.model.request.MergePackagersRequest;

@Component
public class MergePackagerAsync extends AbstractAsyncAction {

	@Autowired
	private PackagerManagementController packagerManagementController;

	private MergePackagersRequest request;

	public MergePackagerAsync(String ticketIdentifier, MergePackagersRequest request) {
		super(ticketIdentifier);
		this.request = request;
	}

	protected void doAction() throws Exception {
		packagerManagementController.mergePackagers(request);
	}

	public MergePackagersRequest getRequest() {
		return request;
	}

	public void setRequest(MergePackagersRequest request) {
		this.request = request;
	}

	public MergePackagerAsync() {
	}
}
