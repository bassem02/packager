package tn.wevioo.dto.packager.action;

import java.util.Date;
import java.util.Set;
import java.util.SortedSet;

import com.fasterxml.jackson.annotation.JsonFormat;

import tn.wevioo.dto.product.action.ProductActionHistoryDTO;

/**
 * The class PackagerActionHistory allows saving in database one history over an
 * action which has been performed on a packager instance. This history should
 * be kept as long as possible in the database, even if the corresponding target
 * packagers are deleted. This class maps the database table
 * 'packager_action_history'.
 */
public class PackagerActionHistoryDTO {

	/**
	 * The date when this history is saved into the database.
	 */

	@JsonFormat(pattern = "yyyy-MM-dd KK:mm:ss")
	private Date creationDate;

	/**
	 * The attribute action contains the type of the action which is performed
	 * over the source packager.
	 */
	private PackagerInstanceActionDTO action;

	/**
	 * The user which has asked current action to be performed. This normally
	 * should correspond to the user which is currently authenticated when the
	 * action is performed.
	 */
	private String username;

	/**
	 * Packager header sources.
	 */
	private Set<ActionPackagerHeaderSourceDTO> sources;

	/**
	 * Packager header destination.
	 */
	private Set<ActionPackagerHeaderDestinationDTO> destinations;

	/**
	 * Product actions history.
	 */
	private SortedSet<ProductActionHistoryDTO> productActions;

	/**
	 * Default constructor.
	 */
	public PackagerActionHistoryDTO() {
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public PackagerInstanceActionDTO getAction() {
		return action;
	}

	public void setAction(PackagerInstanceActionDTO action) {
		this.action = action;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<ActionPackagerHeaderSourceDTO> getSources() {
		return sources;
	}

	public void setSources(Set<ActionPackagerHeaderSourceDTO> sources) {
		this.sources = sources;
	}

	public Set<ActionPackagerHeaderDestinationDTO> getDestinations() {
		return destinations;
	}

	public void setDestinations(Set<ActionPackagerHeaderDestinationDTO> destinations) {
		this.destinations = destinations;
	}

	public void setProductActions(SortedSet<ProductActionHistoryDTO> productActions) {
		this.productActions = productActions;
	}

	public SortedSet<ProductActionHistoryDTO> getProductActions() {
		return productActions;
	}

}
