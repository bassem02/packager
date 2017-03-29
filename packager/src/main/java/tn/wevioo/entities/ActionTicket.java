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
 * ActionTicket generated by hbm2java
 */
@Entity
@Table(name = "action_ticket", catalog = "nn_packager_management_recette")
public class ActionTicket implements java.io.Serializable {

	private String idActionTicket;
	private Date creationDate;
	private Date lastUpdate;
	private String methodName;
	private boolean finished;
	private Date completionDate;
	private boolean successed;
	private String thrownExceptionName;
	private String thrownExceptionMessage;
	private String completeStackTrace;
	private String entityId;

	public ActionTicket() {
	}

	public ActionTicket(String idActionTicket, Date lastUpdate, String methodName, boolean finished,
			boolean successed) {
		this.idActionTicket = idActionTicket;
		this.lastUpdate = lastUpdate;
		this.methodName = methodName;
		this.finished = finished;
		this.successed = successed;
	}

	public ActionTicket(String idActionTicket, Date creationDate, Date lastUpdate, String methodName, boolean finished,
			Date completionDate, boolean successed, String thrownExceptionName, String thrownExceptionMessage,
			String completeStackTrace, String entityId) {
		this.idActionTicket = idActionTicket;
		this.creationDate = creationDate;
		this.lastUpdate = lastUpdate;
		this.methodName = methodName;
		this.finished = finished;
		this.completionDate = completionDate;
		this.successed = successed;
		this.thrownExceptionName = thrownExceptionName;
		this.thrownExceptionMessage = thrownExceptionMessage;
		this.completeStackTrace = completeStackTrace;
		this.entityId = entityId;
	}

	@Id

	@Column(name = "id_action_ticket", unique = true, nullable = false)
	public String getIdActionTicket() {
		return this.idActionTicket;
	}

	public void setIdActionTicket(String idActionTicket) {
		this.idActionTicket = idActionTicket;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", length = 19)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update", nullable = false, length = 19)
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Column(name = "method_name", nullable = false)
	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Column(name = "finished", nullable = false)
	public boolean isFinished() {
		return this.finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "completion_date", length = 19)
	public Date getCompletionDate() {
		return this.completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	@Column(name = "successed", nullable = false)
	public boolean isSuccessed() {
		return this.successed;
	}

	public void setSuccessed(boolean successed) {
		this.successed = successed;
	}

	@Column(name = "thrown_exception_name")
	public String getThrownExceptionName() {
		return this.thrownExceptionName;
	}

	public void setThrownExceptionName(String thrownExceptionName) {
		this.thrownExceptionName = thrownExceptionName;
	}

	@Column(name = "thrown_exception_message", length = 65535)
	public String getThrownExceptionMessage() {
		return this.thrownExceptionMessage;
	}

	public void setThrownExceptionMessage(String thrownExceptionMessage) {
		this.thrownExceptionMessage = thrownExceptionMessage;
	}

	@Column(name = "complete_stack_trace", length = 65535)
	public String getCompleteStackTrace() {
		return this.completeStackTrace;
	}

	public void setCompleteStackTrace(String completeStackTrace) {
		this.completeStackTrace = completeStackTrace;
	}

	@Column(name = "entity_id")
	public String getEntityId() {
		return this.entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

}
