package tn.wevioo.packager.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.packager.entities.AuthorizedIpAddress;
import tn.wevioo.packager.entities.WebServiceUser;

public interface AuthorizedIpAddressDAO extends JpaRepository<AuthorizedIpAddress, Integer> {
	public AuthorizedIpAddress findByWebServiceUser(WebServiceUser webServiceUser);
}
