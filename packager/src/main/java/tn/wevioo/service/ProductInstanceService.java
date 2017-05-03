package tn.wevioo.service;

import java.util.List;

import nordnet.drivers.contract.exceptions.DriverException;
import tn.wevioo.entities.ProductInstance;
import tn.wevioo.facade.product.FProductInstance;
import tn.wevioo.facade.product.FProductProperties;

public interface ProductInstanceService {

	public ProductInstance saveOrUpdate(ProductInstance productInstance);

	public void delete(ProductInstance productInstance);

	public ProductInstance findById(int id);

	public List<ProductInstance> findAll();

	public FProductInstance convertToDTO(ProductInstance findById) throws DriverException;

	public FProductProperties convertToPropertiesDTO(ProductInstance productInstance) throws DriverException;

}
