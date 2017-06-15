package tn.wevioo.packager.controller;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import nordnet.architecture.exceptions.explicit.DataSourceException;
import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import nordnet.drivers.contract.exceptions.DriverException;
import nordnet.drivers.contract.types.State;
import tn.wevioo.packager.entities.PackagerInstance;
import tn.wevioo.packager.entities.ProductInstance;
import tn.wevioo.packager.entities.ProductModel;
import tn.wevioo.packager.exceptions.PackagerException;
import tn.wevioo.packager.exceptions.RestTemplateException;
import tn.wevioo.packager.model.AbstractFacade;
import tn.wevioo.packager.model.action.PackagerInstanceAction;
import tn.wevioo.packager.model.request.DeletePackagersRequest;
import tn.wevioo.packager.model.request.GenerateSqlScriptRequest;
import tn.wevioo.packager.model.request.ImportProductReferencesRequest;
import tn.wevioo.packager.model.request.PackagerRequest;
import tn.wevioo.packager.model.request.ProductRequest;
import tn.wevioo.packager.model.request.UpdatePackagerStatesRequest;
import tn.wevioo.packager.service.PackagerInstanceService;
import tn.wevioo.packager.service.ProductInstanceService;
import tn.wevioo.packager.service.ProductModelProductDriverPortService;
import tn.wevioo.packager.service.ProductModelService;

@RestController
public class BackOfficePackagerManagementController extends AbstractFacade {

	private static final Log LOGGER = LogFactory.getLog(BackOfficePackagerManagementController.class);

	@Autowired
	private PackagerInstanceService packagerInstanceService;

	@Autowired
	private ProductInstanceService productInstanceService;

	@Autowired
	private ProductModelProductDriverPortService productModelProductDriverPortService;

	@Autowired
	private ProductModelService productModelService;

	@Autowired
	private AdminPackagerManagementController adminPackagerManagementController;

	@RequestMapping(value = "/importExistingPackager", method = RequestMethod.POST)
	public void importExistingPackager(@RequestBody PackagerRequest request)
			throws NotFoundException, NotRespectedRulesException, DataSourceException, PackagerException {
		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		packagerInstanceService.setSearchFrequency(1);
		packagerInstanceService.importExistingPackager(request);
	}

	@RequestMapping(value = "/updatePackagerStates", method = RequestMethod.PUT)
	public void updatePackagerStates(@RequestBody UpdatePackagerStatesRequest updatePackagerStatesRequest)
			throws PackagerException, NotFoundException, DataSourceException, RestTemplateException {
		if ((updatePackagerStatesRequest.getRequests() == null)
				|| (updatePackagerStatesRequest.getRequests().size() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "requests parameter");
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Starting updating...");
		}

		// --- preparing advancement logging

		float packagersLenght = updatePackagerStatesRequest.getRequests().size();
		float currentPackagerIndex = 0;
		double lastRounded = -10;

		for (PackagerRequest request : updatePackagerStatesRequest.getRequests()) {

			// --- updating current packager

			if ((request.getRetailerPackagerId() == null) || (request.getRetailerPackagerId().trim().length() == 0)) {
				throw new NullException(NullCases.NULL_EMPTY, "requests.request parameter");
			}

			PackagerInstance packagerInstance = packagerInstanceService
					.findByRetailerPackagerId(request.getRetailerPackagerId());
			try {
				packagerInstance.getCurrentState(productInstanceService, productModelProductDriverPortService);
			} catch (DriverException e) {
				throw new PackagerException(e);
			}

			packagerInstanceService.saveOrUpdate(packagerInstance);

			// --- logging percentage advancement

			currentPackagerIndex++;
			double rounded = Math.floor((currentPackagerIndex / packagersLenght) * 100);

			if (rounded % 10 == 0 && rounded != lastRounded) {

				lastRounded = rounded;

				if (LOGGER.isInfoEnabled()) {
					LOGGER.info(rounded + "% packagers updated.");
				}
			}
		}

	}

	@RequestMapping(value = "/importProductReferences", method = RequestMethod.PUT)
	public void importProductReferences(@RequestBody ImportProductReferencesRequest importProductReferencesRequest)
			throws PackagerException, NotFoundException, DataSourceException, NotRespectedRulesException {

		if ((importProductReferencesRequest.getRequests() == null)
				|| (importProductReferencesRequest.getRequests().size() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "reqeusts attribute");
		}

		// --- preparing advancement logging

		float requestsLenght = importProductReferencesRequest.getRequests().size();
		float currentRequestIndex = 0;
		double lastRounded = -10;

		// --- browsing all requests

		for (ProductRequest request : importProductReferencesRequest.getRequests()) {

			request.validate(PackagerInstanceAction.IMPORT_REFERENCES);
			ProductModel productModel = productModelService.findByRetailerKey(request.getModel());
			ProductInstance productInstance = productInstanceService
					.findByProviderProductId(request.getProviderProductId(), productModel);
			productInstance.importReferences(request);
			productInstanceService.saveOrUpdate(productInstance);

			// --- logging percentage advancement

			currentRequestIndex++;
			double rounded = Math.floor((currentRequestIndex / requestsLenght) * 100);

			if (rounded % 10 == 0 && rounded != lastRounded) {

				lastRounded = rounded;

				if (LOGGER.isInfoEnabled()) {
					LOGGER.info(rounded + "% requests imported.");
				}
			}
		}
	}

