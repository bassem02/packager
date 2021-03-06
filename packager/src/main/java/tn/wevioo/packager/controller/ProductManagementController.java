package tn.wevioo.packager.controller;

import java.util.Date;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
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
import nordnet.architecture.exceptions.utils.ErrorCode;
import nordnet.drivers.contract.exceptions.DriverException;
import nordnet.tools.converter.exceptions.ConverterException;
import tn.wevioo.packager.dto.product.ProductInstanceDTO;
import tn.wevioo.packager.dto.product.ProductModelDTO;
import tn.wevioo.packager.dto.product.ProductPropertiesDTO;
import tn.wevioo.packager.entities.PackagerActionHistory;
import tn.wevioo.packager.entities.PackagerModel;
import tn.wevioo.packager.entities.PackagerModelProductModel;
import tn.wevioo.packager.entities.ProductInstance;
import tn.wevioo.packager.exceptions.PackagerException;
import tn.wevioo.packager.exceptions.RestTemplateException;
import tn.wevioo.packager.model.action.PackagerInstanceAction;
import tn.wevioo.packager.model.feasibility.FeasibilityResult;
import tn.wevioo.packager.model.request.ProductRequest;
import tn.wevioo.packager.service.PackagerActionHistoryService;
import tn.wevioo.packager.service.PackagerModelService;
import tn.wevioo.packager.service.ProductInstanceService;
import tn.wevioo.packager.service.ProductModelProductDriverPortService;
import tn.wevioo.packager.service.ProductModelService;
import tn.wevioo.packager.service.WebServiceUserService;

@RestController
public class ProductManagementController {

	@Autowired
	private ProductInstanceService productInstanceService;

	@Autowired
	private WebServiceUserService webServiceUserService;

	@Autowired
	private PackagerActionHistoryService packagerActionHistoryService;

	@Autowired
	private PackagerModelService packagerModelService;

	@Autowired
	private ProductModelService productModelService;

	@Autowired
	ProductModelProductDriverPortService productModelProductDriverPortService;

	@RequestMapping(value = "/cancelProduct", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductInstanceDTO cancelProduct(@RequestBody ProductRequest request)
			throws NotFoundException, NotRespectedRulesException, DriverException, PackagerException,
			MalformedXMLException, DataSourceException, ConverterException, RestTemplateException {

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		ProductInstance productInstance = productInstanceService.findById(request.getProductId().intValue());

		FeasibilityResult result = productInstance.getPackager().isProductCancelationPossible(request,
				productModelProductDriverPortService);
		if (result.getPossible()) {
			PackagerActionHistory history = new PackagerActionHistory(PackagerInstanceAction.CANCEL,
					webServiceUserService);
			productInstance.cancel(request.getProperties(), history, webServiceUserService, productInstanceService,
					productModelProductDriverPortService);
			history.addDestination(productInstance.getPackager());
			history.addSource(productInstance.getPackager());
			history.setLastUpdate(new Date());
			packagerActionHistoryService.saveOrUpdate(history);

			return productInstanceService.convertToDTO(productInstance);

		} else {
			throw new PackagerException(new ErrorCode("0.2.2.1"), result.getExceptionCause());
		}

	}

	@RequestMapping(value = "/resetProduct", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductInstanceDTO resetProduct(@RequestBody ProductRequest request)
			throws NotFoundException, NotRespectedRulesException, DriverException, PackagerException,
			MalformedXMLException, DataSourceException, RestTemplateException {

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		ProductInstance productInstance = productInstanceService.findById(request.getProductId().intValue());

		PackagerActionHistory history = new PackagerActionHistory(PackagerInstanceAction.RESET, webServiceUserService);
		productInstance.reset(request.getProperties(), history, webServiceUserService, productInstanceService,
				productModelProductDriverPortService);
		history.addDestination(productInstance.getPackager());
		history.addSource(productInstance.getPackager());
		packagerActionHistoryService.saveOrUpdate(history);

		return productInstanceService.convertToDTO(productInstance);

	}

	@RequestMapping(value = "/productInstances/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductInstanceDTO getProductInstance(@PathVariable("id") Integer productId)
			throws PackagerException, DataSourceException, DriverException, RestTemplateException, NotFoundException {

		return productInstanceService.convertToDTO(productInstanceService.findById(productId));
	}

	@RequestMapping(value = "/usageProductProperties/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getUsageProductProperties(@PathVariable("id") Integer productId)
			throws PackagerException, DriverException, NotFoundException, DataSourceException, RestTemplateException {

		if (productId == null) {
			throw new NullException(NullCases.NULL, "productId parameter");
		}

		ProductInstance productInstace = productInstanceService.findById(productId);

		return productInstace.getUsageProperties(productModelProductDriverPortService);
	}

	@RequestMapping(value = "/productProperties/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductPropertiesDTO getProductProperties(@PathVariable("id") Long productId)
			throws NotFoundException, PackagerException, DriverException, DataSourceException, RestTemplateException {

		if (productId == null) {
			throw new NullException(NullCases.NULL, "productId parameter");
		}

		ProductInstance productInstace = productInstanceService.findById(productId.intValue());

		return productInstanceService.convertToPropertiesDTO(productInstace);
	}

	@RequestMapping(value = "/productModelConfigurations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductModelDTO getProductModelConfiguration(@QueryParam("packagerModelKey") String packagerModelKey,
			@QueryParam("productModelKey") String productModelKey)
			throws PackagerException, DataSourceException, NotFoundException {

		if ((productModelKey == null) || (productModelKey.trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "productModelKey parameter");
		}

		PackagerModel packagerModel = packagerModelService.findByRetailerKey(packagerModelKey);

		for (PackagerModelProductModel config : packagerModel.getPackagerModelProductModels()) {
			if (config.getProductModel().getRetailerKey().equals(productModelKey)) {

				return productModelService.convertToDTO(config);
			}
		}
		throw new NotFoundException(new ErrorCode("0.2.1.3.2"),
				new Object[] { "product model", "key", productModelKey });
	}

	@RequestMapping(value = "/productModelConfigurationsByPrefix", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductModelDTO getProductModelConfigurationByPrefix(@QueryParam("packagerModelKey") String packagerModelKey,
			@QueryParam("productModelPrefix") String productModelPrefix)
			throws PackagerException, DataSourceException, NotFoundException {

		if ((productModelPrefix == null) || (productModelPrefix.trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "productModelKey parameter");
		}

		PackagerModel packagerModel = packagerModelService.findByRetailerKey(packagerModelKey);
		for (PackagerModelProductModel config : packagerModel.getPackagerModelProductModels()) {
			if (config.getProductModel().getRetailerKey().startsWith(productModelPrefix)) {
				return productModelService.convertToDTO(config);
			}
		}
		throw new NotFoundException(new ErrorCode("0.2.1.3.2"),
				new Object[] { "product model", "prefix", productModelPrefix });
	}

}
