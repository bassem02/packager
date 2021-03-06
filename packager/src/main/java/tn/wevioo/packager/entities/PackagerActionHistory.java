package tn.wevioo.packager.entities;
// Generated 28 mars 2017 22:31:56 by Hibernate Tools 5.2.1.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import tn.wevioo.packager.model.action.PackagerInstanceAction;
import tn.wevioo.packager.service.WebServiceUserService;
import tn.wevioo.packager.tools.context.ProductActionHistoryCreationDateComparator;

/**
 * PackagerActionHistory generated by hbm2java
 */
@Entity
@Table(name = "packager_action_history", catalog = "nn_packager_management_recette")
public class PackagerActionHistory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6696760643815337167L;
	private Integer id;
	private WebServiceUser webServiceUser;
	private Date creationDate;
	private String packagerAction;
	private Date lastUpdate;
	private String retailerPackagerId;
	private String packagerModel;
	private Set<PackagerActionPackagerHeaderDestination> packagerActionPackagerHeaderDestinations = new HashSet<PackagerActionPackagerHeaderDestination>(
			0);
	private Set<ProductActionHistory> productActionHistories = new HashSet<ProductActionHistory>(0);
	private Set<PackagerActionPackagerHeaderSource> packagerActionPackagerHeaderSources = new HashSet<PackagerActionPackagerHeaderSource>(
			0);

	public PackagerActionHistory() {
	}

	public PackagerActionHistory(WebServiceUser webServiceUser, Date lastUpdate) {
		this.webServiceUser = webServiceUser;
		this.lastUpdate = lastUpdate;
	}

	public PackagerActionHistory(WebServiceUser webServiceUser, Date creationDate, String packagerAction,
			Date lastUpdate, Set<PackagerActionPackagerHeaderDestination> packagerActionPackagerHeaderDestinations,
			Set<ProductActionHistory> productActionHistories,
			Set<PackagerActionPackagerHeaderSource> packagerActionPackagerHeaderSources) {
		this.webServiceUser = webServiceUser;
		this.creationDate = creationDate;
		this.packagerAction = packagerAction;
		this.lastUpdate = lastUpdate;
		this.packagerActionPackagerHeaderDestinations = packagerActionPackagerHeaderDestinations;
		this.productActionHistories = productActionHistories;
		this.packagerActionPackagerHeaderSources = packagerActionPackagerHeaderSources;
	}

	public PackagerActionHistory(PackagerInstanceAction action, WebServiceUserService webServiceUserService)
			throws NotFoundException {

		if (action == null) {
			throw new NullException(NullCases.NULL, "action parameter");
		}

		// this.packagerAction = action;
		this.packagerAction = action.toString();
		this.creationDate = new Date();
		this.webServiceUser = webServiceUserService.getWebserviceUser();
		setLastUpdate(new Date());
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_webservice_user", nullable = false)
	public WebServiceUser getWebServiceUser() {
		return this.webServiceUser;
	}

	public void setWebServiceUser(WebServiceUser webServiceUser) {
		this.webServiceUser = webServiceUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", length = 19)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "packager_action")
	public String getPackagerAction() {
		return this.packagerAction;
	}

	public void setPackagerAction(String packagerAction) {
		this.packagerAction = packagerAction;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update", nullable = false, length = 19)
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Transient
	public String getRetailerPackagerId() {
		return retailerPackagerId;
	}

	public void setRetailerPackagerId(String retailerPackagerId) {
		this.retailerPackagerId = retailerPackagerId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "packagerActionHistory", cascade = CascadeType.ALL)
	public Set<PackagerActionPackagerHeaderDestination> getPackagerActionPackagerHeaderDestinations() {
		return this.packagerActionPackagerHeaderDestinations;
	}

	public void setPackagerActionPackagerHeaderDestinations(
			Set<PackagerActionPackagerHeaderDestination> packagerActionPackagerHeaderDestinations) {
		this.packagerActionPackagerHeaderDestinations = packagerActionPackagerHeaderDestinations;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "packagerActionHistory", cascade = CascadeType.ALL)
	public Set<ProductActionHistory> getProductActionHistories() {
		return this.productActionHistories;
	}

	public void setProductActionHistories(Set<ProductActionHistory> productActionHistories) {
		this.productActionHistories = productActionHistories;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "packagerActionHistory", cascade = CascadeType.ALL)
	public Set<PackagerActionPackagerHeaderSource> getPackagerActionPackagerHeaderSources() {
		return this.packagerActionPackagerHeaderSources;
	}

	public void setPackagerActionPackagerHeaderSources(
			Set<PackagerActionPackagerHeaderSource> packagerActionPackagerHeaderSources) {
		this.packagerActionPackagerHeaderSources = packagerActionPackagerHeaderSources;
	}

	@Transient
	public String getPackagerModel() {
		return packagerModel;
	}

	public void setPackagerModel(String packagerModel) {
		this.packagerModel = packagerModel;
	}

	public void addDestination(PackagerInstance destination) {

		/*
		 * if (destination == null) { throw new NullException(NullCases.NULL,
		 * "source parameter"); }
		 */

		if (this.packagerActionPackagerHeaderDestinations == null) {
			packagerActionPackagerHeaderDestinations = new HashSet<PackagerActionPackagerHeaderDestination>(0);
		}

		PackagerActionPackagerHeaderDestination headerSource = new PackagerActionPackagerHeaderDestination();
		headerSource.setRetailerPackagerId(destination.getRetailerPackagerId());
		headerSource.setPackagerModelKey(destination.getPackagerModel().getRetailerKey());
		headerSource.setRetailer(destination.getRetailer());
		headerSource.setLastUpdate(destination.getLastUpdate());
		headerSource.setPackagerActionHistory(this);

		this.packagerActionPackagerHeaderDestinations.add(headerSource);
	}

	public void addSource(PackagerInstance source) {

		if (source == null) {
			// throw new NullException(NullCases.NULL, "source parameter");
		}

		if (this.packagerActionPackagerHeaderSources == null) {
			packagerActionPackagerHeaderSources = new HashSet<PackagerActionPackagerHeaderSource>(0);
		}

		PackagerActionPackagerHeaderSource headerSource = new PackagerActionPackagerHeaderSource();
		headerSource.setRetailerPackagerId(source.getRetailerPackagerId());
		headerSource.setPackagerModelKey(source.getPackagerModel().getRetailerKey());
		headerSource.setRetailer(source.getRetailer());
		headerSource.setLastUpdate(source.getLastUpdate());
		headerSource.setPackagerActionHistory(this);

		this.packagerActionPackagerHeaderSources.add(headerSource);
	}

	public void addProductAction(ProductActionHistory action) {
		if (action == null) {
			throw new NullException(NullCases.NULL, "action parameter");
		}

		if (this.getProductActionHistories() == null) {
			this.productActionHistories = new TreeSet<ProductActionHistory>(
					new ProductActionHistoryCreationDateComparator());
		}

		this.productActionHistories.add(action);
	}

}
