package tn.wevioo.entities;
// Generated 28 mars 2017 22:31:56 by Hibernate Tools 5.2.1.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * VPackagerAdminft generated by hbm2java
 */
@Entity
@Table(name = "v_packager_adminft", catalog = "nn_packager_management_recette")
public class VPackagerAdminft implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VPackagerAdminftId id;

	public VPackagerAdminft() {
	}

	public VPackagerAdminft(VPackagerAdminftId id) {
		this.id = id;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "retailerPackagerId", column = @Column(name = "retailer_packager_id", nullable = false)),
			@AttributeOverride(name = "providerProductId", column = @Column(name = "provider_product_id", nullable = false)),
			@AttributeOverride(name = "discriminatorValue", column = @Column(name = "discriminator_value", nullable = false)) })
	public VPackagerAdminftId getId() {
		return this.id;
	}

	public void setId(VPackagerAdminftId id) {
		this.id = id;
	}

}
