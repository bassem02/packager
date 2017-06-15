package tn.wevioo.packager.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.QueryParam;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import nordnet.architecture.exceptions.explicit.DataSourceException;
import nordnet.architecture.exceptions.explicit.MalformedXMLException;
import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.drivers.contract.exceptions.DriverException;
import tn.wevioo.packager.dto.packager.PackagerInstanceDTO;
import tn.wevioo.packager.dto.packager.PackagerInstanceHeaderDTO;
import tn.wevioo.packager.dto.packager.PackagerModelDTO;
import tn.wevioo.packager.dto.product.ProductPropertiesDTO;
import tn.wevioo.packager.dto.response.SplitResponseDTO;
import tn.wevioo.packager.dto.response.TranslocateProductResponseDTO;
import tn.wevioo.packager.entities.PackagerActionHistory;
import tn.wevioo.packager.entities.PackagerInstance;
import tn.wevioo.packager.entities.PackagerModel;
import tn.wevioo.packager.entities.ProductInstance;
import tn.wevioo.packager.exceptions.PackagerException;
import tn.wevioo.packager.exceptions.RestTemplateException;
import tn.wevioo.packager.model.AbstractFacade;
import tn.wevioo.packager.model.action.PackagerInstanceAction;
import tn.wevioo.packager.model.feasibility.FeasibilityResult;
import tn.wevioo.packager.model.request.MergePackagersRequest;
import tn.wevioo.packager.model.request.PackagerRequest;
import tn.wevioo.packager.model.request.PackagerTransformationRequest;
import tn.wevioo.packager.model.request.SplitPackagerRequest;
import tn.wevioo.packager.service.PackagerActionHistoryService;
import tn.wevioo.packager.service.PackagerInstanceService;
import tn.wevioo.packager.service.PackagerModelService;
import tn.wevioo.packager.service.ProductInstanceService;
import tn.wevioo.packager.service.ProductModelProductDriverPortService;
import tn.wevioo.packager.service.ProductModelService;
import tn.wevioo.packager.service.WebServiceUserService;

@RestController
public class PackagerManagementController extends AbstractFacade {

	@Autowired
	private PackagerInstanceService packagerInstanceService;

	@Autowired
	private PackagerModelService packagerModelService;

	@Autowired
	private PackagerActionHistoryService packagerActionHistoryService;

	@Autowired
	private ProductModelService productModelService;

	@Autowired
	private WebServiceUserService webServiceUserService;

	@Autowired
	private ProductInstanceService productInstanceService;

	@Autowired
	ProductModelProductDriverPortService productModelProductDriverPortService;

	@RequestMapping(value = "/packagerInstances", method = RequestMethod.POST)
	public PackagerInstanceDTO createPackager(@RequestBody PackagerRequest request)
			throws DriverException, NotRespectedRulesException, MalformedXMLException, PackagerException,
			NotFoundException, DataSourceException, RestTemplateException {

		PackagerModel packagerModel = packagerModelService.findByRetailerKey(request.getModel());
		PackagerInstance createdPackagerInstance = null;
		PackagerActionHistory history = new PackagerActionHistory(PackagerInstanceAction.CREATE, webServiceUserService);
		createdPackagerInstance = packagerModel.instantiate(request, history, productModelService,
				webServiceUserService, productInstanceService, productModelProductDriverPortService);

		packagerInstanceService.saveOrUpdate(createdPackagerInstance);
		packagerActionHistoryService.saveOrUpdate(history);

		return packagerInstanceService.convertToDTO(createdPackagerInstance);
	}

