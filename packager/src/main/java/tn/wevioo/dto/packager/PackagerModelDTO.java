package tn.wevioo.dto.packager;

import java.util.ArrayList;
import java.util.List;

import tn.wevioo.dto.product.ProductModelDTO;

/**
 * The class FPackagerModel allows retrieving the information about an existent
 * packager model.
 * 
 * @author vberezan
 * @since 2.0.0
 */
public class PackagerModelDTO {

	/**
	 * The technical reference under which the current model is known. This
	 * reference is unique into the database.
	 * <p>
	 * Setting this reference to null means deactivating the creation of new
	 * packager instances using this model. However, in this case, the packager
	 * model is not removed from the database, in order to keep the relation
	 * with the already instantiated packager instances. The reference can be
	 * then assigned to another packager model, with a different configuration.
	 */
	private String key;

	/**
	 * A human readable reference allowing to recognize easily the
	 * PackagerModel. In practice, it generally equals the attribute key's
	 * value.
	 * <p>
	 * Contrary to the attribute 'key', when the packager model creations are
	 * deactivated, this attribute is never set to null, in order to keep in
	 * mind which model it was.
	 */
	private String name;

	/**
	 * Product models.
	 */
	private List<ProductModelDTO> products = new ArrayList<ProductModelDTO>();

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProductModelDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductModelDTO> products) {
		this.products = products;
	}

}
