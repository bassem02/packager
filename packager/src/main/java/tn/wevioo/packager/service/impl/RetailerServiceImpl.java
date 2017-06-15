package tn.wevioo.packager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import tn.wevioo.packager.dao.RetailerDAO;
import tn.wevioo.packager.entities.Retailer;
import tn.wevioo.packager.service.RetailerService;

@Service("retailerService")
public class RetailerServiceImpl implements RetailerService {

	@Autowired
	public RetailerDAO retailerDAO;

	@Override
	public Retailer saveOrUpdate(Retailer retailer) {
		return this.saveOrUpdate(retailer);
	}

	@Override
	public void delete(Retailer retailer) {
		retailerDAO.delete(retailer);
	}

	@Override
	public Retailer findById(int id) throws NotFoundException {
		if (((Integer) id == null)) {
			throw new NullException(NullCases.NULL_EMPTY, "id parameter");
		}
		Retailer result = retailerDAO.findOne(id);

		if (result == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"), new Object[] { "Retailer", " id", id });
		}
		return result;

	}

	@Override
	public List<Retailer> findAll() {
		return retailerDAO.findAll();
	}

}
