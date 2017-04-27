package tn.wevioo.model.request;

import java.util.ArrayList;
import java.util.List;

import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import nordnet.drivers.tools.ReferenceKeys;
import tn.wevioo.model.packager.action.PackagerInstanceAction;

/**
 * The class ProductRequest defines a specific skeleton in order to ask action
 * request on a product. Depending on the filled attributes, the requested
 * action is different: This class does not map a table in the database, and is
 * consequently not a persisted entity. However it should be considered as a
 * business object.
 * 
 * @author THUGUERRE
 * @since 2.0.0
 */
public class ProductRequest {

	/**
	 * The identifier of the product instance on which the action will be
	 * applied.
	 */
	private Long productId = null;

	/**
	 * The model in which the product instance should be after the action has
	 * been performed. As example, this could be a creation of a transformation.
	 */
	private String model = null;

	/**
	 * The Xml properties to use to perform the action.
	 */
	private String properties = null;

	/**
	 * The provider product identifier to use to perform the action.
	 */
	private String providerProductId = null;

	/**
	 * Product reference requests.
	 */
	private List<ProductReferenceRequest> references = null;

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getProductId() {
		return this.productId;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModel() {
		return this.model;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getProperties() {
		return this.properties;
	}

	public String getProviderProductId() {
		return providerProductId;
	}

	public void setProviderProductId(String providerProductId) {
		this.providerProductId = providerProductId;
	}

	public List<ProductReferenceRequest> getReferences() {
		if (references == null) {
			references = new ArrayList<ProductReferenceRequest>();
		}
		return references;
	}

	public void setReferences(List<ProductReferenceRequest> references) {
		this.references = references;
	}

	public void validate(PackagerInstanceAction action) throws NotRespectedRulesException {
		if (action == null) {
			throw new NullException(NullCases.NULL, "action parameter");
		}

		switch (action) {
		case CREATE:
		case IMPORT:
			if (this.productId != null) {
				throw new NotRespectedRulesException(new ErrorCode("1.2.1.1.11"), new Object[] { this.productId });
			}

			if ((this.model == null) || (this.model.trim().length() == 0)) {
				throw new NullException(NullCases.NULL_EMPTY, "product's request model key");
			}
			break;

		case CHANGE_PROPERTIES:
		case TRANSFORM:
		case SPLIT_DESTINATION:
		case MERGE_DESTINATION:
			if (this.productId == null) {
				if ((this.model == null) || (this.model.trim().length() == 0)) {
					throw new NullException(NullCases.NULL_EMPTY, "product's request model key");
				}
			}
			break;

		case ACTIVATE:
		case SUSPEND:
		case REACTIVATE:
		case RESET:
		case CANCEL:
		case DELETE:
		case SPLIT_SOURCE:
		case MERGE_SOURCE:
		case TRANSLOCATE_PRODUCT:
			if (this.productId == null) {
				throw new NullException(NullCases.NULL, "product's request product identifier");
			}
			break;
		case IMPORT_REFERENCES:
			if (this.providerProductId == null || this.providerProductId.trim().equals("")) {
				throw new NullException(NullCases.NULL_EMPTY, "product's request provider product identifier");
			}
			if (this.model == null || this.model.trim().equals("")) {
				throw new NullException(NullCases.NULL_EMPTY, "product's request model key");
			}
			break;
		default:
			throw new NotRespectedRulesException(new ErrorCode("0.2.2.2"), new Object[] { action });
		}

		if (action.equals(PackagerInstanceAction.IMPORT)) {
			if ((this.providerProductId == null) || (this.providerProductId.trim().length() == 0)) {
				throw new NullException(NullCases.NULL_EMPTY, "product's request provider product identifier");
			}
		}
		if (action.equals(PackagerInstanceAction.CHANGE_PROPERTIES)
				|| action.equals(PackagerInstanceAction.TRANSFORM)) {
			if ((this.properties == null) || (this.properties.trim().length() == 0)) {
				throw new NullException(NullCases.NULL_EMPTY, "product's request properties");
			}
		}

		if (action.equals(PackagerInstanceAction.IMPORT_REFERENCES)) {
			boolean hasProviderProductId = false;

			if (this.references == null || this.references.size() == 0) {

				throw new NotRespectedRulesException(new ErrorCode("0.2.1.3.2"),
						new Object[] { "reference", "product request", this.providerProductId });

			}

			for (ProductReferenceRequest reference : references) {
				reference.validate(action);

				if (reference.getType().equals(ReferenceKeys.PROVIDER_PRODUCT_ID.toString())) {
					if (reference.getValue().equals(this.providerProductId)) {
						hasProviderProductId = true;
					} else {

						throw new NotRespectedRulesException(new ErrorCode("1.2.1.1.18"),
								new Object[] { reference.getValue(), this.providerProductId });

					}
				}
			}

			if (!hasProviderProductId) {

				throw new NotRespectedRulesException(new ErrorCode("1.2.1.3.5"),
						new Object[] { this.providerProductId });

			}
		}
	}

}