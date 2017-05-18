package tn.wevioo.asynchronous;

import org.springframework.beans.factory.annotation.Autowired;

import tn.wevioo.controller.PackagerManagementController;
import tn.wevioo.model.request.PackagerRequest;

/**
 * The class ResetPackagerAsync implements the action which perform a packager
 * reset asynchronously.
 * 
 * @author mleahu
 * @author vberezan
 * @since 2.0.0
 */
public class ResetPackagerAsync extends AbstractAsyncAction {
	/**
	 * The request to give to the method resetPackager.
	 * 
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
	 *            The request to give to the method resetPackager.
	 */
	public ResetPackagerAsync(String ticketIdentifier, PackagerRequest request) {
		super(ticketIdentifier);
		this.request = request;
	}

	/**
	 * {@inheritDoc}
	 */
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
