package tn.wevioo.packager.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.packager.entities.AuthorizedIpAddress;
import tn.wevioo.packager.entities.WebServiceUser;

public interface AuthorizedIpAddressService {

	public AuthorizedIpAddress saveOrUpdate(AuthorizedIpAddress authorizedIpAddress);

	public void delete(AuthorizedIpAddress authorizedIpAddress);

	public AuthorizedIpAddress findById(int id) throws NotFoundException;

	public AuthorizedIpAddress findByWebServiceUser(WebServiceUser webServiceUser);

	public List<AuthorizedIpAddress> findAll() throws NotFoundException;

}
