package tn.wevioo.packager.dto.product.action;

/**
 * The enumeration ProductInstanceAction defines all the possible business
 * actions which can be performed on a product instance. Some other technical
 * actions (such as clearCache, or updateReferences) are possible, but are not
 * represented in this enumeration.
 */
public enum ProductInstanceActionDTO {

	/**
	 * Creating a new product.
	 */
	CREATE,

	/**
	 * Activating an existing product.
	 */
	ACTIVATE,

	/**
	 * Suspending an existing product.
	 */
	SUSPEND,

	/**
	 * Reactivating an existing product.
	 */
	REACTIVATE,

	/**
	 * Reseting an existing product.
	 */
	RESET,

	/**
	 * Canceling an existing product.
	 */
	CANCEL,

	/**
	 * Deleting an existing product.
	 */
	DELETE,

	/**
	 * Changing properties on an existing product.
	 */
	CHANGE_PROPERTIES,

	/**
	 * Translocating a product from an existing packager to another one.
	 */
	TRANSLOCATE_PRODUCT,

	/**
	 * Importing a product instances into the Packager database.
	 */
	IMPORT,

	/**
	 * Updating a set of product references into the Packager database.
	 */
	UPDATE_REFERENCES;
}
