package tn.wevioo.packager.entities;
// Generated 28 mars 2017 22:31:56 by Hibernate Tools 5.2.1.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PackagerInstanceProductInstanceId generated by hbm2java
 */
@Embeddable
public class PackagerInstanceProductInstanceId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idPackagerInstance;
	private int idProductInstance;

	public PackagerInstanceProductInstanceId() {
	}

	public PackagerInstanceProductInstanceId(int idPackagerInstance, int idProductInstance) {
		this.idPackagerInstance = idPackagerInstance;
		this.idProductInstance = idProductInstance;
	}

	@Column(name = "id_packager_instance", nullable = false)
	public int getIdPackagerInstance() {
		return this.idPackagerInstance;
	}

	public void setIdPackagerInstance(int idPackagerInstance) {
		this.idPackagerInstance = idPackagerInstance;
	}

	@Column(name = "id_product_instance", nullable = false)
	public int getIdProductInstance() {
		return this.idProductInstance;
	}

	public void setIdProductInstance(int idProductInstance) {
		this.idProductInstance = idProductInstance;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PackagerInstanceProductInstanceId))
			return false;
		PackagerInstanceProductInstanceId castOther = (PackagerInstanceProductInstanceId) other;

		return (this.getIdPackagerInstance() == castOther.getIdPackagerInstance())
				&& (this.getIdProductInstance() == castOther.getIdProductInstance());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdPackagerInstance();
		result = 37 * result + this.getIdProductInstance();
		return result;
	}

}