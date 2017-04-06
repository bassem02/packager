package tn.wevioo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.wevioo.dao.PackagerModelDao;
import tn.wevioo.dto.PackagerModelDTO;
import tn.wevioo.entities.PackagerModel;
import tn.wevioo.service.PackagerModelService;

@Service("packagerModelService")
public class PackagerModelServiceImpl implements PackagerModelService {
	@Autowired
	public PackagerModelDao packagerModelDao;

	@Override
	public PackagerModel saveOrUpdate(PackagerModel packagerModel) {
		return packagerModelDao.save(packagerModel);
	}

	@Override
	public void delete(PackagerModel packagerModel) {
		packagerModelDao.delete(packagerModel);
	}

	@Override
	public PackagerModel findById(int id) {
		return packagerModelDao.findOne(id);
	}

	@Override
	public List<PackagerModel> findAll() {
		return packagerModelDao.findAll();
	}

	@Override
	public PackagerModel findByRetailerKey(String retailerKey) {
		return packagerModelDao.findByRetailerKey(retailerKey);
	}

	public PackagerModelDTO convertToDTO(PackagerModel packagerModel) {
		if (packagerModel == null) {
			return null;
		}

		PackagerModelDTO result = new PackagerModelDTO();
		result.setCreationDate(packagerModel.getCreationDate());
		result.setLastUpdate(packagerModel.getLastUpdate());
		result.setIdPackagerModel(packagerModel.getIdPackagerModel());
		result.setMultithreadedActions(packagerModel.isMultithreadedActions());
		result.setOldRetailerKey(packagerModel.getOldRetailerKey());
		result.setRetailerKey(packagerModel.getRetailerKey());

		return result;
	}

}
