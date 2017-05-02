package tn.wevioo.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.dto.PackagerModelDTO;
import tn.wevioo.entities.PackagerModel;

public interface PackagerModelService {

	public PackagerModel saveOrUpdate(PackagerModel packagerModel);

	public void delete(PackagerModel packagerModel);

	public PackagerModel findById(int id);

	public PackagerModel findByRetailerKey(String retailerKey) throws NotFoundException;

	public List<PackagerModel> findAll();

	public PackagerModelDTO convertToDTO(PackagerModel packagerModel);

	public List<PackagerModel> findAllActive();

}
