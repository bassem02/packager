package tn.wevioo.service;

import java.util.List;

import tn.wevioo.dto.ProductInstanceDTO;
import tn.wevioo.entities.ProductInstance;

public interface ProductInstanceService {

	public ProductInstance saveOrUpdate(ProductInstance productInstance);

	public void delete(ProductInstance productInstance);

	public ProductInstance findById(int id);

	public List<ProductInstance> findAll();

	public ProductInstanceDTO convertToDTO(ProductInstance findById);

}
