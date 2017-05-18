package tn.wevioo.asynchronous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.wevioo.controller.ProductManagementController;
import tn.wevioo.model.request.ProductRequest;

/**
 * The class CancelProductAsync implements the action which perform a product
 * cancelation asynchronously.
 * 
 * @author mleahu
 * @author vberezan
 * @since 2.0.0
 */

@Component
public class CancelProductAsync extends AbstractAsyncAction {

	/**
	 * The request to give to the method cancelProduct.
	 */

	@Autowired
	private ProductManagementController productManagementController;

	private ProductRequest request;

	/**
	 * Default constructor.
	 * 
	 * @param ticketIdentifier
	 *            The identifier of the action ticket which contains the
	 *            information about the status of the current asynchronous
	 *            action. Cannot be null.
	 * @param request
	 *            The request to give to the method cancelProduct.
	 */
	public CancelProductAsync(String ticketIdentifier, ProductRequest request) {
		super(ticketIdentifier);
		this.request = request;
	}

	/**
	 * {@inheritDoc}
	 */
	protected void doAction() throws Exception {
		productManagementController.cancelProduct(request);
	}

	public ProductRequest getRequest() {
		return request;
	}

	public void setRequest(ProductRequest request) {
		this.request = request;
	}

	public CancelProductAsync() {
	}
}
