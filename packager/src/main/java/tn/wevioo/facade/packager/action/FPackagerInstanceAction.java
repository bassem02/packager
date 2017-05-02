package tn.wevioo.facade.packager.action;

/**
 * The enumeration PackagerInstanceAction defines all the possible business actions which can be performed over a
 * packager instance. Some other technical actions (such as clearCache, or updateReferences) are possible, but are not
 * represented in this enumeration.
 * <p/>
 * For the moment the possible actions are: - CREATE, - ACTIVATE, - SUSPEND, - REACTIVATE, - RESET, - CANCEL, - DELETE,
 * - CHANGE_PROPERTIES, - SPLIT, - SPLIT_SOURCE, - SPLIT_DESTINATION, - MERGE, - MERGE_SOURCE, - MERGE_DESTINATION, -
 * TRANSFORM, - TRANSLOCATE_PRODUCT, - IMPORT, - IMPORT_REFERENCES.
 * 
 * @author bflorea
 * @since 2.6.2
 */
public enum FPackagerInstanceAction {

	/**
	 * Creating a new packager.
	 */
	CREATE,

	/**
	 * Activating an existing packager.
	 */
	ACTIVATE,

	/**
	 * Suspending an existing packager.
	 */
	SUSPEND,

	/**
	 * Reactivating an existing packager.
	 */
	REACTIVATE,

	/**
	 * Reseting an existing packager.
	 */
	RESET,

	/**
	 * Canceling an existing packager.
	 */
	CANCEL,

	/**
	 * Deleting an existing packager.
	 */
	DELETE,

	/**
	 * Changing properties on an existing packager.
	 */
	CHANGE_PROPERTIES,

	/**
	 * Spliting an existing packager into 2 ones.
	 */
	SPLIT,

	/**
	 * Preparing the source packager of a split action.
	 */
	SPLIT_SOURCE,

	/**
	 * Preparing a destination packager of a split action.
	 */
	SPLIT_DESTINATION,

	/**
	 * Merging 2 existing packagers into one.
	 */
	MERGE,

	/**
	 * Preparing a source packager of a merge action.
	 */
	MERGE_SOURCE,

	/**
	 * Preparing the destination packager of a merge action.
	 */
	MERGE_DESTINATION,

	/**
	 * Transforming an existing packager into a new offer.
	 */
	TRANSFORM,

	/**
	 * Translocating a product from an existing packager to another one.
	 */
	TRANSLOCATE_PRODUCT,

	/**
	 * Importing a set of new packager instances into the Packager database.
	 */
	IMPORT,

	/**
	 * Importing a set of product references into the Packager database.
	 */
	IMPORT_REFERENCES,

	/**
	 * Inser a new delivery demand into the Packager database.
	 */
	NEW_DELIVERY_DEMAND,

	/**
	 * Updating a set of product references into the Packager database.
	 */
	UPDATE_REFERENCES;
}
