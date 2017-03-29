package tn.wevioo.entities;
// Generated 28 mars 2017 22:31:56 by Hibernate Tools 5.2.1.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Retailer generated by hbm2java
 */
@Entity
@Table(name = "retailer", catalog = "nn_packager_management_recette_new")
public class Retailer implements java.io.Serializable {

	private Integer idRetailer;
	private String name;
	private Set<PackagerInstance> packagerInstances = new HashSet<PackagerInstance>(0);
	private Set<PackagerModel> packagerModels = new HashSet<PackagerModel>(0);
	private Set<WebServiceUser> webServiceUsers = new HashSet<WebServiceUser>(0);
	private Set<PackagerActionPackagerHeaderDestination> packagerActionPackagerHeaderDestinations = new HashSet<PackagerActionPackagerHeaderDestination>(
			0);
	private Set<PackagerActionPackagerHeaderSource> packagerActionPackagerHeaderSources = new HashSet<PackagerActionPackagerHeaderSource>(
			0);

	public Retailer() {
	}

	public Retailer(String name) {
		this.name = name;
	}

	public Retailer(String name, Set<PackagerInstance> packagerInstances, Set<PackagerModel> packagerModels,
			Set<WebServiceUser> webServiceUsers,
			Set<PackagerActionPackagerHeaderDestination> packagerActionPackagerHeaderDestinations,
			Set<PackagerActionPackagerHeaderSource> packagerActionPackagerHeaderSources) {
		this.name = name;
		this.packagerInstances = packagerInstances;
		this.packagerModels = packagerModels;
		this.webServiceUsers = webServiceUsers;
		this.packagerActionPackagerHeaderDestinations = packagerActionPackagerHeaderDestinations;
		this.packagerActionPackagerHeaderSources = packagerActionPackagerHeaderSources;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_retailer", unique = true, nullable = false)
	public Integer getIdRetailer() {
		return this.idRetailer;
	}

	public void setIdRetailer(Integer idRetailer) {
		this.idRetailer = idRetailer;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "retailer")
	public Set<PackagerInstance> getPackagerInstances() {
		return this.packagerInstances;
	}

	public void setPackagerInstances(Set<PackagerInstance> packagerInstances) {
		this.packagerInstances = packagerInstances;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "retailer_packager_model", catalog = "nn_packager_management_recette", joinColumns = {
			@JoinColumn(name = "id_retailer", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id_packager_model", nullable = false, updatable = false) })
	public Set<PackagerModel> getPackagerModels() {
		return this.packagerModels;
	}

	public void setPackagerModels(Set<PackagerModel> packagerModels) {
		this.packagerModels = packagerModels;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "retailer")
	public Set<WebServiceUser> getWebServiceUsers() {
		return this.webServiceUsers;
	}

	public void setWebServiceUsers(Set<WebServiceUser> webServiceUsers) {
		this.webServiceUsers = webServiceUsers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "retailer")
	public Set<PackagerActionPackagerHeaderDestination> getPackagerActionPackagerHeaderDestinations() {
		return this.packagerActionPackagerHeaderDestinations;
	}

	public void setPackagerActionPackagerHeaderDestinations(
			Set<PackagerActionPackagerHeaderDestination> packagerActionPackagerHeaderDestinations) {
		this.packagerActionPackagerHeaderDestinations = packagerActionPackagerHeaderDestinations;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "retailer")
	public Set<PackagerActionPackagerHeaderSource> getPackagerActionPackagerHeaderSources() {
		return this.packagerActionPackagerHeaderSources;
	}

	public void setPackagerActionPackagerHeaderSources(
			Set<PackagerActionPackagerHeaderSource> packagerActionPackagerHeaderSources) {
		this.packagerActionPackagerHeaderSources = packagerActionPackagerHeaderSources;
	}

}
