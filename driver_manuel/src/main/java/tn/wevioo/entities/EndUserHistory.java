package tn.wevioo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * this class models {@link EndUserHistory} object.
 */
@Entity
@Table(name = EndUserHistory.TABLE_NAME)
public class EndUserHistory {

	/**
	 * Table name.
	 */
	public static final String TABLE_NAME = "enduser_history";

	/**
	 * id endUser.
	 */

	/**
	 * id enduser column name.
	 */
	public static final String COLUMN_ID_NAME = "id";

	/**
	 * entity id.
	 */
	@Id
	@Column(name = EndUserHistory.COLUMN_ID_NAME)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * EndUserHistory's oldEndUserId.
	 */
	@Column(name = "old_enduser_id")
	private String oldEndUserId;

	/**
	 * EndUserHistory's newEndUserId.
	 */
	@Column(name = "new_enduser_id")
	private String newEndUserId;

	/**
	 * EndUserHistory's providerProductId.
	 */

	@Column(name = "provider_product_id")
	private String providerProductId;

	/**
	 * EndUserHistory's creationDate.
	 */

	@Column(name = "creation_date")
	private Date creationDate;

	public Date getCreationDate() {
		return creationDate;
	}

	public Long getId() {
		return id;
	}

	public String getNewEndUserId() {
		return newEndUserId;
	}

	public String getOldEndUserId() {
		return oldEndUserId;
	}

	public String getProviderProductId() {
		return providerProductId;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNewEndUserId(String newEndUserId) {
		this.newEndUserId = newEndUserId;
	}

	public void setOldEndUserId(String oldEndUserId) {
		this.oldEndUserId = oldEndUserId;
	}

	public void setProviderProductId(String providerProductId) {
		this.providerProductId = providerProductId;
	}

}