	@RequestMapping(value = "/packagerInstances", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public PackagerInstanceDTO getPackagerInstance(@QueryParam("retailerPackagerId") String retailerPackagerId)
			throws DriverException, RestTemplateException, NotFoundException {
		return packagerInstanceService
				.convertToDTO(packagerInstanceService.findByRetailerPackagerId(retailerPackagerId));
	}

	@RequestMapping(value = "/suspendPackager", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public PackagerInstanceDTO suspendPackager(@RequestBody PackagerRequest request) throws NotFoundException,
			NotRespectedRulesException, DriverException, PackagerException, MalformedXMLException, DataSourceException,
			SAXException, IOException, ParserConfigurationException, RestTemplateException {
		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}
		PackagerInstance packagerInstance = null;
		packagerInstance = packagerInstanceService.findByRetailerPackagerId(request.getRetailerPackagerId());
		packagerInstance.suspend(request, productModelService, packagerActionHistoryService, webServiceUserService,
				productInstanceService, productModelProductDriverPortService);

		return packagerInstanceService.convertToDTO(packagerInstance);

	}

	@RequestMapping(value = "/activatePackager", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public PackagerInstanceDTO activatePackager(@RequestBody PackagerRequest request) throws DriverException,
			NotRespectedRulesException, MalformedXMLException, PackagerException, NotFoundException,
			DataSourceException, SAXException, IOException, ParserConfigurationException, RestTemplateException {

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		PackagerInstance packagerInstance = null;
		packagerInstance = packagerInstanceService.findByRetailerPackagerId(request.getRetailerPackagerId());

		packagerInstance.activate(request, productModelService, packagerActionHistoryService, webServiceUserService,
				productInstanceService, productModelProductDriverPortService);

		return packagerInstanceService.convertToDTO(packagerInstance);
	}

	@RequestMapping(value = "/reactivatePackager", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public PackagerInstanceDTO reactivatePackager(@RequestBody PackagerRequest request)
			throws NotRespectedRulesException, NotFoundException, DriverException, PackagerException,
			MalformedXMLException, DataSourceException, SAXException, IOException, ParserConfigurationException,
			RestTemplateException {
		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		PackagerInstance packagerInstance = null;
		packagerInstance = packagerInstanceService.findByRetailerPackagerId(request.getRetailerPackagerId());

		packagerInstance.reactivate(request, productModelService, packagerActionHistoryService, webServiceUserService,
				productInstanceService, productModelProductDriverPortService);

		return packagerInstanceService.convertToDTO(packagerInstance);
	}

	@RequestMapping(value = "/cancelPackager", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public PackagerInstanceDTO cancelPackager(@RequestBody PackagerRequest request) throws NotRespectedRulesException,
			NotFoundException, DriverException, PackagerException, MalformedXMLException, DataSourceException,
			SAXException, IOException, ParserConfigurationException, RestTemplateException {
		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		PackagerInstance packagerInstance = null;
		packagerInstance = packagerInstanceService.findByRetailerPackagerId(request.getRetailerPackagerId());

		packagerInstance.cancel(request, productModelService, packagerActionHistoryService, webServiceUserService,
				productInstanceService, productModelProductDriverPortService);

		return packagerInstanceService.convertToDTO(packagerInstance);
	}

	@RequestMapping(value = "/resetPackager", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public PackagerInstanceDTO resetPackager(@RequestBody PackagerRequest request) throws NotRespectedRulesException,
			NotFoundException, DriverException, PackagerException, MalformedXMLException, DataSourceException,
			SAXException, IOException, ParserConfigurationException, RestTemplateException {
		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		PackagerInstance packagerInstance = null;
		packagerInstance = packagerInstanceService.findByRetailerPackagerId(request.getRetailerPackagerId());

		packagerInstance.reset(request, productModelService, packagerActionHistoryService, webServiceUserService,
				productInstanceService, productModelProductDriverPortService);

		return packagerInstanceService.convertToDTO(packagerInstance);
	}

	@RequestMapping(value = "/isRetailerPackagerIdFree", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean isRetailerPackagerIdFree(@QueryParam("retailerPackagerId") String retailerPackagerId)
			throws PackagerException, DataSourceException {

		return packagerInstanceService.isRetailerPackagerIdFree(retailerPackagerId);
	}

	@RequestMapping(value = "/isPackagerCreationPossible", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public FeasibilityResult isPackagerCreationPossible(@RequestBody PackagerRequest request)
			throws PackagerException, DriverException, DataSourceException, NotRespectedRulesException, SAXException,
			IOException, ParserConfigurationException, TransformerException, RestTemplateException {

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
				productModelProductDriverPortService);
		return feasibilityResult;

	}

	@RequestMapping(value = "/packagerInstanceHeaders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public PackagerInstanceHeaderDTO getPackagerInstanceHeader(
			@QueryParam("retailerPackagerId") String retailerPackagerId)
			throws PackagerException, NotFoundException, DataSourceException {

		PackagerInstance packagerInstance = packagerInstanceService.findByRetailerPackagerId(retailerPackagerId);

		return packagerInstanceService.convertToHeaderDTO(packagerInstance);

	}

	@RequestMapping(value = "/packagerProperties", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProductPropertiesDTO> getPackagerProperties(@QueryParam("retailerPackagerId") String retailerPackagerId)
			throws NotFoundException, PackagerException, DriverException, DataSourceException, RestTemplateException {
		List<ProductPropertiesDTO> result = new ArrayList<ProductPropertiesDTO>();

		PackagerInstance packagerInstance = packagerInstanceService.findByRetailerPackagerId(retailerPackagerId);

		for (ProductInstance pi : packagerInstance.getProducts()) {
			ProductPropertiesDTO productPropertiesDTO = new ProductPropertiesDTO();
			productPropertiesDTO = productInstanceService.convertToProductPropertiesDTO(pi);
			result.add(productPropertiesDTO);
		}

		return result;
	}

	@RequestMapping(value = "/packagerModels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PackagerModelDTO> getPackagerModels() throws PackagerException, DataSourceException {
		List<PackagerModel> packagerModels = new ArrayList<PackagerModel>();

		packagerModels = packagerModelService.findAllActive();

		List<PackagerModelDTO> result = new ArrayList<PackagerModelDTO>();

		for (PackagerModel packagerModel : packagerModels) {
			result.add(packagerModelService.convertToDTO(packagerModel));
		}

		return result;

	}

	@RequestMapping(value = "/packagerModel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public PackagerModelDTO getPackagerModel(@QueryParam("packagerModelKey") String packagerModelKey)
			throws NotFoundException, PackagerException, DataSourceException {
		PackagerModel packagerModel = packagerModelService.findByRetailerKey(packagerModelKey);

		return packagerModelService.convertToDTO(packagerModel);

	}

	@RequestMapping(value = "/updateSelfDiagnostics", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public PackagerInstanceHeaderDTO updateSelfDiagnostics(@QueryParam("retailerPackagerId") String retailerPackagerId)
			throws NotFoundException, PackagerException, DataSourceException, DriverException, RestTemplateException {
		if (retailerPackagerId == null || retailerPackagerId.trim().length() == 0) {
			throw new NullException(NullCases.NULL_EMPTY, "retailerPackagerId parameter");
		}

		PackagerActionHistory packagerHistory = new PackagerActionHistory(PackagerInstanceAction.UPDATE_SELF_DIAGNOSTIC,
				webServiceUserService);
		PackagerInstance packager = packagerInstanceService.findByRetailerPackagerId(retailerPackagerId);
		packager.updateSelfDiagnostics(packagerHistory, webServiceUserService, productInstanceService,
				productModelProductDriverPortService);
		packagerActionHistoryService.saveOrUpdate(packagerHistory);
		packagerInstanceService.saveOrUpdate(packager);

		return packagerInstanceService.convertToHeaderDTO(packager);

	}

	@RequestMapping(value = "/translocateProductInstances", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public TranslocateProductResponseDTO translocateProductInstances(@RequestBody PackagerTransformationRequest request)
			throws PackagerException, DriverException, MalformedXMLException, DataSourceException, NotFoundException,
			NotRespectedRulesException, SAXException, IOException, ParserConfigurationException, RestTemplateException,
			TransformerException {

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		PackagerInstance originalPackagerInstance = packagerInstanceService
				.findByRetailerPackagerId(request.getRetailerPackagerId());
		PackagerActionHistory history = new PackagerActionHistory(PackagerInstanceAction.TRANSLOCATE_PRODUCT,
				webServiceUserService);
		originalPackagerInstance.translocateProductInstances(request, history, packagerInstanceService,
				productInstanceService, productModelService, webServiceUserService,
				productModelProductDriverPortService);

		packagerInstanceService.saveOrUpdate(originalPackagerInstance);
		packagerActionHistoryService.saveOrUpdate(history);

		TranslocateProductResponseDTO translocateProductResponseDTO = new TranslocateProductResponseDTO();
		translocateProductResponseDTO.setSource(packagerInstanceService
				.convertToDTO(packagerInstanceService.findByRetailerPackagerId(request.getRetailerPackagerId())));
		translocateProductResponseDTO.setDestination(packagerInstanceService.convertToDTO(
				packagerInstanceService.findByRetailerPackagerId(request.getDestinationRetailerPackagerId())));

		return translocateProductResponseDTO;
	}

	@RequestMapping(value = "/isProductTranslocationPossible", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public FeasibilityResult isProductTranslocationPossible(@RequestBody PackagerTransformationRequest request)
			throws DriverException, DataSourceException, PackagerException, SAXException, IOException,
			ParserConfigurationException, RestTemplateException, NotFoundException, TransformerException {

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		FeasibilityResult result = null;

		PackagerInstance packagerInstance = packagerInstanceService
				.findByRetailerPackagerId(request.getRetailerPackagerId());
		result = packagerInstance.isProductTranslocationPossible(request, packagerInstanceService,
				productInstanceService, productModelService, productModelProductDriverPortService);
		return result;
	}

	@RequestMapping(value = "/splitPackager", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public SplitResponseDTO splitPackager(@RequestBody SplitPackagerRequest request) throws PackagerException,
			DriverException, DataSourceException, MalformedXMLException, NotFoundException, NotRespectedRulesException,
			SAXException, IOException, ParserConfigurationException, RestTemplateException, TransformerException {
		if (request.getSource() == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}
		if (request.getSource().getRetailerPackagerId() == null) {
			throw new NullException(NullCases.NULL, "retailer packager id parameter");
		}

		PackagerInstance packagerInstance = packagerInstanceService
				.findByRetailerPackagerId(request.getSource().getRetailerPackagerId());
		PackagerActionHistory history = new PackagerActionHistory(PackagerInstanceAction.SPLIT, webServiceUserService);
		List<PackagerInstance> result = packagerInstance.split(request.getSource(), request.getDestination1(),
				request.getDestination2(), history, packagerModelService, webServiceUserService, productInstanceService,
				packagerInstanceService, productModelService, productModelProductDriverPortService);
		if (result != null && result.size() > 0) {
			for (PackagerInstance pi : result) {
				packagerInstanceService.saveOrUpdate(pi);
			}
		}
		packagerActionHistoryService.saveOrUpdate(history);

		SplitResponseDTO splitResponseDTO = new SplitResponseDTO();
		splitResponseDTO.setSource(packagerInstanceService.convertToDTO(
				packagerInstanceService.findByRetailerPackagerId(request.getSource().getRetailerPackagerId())));

		splitResponseDTO.setDestination1(packagerInstanceService.convertToDTO(packagerInstanceService
				.findByRetailerPackagerId(request.getDestination1().getDestinationRetailerPackagerId())));

		if (request.getDestination2() != null)
			splitResponseDTO.setDestination2(packagerInstanceService.convertToDTO(packagerInstanceService
					.findByRetailerPackagerId(request.getDestination2().getDestinationRetailerPackagerId())));

		return splitResponseDTO;

	}

	@RequestMapping(value = "/isSplitPackagerPossible", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public FeasibilityResult isSplitPackagerPossible(@RequestBody SplitPackagerRequest request)
			throws PackagerException, DataSourceException, DriverException, NotFoundException, MalformedXMLException,
			SAXException, IOException, ParserConfigurationException, RestTemplateException, TransformerException {
		FeasibilityResult fResult = new FeasibilityResult();
		if (request.getSource() == null) {
			NullException exp = new NullException(NullCases.NULL, "request parameter");
			fResult.setMotive(exp.getMessage());
			fResult.setPossible(false);
			return fResult;
		}
		if (request.getSource().getRetailerPackagerId() == null) {
			NullException exp = new NullException(NullCases.NULL, "retailer packager id parameter");
			fResult.setMotive(exp.getMessage());
			fResult.setPossible(false);
			return fResult;
		}

		PackagerInstance packagerInstance = null;
		packagerInstance = packagerInstanceService
				.findByRetailerPackagerId(request.getSource().getRetailerPackagerId());

		try {
			FeasibilityResult result = packagerInstance.isSplitPossible(request.getSource(), request.getDestination1(),
					request.getDestination2(), packagerInstanceService, packagerModelService, productModelService,
					productInstanceService, productModelProductDriverPortService);
			return result;
		} catch (NotRespectedRulesException e) {
			fResult.setMotive(e.getMessage());
			fResult.setPossible(false);
			return fResult;
		} catch (NullException e) {
			fResult.setMotive(e.getMessage());
			fResult.setPossible(false);
			return fResult;
		}
	}

	@RequestMapping(value = "/mergePackagers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public PackagerInstanceDTO mergePackagers(@RequestBody MergePackagersRequest request) throws PackagerException,
			NotFoundException, NotRespectedRulesException, DriverException, MalformedXMLException, DataSourceException,
			RestTemplateException, SAXException, IOException, ParserConfigurationException, TransformerException {

		PackagerActionHistory history = new PackagerActionHistory(PackagerInstanceAction.MERGE, webServiceUserService);
		PackagerInstance mergedPackagerInstance = PackagerInstance.merge(request.getSource1(), request.getSource2(),
				request.getDestination(), history, packagerInstanceService, packagerModelService,
				productInstanceService, webServiceUserService, productModelProductDriverPortService,
				productModelService);
		packagerInstanceService.saveOrUpdate(mergedPackagerInstance);
		packagerActionHistoryService.saveOrUpdate(history);

		return packagerInstanceService.convertToDTO(mergedPackagerInstance);
	}

	@RequestMapping(value = "/changePackagerProperties", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public PackagerInstanceDTO changePackagerProperties(@RequestBody PackagerRequest request) throws NotFoundException,
			NotRespectedRulesException, PackagerException, DriverException, MalformedXMLException, DataSourceException,
			RestTemplateException, ParserConfigurationException, SAXException, IOException, TransformerException {

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		PackagerInstance packagerInstance = packagerInstanceService
				.findByRetailerPackagerId(request.getRetailerPackagerId());

		PackagerActionHistory history = new PackagerActionHistory(PackagerInstanceAction.CHANGE_PROPERTIES,
				webServiceUserService);

		packagerInstance.changeProperties(request, history, productInstanceService,
				productModelProductDriverPortService, productModelService, webServiceUserService);

		packagerInstanceService.saveOrUpdate(packagerInstance);
		packagerActionHistoryService.saveOrUpdate(history);

		return packagerInstanceService.convertToDTO(packagerInstance);
	}

	@RequestMapping(value = "/isMergePackagerPossible", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public FeasibilityResult isMergePackagerPossible(@RequestBody MergePackagersRequest request)
			throws PackagerException, DriverException, DataSourceException, NotFoundException, MalformedXMLException,
			NotRespectedRulesException, RestTemplateException, SAXException, IOException, ParserConfigurationException,
			TransformerException {
		FeasibilityResult fResult = new FeasibilityResult();

		try {
			FeasibilityResult result = PackagerInstance.isMergePossible(request.getSource1(), request.getSource2(),
					request.getDestination(), packagerInstanceService, productModelProductDriverPortService,
					packagerModelService, productModelService, productInstanceService);
			return result;
		} catch (NullException e) {
			fResult.setMotive(e.getMessage());
			fResult.setPossible(false);
			return fResult;
		}
	}

}