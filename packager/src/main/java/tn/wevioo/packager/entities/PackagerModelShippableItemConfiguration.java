package tn.wevioo.packager.entities;
// Generated 28 mars 2017 22:31:56 by Hibernate Tools 5.2.1.Final

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * PackagerModelShippableItemConfiguration generated by hbm2java
 */
@Entity
@Table(name = "packager_model_shippable_item_configuration", catalog = "nn_packager_management_recette")
public class PackagerModelShippableItemConfiguration implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private PackagerModel packagerModel;
	private ShippableItemModel shippableItemModel;

	public PackagerModelShippableItemConfiguration() {
	}

	public PackagerModelShippableItemConfiguration(PackagerModel packagerModel, ShippableItemModel shippableItemModel) {
		this.packagerModel = packagerModel;
		this.shippableItemModel = shippableItemModel;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_packager_model")
	public PackagerModel getPackagerModel() {
		return this.packagerModel;
	}

	public void setPackagerModel(PackagerModel packagerModel) {
		this.packagerModel = packagerModel;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_shippable_item_model")
	public ShippableItemModel getShippableItemModel() {
		return this.shippableItemModel;
	}

	public void setShippableItemModel(ShippableItemModel shippableItemModel) {
		this.shippableItemModel = shippableItemModel;
	}

}
