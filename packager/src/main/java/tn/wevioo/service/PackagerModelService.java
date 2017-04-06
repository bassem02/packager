package tn.wevioo.service;

import java.util.List;

import tn.wevioo.dto.PackagerModelDTO;
import tn.wevioo.entities.PackagerModel;

public interface PackagerModelService {

	public PackagerModel saveOrUpdate(PackagerModel packagerModel);

	public void delete(PackagerModel packagerModel);

	public PackagerModel findById(int id);

	public PackagerModel findByRetailerKey(String retailerKey);

	public List<PackagerModel> findAll();

	public PackagerModelDTO convertToDTO(PackagerModel packagerModel);

}
