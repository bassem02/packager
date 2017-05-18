package tn.wevioo.asynchronous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.wevioo.controller.PackagerManagementController;
import tn.wevioo.model.request.PackagerRequest;

@Component
public class ChangePackagerPropertiesAsync extends AbstractAsyncAction {

	@Autowired
	private PackagerManagementController packagerManagementController;

	private PackagerRequest request;

	public ChangePackagerPropertiesAsync(String ticketIdentifier, PackagerRequest request) {
		super(ticketIdentifier);
		this.request = request;
	}

	protected void doAction() throws Exception {
		packagerManagementController.changePackagerProperties(request);
	}

	public PackagerRequest getRequest() {
		return request;
	}

	public void setRequest(PackagerRequest request) {
		this.request = request;
	}

	public ChangePackagerPropertiesAsync() {
	}

}