	@RequestMapping(value = "/deletePackagers", method = RequestMethod.DELETE)
	public Long deletePackagers(@RequestBody DeletePackagersRequest deletePackagersRequest)
			throws PackagerException, NotFoundException, NotRespectedRulesException, DataSourceException,
			RestTemplateException, SAXException, IOException, ParserConfigurationException {

		List<PackagerRequest> boList = deletePackagersRequest.getRequests();
		boList = prepareRequestsToDeletePackagers(boList, deletePackagersRequest.getIgnoreUncanceledState());

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Delete requests prepared.");
			LOGGER.info("Starting deletion...");
		}

		// --- preparing advancement logging

		float packagersLenght = deletePackagersRequest.getRequests().size();
		float currentPackagerIndex = 0;
		double lastRounded = -10;

		for (PackagerRequest request : deletePackagersRequest.getRequests()) {

			// --- deleting current packager

			adminPackagerManagementController.deletePackager(request,
					deletePackagersRequest.getIgnoreProviderException(),
					deletePackagersRequest.getIgnoreUncanceledState());

			// --- logging percentage advancement

			currentPackagerIndex++;
			double rounded = Math.floor((currentPackagerIndex / packagersLenght) * 100);

			if (rounded % 10 == 0 && rounded != lastRounded) {

				lastRounded = rounded;

				if (LOGGER.isInfoEnabled()) {
					LOGGER.info(rounded + "% packagers deleted.");
				}
			}
		}

		return new Long(deletePackagersRequest.getRequests().size());
	}

	private List<PackagerRequest> prepareRequestsToDeletePackagers(List<PackagerRequest> requests,
			Boolean ignoreUncanceledState) throws DataSourceException, NotFoundException, NotRespectedRulesException,
			PackagerException, RestTemplateException {

		if ((requests == null) || (requests.size() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "requests attribute");
		}

		// --- preparing advancement logging

		float packagersLenght = requests.size();
		float currentPackagerIndex = 0;
		double lastRounded = -10;

		// --- browsing all requests to prepare

		for (PackagerRequest request : requests) {

			// --- verifying current request

			request.validate(PackagerInstanceAction.DELETE);

			PackagerInstance pi = packagerInstanceService.findByRetailerPackagerId(request.getRetailerPackagerId());

			try {
				if (!ignoreUncanceledState
						&& (!pi.getCurrentState(productInstanceService, productModelProductDriverPortService)
								.equals(State.CANCELED))) {
					throw new NotRespectedRulesException(new ErrorCode("1.2.2.18"),
							new Object[] { request.getRetailerPackagerId(), State.CANCELED });
				}
			} catch (DriverException e) {
				throw new PackagerException(e);
			}

			// --- logging percentage advancement

			currentPackagerIndex++;
			double rounded = Math.floor((currentPackagerIndex / packagersLenght) * 100);

			if (rounded % 10 == 0 && rounded != lastRounded) {

				lastRounded = rounded;

				if (LOGGER.isInfoEnabled()) {
					LOGGER.info(rounded + "% requests prepared.");
				}
			}
		}
		return requests;
	}

	@RequestMapping(value = "/generateSqlScriptToImportExistingPackagers", method = RequestMethod.POST)
	public void generateSqlScriptToImportExistingPackagers(@RequestBody GenerateSqlScriptRequest request)
			throws PackagerException, DataSourceException, NotFoundException, NotRespectedRulesException {

		packagerInstanceService.setSearchFrequency(1);
		packagerInstanceService.generateSqlScriptToImportExistingPackagers(request.getRequests(),
				request.getWorkspace(), request.getFinalName());
	}

	@RequestMapping(value = "/generateSqlScriptToImportProductReferences", method = RequestMethod.POST)
	public void generateSqlScriptToImportProductReferences(@RequestBody GenerateSqlScriptRequest request)
			throws PackagerException, NotFoundException, NotRespectedRulesException, DataSourceException {
		packagerInstanceService.generateSqlScriptToImportProductReferences(request.getRequests(),
				request.getWorkspace(), request.getFinalName());

	}

	@RequestMapping(value = "/generateSqlScriptToDeletePackagers", method = RequestMethod.POST)
	public void generateSqlScriptToDeletePackagers(@RequestBody GenerateSqlScriptRequest request)
			throws PackagerException, DataSourceException, NotFoundException, NotRespectedRulesException {
		packagerInstanceService.generateSqlScriptToDeletePackagers(request.getRequests(), request.getWorkspace(),
				request.getFinalName());

	}

}
