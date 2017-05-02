package tn.wevioo.dto;

import java.util.Date;

public class ProductInstanceDiagnosticDTO {

	private String diagnosticKey;

	private String diagnosticValue;

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
