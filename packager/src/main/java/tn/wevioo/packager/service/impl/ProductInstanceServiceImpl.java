package tn.wevioo.packager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import nordnet.drivers.contract.exceptions.DriverException;
import tn.wevioo.packager.dao.ProductInstanceDAO;
import tn.wevioo.packager.dto.product.ProductInstanceDTO;
import tn.wevioo.packager.dto.product.ProductInstanceDiagnosticDTO;
import tn.wevioo.packager.dto.product.ProductInstanceReferenceDTO;
import tn.wevioo.packager.dto.product.ProductPropertiesDTO;
import tn.wevioo.packager.entities.ProductInstance;
import tn.wevioo.packager.entities.ProductInstanceDiagnostic;
import tn.wevioo.packager.entities.ProductInstanceReference;
import tn.wevioo.packager.entities.ProductModel;
import tn.wevioo.packager.exceptions.RestTemplateException;
import tn.wevioo.packager.service.ProductInstanceDiagnosticService;
import tn.wevioo.packager.service.ProductInstanceReferenceService;
import tn.wevioo.packager.service.ProductInstanceService;
import tn.wevioo.packager.service.ProductModelProductDriverPortService;
import tn.wevioo.packager.service.ProductModelService;

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
	public ProductInstance findById(Integer id) throws NotFoundException {
		if (((Integer) id == null)) {
			throw new NullException(NullCases.NULL_EMPTY, "id parameter");
		}
		ProductInstance result = productInstanceDAO.findOne(id);

		if (result == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"), new Object[] { "Product instance", " id", id });
		}
		return result;
	}

	@Override
	public List<ProductInstance> findAll() {
		return productInstanceDAO.findAll();
	}

	@Override
	public ProductInstanceDTO convertToDTO(ProductInstance productInstance)
			throws DriverException, RestTemplateException, NotFoundException {

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
			throws DriverException, RestTemplateException, NotFoundException {

		ProductInstanceDTO productInstanceDTO = convertToDTO(productInstance);

		ProductPropertiesDTO productPropertiesDTO = new ProductPropertiesDTO();
		BeanUtils.copyProperties(productInstanceDTO, productPropertiesDTO);
		productPropertiesDTO.setProperties(productInstance.getProductProperties(productModelProductDriverPortService));

		return productPropertiesDTO;
	}

	@Override
	public List<ProductInstance> findByProviderProductIds(List<String> providerProductIds, ProductModel productModel) {

		if (productModel == null) {
			throw new NullException(NullCases.NULL, "productModel attribute");
		}

		if ((providerProductIds == null) || (providerProductIds.size() == 0)) {
			return new ArrayList<ProductInstance>();
		}
		List<ProductInstance> productInstances = new ArrayList<ProductInstance>();
		for (String ppid : providerProductIds) {
			if (productInstanceDAO.findByProviderProductIdAndProductModel(ppid, productModel) != null)
				productInstances.add(productInstanceDAO.findByProviderProductIdAndProductModel(ppid, productModel));
		}

		return productInstances;
	}

	@Override
	public ProductInstance findByProviderProductId(String providerProductId, ProductModel productModel)
			throws NotFoundException {

		if ((providerProductId == null) || (providerProductId.trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "providerProductId attribute");
		}

		if (productModel == null) {
			throw new NullException(NullCases.NULL, "productModel attribute");
		}

		ProductInstance pi = productInstanceDAO.findByProviderProductIdAndProductModel(providerProductId, productModel);

		if (pi == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"),
					new Object[] { "product instance", "provider product identifier", providerProductId });
		}

		return pi;

	}

	@Override
	public long getMaxIdProductInstance() {
		return productInstanceDAO.getMaxIdProductInstance();
	}

	@Override
	public ProductPropertiesDTO convertToProductPropertiesDTO(ProductInstance productInstance)
			throws DriverException, NotFoundException, RestTemplateException {
		ProductPropertiesDTO productPropertiesDTO = new ProductPropertiesDTO();
		productPropertiesDTO.setProductId(productInstance.getIdProductInstance().longValue());
		productPropertiesDTO.setProductModel(productInstance.getProductModel().getRetailerKey());
		productPropertiesDTO.setProviderProductId(productInstance.getProviderProductId());
		productPropertiesDTO.setCurrentState(productInstance.getCurrentState(productModelProductDriverPortService));

		List<ProductInstanceReferenceDTO> productInstanceReferenceDTOs = new ArrayList<ProductInstanceReferenceDTO>();
		for (ProductInstanceReference productInstanceReference : productInstance.getProductInstanceReferences()) {
			productInstanceReferenceDTOs.add(productInstanceReferenceService.convertToDTO(productInstanceReference));
		}
		productPropertiesDTO.setReferences(productInstanceReferenceDTOs);

		List<ProductInstanceDiagnosticDTO> productInstanceDiagnosticDTOs = new ArrayList<ProductInstanceDiagnosticDTO>();
		for (ProductInstanceDiagnostic productInstanceDiagnostic : productInstance.getProductInstanceDiagnostics()) {
			productInstanceDiagnosticDTOs.add(productInstanceDiagnosticService.convertToDTO(productInstanceDiagnostic));
		}
		productPropertiesDTO.setDiagnostics(productInstanceDiagnosticDTOs);
		productPropertiesDTO.setProperties(productInstance.getProductProperties(productModelProductDriverPortService));

		return productPropertiesDTO;

	}

}
