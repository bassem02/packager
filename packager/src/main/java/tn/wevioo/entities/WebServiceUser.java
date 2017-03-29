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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * WebServiceUser generated by hbm2java
 */
@Entity
@Table(name = "web_service_user", catalog = "nn_packager_management_recette_new", uniqueConstraints = @UniqueConstraint(columnNames = "login"))
public class WebServiceUser implements java.io.Serializable {

	private Integer idWebServiceUser;
	private Retailer retailer;
	private String name;
	private String login;
	private String password;
	private String role;
	private Set<ProductActionHistory> productActionHistories = new HashSet<ProductActionHistory>(0);
	private Set<AuthorizedIpAddress> authorizedIpAddresses = new HashSet<AuthorizedIpAddress>(0);
	private Set<PackagerActionHistory> packagerActionHistories = new HashSet<PackagerActionHistory>(0);

	public WebServiceUser() {
	}

	public WebServiceUser(Retailer retailer, String name, String login, String password, String role) {
		this.retailer = retailer;
		this.name = name;
		this.login = login;
		this.password = password;
		this.role = role;
	}

	public WebServiceUser(Retailer retailer, String name, String login, String password, String role,
			Set<ProductActionHistory> productActionHistories, Set<AuthorizedIpAddress> authorizedIpAddresses,
			Set<PackagerActionHistory> packagerActionHistories) {
		this.retailer = retailer;
		this.name = name;
		this.login = login;
		this.password = password;
		this.role = role;
		this.productActionHistories = productActionHistories;
		this.authorizedIpAddresses = authorizedIpAddresses;
		this.packagerActionHistories = packagerActionHistories;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_web_service_user", unique = true, nullable = false)
	public Integer getIdWebServiceUser() {
		return this.idWebServiceUser;
	}

	public void setIdWebServiceUser(Integer idWebServiceUser) {
		this.idWebServiceUser = idWebServiceUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_retailer", nullable = false)
	public Retailer getRetailer() {
		return this.retailer;
	}

	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "login", unique = true, nullable = false)
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "role", nullable = false)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "webServiceUser")
	public Set<ProductActionHistory> getProductActionHistories() {
		return this.productActionHistories;
	}

	public void setProductActionHistories(Set<ProductActionHistory> productActionHistories) {
		this.productActionHistories = productActionHistories;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "webServiceUser")
	public Set<AuthorizedIpAddress> getAuthorizedIpAddresses() {
		return this.authorizedIpAddresses;
	}

	public void setAuthorizedIpAddresses(Set<AuthorizedIpAddress> authorizedIpAddresses) {
		this.authorizedIpAddresses = authorizedIpAddresses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "webServiceUser")
	public Set<PackagerActionHistory> getPackagerActionHistories() {
		return this.packagerActionHistories;
	}

	public void setPackagerActionHistories(Set<PackagerActionHistory> packagerActionHistories) {
		this.packagerActionHistories = packagerActionHistories;
	}

}
