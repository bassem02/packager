package tn.wevioo.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.entities.WebServiceUser;

public interface WebServiceUserService {

	public WebServiceUser saveOrUpdate(WebServiceUser webServiceUser);

	public void delete(WebServiceUser webServiceUser);

	public WebServiceUser findById(int id) throws NotFoundException;

	public List<WebServiceUser> findAll();

}
