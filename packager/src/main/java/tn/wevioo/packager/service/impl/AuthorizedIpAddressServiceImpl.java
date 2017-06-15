package tn.wevioo.packager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.packager.dao.AuthorizedIpAddressDAO;
import tn.wevioo.packager.entities.AuthorizedIpAddress;
import tn.wevioo.packager.entities.WebServiceUser;
import tn.wevioo.packager.service.AuthorizedIpAddressService;

@Service("authorizedIpAddressService")
public class AuthorizedIpAddressServiceImpl implements AuthorizedIpAddressService {

	@Autowired
	public AuthorizedIpAddressDAO authorizedIpAddressDao;

	@Override
	public AuthorizedIpAddress saveOrUpdate(AuthorizedIpAddress authorizedIpAddress) {

		return authorizedIpAddressDao.saveAndFlush(authorizedIpAddress);
	}

	@Override
	public void delete(AuthorizedIpAddress authorizedIpAddress) {

		authorizedIpAddressDao.delete(authorizedIpAddress);
	}

	@Override
	public AuthorizedIpAddress findById(int id) throws NotFoundException {

		return authorizedIpAddressDao.findOne(id);
	}

	@Override
	public AuthorizedIpAddress findByWebServiceUser(WebServiceUser webServiceUser) {

		return authorizedIpAddressDao.findByWebServiceUser(webServiceUser);
	}

	@Override
	public List<AuthorizedIpAddress> findAll() throws NotFoundException {

		return authorizedIpAddressDao.findAll();
	}

}
