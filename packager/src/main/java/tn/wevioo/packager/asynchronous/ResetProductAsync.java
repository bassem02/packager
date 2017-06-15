package tn.wevioo.packager.asynchronous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.wevioo.packager.controller.ProductManagementController;
import tn.wevioo.packager.model.request.ProductRequest;

@Component
public class ResetProductAsync extends AbstractAsyncAction {

	private ProductRequest request;

	@Autowired
	private ProductManagementController productManagementController;

	public ResetProductAsync(String ticketIdentifier, ProductRequest request) {
		super(ticketIdentifier);
		this.request = request;
	}

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
