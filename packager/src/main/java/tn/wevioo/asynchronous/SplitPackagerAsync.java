package tn.wevioo.asynchronous;

import org.springframework.beans.factory.annotation.Autowired;

import tn.wevioo.controller.PackagerManagementController;
import tn.wevioo.model.request.SplitPackagerRequest;

/**
 * The class SplitPackagerAsync implements the action which performs a packager
 * merge asynchronously.
 * 
 * @author vberezan
 * @since 2.13.1
 */
public class SplitPackagerAsync extends AbstractAsyncAction {

	/**
	 * The source to give to the method splitPackager.
	 */
	private SplitPackagerRequest request;

	@Autowired
	private PackagerManagementController packagerManagementController;

	/**
	 * Default construcotr.
	 * 
	 * @param ticketIdentifier
	 *            The identifier of the action ticket which contains the
	 *            information about the status of the current asynchronous
	 *            action. Cannot be null.
	 * @param source
	 *            The source to give to the method splitPackager.
	 * @param destination1
	 *            The destination1 to give to the method splitPackager.
	 * @param destination2
	 *            The destination2 to give to the method splitPackager.
	 */

	/**
	 * {@inheritDoc}
	 */
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
