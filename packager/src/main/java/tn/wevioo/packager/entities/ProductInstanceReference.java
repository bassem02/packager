package tn.wevioo.packager.entities;
// Generated 28 mars 2017 22:31:56 by Hibernate Tools 5.2.1.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ProductInstanceReference generated by hbm2java
 */
@Entity
@Table(name = "product_instance_reference", catalog = "nn_packager_management_recette")
public class ProductInstanceReference implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idProductInstanceReference;
	private ProductInstance productInstance;
	private String discriminatorType;
	private String discriminatorValue;
	private Date creationDate;

	public ProductInstanceReference() {
	}

	public ProductInstanceReference(ProductInstance productInstance, String discriminatorType,
			String discriminatorValue) {
		this.productInstance = productInstance;
		this.discriminatorType = discriminatorType;
		this.discriminatorValue = discriminatorValue;
	}

	public ProductInstanceReference(ProductInstance productInstance, String discriminatorType,
			String discriminatorValue, Date creationDate) {
		this.productInstance = productInstance;
		this.discriminatorType = discriminatorType;
		this.discriminatorValue = discriminatorValue;
		this.creationDate = creationDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_product_instance_reference", unique = true, nullable = false)
	public Integer getIdProductInstanceReference() {
		return this.idProductInstanceReference;
	}

	public void setIdProductInstanceReference(Integer idProductInstanceReference) {
		this.idProductInstanceReference = idProductInstanceReference;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_product_instance", nullable = false)
	public ProductInstance getProductInstance() {
		return this.productInstance;
	}

	public void setProductInstance(ProductInstance productInstance) {
		this.productInstance = productInstance;
	}

	@Column(name = "discriminator_type", nullable = false)
	public String getDiscriminatorType() {
		return this.discriminatorType;
	}

	public void setDiscriminatorType(String discriminatorType) {
		this.discriminatorType = discriminatorType;
	}

	@Column(name = "discriminator_value", nullable = false)
	public String getDiscriminatorValue() {
		return this.discriminatorValue;
	}

	public void setDiscriminatorValue(String discriminatorValue) {
		this.discriminatorValue = discriminatorValue;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", length = 19)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public ProductInstanceReference clone() {
		ProductInstanceReference pir = new ProductInstanceReference();

		pir.setCreationDate(this.getCreationDate());
		pir.setDiscriminatorType(this.getDiscriminatorType());
		pir.setDiscriminatorValue(this.getDiscriminatorValue());

		pir.setIdProductInstanceReference(null);

		return pir;
	}

}
