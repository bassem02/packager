package tn.wevioo.asynchronous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.wevioo.controller.PackagerManagementController;
import tn.wevioo.model.request.PackagerRequest;

/**
 * The class ActivatePackagerAsync implements the action which perform a
 * packager activation asynchronously.
 */

@Component
public class ActivatePackagerAsync extends AbstractAsyncAction {

	@Autowired
	private PackagerManagementController packagerManagementController;

	/**
	 * The request to give to the method activatePackager.
	 */
	private PackagerRequest request;

	/**
	 * Default constructor.
	 * 
	 * @param ticketIdentifier
	 *            The identifier of the action ticket which contains the
	 *            information about the status of the current asynchronous
	 *            action. Cannot be null.
	 * @param request
	 *            The request to give to the method activatePackager.
	 */
	public ActivatePackagerAsync(String ticketIdentifier, PackagerRequest request) {
		super(ticketIdentifier);
		this.request = request;
	}

	/**
	 * {@inheritDoc}
	 */
	protected void doAction() throws Exception {
		packagerManagementController.activatePackager(request);
	}

	public PackagerRequest getRequest() {
		return request;
	}

	public void setRequest(PackagerRequest request) {
		this.request = request;
	}

	public ActivatePackagerAsync() {
	}

}
