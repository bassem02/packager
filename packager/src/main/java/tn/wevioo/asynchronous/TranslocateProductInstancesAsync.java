package tn.wevioo.asynchronous;

import org.springframework.beans.factory.annotation.Autowired;

import tn.wevioo.controller.PackagerManagementController;
import tn.wevioo.model.request.PackagerTransformationRequest;

/**
 * The class SplitPackagerAsync implements the action which performs a packager
 * merge asynchronously.
 * 
 * @author vberezan
 * @since 2.13.1
 */
public class TranslocateProductInstancesAsync extends AbstractAsyncAction {

	/**
	 * The request to give to the method translocateProduct.
	 */

	@Autowired
	private PackagerManagementController packagerManagementController;

	private PackagerTransformationRequest request;

	/**
	 * Default construcotr.
	 * 
	 * @param ticketIdentifier
	 *            The identifier of the action ticket which contains the
	 *            information about the status of the current asynchronous
	 *            action. Cannot be null.
	 * @param request
	 *            The request to give to the method translocateProduct.
	 */
	public TranslocateProductInstancesAsync(String ticketIdentifier, PackagerTransformationRequest request) {
		super(ticketIdentifier);
		this.request = request;
	}

	/**
	 * {@inheritDoc}
	 */
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
