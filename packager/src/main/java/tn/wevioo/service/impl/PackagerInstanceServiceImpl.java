package tn.wevioo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nordnet.drivers.contract.exceptions.DriverException;
import tn.wevioo.dao.PackagerInstanceDao;
import tn.wevioo.dto.packager.PackagerInstanceDTO;
import tn.wevioo.dto.packager.PackagerInstanceHeaderDTO;
import tn.wevioo.dto.product.ProductInstanceDTO;
import tn.wevioo.dto.product.ProductInstanceDiagnosticDTO;
import tn.wevioo.dto.product.ProductInstanceHeaderDTO;
import tn.wevioo.dto.product.ProductInstanceReferenceDTO;
import tn.wevioo.entities.PackagerInstance;
import tn.wevioo.entities.ProductInstance;
import tn.wevioo.entities.ProductInstanceDiagnostic;
import tn.wevioo.entities.ProductInstanceReference;
import tn.wevioo.exceptions.RestTemplateException;
import tn.wevioo.service.PackagerInstanceService;
import tn.wevioo.service.PackagerModelService;
import tn.wevioo.service.ProductInstanceDiagnosticService;
import tn.wevioo.service.ProductInstanceReferenceService;
import tn.wevioo.service.ProductInstanceService;
import tn.wevioo.service.ProductModelProductDriverPortService;

@Service("packagerInstanceService")
public class PackagerInstanceServiceImpl implements PackagerInstanceService {

	@Autowired
	public PackagerInstanceDao packagerInstanceDao;

	@Autowired
	public PackagerModelService packagerModelService;

	@Autowired
	public ProductInstanceService productInstanceService;

	@Autowired
	private ProductInstanceReferenceService productInstanceReferenceService;

	@Autowired
	private ProductInstanceDiagnosticService productInstanceDiagnosticService;

	@Autowired
	private ProductModelProductDriverPortService productModelProductDriverPortService;

	@Override
	public PackagerInstance saveOrUpdate(PackagerInstance packagerInstance) {
		return packagerInstanceDao.saveAndFlush(packagerInstance);
	}

	@Override
	public void delete(PackagerInstance packagerInstance) {
		packagerInstanceDao.delete(packagerInstance);
	}

	@Override
	public PackagerInstance findById(int id) {
		return packagerInstanceDao.findOne(id);
	}

	@Override
	public List<PackagerInstance> findAll() {
		return packagerInstanceDao.findAll();
	}

	@Override
	public PackagerInstance findByRetailerPackagerId(String retailerPackagerId) {
		return packagerInstanceDao.findByRetailerPackagerId(retailerPackagerId);
	}

	public PackagerInstanceDTO convertToDTO(PackagerInstance packagerInstance)
			throws DriverException, RestTemplateException {
		if (packagerInstance == null) {
			return null;
		}

		PackagerInstanceDTO result = new PackagerInstanceDTO();

		result.setCurrentState(
				packagerInstance.getCurrentState(productInstanceService, productModelProductDriverPortService));
		result.setPackagerModel(packagerInstance.getPackagerModel().getRetailerKey());
		result.setRetailerPackagerId(packagerInstance.getRetailerPackagerId());
		List<ProductInstanceDTO> productInstanceDTOs = new ArrayList<ProductInstanceDTO>();
		for (ProductInstance productInstance : packagerInstance.getProducts()) {

			productInstanceDTOs.add(productInstanceService.convertToDTO(productInstance));
		}
		result.setProducts(productInstanceDTOs);

		return result;
	}

	@Override
	public Boolean isRetailerPackagerIdFree(String retailerPackagerId) {
		return packagerInstanceDao.findByRetailerPackagerId(retailerPackagerId) == null;
	}

	@Override
	public PackagerInstanceHeaderDTO convertToHeaderDTO(PackagerInstance packagerInstance) {
		PackagerInstanceHeaderDTO packagerInstanceHeaderDTO = new PackagerInstanceHeaderDTO();
		packagerInstanceHeaderDTO.setCreationDate(packagerInstance.getCreationDate());
		packagerInstanceHeaderDTO.setRetailerPackagerId(packagerInstance.getRetailerPackagerId());
		packagerInstanceHeaderDTO.setPackagerModel(packagerInstance.getPackagerModel().getRetailerKey());

		List<ProductInstanceHeaderDTO> products = new ArrayList<ProductInstanceHeaderDTO>();
		Set<ProductInstance> productsInstance = packagerInstance.getProducts();

		for (ProductInstance productInstance : productsInstance) {
			ProductInstanceHeaderDTO productInstanceHeaderDTO = new ProductInstanceHeaderDTO();
			productInstanceHeaderDTO.setProductId(productInstance.getIdProductInstance().longValue());
			productInstanceHeaderDTO.setProductModel(productInstance.getProductModel().getRetailerKey());
			productInstanceHeaderDTO.setProviderProductId(productInstance.getProviderProductId());

			List<ProductInstanceReferenceDTO> productInstanceReferenceDTOs = new ArrayList<ProductInstanceReferenceDTO>();
			for (ProductInstanceReference productInstanceReference : productInstance.getProductInstanceReferences()) {
				productInstanceReferenceDTOs
						.add(productInstanceReferenceService.convertToDTO(productInstanceReference));
			}
			productInstanceHeaderDTO.setReferences(productInstanceReferenceDTOs);

			List<ProductInstanceDiagnosticDTO> productInstanceDiagnosticDTOs = new ArrayList<ProductInstanceDiagnosticDTO>();
			for (ProductInstanceDiagnostic productInstanceDiagnostic : productInstance
					.getProductInstanceDiagnostics()) {
				productInstanceDiagnosticDTOs
						.add(productInstanceDiagnosticService.convertToDTO(productInstanceDiagnostic));
			}
			productInstanceHeaderDTO.setDiagnostics(productInstanceDiagnosticDTOs);
			products.add(productInstanceHeaderDTO);
		}
		packagerInstanceHeaderDTO.setProducts(products);

		return packagerInstanceHeaderDTO;
	}

}
