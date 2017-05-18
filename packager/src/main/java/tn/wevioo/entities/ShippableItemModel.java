package tn.wevioo.entities;
// Generated 28 mars 2017 22:31:56 by Hibernate Tools 5.2.1.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ShippableItemModel generated by hbm2java
 */
@Entity
@Table(name = "shippable_item_model", catalog = "nn_packager_management_recette")
public class ShippableItemModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String referenceName;
	private Set<PackagerModelShippableItemConfiguration> packagerModelShippableItemConfigurations = new HashSet<PackagerModelShippableItemConfiguration>(
			0);
	private Set<ProductModelShippableItemConfiguration> productModelShippableItemConfigurations = new HashSet<ProductModelShippableItemConfiguration>(
			0);

	public ShippableItemModel() {
	}

	public ShippableItemModel(String referenceName,
			Set<PackagerModelShippableItemConfiguration> packagerModelShippableItemConfigurations,
			Set<ProductModelShippableItemConfiguration> productModelShippableItemConfigurations) {
		this.referenceName = referenceName;
		this.packagerModelShippableItemConfigurations = packagerModelShippableItemConfigurations;
		this.productModelShippableItemConfigurations = productModelShippableItemConfigurations;
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

	@Column(name = "reference_name")
	public String getReferenceName() {
		return this.referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "shippableItemModel")
	public Set<PackagerModelShippableItemConfiguration> getPackagerModelShippableItemConfigurations() {
		return this.packagerModelShippableItemConfigurations;
	}

	public void setPackagerModelShippableItemConfigurations(
			Set<PackagerModelShippableItemConfiguration> packagerModelShippableItemConfigurations) {
		this.packagerModelShippableItemConfigurations = packagerModelShippableItemConfigurations;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "shippableItemModel")
	public Set<ProductModelShippableItemConfiguration> getProductModelShippableItemConfigurations() {
		return this.productModelShippableItemConfigurations;
	}

	public void setProductModelShippableItemConfigurations(
			Set<ProductModelShippableItemConfiguration> productModelShippableItemConfigurations) {
		this.productModelShippableItemConfigurations = productModelShippableItemConfigurations;
	}

}
