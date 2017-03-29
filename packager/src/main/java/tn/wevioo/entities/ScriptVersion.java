package tn.wevioo.entities;
// Generated 28 mars 2017 22:31:56 by Hibernate Tools 5.2.1.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ScriptVersion generated by hbm2java
 */
@Entity
@Table(name = "script_version", catalog = "nn_packager_management_recette_new")
public class ScriptVersion implements java.io.Serializable {

	private String scriptName;
	private int numScript;
	private Date creationDate;

	public ScriptVersion() {
	}

	public ScriptVersion(String scriptName, int numScript, Date creationDate) {
		this.scriptName = scriptName;
		this.numScript = numScript;
		this.creationDate = creationDate;
	}

	@Id

	@Column(name = "script_name", unique = true, nullable = false)
	public String getScriptName() {
		return this.scriptName;
	}

	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	@Column(name = "num_script", nullable = false)
	public int getNumScript() {
		return this.numScript;
	}

	public void setNumScript(int numScript) {
		this.numScript = numScript;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", nullable = false, length = 19)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
