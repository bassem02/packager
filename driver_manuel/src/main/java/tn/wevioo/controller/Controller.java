package tn.wevioo.controller;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.QueryParam;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import nordnet.architecture.exceptions.explicit.MalformedXMLException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.drivers.contract.exceptions.DriverException;
import nordnet.drivers.contract.types.FeasibilityTestResult;
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

	@RequestMapping(value = "/createProductManual", method = RequestMethod.GET)
	public String createProductManual(@QueryParam("properties") String properties)
			throws DriverException, MalformedXMLException, NotRespectedRulesException {

		return manualDriverFactory.createProduct(properties).getProviderProductId();
	}

	@RequestMapping(value = "/getReferences", method = RequestMethod.GET)
	public List<tn.wevioo.tools.Reference> getReferences(@QueryParam("ppid") String ppid) throws DriverException {

		return manualDriver.getReference(ppid);
	}

	@RequestMapping(value = "/getCurrentState", method = RequestMethod.GET)
	public String getCurrentState(@QueryParam("ref") String ref) throws DriverException {

		return manualDriver.getCurrentState(ref).toString();
	}

	@RequestMapping(value = "/suspendProduct", method = RequestMethod.GET)
	public String suspendProduct(@QueryParam("properties") String properties, @QueryParam("ppid") String ppid)
			throws DriverException, MalformedXMLException, NotRespectedRulesException, SAXException, IOException,
			ParserConfigurationException {

		manualDriver.suspendManual(properties, ppid);
		return "suspended";
	}

	@RequestMapping(value = "/activateProduct", method = RequestMethod.GET)
	public String activateProduct(@QueryParam("properties") String properties, @QueryParam("ppid") String ppid)
			throws DriverException, MalformedXMLException, NotRespectedRulesException, SAXException, IOException,
			ParserConfigurationException {

		manualDriver.activateManual(properties, ppid);
		return "activated";
	}

	@RequestMapping(value = "/reactivateProduct", method = RequestMethod.GET)
	public String reactivateProduct(@QueryParam("properties") String properties, @QueryParam("ppid") String ppid)
			throws DriverException, MalformedXMLException, NotRespectedRulesException, SAXException, IOException,
			ParserConfigurationException {

		manualDriver.reactivateManual(properties, ppid);
		return "reactivated";
	}

	@RequestMapping(value = "/cancelProduct", method = RequestMethod.GET)
	public String cancelProduct(@QueryParam("properties") String properties, @QueryParam("ppid") String ppid)
			throws DriverException, MalformedXMLException, NotRespectedRulesException, SAXException, IOException,
			ParserConfigurationException {

		manualDriver.cancelManual(properties, ppid);
		return "canceled";
	}

	@RequestMapping(value = "/resetProduct", method = RequestMethod.GET)
	public String resetProduct(@QueryParam("properties") String properties, @QueryParam("ppid") String ppid)
			throws DriverException, MalformedXMLException, NotRespectedRulesException, SAXException, IOException,
			ParserConfigurationException {

		manualDriver.resetManual(properties, ppid);
		return "canceled";
	}

	@RequestMapping(value = "/performGetUsageProperties", method = RequestMethod.GET)
	public String performGetUsageProperties(@QueryParam("ppid") String ppid) throws DriverException, JAXBException {

		return manualDriver.performGetUsageProperties(ppid);
	}

	@RequestMapping(value = "/getProductProperties", method = RequestMethod.GET)
	public String getProductProperties(@QueryParam("ppid") String ppid) throws DriverException, JAXBException {

		return manualDriver.getProductProperties(ppid);
	}

	@RequestMapping(value = "/getSelfDiagnostics", method = RequestMethod.GET)
	public String getSelfDiagnostics() throws DriverException, JAXBException {

		return manualDriver.getSelfDiagnostics().toString();
	}

	@RequestMapping(value = "/isPropertiesChangePossible", method = RequestMethod.GET)
	public FeasibilityTestResult isPropertiesChangePossible(@QueryParam("properties") String properties)
			throws DriverException, JAXBException, MalformedXMLException {

		return manualDriver.isPropertiesChangePossible(properties);
	}

	@RequestMapping(value = "/changePropertiesManual", method = RequestMethod.GET)
	public String changePropertiesManual(@QueryParam("properties") String properties, @QueryParam("ppid") String ppid)
			throws DriverException, JAXBException, MalformedXMLException, NotRespectedRulesException, SAXException,
			IOException, ParserConfigurationException {

		manualDriver.changePropertiesManual(properties, ppid);

		return "properties changed";
	}

}
