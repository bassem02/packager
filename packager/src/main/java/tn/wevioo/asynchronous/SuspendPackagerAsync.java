package tn.wevioo.asynchronous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.wevioo.controller.PackagerManagementController;
import tn.wevioo.model.request.PackagerRequest;

/**
 * The class SuspendPackagerAsync implements the action which perform a packager
 * suspend asynchronously.
 * 
 * @author mleahu
 * @author vberezan
 * @since 2.0.0
 */
@Component
public class SuspendPackagerAsync extends AbstractAsyncAction {
	/**
	 * The request to give to the method suspendPackager.
	 */
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
	 *            The request to give to the method suspendPackager.
	 */
	public SuspendPackagerAsync(String ticketIdentifier, PackagerRequest request) {
		super(ticketIdentifier);
		this.request = request;
	}

	/**
	 * {@inheritDoc}
	 */
	protected void doAction() throws Exception {
		packagerManagementController.suspendPackager(request);
	}

	public PackagerRequest getRequest() {
		return request;
	}

	public void setRequest(PackagerRequest request) {
		this.request = request;
	}

	public SuspendPackagerAsync() {
	}
}