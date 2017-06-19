package tn.wevioo.packager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import tn.wevioo.packager.dao.WebServiceUserDAO;
import tn.wevioo.packager.entities.WebServiceUser;
import tn.wevioo.packager.service.WebServiceUserService;

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
	public WebServiceUser findById(Integer id) throws NotFoundException {
		if (((Integer) id == null)) {
			throw new NullException(NullCases.NULL_EMPTY, "id parameter");
		}
		WebServiceUser result = webServiceUserDAO.findOne(id);

		if (result == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"), new Object[] { "Web service user", " id", id });
		}
		return result;
	}

	@Override
	public List<WebServiceUser> findAll() {
		return webServiceUserDAO.findAll();
	}

	public WebServiceUser getWebserviceUser() {

		String currentPrincipalName;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		try {
			currentPrincipalName = authentication.getName();
		} catch (Exception e) {
			currentPrincipalName = "admin";
		}

		WebServiceUser result = webServiceUserDAO.findByLogin(currentPrincipalName);

		return result;

	}

}
