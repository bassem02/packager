package tn.wevioo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nordnet.architecture.exceptions.explicit.DataSourceException;
import nordnet.architecture.exceptions.explicit.MalformedXMLException;
import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.drivers.contract.exceptions.DriverException;
import tn.wevioo.ManualDriver;
import tn.wevioo.ManualDriverFactory;
import tn.wevioo.dto.FPackagerModelDTO;
import tn.wevioo.dto.FProductModelDTO;
import tn.wevioo.dto.PackagerInstanceDTO;
import tn.wevioo.dto.PackagerInstanceHeaderDTO;
import tn.wevioo.dto.ProductInstanceDiagnosticDTO;
import tn.wevioo.dto.ProductInstanceHeaderDTO;
import tn.wevioo.dto.ProductInstanceReferenceDTO;
import tn.wevioo.dto.ProductPropertiesDTO;
import tn.wevioo.entities.PackagerActionHistory;
import tn.wevioo.entities.PackagerInstance;
import tn.wevioo.entities.PackagerModel;
import tn.wevioo.entities.PackagerModelProductModel;
import tn.wevioo.entities.ProductInstance;
import tn.wevioo.entities.ProductInstanceDiagnostic;
import tn.wevioo.entities.ProductInstanceReference;
import tn.wevioo.exceptions.PackagerException;
import tn.wevioo.facade.impl.AbstractFacade;
import tn.wevioo.facade.packager.FPackagerInstanceHeader;
import tn.wevioo.facade.product.FProductInstanceDiagnostic;
import tn.wevioo.facade.product.FProductInstanceHeader;
import tn.wevioo.facade.product.FProductInstanceReference;
import tn.wevioo.feasibility.FeasibilityResult;
import tn.wevioo.model.packager.action.PackagerInstanceAction;
import tn.wevioo.model.request.PackagerRequest;
import tn.wevioo.service.PackagerActionHistoryService;
import tn.wevioo.service.PackagerInstanceService;
import tn.wevioo.service.PackagerModelService;
import tn.wevioo.service.ProductInstanceService;
import tn.wevioo.service.ProductModelService;
import tn.wevioo.service.WebServiceUserService;

@RestController
@EnableAutoConfiguration(exclude = JpaRepositoriesAutoConfiguration.class)
public class PackagerController extends AbstractFacade {

	@Autowired
	private PackagerInstanceService packagerInstanceService;

	@Autowired
	private PackagerModelService packagerModelService;

	@Autowired
	private PackagerActionHistoryService packagerActionHistoryService;

	@Autowired
	private ProductModelService productModelService;

	@Autowired
	private ProductInstanceService productInstanceService;

	@Autowired
	private ManualDriverFactory manualDriverFactory;

	@Autowired
	private ManualDriver manualDriver;

	@Autowired
	private WebServiceUserService webServiceUserService;

	@RequestMapping(value = "/createPackager", method = RequestMethod.POST)
	public PackagerInstanceDTO createPackager(@RequestBody PackagerRequest request)
			throws DriverException, NotRespectedRulesException, MalformedXMLException, PackagerException,
			NotFoundException, DataSourceException {

		PackagerModel packagerModel = packagerModelService.findByRetailerKey(request.getModel());
		PackagerInstance createdPackagerInstance = null;
		PackagerActionHistory history = new PackagerActionHistory(PackagerInstanceAction.CREATE, webServiceUserService);
		createdPackagerInstance = packagerModel.instantiate(request, history, productModelService, manualDriverFactory,
				manualDriver);

		packagerInstanceService.saveOrUpdate(createdPackagerInstance);
		packagerActionHistoryService.saveOrUpdate(history);

		return packagerInstanceService.convertToDTO(createdPackagerInstance);
	}

	@RequestMapping(value = "/getPackagerInstance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public PackagerInstanceDTO getPackagerInstance(@QueryParam("retailerPackagerId") String retailerPackagerId) {
		return packagerInstanceService
				.convertToDTO(packagerInstanceService.findByRetailerPackagerId(retailerPackagerId));
	}

