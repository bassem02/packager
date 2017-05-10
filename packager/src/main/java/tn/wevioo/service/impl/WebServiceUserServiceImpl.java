package tn.wevioo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
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
	public WebServiceUser findById(int id) throws NotFoundException {
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

}
