package tn.wevioo.packager.asynchronous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.wevioo.packager.controller.PackagerManagementController;
import tn.wevioo.packager.model.request.PackagerRequest;

@Component
public class CreatePackagerAsync extends AbstractAsyncAction {

	@Autowired
	private PackagerManagementController packagerManagementController;

	private PackagerRequest request;

	/**
	 * Default constructor.
	 * 
	 * @param ticketIdentifier
	 *            The identifier of the action ticket which contains the
	 *            information about the status of the current asynchronous
	 *            action. Cannot be null.
	 * @param request
	 *            The request to give to the method createPackager.
	 */
	public CreatePackagerAsync(String ticketIdentifier, PackagerRequest request) {
		super(ticketIdentifier);
		this.request = request;
	}

	protected void doAction() throws Exception {
		packagerManagementController.createPackager(request);
	}

	public PackagerRequest getRequest() {
		return request;
	}

	public void setRequest(PackagerRequest request) {
		this.request = request;
	}

	public CreatePackagerAsync() {
	}
}
