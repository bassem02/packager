package tn.wevioo.service;

import java.util.List;

import tn.wevioo.entities.WebServiceUser;

public interface WebServiceUserService {

	public WebServiceUser saveOrUpdate(WebServiceUser webServiceUser);

	public void delete(WebServiceUser webServiceUser);

	public WebServiceUser findById(int id);

	public List<WebServiceUser> findAll();

}
