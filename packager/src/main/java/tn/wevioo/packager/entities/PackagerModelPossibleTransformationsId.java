package tn.wevioo.packager.entities;
// Generated 28 mars 2017 22:31:56 by Hibernate Tools 5.2.1.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PackagerModelPossibleTransformationsId generated by hbm2java
 */
@Embeddable
public class PackagerModelPossibleTransformationsId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idPackagerModelFrom;
	private int idPackagerModelTo;

	public PackagerModelPossibleTransformationsId() {
	}

	public PackagerModelPossibleTransformationsId(int idPackagerModelFrom, int idPackagerModelTo) {
		this.idPackagerModelFrom = idPackagerModelFrom;
		this.idPackagerModelTo = idPackagerModelTo;
	}

	@Column(name = "id_packager_model_from", nullable = false)
	public int getIdPackagerModelFrom() {
		return this.idPackagerModelFrom;
	}

	public void setIdPackagerModelFrom(int idPackagerModelFrom) {
		this.idPackagerModelFrom = idPackagerModelFrom;
	}

	@Column(name = "id_packager_model_to", nullable = false)
	public int getIdPackagerModelTo() {
		return this.idPackagerModelTo;
	}

	public void setIdPackagerModelTo(int idPackagerModelTo) {
		this.idPackagerModelTo = idPackagerModelTo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PackagerModelPossibleTransformationsId))
			return false;
		PackagerModelPossibleTransformationsId castOther = (PackagerModelPossibleTransformationsId) other;

		return (this.getIdPackagerModelFrom() == castOther.getIdPackagerModelFrom())
				&& (this.getIdPackagerModelTo() == castOther.getIdPackagerModelTo());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdPackagerModelFrom();
		result = 37 * result + this.getIdPackagerModelTo();
		return result;
	}

}
