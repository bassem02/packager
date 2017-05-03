package tn.wevioo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nordnet.drivers.contract.exceptions.DriverException;
import tn.wevioo.dao.PackagerInstanceDao;
import tn.wevioo.entities.PackagerInstance;
import tn.wevioo.entities.ProductInstance;
import tn.wevioo.entities.ProductInstanceDiagnostic;
import tn.wevioo.entities.ProductInstanceReference;
import tn.wevioo.facade.packager.FPackagerInstance;
import tn.wevioo.facade.packager.FPackagerInstanceHeader;
import tn.wevioo.facade.product.FProductInstance;
import tn.wevioo.facade.product.FProductInstanceDiagnostic;
import tn.wevioo.facade.product.FProductInstanceHeader;
import tn.wevioo.facade.product.FProductInstanceReference;
import tn.wevioo.service.PackagerInstanceService;
import tn.wevioo.service.PackagerModelService;
import tn.wevioo.service.ProductInstanceDiagnosticService;
import tn.wevioo.service.ProductInstanceReferenceService;
import tn.wevioo.service.ProductInstanceService;

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

	public FPackagerInstance convertToDTO(PackagerInstance packagerInstance) throws DriverException {
		if (packagerInstance == null) {
			return null;
		}

		FPackagerInstance result = new FPackagerInstance();

		result.setCurrentState(packagerInstance.getCurrentState());
		result.setPackagerModel(packagerInstance.getPackagerModel().getRetailerKey());
		result.setRetailerPackagerId(packagerInstance.getRetailerPackagerId());
		List<FProductInstance> fProductInstances = new ArrayList<FProductInstance>();
		for (ProductInstance productInstance : packagerInstance.getProducts()) {

			fProductInstances.add(productInstanceService.convertToDTO(productInstance));
		}
		result.setProducts(fProductInstances);

		return result;
	}

	@Override
	public Boolean isRetailerPackagerIdFree(String retailerPackagerId) {
		return packagerInstanceDao.findByRetailerPackagerId(retailerPackagerId) == null;
	}

	@Override
	public FPackagerInstanceHeader convertToHeaderDTO(PackagerInstance packagerInstance) {
		FPackagerInstanceHeader fPackagerInstanceHeader = new FPackagerInstanceHeader();
		fPackagerInstanceHeader.setCreationDate(packagerInstance.getCreationDate());
		fPackagerInstanceHeader.setRetailerPackagerId(packagerInstance.getRetailerPackagerId());
		fPackagerInstanceHeader.setPackagerModel(packagerInstance.getPackagerModel().getRetailerKey());

		List<FProductInstanceHeader> products = new ArrayList<FProductInstanceHeader>();
		Set<ProductInstance> productsInstance = packagerInstance.getProducts();

		for (ProductInstance productInstance : productsInstance) {
			FProductInstanceHeader fProductInstanceHeader = new FProductInstanceHeader();
			fProductInstanceHeader.setProductId(productInstance.getIdProductInstance().longValue());
			fProductInstanceHeader.setProductModel(productInstance.getProductModel().getRetailerKey());
			fProductInstanceHeader.setProviderProductId(productInstance.getProviderProductId());

			List<FProductInstanceReference> fProductInstanceReferences = new ArrayList<FProductInstanceReference>();
			for (ProductInstanceReference productInstanceReference : productInstance.getProductInstanceReferences()) {
				fProductInstanceReferences.add(productInstanceReferenceService.convertToDTO(productInstanceReference));
			}
			fProductInstanceHeader.setReferences(fProductInstanceReferences);

			List<FProductInstanceDiagnostic> fProductInstanceDiagnostics = new ArrayList<FProductInstanceDiagnostic>();
			for (ProductInstanceDiagnostic productInstanceDiagnostic : productInstance
					.getProductInstanceDiagnostics()) {
				fProductInstanceDiagnostics
						.add(productInstanceDiagnosticService.convertToDTO(productInstanceDiagnostic));
			}
			fProductInstanceHeader.setDiagnostics(fProductInstanceDiagnostics);
			products.add(fProductInstanceHeader);
		}
		fPackagerInstanceHeader.setProducts(products);

		return fPackagerInstanceHeader;
	}

}
