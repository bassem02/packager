package tn.wevioo.entities;
// Generated 28 mars 2017 22:31:56 by Hibernate Tools 5.2.1.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ProductInstanceDiagnostic generated by hbm2java
 */
@Entity
@Table(name = "product_instance_diagnostic", catalog = "nn_packager_management_recette")
public class ProductInstanceDiagnostic implements java.io.Serializable {

	private Integer idProductInstanceDiagnostic;
	private ProductInstance productInstance;
	private Date creationDate;
	private String diagnosticKey;
	private String diagnosticValue;

	public ProductInstanceDiagnostic() {
	}

	public ProductInstanceDiagnostic(ProductInstance productInstance, String diagnosticKey, String diagnosticValue) {
		this.productInstance = productInstance;
		this.diagnosticKey = diagnosticKey;
		this.diagnosticValue = diagnosticValue;
	}

	public ProductInstanceDiagnostic(ProductInstance productInstance, Date creationDate, String diagnosticKey,
			String diagnosticValue) {
		this.productInstance = productInstance;
		this.creationDate = creationDate;
		this.diagnosticKey = diagnosticKey;
		this.diagnosticValue = diagnosticValue;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_product_instance_diagnostic", unique = true, nullable = false)
	public Integer getIdProductInstanceDiagnostic() {
		return this.idProductInstanceDiagnostic;
	}

	public void setIdProductInstanceDiagnostic(Integer idProductInstanceDiagnostic) {
		this.idProductInstanceDiagnostic = idProductInstanceDiagnostic;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_product_instance", nullable = false)
	public ProductInstance getProductInstance() {
		return this.productInstance;
	}

	public void setProductInstance(ProductInstance productInstance) {
		this.productInstance = productInstance;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", length = 19)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "diagnostic_key", nullable = false)
	public String getDiagnosticKey() {
		return this.diagnosticKey;
	}

	public void setDiagnosticKey(String diagnosticKey) {
		this.diagnosticKey = diagnosticKey;
	}

	@Column(name = "diagnostic_value", nullable = false)
	public String getDiagnosticValue() {
		return this.diagnosticValue;
	}

	public void setDiagnosticValue(String diagnosticValue) {
		this.diagnosticValue = diagnosticValue;
	}

}
