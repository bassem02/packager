package tn.wevioo.packager.entities;
// Generated 28 mars 2017 22:31:56 by Hibernate Tools 5.2.1.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * PackagerInstanceProductInstance generated by hbm2java
 */
@Entity
@Table(name = "packager_instance_product_instance", catalog = "nn_packager_management_recette")
public class PackagerInstanceProductInstance implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PackagerInstanceProductInstanceId id;
	private PackagerInstance packagerInstance;
	private ProductInstance productInstance;

	public PackagerInstanceProductInstance() {
	}

	public PackagerInstanceProductInstance(PackagerInstanceProductInstanceId id, PackagerInstance packagerInstance,
			ProductInstance productInstance) {
		this.id = id;
		this.packagerInstance = packagerInstance;
		this.productInstance = productInstance;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "idPackagerInstance", column = @Column(name = "id_packager_instance", nullable = false)),
			@AttributeOverride(name = "idProductInstance", column = @Column(name = "id_product_instance", nullable = false)) })
	public PackagerInstanceProductInstanceId getId() {
		return this.id;
	}

	public void setId(PackagerInstanceProductInstanceId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_packager_instance", nullable = false, insertable = false, updatable = false)
	public PackagerInstance getPackagerInstance() {
		return this.packagerInstance;
	}

	public void setPackagerInstance(PackagerInstance packagerInstance) {
		this.packagerInstance = packagerInstance;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_product_instance", nullable = false, insertable = false, updatable = false)
	public ProductInstance getProductInstance() {
		return this.productInstance;
	}

	public void setProductInstance(ProductInstance productInstance) {
		this.productInstance = productInstance;
	}

}
