package tn.wevioo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nordnet.drivers.contract.exceptions.DriverException;
import tn.wevioo.dao.ProductInstanceDAO;
import tn.wevioo.entities.ProductInstance;
import tn.wevioo.entities.ProductInstanceDiagnostic;
import tn.wevioo.entities.ProductInstanceReference;
import tn.wevioo.facade.product.FProductInstance;
import tn.wevioo.facade.product.FProductInstanceDiagnostic;
import tn.wevioo.facade.product.FProductInstanceReference;
import tn.wevioo.facade.product.FProductProperties;
import tn.wevioo.service.ProductInstanceDiagnosticService;
import tn.wevioo.service.ProductInstanceReferenceService;
import tn.wevioo.service.ProductInstanceService;
import tn.wevioo.service.ProductModelService;

@Service("productInstanceService")
public class ProductInstanceServiceImpl implements ProductInstanceService {

	@Autowired
	public ProductInstanceDAO productInstanceDAO;

	@Autowired
	public ProductModelService productModelService;

	@Autowired
	public ProductInstanceReferenceService productInstanceReferenceService;

	@Autowired
	public ProductInstanceDiagnosticService productInstanceDiagnosticService;

	@Override
	public ProductInstance saveOrUpdate(ProductInstance productInstance) {
		return productInstanceDAO.saveAndFlush(productInstance);
	}

	@Override
	public void delete(ProductInstance productInstance) {

		productInstanceDAO.delete(productInstance);
	}

	@Override
	public ProductInstance findById(int id) {
		return productInstanceDAO.findOne(id);
	}

	@Override
	public List<ProductInstance> findAll() {
		return productInstanceDAO.findAll();
	}

	@Override
	public FProductInstance convertToDTO(ProductInstance productInstance) throws DriverException {

		FProductInstance fProductInstance = new FProductInstance();
		fProductInstance.setProductId(productInstance.getIdProductInstance().longValue());
		fProductInstance.setProductModel(productInstance.getProductModel().getRetailerKey());
		fProductInstance.setProviderProductId(productInstance.getProviderProductId());
		fProductInstance.setCurrentState(productInstance.getCurrentState());

		List<FProductInstanceReference> fProductInstanceReferences = new ArrayList<FProductInstanceReference>();
		for (ProductInstanceReference productInstanceReference : productInstance.getProductInstanceReferences()) {
			fProductInstanceReferences.add(productInstanceReferenceService.convertToDTO(productInstanceReference));
		}
		fProductInstance.setReferences(fProductInstanceReferences);

		List<FProductInstanceDiagnostic> fProductInstanceDiagnostics = new ArrayList<FProductInstanceDiagnostic>();
		for (ProductInstanceDiagnostic productInstanceDiagnostic : productInstance.getProductInstanceDiagnostics()) {
			fProductInstanceDiagnostics.add(productInstanceDiagnosticService.convertToDTO(productInstanceDiagnostic));
		}
		fProductInstance.setDiagnostics(fProductInstanceDiagnostics);

		return fProductInstance;
	}

	@Override
	public FProductProperties convertToPropertiesDTO(ProductInstance productInstance) throws DriverException {

		FProductInstance fProductInstance = convertToDTO(productInstance);

		FProductProperties fProductProperties = new FProductProperties();
		BeanUtils.copyProperties(fProductInstance, fProductProperties);
		fProductProperties.setProperties(productInstance.getProductProperties());

		return fProductProperties;
	}

}
