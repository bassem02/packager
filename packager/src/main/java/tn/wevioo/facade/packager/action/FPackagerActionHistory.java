package tn.wevioo.facade.packager.action;

import java.util.Date;
import java.util.Set;
import java.util.SortedSet;

import tn.wevioo.facade.product.action.FProductActionHistory;

/**
 * The class PackagerActionHistory allows saving in database one history over an
 * action which has been performed on a packager instance. This history should
 * be kept as long as possible in the database, even if the corresponding target
 * packagers are deleted. This class maps the database table
 * 'packager_action_history'.
 * 
 * @author bflorea
 * @since 2.6.2
 */
public class FPackagerActionHistory {

	/**
	 * The date when this history is saved into the database.
	 */
	private Date creationDate;

	/**
	 * The attribute action contains the type of the action which is performed
	 * over the source packager.
	 */
	private FPackagerInstanceAction action;

	/**
	 * The user which has asked current action to be performed. This normally
	 * should correspond to the user which is currently authenticated when the
	 * action is performed.
	 */
	private String username;

	/**
	 * Packager header sources.
	 */
	private Set<FActionPackagerHeaderSource> sources;

	/**
	 * Packager header destination.
	 */
	private Set<FActionPackagerHeaderDestination> destinations;

	/**
	 * Product actions history.
	 */
	private SortedSet<FProductActionHistory> productActions;

	/**
	 * Default constructor.
	 */
	public FPackagerActionHistory() {
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public FPackagerInstanceAction getAction() {
		return action;
	}

	public void setAction(FPackagerInstanceAction action) {
		this.action = action;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<FActionPackagerHeaderSource> getSources() {
		return sources;
	}

	public void setSources(Set<FActionPackagerHeaderSource> sources) {
		this.sources = sources;
	}

	public Set<FActionPackagerHeaderDestination> getDestinations() {
		return destinations;
	}

	public void setDestinations(Set<FActionPackagerHeaderDestination> destinations) {
		this.destinations = destinations;
	}

	public void setProductActions(SortedSet<FProductActionHistory> productActions) {
		this.productActions = productActions;
	}

	public SortedSet<FProductActionHistory> getProductActions() {
		return productActions;
	}

}
