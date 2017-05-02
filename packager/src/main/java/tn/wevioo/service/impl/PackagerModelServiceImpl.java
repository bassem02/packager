package tn.wevioo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.utils.ErrorCode;
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
	public PackagerModel findByRetailerKey(String retailerKey) throws NotFoundException {
		PackagerModel packagerModel = packagerModelDao.findByRetailerKey(retailerKey);
		if (packagerModel == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"),
					new Object[] { "packager model", "model key", retailerKey });
		}
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

	@Override
	public List<PackagerModel> findAllActive() {
		List<PackagerModel> packagerModels = packagerModelDao.findAll();
		List<PackagerModel> result = new ArrayList<PackagerModel>();
		for (PackagerModel packagerModel : packagerModels) {
			if ((packagerModel.getRetailerKey() != null) && !(packagerModel.getRetailerKey().equals("")))
				result.add(packagerModel);
		}
		return result;
	}

}
