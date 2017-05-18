package tn.wevioo.asynchronous;

import org.springframework.beans.factory.annotation.Autowired;

import tn.wevioo.controller.ProductManagementController;
import tn.wevioo.model.request.ProductRequest;

/**
 * The class ResetProductAsync implements the action which perform a product
 * reset asynchronously.
 * 
 * @author mleahu
 * @author vberezan
 * @since 2.0.0
 */
public class ResetProductAsync extends AbstractAsyncAction {
	/**
	 * The request to give to the method resetProduct.
	 */
	private ProductRequest request;

	@Autowired
	private ProductManagementController productManagementController;

	/**
	 * Default constructor.
	 * 
	 * @param ticketIdentifier
	 *            The identifier of the action ticket which contains the
	 *            information about the status of the current asynchronous
	 *            action. Cannot be null.
	 * @param request
	 *            The request to give to the method resetProduct.
	 */
	public ResetProductAsync(String ticketIdentifier, ProductRequest request) {
		super(ticketIdentifier);
		this.request = request;
	}

	/**
	 * {@inheritDoc}
	 */
	protected void doAction() throws Exception {
		productManagementController.resetProduct(request);
	}

	public ProductRequest getRequest() {
		return request;
	}

	public void setRequest(ProductRequest request) {
		this.request = request;
	}

	public ResetProductAsync() {
	}
}
