package tn.wevioo.facade.product;

import java.util.Date;

/**
 * The class FProductInstanceDiagnostic allows retrieving information about a product instance diagnostic. Its
 * attributes are got from the database and are guaranteed to be up to date. However, nothing guarantees the database is
 * up to date regarding the true provider system.
 * 
 * @author bflorea
 * @since 2.9.0
 * 
 */
public class FProductInstanceDiagnostic {

	/**
	 * Key of the diagnostic. Specific to product type.
	 */
	private String diagnosticKey;

	/**
	 * Value of the diagnostics.
	 */
	private String diagnosticValue;

	/**
	 * Date when the diagnostic has been computed.
	 */
	private Date creationDate;

	public String getDiagnosticKey() {
		return diagnosticKey;
	}

	public void setDiagnosticKey(String diagnosticKey) {
		this.diagnosticKey = diagnosticKey;
	}

	public String getDiagnosticValue() {
		return diagnosticValue;
	}

	public void setDiagnosticValue(String diagnosticValue) {
		this.diagnosticValue = diagnosticValue;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
