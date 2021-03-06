package tn.wevioo.packager.dto.product;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The class FProductInstanceReference represents the Facade Object for an
 * Instance reference.
 */
public class ProductInstanceReferenceDTO {

	/**
	 * This attribute contains the type of this reference. This type is not
	 * constrained in the database, as the drivers define it.
	 */
	private String referenceType;

	/**
	 * This attribute contains the value of this reference.
	 */
	private String referenceValue;

	/**
	 * The date when this product reference has been created into the database.
	 * This attribute did not exist into the version 1.x of the Packager
	 * application. It has been added on the version 2.0.0 in order to have
	 * information about the relevance of this information. The database must be
	 * completed with the required new field on the mapped table.
	 */
	@JsonFormat(pattern = "yyyy-MM-dd KK:mm:ss")
	private Date creationDate;

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Reference type getter.
	 * 
	 * @return The reference type;
	 */
	public String getReferenceType() {
		return referenceType;
	}

	/**
	 * Reference type setter.
	 * 
	 * @param referenceType
	 *            The reference type.
	 */
	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	/**
	 * Reference value getter.
	 * 
	 * @return The reference value.
	 */
	public String getReferenceValue() {
		return referenceValue;
	}

	/**
	 * Reference value setter.
	 * 
	 * @param referenceValue
	 *            The reference value.
	 */
	public void setReferenceValue(String referenceValue) {
		this.referenceValue = referenceValue;
	}

}
