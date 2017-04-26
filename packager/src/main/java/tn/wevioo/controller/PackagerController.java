package tn.wevioo.controller;

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
import tn.wevioo.dto.PackagerInstanceDTO;
import tn.wevioo.entities.PackagerActionHistory;
import tn.wevioo.entities.PackagerInstance;
import tn.wevioo.entities.PackagerModel;
import tn.wevioo.exceptions.PackagerException;
import tn.wevioo.facade.impl.AbstractFacade;
import tn.wevioo.model.packager.action.PackagerInstanceAction;
import tn.wevioo.model.request.PackagerRequest;
import tn.wevioo.service.PackagerActionHistoryService;
import tn.wevioo.service.PackagerInstanceService;
import tn.wevioo.service.PackagerModelService;
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
		// try {
		packagerInstance.suspend(request, productModelService, manualDriverFactory, packagerActionHistoryService,
				webServiceUserService);
		/*
		 * } catch (ConverterException e) { Throwable originalEx =
		 * super.findOriginalException(e); if (originalEx instanceof
		 * NNException) { throw new PackagerException((NNException) originalEx);
		 * } else if (originalEx instanceof NNImplicitException) { throw new
		 * PackagerException((NNImplicitException) originalEx); }
		 * 
		 * throw new PackagerException(e); }
		 */
	}

}