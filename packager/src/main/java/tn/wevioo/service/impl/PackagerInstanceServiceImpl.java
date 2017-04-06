package tn.wevioo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.wevioo.dao.PackagerInstanceDao;
import tn.wevioo.dto.PackagerInstanceDTO;
import tn.wevioo.dto.RetailerDTO;
import tn.wevioo.entities.PackagerInstance;
import tn.wevioo.entities.Retailer;
import tn.wevioo.service.PackagerInstanceService;
import tn.wevioo.service.PackagerModelService;

@Service("packagerInstanceService")
public class PackagerInstanceServiceImpl implements PackagerInstanceService {

	@Autowired
	public PackagerInstanceDao packagerInstanceDao;

	@Autowired
	public PackagerModelService packagerModelService;

	@Override
	public PackagerInstance saveOrUpdate(PackagerInstance packagerInstance) {
		return packagerInstanceDao.saveAndFlush(packagerInstance);
	}

	@Override
	public void delete(PackagerInstance packagerInstance) {
		packagerInstanceDao.delete(packagerInstance);
	}

	@Override
	public PackagerInstance findById(int id) {
		return packagerInstanceDao.findOne(id);
	}

	@Override
	public List<PackagerInstance> findAll() {
		return packagerInstanceDao.findAll();
	}

	@Override
	public PackagerInstance findByRetailerPackagerId(String retailerPackagerId) {
		return packagerInstanceDao.findByRetailerPackagerId(retailerPackagerId);
	}

	public PackagerInstanceDTO convertToDTO(PackagerInstance packagerInstance) {
		if (packagerInstance == null) {
			return null;
		}

		PackagerInstanceDTO result = new PackagerInstanceDTO();
		result.setCreationDate(packagerInstance.getCreationDate());
		result.setIdPackagerInstance(packagerInstance.getIdPackagerInstance());
		result.setRetailerPackagerId(packagerInstance.getRetailerPackagerId());
		result.setLastUpdate(packagerInstance.getLastUpdate());
		result.setToto(packagerInstance.getToto());

		result.setPackagerModel(packagerModelService.convertToDTO(packagerInstance.getPackagerModel()));

		Retailer retailer = packagerInstance.getRetailer();
		if (retailer != null) {
			RetailerDTO retailerDTO = new RetailerDTO();
			retailerDTO.setIdRetailer(retailer.getIdRetailer());
			retailerDTO.setName(retailer.getName());
			result.setRetailer(retailerDTO);
		}
		return result;
	}

}
