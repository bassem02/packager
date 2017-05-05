package tn.wevioo.tools.context;

import java.util.Comparator;

import tn.wevioo.entities.ProductActionHistory;

/**
 * This class compares two {@link ProductActionHistory} objects by creation
 * date.
 * 
 * @author vberezan
 * @since 1.0.0
 */
public class ProductActionHistoryCreationDateComparator implements Comparator<ProductActionHistory> {

	/**
	 * Compare method.
	 * 
	 * @param history1
	 *            first object.
	 * @param history2
	 *            second object.
	 */
	public int compare(ProductActionHistory history1, ProductActionHistory history2) {
		if (history1.getCreationDate().getTime() == history2.getCreationDate().getTime()) {
			// -- this returns -1 if first object id is < than second object id.
			// -- this returns 1 if first object id is > than second object id.

			if ((history1.getId() != null) && (history2.getId() != null)) {
				return (history1.getId().compareTo(history2.getId()));
			} else {
				// -- this is the case of multi threading actions and is quite
				// impossible to predict the order.
				// -- if the date is the same, we let the "natural order" of the
				// objects.
				return -1;
			}
		} else {

			// -- this returns -1 if first object date is < than second object
			// date.
			// -- this returns 1 if first object date is > than second object
			// date.
			return (history1.getCreationDate().compareTo(history2.getCreationDate()));
		}
	}

}
