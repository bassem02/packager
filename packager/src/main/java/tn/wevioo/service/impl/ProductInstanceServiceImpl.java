package tn.wevioo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nordnet.drivers.contract.exceptions.DriverException;
import tn.wevioo.dao.ProductInstanceDAO;
import tn.wevioo.dto.product.ProductInstanceDTO;
import tn.wevioo.dto.product.ProductInstanceDiagnosticDTO;
import tn.wevioo.dto.product.ProductInstanceReferenceDTO;
import tn.wevioo.dto.product.ProductPropertiesDTO;
import tn.wevioo.entities.ProductInstance;
import tn.wevioo.entities.ProductInstanceDiagnostic;
import tn.wevioo.entities.ProductInstanceReference;
import tn.wevioo.exceptions.RestTemplateException;
import tn.wevioo.service.ProductInstanceDiagnosticService;
import tn.wevioo.service.ProductInstanceReferenceService;
import tn.wevioo.service.ProductInstanceService;
import tn.wevioo.service.ProductModelProductDriverPortService;
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

	@Autowired
	ProductModelProductDriverPortService productModelProductDriverPortService;

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
	public ProductInstanceDTO convertToDTO(ProductInstance productInstance)
			throws DriverException, RestTemplateException {

		ProductInstanceDTO productInstanceDTO = new ProductInstanceDTO();
		productInstanceDTO.setProductId(productInstance.getIdProductInstance().longValue());
		productInstanceDTO.setProductModel(productInstance.getProductModel().getRetailerKey());
		productInstanceDTO.setProviderProductId(productInstance.getProviderProductId());
		productInstanceDTO.setCurrentState(productInstance.getCurrentState(productModelProductDriverPortService));

		List<ProductInstanceReferenceDTO> productInstanceReferenceDTOs = new ArrayList<ProductInstanceReferenceDTO>();
		for (ProductInstanceReference productInstanceReference : productInstance.getProductInstanceReferences()) {
			productInstanceReferenceDTOs.add(productInstanceReferenceService.convertToDTO(productInstanceReference));
		}
		productInstanceDTO.setReferences(productInstanceReferenceDTOs);

		List<ProductInstanceDiagnosticDTO> productInstanceDiagnosticDTOs = new ArrayList<ProductInstanceDiagnosticDTO>();
		for (ProductInstanceDiagnostic productInstanceDiagnostic : productInstance.getProductInstanceDiagnostics()) {
			productInstanceDiagnosticDTOs.add(productInstanceDiagnosticService.convertToDTO(productInstanceDiagnostic));
		}
		productInstanceDTO.setDiagnostics(productInstanceDiagnosticDTOs);

		return productInstanceDTO;
	}

	@Override
	public ProductPropertiesDTO convertToPropertiesDTO(ProductInstance productInstance)
			throws DriverException, RestTemplateException {

		ProductInstanceDTO productInstanceDTO = convertToDTO(productInstance);

		ProductPropertiesDTO productPropertiesDTO = new ProductPropertiesDTO();
		BeanUtils.copyProperties(productInstanceDTO, productPropertiesDTO);
		productPropertiesDTO.setProperties(productInstance.getProductProperties(productModelProductDriverPortService));

		return productPropertiesDTO;
	}

}
