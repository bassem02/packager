package tn.wevioo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import tn.wevioo.entities.type.ActionEnum;

/**
 * Product Entity.
 */
@Entity
@Table(name = Parameters.TABLE_NAME)
public class Parameters {

	/**
	 * Table name.
	 */
	public static final String TABLE_NAME = "parameters";

	/**
	 * id product column name.
	 */
	public static final String COLUMN_ID_NAME = "id_parameter";

	/**
	 * Parameters's id.
	 */
	@Id
	@Column(name = Parameters.COLUMN_ID_NAME)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Parameters's typeProduct.
	 */
	@Column(name = "type_product")
	private String typeProduct;

	/**
	 * Parameters's action.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "action")
	private ActionEnum action;

	/**
	 * Parameters's mantisProjectId.
	 */
	@Column(name = "mantis_project_id")
	private Long mantisProjectId;

	/**
	 * Parameters's mantisTitle.
	 */
	@Column(name = "mantis_title")
	private String mantisTitle;

	/**
	 * Parameters's mantisDesription.
	 */
	@Column(name = "mantis_desription")
	private String mantisDesription;

	/**
	 * Parameter's mantisCategory.
	 */

	@Column(name = "mantis_category")
	private String mantisCategory;

	public ActionEnum getAction() {
		return this.action;
	}

	/*
	 * Getter && Setter.
	 */
	public Long getId() {
		return this.id;
	}

	public String getMantisCategory() {
		return this.mantisCategory;
	}

	public String getMantisDesription() {
		return this.mantisDesription;
	}

	public Long getMantisProjectId() {
		return this.mantisProjectId;
	}

	public String getMantisTitle() {
		return this.mantisTitle;
	}

	public String getTypeProduct() {
		return this.typeProduct;
	}

	public void setAction(ActionEnum action) {
		this.action = action;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMantisCategory(String mantisCategory) {
		this.mantisCategory = mantisCategory;
	}

	public void setMantisDesription(String mantisDesription) {
		this.mantisDesription = mantisDesription;
	}

	public void setMantisProjectId(Long mantisProjectId) {
		this.mantisProjectId = mantisProjectId;
	}

	public void setMantisTitle(String mantisTitle) {
		this.mantisTitle = mantisTitle;
	}

	public void setTypeProduct(String typeProduct) {
		this.typeProduct = typeProduct;
	}

}
