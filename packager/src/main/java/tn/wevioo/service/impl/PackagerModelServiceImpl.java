package tn.wevioo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.utils.ErrorCode;
import tn.wevioo.dao.PackagerModelDao;
import tn.wevioo.dto.packager.PackagerModelDTO;
import tn.wevioo.dto.product.ProductModelDTO;
import tn.wevioo.entities.PackagerModel;
import tn.wevioo.entities.PackagerModelProductModel;
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

		PackagerModelDTO packagerModelDTO = new PackagerModelDTO();
		packagerModelDTO.setKey(packagerModel.getRetailerKey());
		packagerModelDTO.setName(packagerModel.getOldRetailerKey());

		List<ProductModelDTO> productModelDTOs = new ArrayList<ProductModelDTO>();

		for (PackagerModelProductModel packagerModelProductModel : packagerModel.getPackagerModelProductModels()) {
			ProductModelDTO productModelDTO = new ProductModelDTO();
			productModelDTO.setName(packagerModelProductModel.getProductModel().getRetailerKey());
			productModelDTO.setKey(packagerModelProductModel.getProductModel().getOldRetailerKey());
			productModelDTO.setMaximumInstances(packagerModelProductModel.getMaximumInstances());
			productModelDTO.setMinimumInstances(packagerModelProductModel.getMinimumInstances());
			productModelDTOs.add(productModelDTO);
		}

		packagerModelDTO.setProducts(productModelDTOs);

		return packagerModelDTO;
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
