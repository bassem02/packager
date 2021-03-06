package tn.wevioo.packager.entities;
// Generated 28 mars 2017 22:31:56 by Hibernate Tools 5.2.1.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.client.RestTemplate;

import nordnet.architecture.exceptions.NNImplicitException;
import nordnet.architecture.exceptions.explicit.MalformedXMLException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.explicit.ResourceAccessException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import nordnet.drivers.contract.exceptions.DriverException;
import tn.wevioo.packager.exceptions.RestTemplateException;
import tn.wevioo.packager.model.action.ProductInstanceAction;
import tn.wevioo.packager.model.request.ProductRequest;
import tn.wevioo.packager.service.ProductInstanceService;
import tn.wevioo.packager.service.WebServiceUserService;

/**
 * ProductModel generated by hbm2java
 */
@Entity
@Table(name = "product_model", catalog = "nn_packager_management_recette")
public class ProductModel implements java.io.Serializable {

	private static final Log LOGGER = LogFactory.getLog(ProductModel.class);
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private Integer idProductModel;
	private String retailerKey;
	private String oldRetailerKey;
	private Date creationDate;
	private Date lastUpdate;
	private String productDriverFactoryBeanName;
	private String defaultTemplateUrlV2;
	private String defaultConfigurationUri;
	private Set<PackagerModelProductModel> packagerModelProductModels = new HashSet<PackagerModelProductModel>(0);
	private Set<ProductInstance> productInstances = new HashSet<ProductInstance>(0);

	public ProductModel() {
	}

	public ProductModel(String oldRetailerKey, Date creationDate, Date lastUpdate,
			String productDriverFactoryBeanName) {
		this.oldRetailerKey = oldRetailerKey;
		this.creationDate = creationDate;
		this.lastUpdate = lastUpdate;
		this.productDriverFactoryBeanName = productDriverFactoryBeanName;
	}

