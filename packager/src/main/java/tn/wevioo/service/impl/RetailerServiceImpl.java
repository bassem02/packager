package tn.wevioo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.wevioo.dao.RetailerDAO;
import tn.wevioo.dto.RetailerDTO;
import tn.wevioo.entities.Retailer;
import tn.wevioo.service.RetailerService;

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
	public Retailer findById(int id) {
		return retailerDAO.findOne(id);

	}

	@Override
	public List<Retailer> findAll() {
		return retailerDAO.findAll();
	}

	@Override
	public RetailerDTO convertToDTO(Retailer retailer) {
		if (retailer == null) {
			return null;
		}

		RetailerDTO result = new RetailerDTO();
		result.setIdRetailer(retailer.getIdRetailer());
		result.setName(result.getName());

		return result;
	}

}
