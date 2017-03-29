package tn.wevioo.entities;
// Generated 28 mars 2017 22:31:56 by Hibernate Tools 5.2.1.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * WsCallHistory generated by hbm2java
 */
@Entity
@Table(name = "ws_call_history", catalog = "nn_packager_management_recette")
public class WsCallHistory implements java.io.Serializable {

	private Integer id;
	private Date creationDate;
	private String level;
	private String remoteIpAddress;
	private String wsUser;
	private String methodName;
	private String parameters;
	private String retailerPackagerId;
	private String productId;
	private String durationCallMs;
	private String exceptionName;
	private String errorCode;
	private String wsdlName;

	public WsCallHistory() {
	}

	public WsCallHistory(Date creationDate, String level, String remoteIpAddress, String wsUser, String methodName,
			String parameters, String retailerPackagerId, String productId, String durationCallMs, String exceptionName,
			String errorCode, String wsdlName) {
		this.creationDate = creationDate;
		this.level = level;
		this.remoteIpAddress = remoteIpAddress;
		this.wsUser = wsUser;
		this.methodName = methodName;
		this.parameters = parameters;
		this.retailerPackagerId = retailerPackagerId;
		this.productId = productId;
		this.durationCallMs = durationCallMs;
		this.exceptionName = exceptionName;
		this.errorCode = errorCode;
		this.wsdlName = wsdlName;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", length = 19)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "level", length = 45)
	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Column(name = "remote_ip_address", length = 20)
	public String getRemoteIpAddress() {
		return this.remoteIpAddress;
	}

	public void setRemoteIpAddress(String remoteIpAddress) {
		this.remoteIpAddress = remoteIpAddress;
	}

	@Column(name = "ws_user", length = 45)
	public String getWsUser() {
		return this.wsUser;
	}

	public void setWsUser(String wsUser) {
		this.wsUser = wsUser;
	}

	@Column(name = "method_name")
	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Column(name = "parameters")
	public String getParameters() {
		return this.parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	@Column(name = "retailer_packager_id")
	public String getRetailerPackagerId() {
		return this.retailerPackagerId;
	}

	public void setRetailerPackagerId(String retailerPackagerId) {
		this.retailerPackagerId = retailerPackagerId;
	}

	@Column(name = "product_id")
	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Column(name = "duration_call_ms")
	public String getDurationCallMs() {
		return this.durationCallMs;
	}

	public void setDurationCallMs(String durationCallMs) {
		this.durationCallMs = durationCallMs;
	}

	@Column(name = "exception_name")
	public String getExceptionName() {
		return this.exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

	@Column(name = "error_code")
	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Column(name = "wsdl_name")
	public String getWsdlName() {
		return this.wsdlName;
	}

	public void setWsdlName(String wsdlName) {
		this.wsdlName = wsdlName;
	}

}
