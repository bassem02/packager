package tn.wevioo.model.request;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tn.wevioo.model.packager.action.PackagerInstanceAction;

/*import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import nordnet.packager.bo.packager.action.PackagerInstanceAction;*/

/**
 * The class PackagerRequest defines a specific skeleton in order to ask action
 * request on a packager. Depending on the requested action and on the expected
 * behavior, the different attributes of this request could be filled or not.
 * The list of products can be filled if some specific properties are required
 * to perform the wanted action on the products the regarded packager owns.
 * However, if no propertie is required on the products, the list of product can
 * be empty. These are the required attributes for the packager, depending on
 * the action : This class does not map a table in the database, and is
 * consequently not a persisted entity. However it should be considered as a
 * business object.
 * 
 * @author THUGUERRE
 * @since 2.0.0
 */
public class PackagerRequest {

	/**
	 * The identifier under which the regarded packager is known by the retailer
	 * and on which the action will be performed.
	 */
	private String retailerPackagerId = null;

	/**
	 * The model in which the packager instance should be after the action has
	 * been performed. As example, this could be a creation of a transformation.
	 */
	private String model = null;

	/**
	 * This attributes contains all the product request to apply on the
	 * different products the regarded packager owns.
	 */
	private Set<ProductRequest> products = new HashSet<ProductRequest>();

	/**
	 * Delivery request.
	 */
	private DeliveryRequest deliveryRequest;

	/**
	 * The method getCreationProductRequests returns all the product requests
	 * which have been given in order to ask the creation of new product
	 * instances, i.e. all the product requests whose product instance id is
	 * null and the model key is not null and not empty.
	 * 
	 * <p>
	 * 
	 * If this packager request does not contain any product request, or if the
	 * list is null, this method returns an instantiated but empty list.
	 * 
	 * <p>
	 * 
	 * This method has been designed in order to be a convenience one for more
	 * complex process. In this mind, if you modify one of the returned product
	 * request, you modify the original product request into this current
	 * packager request to.
	 * 
	 * @return A list with product requests.
	 */
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

	/**
	 * The method getChangeProductRequests returns all the product requests
	 * which have been given in order to ask the modification (properties
	 * change) of existing product instances, i.e. all the product requests
	 * whose product instance id is not null.
	 * 
	 * If this packager request does not contain any product request, or if the
	 * list is null, this method returns an instantiated but empty list.
	 * 
	 * This method has been designed in order to be a convenience one for more
	 * complex process. In this mind, if you modify one of the returned product
	 * request, you modify the original product request into this current
	 * packager request to.
	 * 
	 * @return A list with product requests.
	 */
	public List<ProductRequest> getChangeProductRequests() {
		List<ProductRequest> result = new ArrayList<ProductRequest>();

		for (ProductRequest productRequest : this.products) {
			if (productRequest.getProductId() != null) {
				result.add(productRequest);
			}
		}

		return result;
	}