	@RequestMapping(value = "/suspendPackager", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void suspendPackager(@RequestBody PackagerRequest request) throws NotFoundException,
			NotRespectedRulesException, DriverException, PackagerException, MalformedXMLException, DataSourceException {
		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}
		PackagerInstance packagerInstance = null;
		packagerInstance = packagerInstanceService.findByRetailerPackagerId(request.getRetailerPackagerId());
		packagerInstance.suspend(request, productModelService, manualDriverFactory, packagerActionHistoryService,
				webServiceUserService);

	}

	@RequestMapping(value = "/activatePackager", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void activatePackager(@RequestBody PackagerRequest request)
			throws DriverException, NotRespectedRulesException, MalformedXMLException, PackagerException,
			NotFoundException, DataSourceException {

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		PackagerInstance packagerInstance = null;
		packagerInstance = packagerInstanceService.findByRetailerPackagerId(request.getRetailerPackagerId());

		packagerInstance.activate(request, productModelService, manualDriverFactory, packagerActionHistoryService,
				webServiceUserService);

	}

	@RequestMapping(value = "/reactivatePackager", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void reactivatePackager(@RequestBody PackagerRequest request) throws NotRespectedRulesException,
			NotFoundException, DriverException, PackagerException, MalformedXMLException, DataSourceException {
		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		PackagerInstance packagerInstance = null;
		packagerInstance = packagerInstanceService.findByRetailerPackagerId(request.getRetailerPackagerId());

		packagerInstance.reactivate(request, productModelService, manualDriverFactory, packagerActionHistoryService,
				webServiceUserService);
	}

	@RequestMapping(value = "/cancelPackager", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void cancelPackager(@RequestBody PackagerRequest request) throws NotRespectedRulesException,
			NotFoundException, DriverException, PackagerException, MalformedXMLException, DataSourceException {
		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		PackagerInstance packagerInstance = null;
		packagerInstance = packagerInstanceService.findByRetailerPackagerId(request.getRetailerPackagerId());

		packagerInstance.cancel(request, productModelService, manualDriverFactory, packagerActionHistoryService,
				webServiceUserService);
	}

	@RequestMapping(value = "/resetPackager", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void resetPackager(@RequestBody PackagerRequest request) throws NotRespectedRulesException,
			NotFoundException, DriverException, PackagerException, MalformedXMLException, DataSourceException {
		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		PackagerInstance packagerInstance = null;
		packagerInstance = packagerInstanceService.findByRetailerPackagerId(request.getRetailerPackagerId());

		packagerInstance.reset(request, productModelService, manualDriverFactory, packagerActionHistoryService,
				webServiceUserService);
	}

	@RequestMapping(value = "/isRetailerPackagerIdFree", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean isRetailerPackagerIdFree(@QueryParam("retailerPackagerId") String retailerPackagerId)
			throws PackagerException, DataSourceException {

		return packagerInstanceService.isRetailerPackagerIdFree(retailerPackagerId);
	}

	@RequestMapping(value = "/isPackagerCreationPossible", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public FeasibilityResult isPackagerCreationPossible(@RequestBody PackagerRequest request)
			throws PackagerException, DriverException, DataSourceException, NotRespectedRulesException {

		if (request == null) {
			FeasibilityResult fFeasibilityResult = new FeasibilityResult();
			fFeasibilityResult.setPossible(false);
			fFeasibilityResult.setMotive("The provided request is null");
			return fFeasibilityResult;
		}

		if (request.getModel() == null || request.getModel().trim().length() == 0) {
			FeasibilityResult fFeasibilityResult = new FeasibilityResult();
			fFeasibilityResult.setPossible(false);
			fFeasibilityResult.setMotive("The provided model is null or empty");
			return fFeasibilityResult;
		}

		if (request.getRetailerPackagerId() == null || request.getRetailerPackagerId().trim().length() == 0) {
			FeasibilityResult fFeasibilityResult = new FeasibilityResult();
			fFeasibilityResult.setPossible(false);
			fFeasibilityResult.setMotive("The provided retailer packager identifier is null or empty");
			return fFeasibilityResult;
		}

		PackagerModel packagerModel = null;

		try {
			packagerModel = packagerModelService.findByRetailerKey(request.getModel());
		} catch (NotFoundException e) {
			FeasibilityResult fFeasibilityResult = new FeasibilityResult();
			fFeasibilityResult.setPossible(false);
			fFeasibilityResult.setMotive(e.getMessage());
			return fFeasibilityResult;
		}

		FeasibilityResult feasibilityResult;
		feasibilityResult = packagerModel.isInstantiationPossible(request, packagerInstanceService, productModelService,
				manualDriverFactory);
		return feasibilityResult;

	}

	@RequestMapping(value = "/getPackagerInstanceHeader", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public PackagerInstanceHeaderDTO getPackagerInstanceHeader(
			@QueryParam("retailerPackagerId") String retailerPackagerId)
			throws PackagerException, NotFoundException, DataSourceException {

		PackagerInstance packagerInstance = packagerInstanceService.findByRetailerPackagerId(retailerPackagerId);
		PackagerInstanceHeaderDTO packagerInstanceHeaderDTO = new PackagerInstanceHeaderDTO();
		packagerInstanceHeaderDTO.setCreationDate(packagerInstance.getCreationDate());
		packagerInstanceHeaderDTO.setRetailerPackagerId(packagerInstance.getRetailerPackagerId());
		packagerInstanceHeaderDTO.setPackagerModel(packagerInstance.getPackagerModel().getRetailerKey());

		List<ProductInstanceHeaderDTO> products = new ArrayList<ProductInstanceHeaderDTO>();
		Set<ProductInstance> productsInstance = packagerInstance.getProducts();

		for (ProductInstance productInstance : productsInstance) {
			ProductInstanceHeaderDTO productInstanceHeaderDTO = new ProductInstanceHeaderDTO();
			productInstanceHeaderDTO.setProductId(productInstance.getIdProductInstance());
			productInstanceHeaderDTO.setProductModel(productInstance.getProductModel().getRetailerKey());
			productInstanceHeaderDTO.setProviderProductId(productInstance.getProviderProductId());

			List<ProductInstanceReferenceDTO> productInstanceReferencesDTO = new ArrayList<ProductInstanceReferenceDTO>();
			Set<ProductInstanceReference> productInstanceReferences = productInstance.getProductInstanceReferences();
			for (ProductInstanceReference productInstanceReference : productInstanceReferences) {
				ProductInstanceReferenceDTO productInstanceReferenceDTO = new ProductInstanceReferenceDTO();
				productInstanceReferenceDTO.setCreationDate(productInstanceReference.getCreationDate());
				productInstanceReferenceDTO.setReferenceType(productInstanceReference.getDiscriminatorType());
				productInstanceReferenceDTO.setReferenceValue(productInstanceReference.getDiscriminatorValue());
				productInstanceReferencesDTO.add(productInstanceReferenceDTO);
			}
			productInstanceHeaderDTO.setProductInstanceReferencesDTO(productInstanceReferencesDTO);

			List<ProductInstanceDiagnosticDTO> productInstanceDiagnosticsDTO = new ArrayList<ProductInstanceDiagnosticDTO>();
			Set<ProductInstanceDiagnostic> productInstanceDiagnostics = productInstance.getProductInstanceDiagnostics();
			for (ProductInstanceDiagnostic productInstanceDiagnostic : productInstanceDiagnostics) {
				ProductInstanceDiagnosticDTO productInstanceDiagnosticDTO = new ProductInstanceDiagnosticDTO();
				productInstanceDiagnosticDTO.setCreationDate(productInstanceDiagnostic.getCreationDate());
				productInstanceDiagnosticDTO.setDiagnosticKey(productInstanceDiagnostic.getDiagnosticKey());
				productInstanceDiagnosticDTO.setDiagnosticValue(productInstanceDiagnostic.getDiagnosticValue());
				productInstanceDiagnosticsDTO.add(productInstanceDiagnosticDTO);
			}
			productInstanceHeaderDTO.setProductInstanceDiagnosticsDTO(productInstanceDiagnosticsDTO);
			products.add(productInstanceHeaderDTO);
		}
		packagerInstanceHeaderDTO.setProducts(products);

		return packagerInstanceHeaderDTO;

	}

	@RequestMapping(value = "/getPackagerProperties", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProductPropertiesDTO> getPackagerProperties(@QueryParam("retailerPackagerId") String retailerPackagerId)
			throws NotFoundException, PackagerException, DriverException, DataSourceException {
		List<ProductPropertiesDTO> result = new ArrayList<ProductPropertiesDTO>();

		PackagerInstance packagerInstance = packagerInstanceService.findByRetailerPackagerId(retailerPackagerId);

		for (ProductInstance pi : packagerInstance.getProducts()) {
			ProductPropertiesDTO productPropertiesDTO = new ProductPropertiesDTO();
			productPropertiesDTO.setProperties(pi.getProductProperties());
			result.add(productPropertiesDTO);
		}

		return result;
	}

	@RequestMapping(value = "/getPackagerModels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<FPackagerModelDTO> getPackagerModels() throws PackagerException, DataSourceException {
		List<PackagerModel> packagerModels = new ArrayList<PackagerModel>();

		packagerModels = packagerModelService.findAllActive();

		List<FPackagerModelDTO> result = new ArrayList<FPackagerModelDTO>();

		for (PackagerModel packagerModel : packagerModels) {
			FPackagerModelDTO fPackagerModelDTO = new FPackagerModelDTO();
			fPackagerModelDTO.setKey(packagerModel.getRetailerKey());
			fPackagerModelDTO.setName(packagerModel.getOldRetailerKey());
			Set<PackagerModelProductModel> packagerModelProductModels = packagerModel.getPackagerModelProductModels();
			List<FProductModelDTO> fProductModelDTOs = new ArrayList<FProductModelDTO>();

			for (PackagerModelProductModel packagerModelProductModel : packagerModelProductModels) {
				FProductModelDTO fProductModelDTO = new FProductModelDTO();
				fProductModelDTO.setName(packagerModelProductModel.getProductModel().getRetailerKey());
				fProductModelDTO.setKey(packagerModelProductModel.getProductModel().getOldRetailerKey());
				fProductModelDTO.setMaximumInstances(packagerModelProductModel.getMaximumInstances());
				fProductModelDTO.setMinimumInstances(packagerModelProductModel.getMinimumInstances());
				fProductModelDTOs.add(fProductModelDTO);
			}

			fPackagerModelDTO.setProducts(fProductModelDTOs);

			result.add(fPackagerModelDTO);

		}

		return result;

	}

	@RequestMapping(value = "/getPackagerModel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public FPackagerModelDTO getPackagerModel(@QueryParam("packagerModelKey") String packagerModelKey)
			throws NotFoundException, PackagerException, DataSourceException {
		PackagerModel packagerModel = packagerModelService.findByRetailerKey(packagerModelKey);
		FPackagerModelDTO fPackagerModelDTO = new FPackagerModelDTO();
		fPackagerModelDTO.setKey(packagerModel.getRetailerKey());
		fPackagerModelDTO.setName(packagerModel.getOldRetailerKey());
		Set<PackagerModelProductModel> packagerModelProductModels = packagerModel.getPackagerModelProductModels();
		List<FProductModelDTO> fProductModelDTOs = new ArrayList<FProductModelDTO>();

		for (PackagerModelProductModel packagerModelProductModel : packagerModelProductModels) {
			FProductModelDTO fProductModelDTO = new FProductModelDTO();
			fProductModelDTO.setName(packagerModelProductModel.getProductModel().getRetailerKey());
			fProductModelDTO.setKey(packagerModelProductModel.getProductModel().getOldRetailerKey());
			fProductModelDTO.setMaximumInstances(packagerModelProductModel.getMaximumInstances());
			fProductModelDTO.setMinimumInstances(packagerModelProductModel.getMinimumInstances());
			fProductModelDTOs.add(fProductModelDTO);
		}

		fPackagerModelDTO.setProducts(fProductModelDTOs);

		return fPackagerModelDTO;

	}

	public FPackagerInstanceHeader updateSelfDiagnostics(@QueryParam("retailerPackagerId") String retailerPackagerId)
			throws NotFoundException, PackagerException, DataSourceException, DriverException {
		if (retailerPackagerId == null || retailerPackagerId.trim().length() == 0) {
			throw new NullException(NullCases.NULL_EMPTY, "retailerPackagerId parameter");
		}

		PackagerActionHistory packagerHistory = new PackagerActionHistory(PackagerInstanceAction.UPDATE_SELF_DIAGNOSTIC,
				webServiceUserService);
		PackagerInstance packager = packagerInstanceService.findByRetailerPackagerId(retailerPackagerId);
		packager.updateSelfDiagnostics(packagerHistory);
		packagerActionHistoryService.saveOrUpdate(packagerHistory);
		packagerInstanceService.saveOrUpdate(packager);
		FPackagerInstanceHeader fPackagerInstanceHeader = new FPackagerInstanceHeader();
		fPackagerInstanceHeader.setCreationDate(packager.getCreationDate());
		fPackagerInstanceHeader.setPackagerModel(packager.getPackagerModel().getRetailerKey());
		fPackagerInstanceHeader.setRetailerPackagerId(packager.getRetailerPackagerId());
		List<FProductInstanceHeader> fProductInstanceHeaders = new ArrayList<FProductInstanceHeader>();
		for (ProductInstance productInstance : packager.getProducts()) {
			FProductInstanceHeader fProductInstanceHeader = new FProductInstanceHeader();
			fProductInstanceHeader.setProductId(productInstance.getIdProductInstance().longValue());
			fProductInstanceHeader.setProductModel(productInstance.getProductModel().getRetailerKey());
			fProductInstanceHeader.setProviderProductId(productInstance.getProviderProductId());

			List<FProductInstanceReference> fProductInstanceReferences = new ArrayList<FProductInstanceReference>();
			for (ProductInstanceReference productInstanceReference : productInstance.getProductInstanceReferences()) {
				FProductInstanceReference fProductInstanceReference = new FProductInstanceReference();
				fProductInstanceReference.setCreationDate(productInstanceReference.getCreationDate());
				fProductInstanceReference.setReferenceType(productInstanceReference.getDiscriminatorType());
				fProductInstanceReference.setReferenceValue(productInstanceReference.getDiscriminatorValue());
				fProductInstanceReferences.add(fProductInstanceReference);
			}
			fProductInstanceHeader.setReferences(fProductInstanceReferences);
			List<FProductInstanceDiagnostic> fProductInstanceDiagnostics = new ArrayList<FProductInstanceDiagnostic>();
			for (ProductInstanceDiagnostic productInstanceDiagnostic : productInstance
					.getProductInstanceDiagnostics()) {
				FProductInstanceDiagnostic fProductInstanceDiagnostic = new FProductInstanceDiagnostic();
				fProductInstanceDiagnostic.setCreationDate(productInstanceDiagnostic.getCreationDate());
				fProductInstanceDiagnostic.setDiagnosticKey(productInstanceDiagnostic.getDiagnosticKey());
				fProductInstanceDiagnostic.setDiagnosticValue(productInstanceDiagnostic.getDiagnosticValue());
				fProductInstanceDiagnostics.add(fProductInstanceDiagnostic);
			}
			fProductInstanceHeader.setDiagnostics(fProductInstanceDiagnostics);
			fProductInstanceHeaders.add(fProductInstanceHeader);
		}
		fPackagerInstanceHeader.setProducts(fProductInstanceHeaders);

		return fPackagerInstanceHeader;
	}
}