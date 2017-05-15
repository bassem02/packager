package tn.wevioo.entities;
// Generated 28 mars 2017 22:31:56 by Hibernate Tools 5.2.1.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import tn.wevioo.model.product.action.ProductInstanceAction;
import tn.wevioo.service.ProductInstanceService;
import tn.wevioo.service.WebServiceUserService;

/**
 * ProductActionHistory generated by hbm2java
 */
@Entity
@Table(name = "product_action_history", catalog = "nn_packager_management_recette")
public class ProductActionHistory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private PackagerActionHistory packagerActionHistory;
	private WebServiceUser webServiceUser;
	private Date creationDate;
	private String productAction;
	private String properties;
	private String destProductModel;
	private String srcProductModel;
	private Integer destProductId;
	private Integer srcProductId;
	private Date lastUpdate;

	public ProductActionHistory() {
	}

	public ProductActionHistory(WebServiceUser webServiceUser, Date lastUpdate) {
		this.webServiceUser = webServiceUser;
		this.lastUpdate = lastUpdate;
	}

	public ProductActionHistory(PackagerActionHistory packagerActionHistory, WebServiceUser webServiceUser,
			Date creationDate, String productAction, String properties, String destProductModel, String srcProductModel,
			Integer destProductId, Integer srcProductId, Date lastUpdate) {
		this.packagerActionHistory = packagerActionHistory;
		this.webServiceUser = webServiceUser;
		this.creationDate = creationDate;
		this.productAction = productAction;
		this.properties = properties;
		this.destProductModel = destProductModel;
		this.srcProductModel = srcProductModel;
		this.destProductId = destProductId;
		this.srcProductId = srcProductId;
		this.lastUpdate = lastUpdate;
	}

	public ProductActionHistory(ProductInstanceAction action, ProductInstance source, ProductInstance destination,
			String properties, WebServiceUserService webServiceUserService,
			ProductInstanceService productInstanceService) throws NotFoundException {

		if (action == null) {
			throw new NullException(NullCases.NULL, "action parameter");
		}

		if ((properties != null) && (properties.trim().length() == 0)) {
			throw new NullException(NullCases.EMPTY, "properties parameter");
		}

		this.productAction = action.name();
		this.properties = properties;
		this.webServiceUser = webServiceUserService.getWebserviceUser();
		setLastUpdate(new Date());
		this.creationDate = new Date();

		if (source != null) {
			this.srcProductId = source.getIdProductInstance();
			this.srcProductModel = source.getProductModel().getRetailerKey();
		}

		if (destination != null) {
			if (action.name().equals("CREATE")) {
				this.destProductId = productInstanceService.findAll().get(productInstanceService.findAll().size() - 1)
						.getIdProductInstance() + 1;
			} else {
				this.destProductId = destination.getIdProductInstance();
			}
			this.destProductModel = destination.getProductModel().getRetailerKey();
		}

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
	@JoinColumn(name = "id_packager_action_history")
	public PackagerActionHistory getPackagerActionHistory() {
		return this.packagerActionHistory;
	}

	public void setPackagerActionHistory(PackagerActionHistory packagerActionHistory) {
		this.packagerActionHistory = packagerActionHistory;
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

	@Column(name = "product_action")
	public String getProductAction() {
		return this.productAction;
	}

	public void setProductAction(String productAction) {
		this.productAction = productAction;
	}

	@Column(name = "properties", length = 65535)
	public String getProperties() {
		return this.properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	@Column(name = "dest_product_model")
	public String getDestProductModel() {
		return this.destProductModel;
	}

	public void setDestProductModel(String destProductModel) {
		this.destProductModel = destProductModel;
	}

	@Column(name = "src_product_model")
	public String getSrcProductModel() {
		return this.srcProductModel;
	}

	public void setSrcProductModel(String srcProductModel) {
		this.srcProductModel = srcProductModel;
	}

	@Column(name = "dest_product_id")
	public Integer getDestProductId() {
		return this.destProductId;
	}

	public void setDestProductId(Integer destProductId) {
		this.destProductId = destProductId;
	}

	@Column(name = "src_product_id")
	public Integer getSrcProductId() {
		return this.srcProductId;
	}

	public void setSrcProductId(Integer srcProductId) {
		this.srcProductId = srcProductId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update", nullable = false, length = 19)
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