	/**
	 * The method validate verifies if the current packager request is right
	 * enough to perform the received action on a packager instance, i.e. it
	 * verifies if all required and forbidden attributes correspond to the
	 * requirements of the action. All the inner product requests are also
	 * validated.
	 * 
	 * This method does not validate the Xml properties, as they could have to
	 * be merged with other ones before.
	 * 
	 * If the inner product request list is null or empty, nothing is done but
	 * this packager request's attributes are validated anyway.
	 * 
	 * @param action
	 *            The action which will is going to be performed on the
	 *            corresponding packager instance. Cannot be null.
	 * @throws NotRespectedRulesException
	 *             custom exception.
	 */
	/*
	 * public void
	 * validate(tn.wevioo.model.packager.action.PackagerInstanceAction action)
	 * throws NotRespectedRulesException { if (action == null) { throw new
	 * NullException(NullCases.NULL, "action parameter"); }
	 * 
	 * switch (action) { case CREATE: case IMPORT: if
	 * ((this.getRetailerPackagerId() == null) ||
	 * (this.getRetailerPackagerId().trim().length() == 0)) { throw new
	 * NullException(NullCases.NULL_EMPTY,
	 * "packager's request retailer packager id"); } if ((this.getModel() ==
	 * null) || (this.getModel().trim().length() == 0)) { throw new
	 * NullException(NullCases.NULL_EMPTY, "packager's request model key"); }
	 * break; case ACTIVATE: case SUSPEND: case REACTIVATE: case RESET: case
	 * CANCEL: case DELETE: case CHANGE_PROPERTIES: case SPLIT_SOURCE: case
	 * SPLIT_DESTINATION: case MERGE_SOURCE: case MERGE_DESTINATION: case
	 * TRANSFORM: case TRANSLOCATE_PRODUCT: case IMPORT_REFERENCES: if
	 * ((this.getRetailerPackagerId() == null) ||
	 * (this.getRetailerPackagerId().trim().length() == 0)) { throw new
	 * NullException(NullCases.NULL_EMPTY,
	 * "packager's request retailer packager id"); } break; default: throw new
	 * NotRespectedRulesException(new ErrorCode("0.2.2.2"), new Object[]{ action
	 * }); }
	 * 
	 * switch (action) { case CREATE: case ACTIVATE: case SUSPEND: case
	 * REACTIVATE: case RESET: case CANCEL: case DELETE: case SPLIT_SOURCE: case
	 * MERGE_SOURCE: case TRANSFORM: break; case CHANGE_PROPERTIES: case
	 * SPLIT_DESTINATION: case MERGE_DESTINATION: case TRANSLOCATE_PRODUCT: case
	 * IMPORT: case IMPORT_REFERENCES: if ((this.getProducts() == null) ||
	 * (this.getProducts().size() == 0)) { throw new
	 * NotRespectedRulesException(new ErrorCode("1.2.1.1.12")); }
	 * 
	 * break; default: throw new NotRespectedRulesException(new
	 * ErrorCode("0.2.2.2"), new Object[]{ action }); }
	 * 
	 * if (this.getProducts() != null) { List<Long> productIdList = new
	 * ArrayList<Long>(); List<String> providerProductIdList = new
	 * ArrayList<String>();
	 * 
	 * for (ProductRequest pr : this.getProducts()) { if (pr.getProductId() !=
	 * null) { if (productIdList.contains(pr.getProductId())) { throw new
	 * NotRespectedRulesException(new ErrorCode("1.2.1.1.17"), new Object[]{
	 * "product identifier", pr.getProductId() }); } else {
	 * productIdList.add(pr.getProductId()); } }
	 * 
	 * if (pr.getProviderProductId() != null) { if
	 * (providerProductIdList.contains(pr.getProviderProductId())) { throw new
	 * NotRespectedRulesException(new ErrorCode("1.2.1.1.17"), new Object[]{
	 * "provider product identifier", pr.getProviderProductId() }); } else {
	 * providerProductIdList.add(pr.getProviderProductId()); } }
	 * 
	 * pr.validate(action); } } }
	 */

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

	public void validate(PackagerInstanceAction action) {
		if (action == null) {
			// throw new NullException(NullCases.NULL, "action parameter");
		}

		switch (action) {
		case CREATE:
		case IMPORT:
			if ((this.getRetailerPackagerId() == null) || (this.getRetailerPackagerId().trim().length() == 0)) {
				// throw new NullException(NullCases.NULL_EMPTY, "packager's
				// request retailer packager id");
			}
			if ((this.getModel() == null) || (this.getModel().trim().length() == 0)) {
				// throw new NullException(NullCases.NULL_EMPTY, "packager's
				// request model key");
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
				// throw new NullException(NullCases.NULL_EMPTY, "packager's
				// request retailer packager id");
			}
			break;
		default:
			// throw new NotRespectedRulesException(new ErrorCode("0.2.2.2"),
			// new Object[] { action });
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
				// throw new NotRespectedRulesException(new
				// ErrorCode("1.2.1.1.12"));
			}

			break;
		default:
			// throw new NotRespectedRulesException(new ErrorCode("0.2.2.2"),
			// new Object[] { action });
		}

		if (this.getProducts() != null) {
			List<Long> productIdList = new ArrayList<Long>();
			List<String> providerProductIdList = new ArrayList<String>();

			for (ProductRequest pr : this.getProducts()) {
				if (pr.getProductId() != null) {
					if (productIdList.contains(pr.getProductId())) {
						/*
						 * throw new NotRespectedRulesException(new
						 * ErrorCode("1.2.1.1.17"), new Object[] {
						 * "product identifier", pr.getProductId() });
						 */
					} else {
						productIdList.add(pr.getProductId());
					}
				}

				if (pr.getProviderProductId() != null) {
					if (providerProductIdList.contains(pr.getProviderProductId())) {
						/*
						 * throw new NotRespectedRulesException(new
						 * ErrorCode("1.2.1.1.17"), new Object[] {
						 * "provider product identifier",
						 * pr.getProviderProductId() });
						 */
					} else {
						providerProductIdList.add(pr.getProviderProductId());
					}
				}

				pr.validate(action);
			}
		}
	}

}