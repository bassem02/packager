package tn.wevioo.driverManual.entities;

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

import tn.wevioo.driverManual.entities.type.StateProduct;

/**
 * Product action History Entity.
 */
@Entity
@Table(name = ProductHistory.TABLE_NAME)
public class ProductHistory {

	/**
	 * Table name.
	 */
	public static final String TABLE_NAME = "product_history";

	/**
	 * id product column name.
	 */
	public static final String COLUMN_ID_NAME = "id_product_history";

	/**
	 * entity id.
	 */
	@Id
	@Column(name = ProductHistory.COLUMN_ID_NAME)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * ProductHistory's creationDate.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_changement_etat")
	private Date dateChangementEtat;

	/**
	 * ProductHistory's state.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "etat_product")
	private StateProduct state;

	/**
	 * ProductHistory's userChangementEtat.
	 */
	@Column(name = "user_changement_etat")
	private String userChangementEtat;

	/**
	 * ProductHistory's idProduct.
	 */
	@Column(name = Product.COLUMN_ID_NAME)
	private Long idProduct;

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

	/**
	 * Set id.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateChangementEtat() {
		return dateChangementEtat;
	}

	public void setDateChangementEtat(Date dateChangementEtat) {
		this.dateChangementEtat = dateChangementEtat;
	}

	public StateProduct getState() {
		return state;
	}

	public void setState(StateProduct state) {
		this.state = state;
	}

	public String getUserChangementEtat() {
		return userChangementEtat;
	}

	public void setUserChangementEtat(String userChangementEtat) {
		this.userChangementEtat = userChangementEtat;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

}