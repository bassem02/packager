package tn.wevioo.controller;

import java.util.Date;

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
import nordnet.architecture.exceptions.utils.ErrorCode;
import nordnet.drivers.contract.exceptions.DriverException;
import nordnet.tools.converter.exceptions.ConverterException;
import tn.wevioo.entities.PackagerActionHistory;
import tn.wevioo.entities.PackagerModel;
import tn.wevioo.entities.PackagerModelProductModel;
import tn.wevioo.entities.ProductInstance;
import tn.wevioo.exceptions.PackagerException;
import tn.wevioo.facade.product.FProductInstance;
import tn.wevioo.facade.product.FProductModel;
import tn.wevioo.facade.product.FProductProperties;
import tn.wevioo.feasibility.FeasibilityResult;
import tn.wevioo.model.packager.action.PackagerInstanceAction;
import tn.wevioo.model.request.ProductRequest;
import tn.wevioo.service.PackagerActionHistoryService;
import tn.wevioo.service.PackagerModelService;
import tn.wevioo.service.ProductInstanceService;
import tn.wevioo.service.ProductModelService;
import tn.wevioo.service.WebServiceUserService;

@RestController
@EnableAutoConfiguration(exclude = JpaRepositoriesAutoConfiguration.class)
public class ProductController {

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

	@RequestMapping(value = "/cancelProduct", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void cancelProduct(@RequestBody ProductRequest request) throws NotFoundException, NotRespectedRulesException,
			DriverException, PackagerException, MalformedXMLException, DataSourceException, ConverterException {

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		ProductInstance productInstance = productInstanceService.findById(request.getProductId().intValue());

		FeasibilityResult result = productInstance.getPackager().isProductCancelationPossible(request);
		if (result.getPossible()) {
			PackagerActionHistory history = new PackagerActionHistory(PackagerInstanceAction.CANCEL,
					webServiceUserService);
			productInstance.cancel(request.getProperties(), history);
			history.addDestination(productInstance.getPackager());
			history.addSource(productInstance.getPackager());
			history.setLastUpdate(new Date());
			packagerActionHistoryService.saveOrUpdate(history);
		} else {
			throw new PackagerException(new ErrorCode("0.2.2.1"), result.getExceptionCause());
		}

	}

	@RequestMapping(value = "/resetProduct", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void resetProduct(@RequestBody ProductRequest request) throws NotFoundException, NotRespectedRulesException,
			DriverException, PackagerException, MalformedXMLException, DataSourceException {

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		ProductInstance productInstance = productInstanceService.findById(request.getProductId().intValue());

		PackagerActionHistory history = new PackagerActionHistory(PackagerInstanceAction.RESET, webServiceUserService);
		productInstance.reset(request.getProperties(), history);
		history.addDestination(productInstance.getPackager());
		history.addSource(productInstance.getPackager());
		packagerActionHistoryService.saveOrUpdate(history);
	}

	@RequestMapping(value = "/getProductInstance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public FProductInstance getProductInstance(@QueryParam("productId") Integer productId)
			throws PackagerException, DataSourceException, DriverException {

		return productInstanceService.convertToDTO(productInstanceService.findById(productId));
	}

	@RequestMapping(value = "/getUsageProductProperties", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getUsageProductProperties(@QueryParam("productId") Integer productId)
			throws PackagerException, DriverException, NotFoundException, DataSourceException {

		if (productId == null) {
			throw new NullException(NullCases.NULL, "productId parameter");
		}

		ProductInstance productInstace = productInstanceService.findById(productId);

		return productInstace.getUsageProperties();
	}

	@RequestMapping(value = "/getProductProperties", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public FProductProperties getProductProperties(@QueryParam("productId") Long productId)
			throws NotFoundException, PackagerException, DriverException, DataSourceException {

		if (productId == null) {
			throw new NullException(NullCases.NULL, "productId parameter");
		}

		ProductInstance productInstace = productInstanceService.findById(productId.intValue());

		return productInstanceService.convertToPropertiesDTO(productInstace);
	}

	@RequestMapping(value = "/getProductModelConfiguration", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public FProductModel getProductModelConfiguration(@QueryParam("packagerModelKey") String packagerModelKey,
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

	@RequestMapping(value = "/getProductModelConfigurationByPrefix", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public FProductModel getProductModelConfigurationByPrefix(@QueryParam("packagerModelKey") String packagerModelKey,
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
