package tn.wevioo.service;

import java.util.List;

import nordnet.drivers.contract.exceptions.DriverException;
import tn.wevioo.dto.product.ProductInstanceDTO;
import tn.wevioo.dto.product.ProductPropertiesDTO;
import tn.wevioo.entities.ProductInstance;

public interface ProductInstanceService {

	public ProductInstance saveOrUpdate(ProductInstance productInstance);

	public void delete(ProductInstance productInstance);

	public ProductInstance findById(int id);

	public List<ProductInstance> findAll();

	public ProductInstanceDTO convertToDTO(ProductInstance findById) throws DriverException;

	public ProductPropertiesDTO convertToPropertiesDTO(ProductInstance productInstance) throws DriverException;

}
