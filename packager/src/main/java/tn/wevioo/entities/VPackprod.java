package tn.wevioo.entities;
// Generated 28 mars 2017 22:31:56 by Hibernate Tools 5.2.1.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * VPackprod generated by hbm2java
 */
@Entity
@Table(name = "v_packprod", catalog = "nn_packager_management_recette")
public class VPackprod implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VPackprodId id;

	public VPackprod() {
	}

	public VPackprod(VPackprodId id) {
		this.id = id;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "packagerCreationDate", column = @Column(name = "packager_creation_date", nullable = false, length = 19)),
			@AttributeOverride(name = "retailerPackagerId", column = @Column(name = "retailer_packager_id", nullable = false)),
			@AttributeOverride(name = "model", column = @Column(name = "model")),
			@AttributeOverride(name = "idProductInstance", column = @Column(name = "id_product_instance", nullable = false)),
			@AttributeOverride(name = "productModel", column = @Column(name = "product_model")),
			@AttributeOverride(name = "providerProductId", column = @Column(name = "provider_product_id", nullable = false)),
			@AttributeOverride(name = "lastKnownState", column = @Column(name = "last_known_state")),
			@AttributeOverride(name = "productCreationDate", column = @Column(name = "product_creation_date", nullable = false, length = 19)) })
	public VPackprodId getId() {
		return this.id;
	}

	public void setId(VPackprodId id) {
		this.id = id;
	}

}
