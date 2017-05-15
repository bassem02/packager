package tn.wevioo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.entities.AuthorizedIpAddress;
import tn.wevioo.entities.WebServiceUser;

public interface AuthorizedIpAddressDAO extends JpaRepository<AuthorizedIpAddress, Integer> {
	public AuthorizedIpAddress findByWebServiceUser(WebServiceUser webServiceUser);
}
