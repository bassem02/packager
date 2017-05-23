package tn.wevioo.controller;

import java.io.IOException;

import javax.ws.rs.QueryParam;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import nordnet.architecture.exceptions.utils.ErrorCode;
import nordnet.drivers.contract.exceptions.DriverException;
import nordnet.drivers.contract.types.State;
import tn.wevioo.ManualDriverFactory;
import tn.wevioo.entities.PackagerInstance;
import tn.wevioo.exceptions.PackagerException;
import tn.wevioo.exceptions.RestTemplateException;
import tn.wevioo.model.AbstractFacade;
import tn.wevioo.model.request.PackagerRequest;
import tn.wevioo.service.PackagerActionHistoryService;
import tn.wevioo.service.PackagerInstanceService;
import tn.wevioo.service.ProductInstanceService;
import tn.wevioo.service.ProductModelProductDriverPortService;
import tn.wevioo.service.ProductModelService;
import tn.wevioo.service.WebServiceUserService;

@RestController
public class AdminPackagerManagementController extends AbstractFacade {

	private static final Log LOGGER = LogFactory.getLog(AdminPackagerManagementController.class);

	@Autowired
	private PackagerInstanceService packagerInstanceService;

	@Autowired
	private ProductInstanceService productInstanceService;

	@Autowired
	ProductModelProductDriverPortService productModelProductDriverPortService;

	@Autowired
	private PackagerActionHistoryService packagerActionHistoryService;

	@Autowired
	private ProductModelService productModelService;

	@Autowired
	private ManualDriverFactory manualDriverFactory;

	@Autowired
	private WebServiceUserService webServiceUserService;

	@RequestMapping(value = "/packagerInstances", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deletePackager(@RequestBody PackagerRequest request,
			@QueryParam("ignoreProviderException") Boolean ignoreProviderException,
			@QueryParam("ignoreUncanceledState") Boolean ignoreUncanceledState)
			throws NotFoundException, NotRespectedRulesException, DataSourceException, PackagerException,
			RestTemplateException, SAXException, IOException, ParserConfigurationException {

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		if (ignoreProviderException == null) {
			ignoreProviderException = false;
		}

		if (ignoreUncanceledState == null) {
			ignoreUncanceledState = false;
		}

		PackagerInstance packager = packagerInstanceService.findByRetailerPackagerId(request.getRetailerPackagerId());

		if (!ignoreUncanceledState) {

			try {
				State packagerState = packager.getCurrentState(productInstanceService,
						productModelProductDriverPortService);
				if (packagerState != null && packagerState != State.CANCELED) {
					throw new NotRespectedRulesException(new ErrorCode("1.2.2.18"),
							new Object[] { request.getRetailerPackagerId(), "CANCELED" });
				}
			} catch (DriverException e) {
				if (!ignoreProviderException) {
					throw new PackagerException(e);
				}
			}
		}

		try {
			packager.delete(request, ignoreUncanceledState, ignoreProviderException, productInstanceService,
					productModelProductDriverPortService, productModelService, manualDriverFactory,
					webServiceUserService, packagerActionHistoryService);
		} catch (DriverException e) {
			if (!ignoreProviderException) {
				throw new PackagerException(e);
			} else {
				if (LOGGER.isWarnEnabled()) {
					LOGGER.warn("Driver has thrown an exception when deleting packager. Ignoring it.", e);
				}
			}
		} catch (MalformedXMLException e) {
			throw new PackagerException(e);
		}

		packagerInstanceService.delete(packager);

		return "packager instance deleted";
	}

}
