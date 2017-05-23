package tn.wevioo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import tn.wevioo.entities.type.StateProduct;

/**
 * Product Entity.
 */
@Entity
@Table(name = Product.TABLE_NAME)
public class Product {

	/**
	 * Table name.
	 */
	public static final String TABLE_NAME = "product";

	/**
	 * id product column name.
	 */
	public static final String COLUMN_ID_NAME = "id_product";

	/**
	 * Product's id.
	 */
	@Id
	@Column(name = Product.COLUMN_ID_NAME)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Product's productModel.
	 */
	@Column(name = "type_product")
	private String typeProduct;

	/**
	 * Product's providerProductId.
	 */
	@Column(name = "provider_product_id")
	private String providerProductId;

	/**
	 * creation date property.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	private Date creationDate;

	/**
	 * last known state.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "etat_product")
	private StateProduct state;

	/**
	 * Product's idClientInterne.
	 */
	@Column(name = "id_client_interne")
	private String idClientInterne;

	/**
	 * Product's refExterne.
	 */
	@Column(name = "reference_externe")
	private String refExterne;

	/**
	 * Product's sendingCommandSupplier.
	 */
	@Column(name = "sending_command_supplier")
	private Boolean sendingCommandSupplier;

	/**
	 * Product's sendingTerminationSupplier.
	 */
	@Column(name = "sending_termination_supplier")
	private Boolean sendingTerminationSupplier;

	/**
	 * Product's effectiveTermination.
	 */
	@Column(name = "effective_termination")
	private Boolean effectiveTermination;

	/**
	 * Product's currentHexacle.
	 */
	@Column(name = "current_hexacle")
	private String currentHexacle;

	/**
	 * Product's previousHexacle.
	 */
	@Column(name = "previous_hexacle")
	private String previousHexacle;
	/**
	 * Product's endUserId.
	 */
	@Column(name = "enduser_id")
	private String endUserId;

	/**
	 * Product's infoCompl.
	 */
	@Column(name = "info_compl")
	private String infoCompl;

	/**
	 * Get creationDate.
	 * 
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	public String getCurrentHexacle() {
		return currentHexacle;
	}

	public Boolean getEffectiveTermination() {
		return effectiveTermination;
	}

	public String getEndUserId() {
		return endUserId;
	}

	/*
	 * Getter && Setter.
	 */
	/**
	 * Get id.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	public String getIdClientInterne() {
		return idClientInterne;
	}

	public String getPreviousHexacle() {
		return previousHexacle;
	}

	/**
	 * Get providerProductId.
	 * 
	 * @return the providerProductId
	 */
	public String getProviderProductId() {
		return providerProductId;
	}

	public String getRefExterne() {
		return refExterne;
	}

	public Boolean getSendingCommandSupplier() {
		return sendingCommandSupplier;
	}

	public Boolean getSendingTerminationSupplier() {
		return sendingTerminationSupplier;
	}

	/**
	 * Get state.
	 * 
	 * @return the state
	 */
	public StateProduct getState() {
		return state;
	}

	public String getTypeProduct() {
		return typeProduct;
	}

	/**
	 * Set creationDate.
	 * 
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setCurrentHexacle(String currentHexacle) {
		this.currentHexacle = currentHexacle;
	}

	public void setEffectiveTermination(Boolean effectiveTermination) {
		this.effectiveTermination = effectiveTermination;
	}

	public void setEndUserId(String endUserId) {
		this.endUserId = endUserId;
	}

	/**
	 * Set Id.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public void setIdClientInterne(String idClientInterne) {
		this.idClientInterne = idClientInterne;
	}

	public void setPreviousHexacle(String previousHexacle) {
		this.previousHexacle = previousHexacle;
	}

	/**
	 * Set providerProductId.
	 * 
	 * @param providerProductId
	 *            the providerProductId to set
	 */
	public void setProviderProductId(String providerProductId) {
		this.providerProductId = providerProductId;
	}

	public void setRefExterne(String refExterne) {
		this.refExterne = refExterne;
	}

	public void setSendingCommandSupplier(Boolean sendingCommandSupplier) {
		this.sendingCommandSupplier = sendingCommandSupplier;
	}

	public void setSendingTerminationSupplier(Boolean sendingTerminationSupplier) {
		this.sendingTerminationSupplier = sendingTerminationSupplier;
	}

	/**
	 * Set state.
	 * 
	 * @param state
	 *            the state to set
	 */
	public void setState(StateProduct state) {
		this.state = state;
	}

	public void setTypeProduct(String typeProduct) {
		this.typeProduct = typeProduct;
	}

	public String getInfoCompl() {
		return infoCompl;
	}

	public void setInfoCompl(String infoCompl) {
		this.infoCompl = infoCompl;
	}

}
