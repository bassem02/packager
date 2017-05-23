package tn.wevioo.model.packager;

import java.util.Set;

import tn.wevioo.entities.PackagerModel;
import tn.wevioo.entities.ProductModel;
import tn.wevioo.model.delivery.PackagingDeliveryConfiguration;

/**
 * The class PackagingConfiguration configures the relationship between a
 * PackagerModel and a ProductModel. It defines under which conditions (instance
 * occurrence, shipping properties, ...) the product model can be packaged into
 * the packager model.
 * <p>
 * As a product model can be packaged into several packager models, several
 * instances of this class can reference several times the same product model.
 * <p>
 * This class maps the table 'packager_model_product_model' into the database.
 */
public class PackagingConfiguration {

	/**
	 * Unique database identifier.
	 */
	private Long id;

	/**
	 * This attribute defines the minimal number of product instance the
	 * regarded packager model must contain. Of course, these product instances
	 * respect the specified product model.
	 */
	private Integer minimumInstances;

	/**
	 * This attribute defines the maximal number of product instances the
	 * regarded packager model must contain. Of course, these product instances
	 * respect the specified product model. If null, the number of instances is
	 * unlimited.
	 */
	private Integer maximumInstances;

	/**
	 * The packager model which packages the product model.
	 */
	private PackagerModel packagerModel;

	/**
	 * The product model which is packaged into the regarded packager model.
	 */

	private tn.wevioo.entities.ProductModel productModel;

	/**
	 * Delivery configuration.
	 */
	private Set<PackagingDeliveryConfiguration> deliveryConfigurations;

	public void setPackagerModel(PackagerModel packagerModel) {
		this.packagerModel = packagerModel;
	}

	public PackagerModel getPackagerModel() {
		return this.packagerModel;
	}

	public void setProductModel(ProductModel productModel) {
		this.productModel = productModel;
	}

	public ProductModel getProductModel() {
		return this.productModel;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setMinimumInstances(Integer minimumInstances) {
		this.minimumInstances = minimumInstances;
	}

	public Integer getMinimumInstances() {
		return this.minimumInstances;
	}

	public void setMaximumInstances(Integer maximumInstances) {
		this.maximumInstances = maximumInstances;
	}

	public Integer getMaximumInstances() {
		return this.maximumInstances;
	}

	public Set<PackagingDeliveryConfiguration> getDeliveryConfigurations() {
		return deliveryConfigurations;
	}

	public void setDeliveryConfigurations(Set<PackagingDeliveryConfiguration> deliveryConfigurations) {
		this.deliveryConfigurations = deliveryConfigurations;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maximumInstances == null) ? 0 : maximumInstances.hashCode());
		result = prime * result + ((minimumInstances == null) ? 0 : minimumInstances.hashCode());
		result = prime * result + ((packagerModel == null) ? 0 : packagerModel.hashCode());
		result = prime * result + ((productModel == null) ? 0 : productModel.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PackagingConfiguration other = (PackagingConfiguration) obj;
		if (maximumInstances == null) {
			if (other.maximumInstances != null) {
				return false;
			}
		} else if (!maximumInstances.equals(other.maximumInstances)) {
			return false;
		}
		if (minimumInstances == null) {
			if (other.minimumInstances != null) {
				return false;
			}
		} else if (!minimumInstances.equals(other.minimumInstances)) {
			return false;
		}
		if (packagerModel == null) {
			if (other.packagerModel != null) {
				return false;
			}
		} else if (!packagerModel.equals(other.packagerModel)) {
			return false;
		}
		if (productModel == null) {
			if (other.productModel != null) {
				return false;
			}
		} else if (!productModel.equals(other.productModel)) {
			return false;
		}
		return true;
	}

}