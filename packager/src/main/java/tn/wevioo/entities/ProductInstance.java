package tn.wevioo.entities;
// Generated 28 mars 2017 22:31:56 by Hibernate Tools 5.2.1.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.client.RestTemplate;

import nordnet.architecture.exceptions.explicit.MalformedXMLException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.drivers.contract.ProductDriver;
import nordnet.drivers.contract.exceptions.DriverException;
import nordnet.drivers.contract.types.State;
import tn.wevioo.model.product.action.ProductInstanceAction;

/**
 * ProductInstance generated by hbm2java
 */
@Entity
@Table(name = "product_instance", catalog = "nn_packager_management_recette")
public class ProductInstance implements java.io.Serializable {

	private static final Log LOGGER = LogFactory.getLog(ProductInstance.class);

	private static final long serialVersionUID = 1L;
	private Integer idProductInstance;
	private ProductModel productModel;
	private String providerProductId;
	private Date creationDate;
	private Date lastUpdate;
	private String lastKnownState;
	private Date lastKnownStateUpdate;
	private ProductInstance originalProductInstance = null;
	private ProductDriver productDriver;
	// private PackagerInstance packagerSet = new HashSet<PackagerInstance>();
	private Set<PackagerInstanceProductInstance> packagerInstanceProductInstances = new HashSet<PackagerInstanceProductInstance>(
			0);
	private Set<ProductInstanceReference> productInstanceReferences = new HashSet<ProductInstanceReference>(0);
	private Set<ShippingDemand> shippingDemands = new HashSet<ShippingDemand>(0);
	private Set<ProductInstanceDiagnostic> productInstanceDiagnostics = new HashSet<ProductInstanceDiagnostic>(0);

	public ProductInstance() {
	}

	@Transient
	public Set<PackagerInstance> getPackagerSet() {
		Set<PackagerInstance> result = new HashSet<PackagerInstance>();
		for (PackagerInstanceProductInstance packagerInstanceProductInstance : packagerInstanceProductInstances) {
			result.add(packagerInstanceProductInstance.getPackagerInstance());
		}
		return result;
	}

	// @Transient
	// public PackagerInstance getPackagerSet() {
	// return packagerSet;
	// }
	//
	// public void setPackagerSet(Set<PackagerInstance> packagerSet) {
	// this.packagerSet = packagerSet;
	// }

	public ProductInstance(ProductModel productModel, String providerProductId, Date creationDate, Date lastUpdate) {
		this.productModel = productModel;
		this.providerProductId = providerProductId;
		this.creationDate = creationDate;
		this.lastUpdate = lastUpdate;
	}

	public ProductInstance(ProductModel productModel, String providerProductId, Date creationDate, Date lastUpdate,
			String lastKnownState, Date lastKnownStateUpdate,
			Set<PackagerInstanceProductInstance> packagerInstanceProductInstances,
			Set<ProductInstanceReference> productInstanceReferences, Set<ShippingDemand> shippingDemands,
			Set<ProductInstanceDiagnostic> productInstanceDiagnostics) {
		this.productModel = productModel;
		this.providerProductId = providerProductId;
		this.creationDate = creationDate;
		this.lastUpdate = lastUpdate;
		this.lastKnownState = lastKnownState;
		this.lastKnownStateUpdate = lastKnownStateUpdate;
		this.packagerInstanceProductInstances = packagerInstanceProductInstances;
		this.productInstanceReferences = productInstanceReferences;
		this.shippingDemands = shippingDemands;
		this.productInstanceDiagnostics = productInstanceDiagnostics;
	}

