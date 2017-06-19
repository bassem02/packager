package tn.wevioo.packager.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.packager.entities.AuthorizedIpAddress;
import tn.wevioo.packager.entities.WebServiceUser;

public interface AuthorizedIpAddressService extends CrudService<AuthorizedIpAddress, Integer> {

	public AuthorizedIpAddress findByWebServiceUser(WebServiceUser webServiceUser);

	public List<AuthorizedIpAddress> findAll() throws NotFoundException;

}
