package tn.wevioo.model.request;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import tn.wevioo.model.packager.action.PackagerInstanceAction;

public class PackagerRequest {

	private String retailerPackagerId = null;

	private String model = null;

	private Set<ProductRequest> products = new HashSet<ProductRequest>();

	private DeliveryRequest deliveryRequest;

	public List<ProductRequest> getCreationProductRequests() {
		List<ProductRequest> result = new ArrayList<ProductRequest>();

		for (ProductRequest productRequest : this.products) {
			if (productRequest.getProductId() == null) {
				if ((productRequest.getModel() != null) && (productRequest.getModel().trim().length() > 0)) {
					result.add(productRequest);
				}
			}
		}

		return result;
	}

	public List<ProductRequest> getChangeProductRequests() {
		List<ProductRequest> result = new ArrayList<ProductRequest>();

		for (ProductRequest productRequest : this.products) {
			if (productRequest.getProductId() != null) {
				result.add(productRequest);
			}
		}

		return result;
	}

	public Set<ProductRequest> getProducts() {
		return products;
	}

	public void setProducts(Set<ProductRequest> products) {
		this.products = products;
	}

	public void setRetailerPackagerId(String retailerPackagerId) {
		this.retailerPackagerId = retailerPackagerId;
	}

	public String getRetailerPackagerId() {
		return this.retailerPackagerId;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModel() {
		return this.model;
	}

	public void addProductRequest(ProductRequest productRequest) {
		this.products.add(productRequest);
	}

	public DeliveryRequest getDeliveryRequest() {
		return deliveryRequest;
	}

	public void setDeliveryRequest(DeliveryRequest deliveryRequest) {
		this.deliveryRequest = deliveryRequest;
	}

	public void validate(PackagerInstanceAction action) throws NotRespectedRulesException {
		if (action == null) {
			throw new NullException(NullCases.NULL, "action parameter");
		}

		switch (action) {
		case CREATE:
		case IMPORT:
			if ((this.getRetailerPackagerId() == null) || (this.getRetailerPackagerId().trim().length() == 0)) {
				throw new NullException(NullCases.NULL_EMPTY, "packager's request retailer packager id");
			}
			if ((this.getModel() == null) || (this.getModel().trim().length() == 0)) {
				throw new NullException(NullCases.NULL_EMPTY, "packager's request model key");
			}
			break;
		case ACTIVATE:
		case SUSPEND:
		case REACTIVATE:
		case RESET:
		case CANCEL:
		case DELETE:
		case CHANGE_PROPERTIES:
		case SPLIT_SOURCE:
		case SPLIT_DESTINATION:
		case MERGE_SOURCE:
		case MERGE_DESTINATION:
		case TRANSFORM:
		case TRANSLOCATE_PRODUCT:
		case IMPORT_REFERENCES:
			if ((this.getRetailerPackagerId() == null) || (this.getRetailerPackagerId().trim().length() == 0)) {
				throw new NullException(NullCases.NULL_EMPTY, "packager's request retailer packager id");
			}
			break;
		default:
			throw new NotRespectedRulesException(new ErrorCode("0.2.2.2"), new Object[] { action });
		}

		switch (action) {
		case CREATE:
		case ACTIVATE:
		case SUSPEND:
		case REACTIVATE:
		case RESET:
		case CANCEL:
		case DELETE:
		case SPLIT_SOURCE:
		case MERGE_SOURCE:
		case TRANSFORM:
			break;
		case CHANGE_PROPERTIES:
		case SPLIT_DESTINATION:
		case MERGE_DESTINATION:
		case TRANSLOCATE_PRODUCT:
		case IMPORT:
		case IMPORT_REFERENCES:
			if ((this.getProducts() == null) || (this.getProducts().size() == 0)) {
				throw new NotRespectedRulesException(new ErrorCode("1.2.1.1.12"));
			}

			break;
		default:
			throw new NotRespectedRulesException(new ErrorCode("0.2.2.2"), new Object[] { action });
		}

		if (this.getProducts() != null) {
			List<Long> productIdList = new ArrayList<Long>();
			List<String> providerProductIdList = new ArrayList<String>();

			for (ProductRequest pr : this.getProducts()) {
				if (pr.getProductId() != null) {
					if (productIdList.contains(pr.getProductId())) {

						throw new NotRespectedRulesException(new ErrorCode("1.2.1.1.17"),
								new Object[] { "product identifier", pr.getProductId() });

					} else {
						productIdList.add(pr.getProductId());
					}
				}

				if (pr.getProviderProductId() != null) {
					if (providerProductIdList.contains(pr.getProviderProductId())) {

						throw new NotRespectedRulesException(new ErrorCode("1.2.1.1.17"),
								new Object[] { "provider product identifier", pr.getProviderProductId() });

					} else {
						providerProductIdList.add(pr.getProviderProductId());
					}
				}

				pr.validate(action);
			}
		}
	}

}