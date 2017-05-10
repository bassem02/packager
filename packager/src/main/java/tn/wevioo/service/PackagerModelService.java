package tn.wevioo.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.dto.packager.PackagerModelDTO;
import tn.wevioo.entities.PackagerModel;

public interface PackagerModelService {

	public PackagerModel saveOrUpdate(PackagerModel packagerModel);

	public void delete(PackagerModel packagerModel);

	public PackagerModel findById(int id) throws NotFoundException;

	public PackagerModel findByRetailerKey(String retailerKey) throws NotFoundException;

	public List<PackagerModel> findAll() throws NotFoundException;

	public PackagerModelDTO convertToDTO(PackagerModel packagerModel);

	public List<PackagerModel> findAllActive();

}
