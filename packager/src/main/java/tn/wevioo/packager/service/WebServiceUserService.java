package tn.wevioo.packager.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.packager.entities.WebServiceUser;

public interface WebServiceUserService {

	public WebServiceUser saveOrUpdate(WebServiceUser webServiceUser);

	public void delete(WebServiceUser webServiceUser);

	public WebServiceUser findById(int id) throws NotFoundException;

	public List<WebServiceUser> findAll();

	public WebServiceUser getWebserviceUser();

}
