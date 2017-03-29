package tn.wevioo.entities;
// Generated 28 mars 2017 22:31:56 by Hibernate Tools 5.2.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ProductModelShippableItemConfiguration generated by hbm2java
 */
@Entity
@Table(name = "product_model_shippable_item_configuration", catalog = "nn_packager_management_recette_new")
public class ProductModelShippableItemConfiguration implements java.io.Serializable {

	private Integer id;
	private PackagerModelProductModel packagerModelProductModel;
	private ShippableItemModel shippableItemModel;

	public ProductModelShippableItemConfiguration() {
	}

	public ProductModelShippableItemConfiguration(PackagerModelProductModel packagerModelProductModel,
			ShippableItemModel shippableItemModel) {
		this.packagerModelProductModel = packagerModelProductModel;
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
	@JoinColumn(name = "id_packager_model_product_model")
	public PackagerModelProductModel getPackagerModelProductModel() {
		return this.packagerModelProductModel;
	}

	public void setPackagerModelProductModel(PackagerModelProductModel packagerModelProductModel) {
		this.packagerModelProductModel = packagerModelProductModel;
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
