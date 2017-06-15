package tn.wevioo.packager.model.request;

import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import tn.wevioo.packager.model.action.PackagerInstanceAction;

/*import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.packager.bo.packager.action.PackagerInstanceAction;*/

/**
 * The class ProductReferenceRequest defines a specific skeleton in order to ask
 * action request on a product instance reference.
 * 
 * @author mleahu
 * @since 2.6.2
 */
public class ProductReferenceRequest {

	/**
	 * Initial Value: Type of the reference.
	 */
	private String type;

	/**
	 * Initial Value: Value of the reference.
	 */
	private String value;

	/**
	 * The method validate verifies if the current product reference request is
	 * right enough to perform the received action on a product instance
	 * reference, i.e. it verifies if all required and forbidden attributes
	 * correspond to the requirements of the action.
	 * 
	 * @param action
	 *            The action which will is going to be performed on the
	 *            corresponding product instance. Cannot be null.
	 * @throws NotRespectedRulesException
	 */
	/*
	 * public void
	 * validate(tn.wevioo.model.packager.action.PackagerInstanceAction action)
	 * throws NotRespectedRulesException { if (type == null || value == null ||
	 * type.trim().equals("") || value.trim().equals("")) { throw new
	 * NullException(NullCases.NULL_EMPTY,
	 * "product reference request's type/value"); } }
	 */

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void validate(
			PackagerInstanceAction action) /*
											 * throws NotRespectedRulesException
											 */ {
		if (type == null || value == null || type.trim().equals("") || value.trim().equals("")) {
			// throw new NullException(NullCases.NULL_EMPTY, "product reference
			// request's type/value");
		}
	}

}