	public ProductModel(String retailerKey, String oldRetailerKey, Date creationDate, Date lastUpdate,
			String productDriverFactoryBeanName, String defaultTemplateUrlV2, String defaultConfigurationUri,
			Set<PackagerModelProductModel> packagerModelProductModels, Set<ProductInstance> productInstances) {
		this.retailerKey = retailerKey;
		this.oldRetailerKey = oldRetailerKey;
		this.creationDate = creationDate;
		this.lastUpdate = lastUpdate;
		this.productDriverFactoryBeanName = productDriverFactoryBeanName;
		this.defaultTemplateUrlV2 = defaultTemplateUrlV2;
		this.defaultConfigurationUri = defaultConfigurationUri;
		this.packagerModelProductModels = packagerModelProductModels;
		this.productInstances = productInstances;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_product_model", unique = true, nullable = false)
	public Integer getIdProductModel() {
		return this.idProductModel;
	}

	public void setIdProductModel(Integer idProductModel) {
		this.idProductModel = idProductModel;
	}

	@Column(name = "retailer_key")
	public String getRetailerKey() {
		return this.retailerKey;
	}

	public void setRetailerKey(String retailerKey) {
		this.retailerKey = retailerKey;
	}

	@Column(name = "old_retailer_key", nullable = false)
	public String getOldRetailerKey() {
		return this.oldRetailerKey;
	}

	public void setOldRetailerKey(String oldRetailerKey) {
		this.oldRetailerKey = oldRetailerKey;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", nullable = false, length = 19)
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

	@Column(name = "product_driver_factory_bean_name", nullable = false)
	public String getProductDriverFactoryBeanName() {
		return this.productDriverFactoryBeanName;
	}

	public void setProductDriverFactoryBeanName(String productDriverFactoryBeanName) {
		this.productDriverFactoryBeanName = productDriverFactoryBeanName;
	}

	@Column(name = "default_template_url_v2", length = 512)
	public String getDefaultTemplateUrlV2() {
		return this.defaultTemplateUrlV2;
	}

	public void setDefaultTemplateUrlV2(String defaultTemplateUrlV2) {
		this.defaultTemplateUrlV2 = defaultTemplateUrlV2;
	}

	@Column(name = "default_configuration_uri", length = 512)
	public String getDefaultConfigurationUri() {
		return this.defaultConfigurationUri;
	}

	public void setDefaultConfigurationUri(String defaultConfigurationUri) {
		this.defaultConfigurationUri = defaultConfigurationUri;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productModel")
	public Set<PackagerModelProductModel> getPackagerModelProductModels() {
		return this.packagerModelProductModels;
	}

	public void setPackagerModelProductModels(Set<PackagerModelProductModel> packagerModelProductModels) {
		this.packagerModelProductModels = packagerModelProductModels;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productModel")
	public Set<ProductInstance> getProductInstances() {
		return this.productInstances;
	}

	public void setProductInstances(Set<ProductInstance> productInstances) {
		this.productInstances = productInstances;
	}

	public ProductInstance instantiate(final String properties, PackagerActionHistory history,
			WebServiceUserService webServiceUserService, ProductInstanceService productInstanceService)
			throws NotRespectedRulesException, DriverException, MalformedXMLException {

		if ((properties != null) && (properties.trim().length() == 0)) {
			throw new NullException(NullCases.EMPTY, "properties parameter");
		}

		if (history == null) {
			throw new NullException(NullCases.NULL, "history parameter");
		}

		try {
			return this.customInstantiate(properties, history, webServiceUserService, productInstanceService);
		} catch (NotRespectedRulesException e) {
			throw e;
		} catch (DriverException e) {
			throw e;
		} catch (MalformedXMLException e) {
			throw e;
		} catch (NNImplicitException e) {
			throw e;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public ProductInstance customInstantiate(final String properties, PackagerActionHistory history,
			WebServiceUserService webServiceUserService, ProductInstanceService productInstanceService)
			throws Exception {

		String url = "http://localhost:8093";
		RestTemplate rest = new RestTemplate();
		String createdProductDriverPPId;

		try {
			createdProductDriverPPId = (String) rest.getForObject(url + "/createProductManual?properties=" + properties,
					String.class);
		} catch (Exception e) {
			throw new RestTemplateException("ProductDriver's project has occured a problem");
		}
		// manualDriver.setProviderProductId(createdProductDriverPPId);
		// ProductInstance createdProductInstance = new
		// ProductInstance(manualDriver);
		ProductInstance createdProductInstance = new ProductInstance(createdProductDriverPPId);
		createdProductInstance.setProductModel(this);
		createdProductInstance.setCreationDate(new Date());
		createdProductInstance.setLastUpdate(new Date());
		createdProductInstance.setLastKnownState("INPROGRESS");
		createdProductInstance.setLastKnownStateUpdate(new Date());

		ProductActionHistory productHistory = new ProductActionHistory(ProductInstanceAction.CREATE, null,
				createdProductInstance, properties, webServiceUserService, productInstanceService);
		history.addProductAction(productHistory);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("A new product [" + this.getRetailerKey() + "] has been successfully created.");
		}

		return createdProductInstance;

	}

	@Transient
	public ProductRequest getDefaultRequest() throws ResourceAccessException {
		ProductRequest result = new ProductRequest();
		result.setModel(this.getRetailerKey());

		String properties = new String("");
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(this.getDefaultTemplateResource().getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				properties = properties.concat(line + System.getProperty("line.separator"));
			}
			br.close();
		} catch (IOException ex) {
			throw new ResourceAccessException(new ErrorCode("0.1.1.2.7"),
					new Object[] { this.getDefaultTemplateUrlV2() }, ex);
		}
		result.setProperties(properties);

		return result;
	}

	@Transient
	public Resource getDefaultTemplateResource() {

		DefaultResourceLoader loader = new DefaultResourceLoader();
		return loader.getResource(this.getDefaultTemplateUrlV2());
	}

	@Transient
	public Resource getDefaultConfigurationResource() {

		ResourceLoader loader = new DefaultResourceLoader();
		return loader.getResource(this.getDefaultConfigurationUri());
	}

}
