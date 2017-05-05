package tn.wevioo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.wevioo.dao.WebServiceUserDAO;
import tn.wevioo.entities.WebServiceUser;
import tn.wevioo.service.WebServiceUserService;

@Service("webServiceUserService")
public class WebServiceUserServiceImpl implements WebServiceUserService {

	@Autowired
	public WebServiceUserDAO webServiceUserDAO;

	@Override
	public WebServiceUser saveOrUpdate(WebServiceUser webServiceUser) {
		return webServiceUserDAO.saveAndFlush(webServiceUser);
	}

	@Override
	public void delete(WebServiceUser webServiceUser) {
		webServiceUserDAO.delete(webServiceUser);
	}

	@Override
	public WebServiceUser findById(int id) {
		return webServiceUserDAO.findOne(id);
	}

	@Override
	public List<WebServiceUser> findAll() {
		return webServiceUserDAO.findAll();
	}

}
