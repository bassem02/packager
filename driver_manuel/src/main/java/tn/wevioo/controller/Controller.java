package tn.wevioo.controller;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nordnet.architecture.exceptions.explicit.MalformedXMLException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.drivers.contract.exceptions.DriverException;
import tn.wevioo.ManualDriver;
import tn.wevioo.ManualDriverFactory;
import tn.wevioo.entities.Product;
import tn.wevioo.service.ProductService;

@RestController
public class Controller {

	@Autowired
	ManualDriverFactory manualDriverFactory;

	@Autowired
	ManualDriver manualDriver;

	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/getProduct/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Product getProduct(@PathVariable("id") long id) {

		return productService.findById(id);
	}

	@RequestMapping(value = "/manual/createProductManual", method = RequestMethod.GET)
	public String createProductManual(@QueryParam("properties") String properties)
			throws DriverException, MalformedXMLException, NotRespectedRulesException {

		return manualDriverFactory.createProduct(properties).getProviderProductId();
	}

	@RequestMapping(value = "/manual/getReferences", method = RequestMethod.GET)
	public List<tn.wevioo.tools.Reference> getReferences() throws DriverException {

		return manualDriver.getReference();
	}

}
