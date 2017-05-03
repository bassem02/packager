package tn.wevioo.controller;

import java.util.ArrayList;
import java.util.List;

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
import tn.wevioo.entities.PackagerActionHistory;
import tn.wevioo.entities.PackagerInstance;
import tn.wevioo.entities.PackagerModel;
import tn.wevioo.entities.ProductInstance;
import tn.wevioo.exceptions.PackagerException;
import tn.wevioo.facade.impl.AbstractFacade;
import tn.wevioo.facade.packager.FPackagerInstance;
import tn.wevioo.facade.packager.FPackagerInstanceHeader;
import tn.wevioo.facade.packager.FPackagerModel;
import tn.wevioo.facade.product.FProductProperties;
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
	public FPackagerInstance createPackager(@RequestBody PackagerRequest request)
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
	public FPackagerInstance getPackagerInstance(@QueryParam("retailerPackagerId") String retailerPackagerId)
			throws DriverException {
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
	public FPackagerInstanceHeader getPackagerInstanceHeader(
			@QueryParam("retailerPackagerId") String retailerPackagerId)
			throws PackagerException, NotFoundException, DataSourceException {

		PackagerInstance packagerInstance = packagerInstanceService.findByRetailerPackagerId(retailerPackagerId);

		return packagerInstanceService.convertToHeaderDTO(packagerInstance);

	}

	@RequestMapping(value = "/getPackagerProperties", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<FProductProperties> getPackagerProperties(@QueryParam("retailerPackagerId") String retailerPackagerId)
			throws NotFoundException, PackagerException, DriverException, DataSourceException {
		List<FProductProperties> result = new ArrayList<FProductProperties>();

		PackagerInstance packagerInstance = packagerInstanceService.findByRetailerPackagerId(retailerPackagerId);

		for (ProductInstance pi : packagerInstance.getProducts()) {
			FProductProperties fProductProperties = new FProductProperties();
			fProductProperties.setProperties(pi.getProductProperties());
			result.add(fProductProperties);
		}

		return result;
	}

	@RequestMapping(value = "/getPackagerModels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<FPackagerModel> getPackagerModels() throws PackagerException, DataSourceException {
		List<PackagerModel> packagerModels = new ArrayList<PackagerModel>();

		packagerModels = packagerModelService.findAllActive();

		List<FPackagerModel> result = new ArrayList<FPackagerModel>();

		for (PackagerModel packagerModel : packagerModels) {
			result.add(packagerModelService.convertToDTO(packagerModel));
		}

		return result;

	}

	@RequestMapping(value = "/getPackagerModel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public FPackagerModel getPackagerModel(@QueryParam("packagerModelKey") String packagerModelKey)
			throws NotFoundException, PackagerException, DataSourceException {
		PackagerModel packagerModel = packagerModelService.findByRetailerKey(packagerModelKey);

		return packagerModelService.convertToDTO(packagerModel);

	}

	@RequestMapping(value = "/updateSelfDiagnostics", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
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

		return packagerInstanceService.convertToHeaderDTO(packager);
	}
}