	public ProductInstance(ProductDriver productDriver) {
		if (productDriver == null) {
			throw new NullException(NullCases.NULL, "productDriver parameter");
		}
		setProviderProductId(productDriver.getProviderProductId());
		// this.providerProductId = productDriver.getProviderProductId();
		// setProductDriver(productDriver);
		// this.productDriver = productDriver;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_product_instance", unique = true, nullable = false)
	public Integer getIdProductInstance() {
		return this.idProductInstance;
	}

	public void setIdProductInstance(Integer idProductInstance) {
		this.idProductInstance = idProductInstance;
	}

	@Transient
	public ProductInstance getOriginalProductInstance() {
		return originalProductInstance;
	}

	public void setOriginalProductInstance(ProductInstance originalProductInstance) {
		this.originalProductInstance = originalProductInstance;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_product_model", nullable = false)
	public ProductModel getProductModel() {
		return this.productModel;
	}

	public void setProductModel(ProductModel productModel) {
		this.productModel = productModel;
	}

	@Column(name = "provider_product_id", nullable = false)
	public String getProviderProductId() {
		return this.providerProductId;
	}

	public void setProviderProductId(String providerProductId) {
		this.providerProductId = providerProductId;
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

	@Column(name = "last_known_state")
	public String getLastKnownState() {
		return this.lastKnownState;
	}

	public void setLastKnownState(String lastKnownState) {
		this.lastKnownState = lastKnownState;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_known_state_update", length = 19)
	public Date getLastKnownStateUpdate() {
		return this.lastKnownStateUpdate;
	}

	public void setLastKnownStateUpdate(Date lastKnownStateUpdate) {
		this.lastKnownStateUpdate = lastKnownStateUpdate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productInstance")
	public Set<PackagerInstanceProductInstance> getPackagerInstanceProductInstances() {
		return this.packagerInstanceProductInstances;
	}

	public void setPackagerInstanceProductInstances(
			Set<PackagerInstanceProductInstance> packagerInstanceProductInstances) {
		this.packagerInstanceProductInstances = packagerInstanceProductInstances;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productInstance")
	public Set<ProductInstanceReference> getProductInstanceReferences() {
		return this.productInstanceReferences;
	}

	public void setProductInstanceReferences(Set<ProductInstanceReference> productInstanceReferences) {
		this.productInstanceReferences = productInstanceReferences;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "shipping_demand_product_instance", catalog = "nn_packager_management_recette", joinColumns = {
			@JoinColumn(name = "id_product_instance", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id_shipping_demand", nullable = false, updatable = false) })
	public Set<ShippingDemand> getShippingDemands() {
		return this.shippingDemands;
	}

	public void setShippingDemands(Set<ShippingDemand> shippingDemands) {
		this.shippingDemands = shippingDemands;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productInstance")
	public Set<ProductInstanceDiagnostic> getProductInstanceDiagnostics() {
		return this.productInstanceDiagnostics;
	}

	public void setProductInstanceDiagnostics(Set<ProductInstanceDiagnostic> productInstanceDiagnostics) {
		this.productInstanceDiagnostics = productInstanceDiagnostics;
	}

	@Transient
	public PackagerInstance getPackager() {
		List<PackagerInstance> asList = new ArrayList<PackagerInstance>(getPackagerSet());

		if (asList.size() > 0) {
			return asList.get(0);
		} else {
			return null;
		}
	}

	public void setPackager(PackagerInstance packager) {
		PackagerInstanceProductInstance packagerInstanceProductInstance = new PackagerInstanceProductInstance();
		// this.packagerSet = new HashSet<PackagerInstance>();
		if (packager != null) {
			packagerInstanceProductInstance.setPackagerInstance(packager);
			packagerInstanceProductInstance.setProductInstance(this);
			this.packagerInstanceProductInstances.add(packagerInstanceProductInstance);

		}
	}

	@Transient
	public ProductDriver getProductDriver() {
		return productDriver;
	}

	public void setProductDriver(ProductDriver productDriver) {
		this.productDriver = productDriver;
	}

	@Transient
	public State getCurrentState() throws DriverException {

		State currentState = this.getProductDriver().getCurrentState();

		String currentState_string = currentState.toString();
		String productInstanceStatus = this.getLastKnownState();

		if (!currentState_string.equals(productInstanceStatus)) {
			this.setLastKnownState(currentState_string);
			this.setLastKnownStateUpdate(new Date());
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Product [" + this.getIdProductInstance() + "]'s current state : [" + currentState.toString()
					+ "]");
		}

		return currentState;
	}

	public void suspend(final String properties, PackagerActionHistory packagerHistory)
			throws DriverException, MalformedXMLException, NotRespectedRulesException {

		if ((properties != null) && (properties.trim().length() == 0)) {
			throw new NullException(NullCases.EMPTY, "properties parameter");
		}

		if (packagerHistory == null) {
			throw new NullException(NullCases.NULL, "packagerHistory parameter");
		}

		this.getProductDriver().suspend(properties);

		ProductActionHistory history = new ProductActionHistory(ProductInstanceAction.SUSPEND, this, this, properties);
		packagerHistory.addProductAction(history);

		this.resetLastKnownState();

		// if
		// (this.productModel.getDriverFactory().getDriverInternalConfiguration().areReferencesChangedOnSuspension())
		// {
		try {
			this.updateReferences(packagerHistory);
		} catch (DriverException e) {
			this.productInstanceReferences.clear();
		}
		// }

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Product [" + getIdProductInstance() + "] has been successfully suspended.");
		}
	}

	public void updateReferences(PackagerActionHistory packagerHistory) throws DriverException {

		if (packagerHistory == null) {
			throw new NullException(NullCases.NULL, "packagerHistory parameter");
		}

		// List<Reference> newDriverReferences =
		// this.getProductDriver().getReferences();
		String url = "http://localhost:8093";
		RestTemplate rest = new RestTemplate();

		tn.wevioo.tools.Reference[] newDriverReference = (tn.wevioo.tools.Reference[]) rest
				.getForObject(url + "/manual/getReferences", tn.wevioo.tools.Reference[].class);
		List<tn.wevioo.tools.Reference> newDriverReferences = Arrays.asList(newDriverReference);

		if (this.productInstanceReferences != null) {
			this.productInstanceReferences.clear();
		} else {
			this.productInstanceReferences = new HashSet<ProductInstanceReference>();
		}

		for (tn.wevioo.tools.Reference reference : newDriverReferences) {
			ProductInstanceReference newReference = new ProductInstanceReference();
			newReference.setDiscriminatorType(reference.getKey().toString());
			newReference.setDiscriminatorValue(reference.getValue());
			newReference.setCreationDate(new Date());

			this.addReference(newReference);
		}

		ProductActionHistory history = null;
		if (this.originalProductInstance == null) {
			history = new ProductActionHistory(ProductInstanceAction.UPDATE_REFERENCES, this, this, null);
		} else {
			history = new ProductActionHistory(ProductInstanceAction.UPDATE_REFERENCES, this.originalProductInstance,
					this, null);
		}

		packagerHistory.addProductAction(history);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Product [" + getIdProductInstance() + "]'s references have been updated.");
		}
	}

	public void addReference(ProductInstanceReference reference) {
		if (this.productInstanceReferences == null) {
			this.productInstanceReferences = new HashSet<ProductInstanceReference>();
		}

		if (reference == null) {
			throw new NullException(NullCases.NULL, "reference parameter");
		}

		if ((reference.getDiscriminatorType() == null) || (reference.getDiscriminatorType().trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "reference.type parameter");
		}

		if ((reference.getDiscriminatorValue() == null) || (reference.getDiscriminatorValue().trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "reference.value parameter");
		}

		if (reference.getProductInstance() != null) {
			reference.getProductInstance().getProductInstanceReferences().remove(reference);
		}

		reference.setProductInstance(this);
		reference.setCreationDate(new Date());
		this.productInstanceReferences.add(reference);
	}

	public void resetLastKnownState() {
		this.lastKnownState = null;
		this.lastKnownStateUpdate = null;
	}
}
