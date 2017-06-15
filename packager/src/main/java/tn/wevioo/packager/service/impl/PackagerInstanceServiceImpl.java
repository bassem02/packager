package tn.wevioo.packager.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nordnet.architecture.exceptions.explicit.DataSourceException;
import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.implicit.IllegalParameterException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import nordnet.drivers.contract.exceptions.DriverException;
import tn.wevioo.packager.dao.PackagerInstanceDao;
import tn.wevioo.packager.dto.packager.PackagerInstanceDTO;
import tn.wevioo.packager.dto.packager.PackagerInstanceHeaderDTO;
import tn.wevioo.packager.dto.product.ProductInstanceDTO;
import tn.wevioo.packager.dto.product.ProductInstanceDiagnosticDTO;
import tn.wevioo.packager.dto.product.ProductInstanceHeaderDTO;
import tn.wevioo.packager.dto.product.ProductInstanceReferenceDTO;
import tn.wevioo.packager.entities.PackagerActionHistory;
import tn.wevioo.packager.entities.PackagerInstance;
import tn.wevioo.packager.entities.PackagerModel;
import tn.wevioo.packager.entities.ProductActionHistory;
import tn.wevioo.packager.entities.ProductInstance;
import tn.wevioo.packager.entities.ProductInstanceDiagnostic;
import tn.wevioo.packager.entities.ProductInstanceReference;
import tn.wevioo.packager.entities.ProductModel;
import tn.wevioo.packager.exceptions.PackagerException;
import tn.wevioo.packager.exceptions.RestTemplateException;
import tn.wevioo.packager.exporter.sql.DeletePackagersSqlExporter;
import tn.wevioo.packager.exporter.sql.ImportExistingPackagerSqlExporter;
import tn.wevioo.packager.exporter.sql.ImportProductReferencesSqlExporter;
import tn.wevioo.packager.model.action.PackagerInstanceAction;
import tn.wevioo.packager.model.action.ProductInstanceAction;
import tn.wevioo.packager.model.request.PackagerRequest;
import tn.wevioo.packager.model.request.ProductRequest;
import tn.wevioo.packager.service.PackagerActionHistoryService;
import tn.wevioo.packager.service.PackagerInstanceService;
import tn.wevioo.packager.service.PackagerModelService;
import tn.wevioo.packager.service.ProductInstanceDiagnosticService;
import tn.wevioo.packager.service.ProductInstanceReferenceService;
import tn.wevioo.packager.service.ProductInstanceService;
import tn.wevioo.packager.service.ProductModelProductDriverPortService;
import tn.wevioo.packager.service.ProductModelService;
import tn.wevioo.packager.service.WebServiceUserService;

@Service("packagerInstanceService")
public class PackagerInstanceServiceImpl implements PackagerInstanceService {

	private static final Log LOGGER = LogFactory.getLog(PackagerInstanceServiceImpl.class);

	@Autowired
	public PackagerInstanceDao packagerInstanceDao;

	@Autowired
	public PackagerModelService packagerModelService;

	@Autowired
	public ProductInstanceService productInstanceService;

	@Autowired
	private ProductInstanceReferenceService productInstanceReferenceService;

	@Autowired
	private ProductInstanceDiagnosticService productInstanceDiagnosticService;

	@Autowired
	private ProductModelProductDriverPortService productModelProductDriverPortService;

	@Autowired
	private WebServiceUserService webServiceUserService;

	@Autowired
	private ProductModelService productModelService;

	@Autowired
	private PackagerActionHistoryService packagerActionHistoryService;

	@Autowired
	ImportExistingPackagerSqlExporter exporterPackager;

	@Autowired
	ImportProductReferencesSqlExporter exporterProductReferences;

	@Autowired
	DeletePackagersSqlExporter exporterDeletePackagers;

	protected Integer searchFrequency;

	public void setSearchFrequency(Integer searchFrequency) {
		if (!(searchFrequency > 0)) {
			throw new IllegalParameterException(new ErrorCode("0.2.1.1.21"), new Object[] { "searchFrequency", 0 });
		}
		this.searchFrequency = searchFrequency;
	}

	@Override
	public PackagerInstance saveOrUpdate(PackagerInstance packagerInstance) {
		return packagerInstanceDao.saveAndFlush(packagerInstance);
	}

	@Override
	public void delete(PackagerInstance packagerInstance) {
		packagerInstanceDao.delete(packagerInstance);
	}

	@Override
	public PackagerInstance findById(int id) throws NotFoundException {
		if (((Integer) id == null)) {
			throw new NullException(NullCases.NULL_EMPTY, "id parameter");
		}
		PackagerInstance result = packagerInstanceDao.findOne(id);

		if (result == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"), new Object[] { "Packager instance", " id", id });
		}
		return result;
	}

	@Override
	public List<PackagerInstance> findAll() {
		return packagerInstanceDao.findAll();
	}

	@Override
	public PackagerInstance findByRetailerPackagerId(String retailerPackagerId) throws NotFoundException {
		if ((retailerPackagerId == null) || (retailerPackagerId.length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "retailerPackagerId parameter");
		}

		PackagerInstance result = packagerInstanceDao.findByRetailerPackagerId(retailerPackagerId);

		if (result == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"),
					new Object[] { "Packager instance", " retailerPackagerId", retailerPackagerId });
		}

		return result;

	}

	public PackagerInstanceDTO convertToDTO(PackagerInstance packagerInstance)
			throws DriverException, RestTemplateException, NotFoundException {
		if (packagerInstance == null) {
			return null;
		}

		PackagerInstanceDTO result = new PackagerInstanceDTO();

		result.setCurrentState(
				packagerInstance.getCurrentState(productInstanceService, productModelProductDriverPortService));
		result.setPackagerModel(packagerInstance.getPackagerModel().getRetailerKey());
		result.setRetailerPackagerId(packagerInstance.getRetailerPackagerId());
		List<ProductInstanceDTO> productInstanceDTOs = new ArrayList<ProductInstanceDTO>();
		for (ProductInstance productInstance : packagerInstance.getProducts()) {

			productInstanceDTOs.add(productInstanceService.convertToDTO(productInstance));
		}
		result.setProducts(productInstanceDTOs);

		return result;
	}

	@Override
	public Boolean isRetailerPackagerIdFree(String retailerPackagerId) {

		if ((retailerPackagerId == null) || (retailerPackagerId.length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "retailerPackagerId parameter");
		}

		return packagerInstanceDao.findByRetailerPackagerId(retailerPackagerId) == null;
	}

	@Override
	public PackagerInstanceHeaderDTO convertToHeaderDTO(PackagerInstance packagerInstance) {
		PackagerInstanceHeaderDTO packagerInstanceHeaderDTO = new PackagerInstanceHeaderDTO();
		packagerInstanceHeaderDTO.setCreationDate(packagerInstance.getCreationDate());
		packagerInstanceHeaderDTO.setRetailerPackagerId(packagerInstance.getRetailerPackagerId());
		packagerInstanceHeaderDTO.setPackagerModel(packagerInstance.getPackagerModel().getRetailerKey());

		List<ProductInstanceHeaderDTO> products = new ArrayList<ProductInstanceHeaderDTO>();
		Set<ProductInstance> productsInstance = packagerInstance.getProducts();

		for (ProductInstance productInstance : productsInstance) {
			ProductInstanceHeaderDTO productInstanceHeaderDTO = new ProductInstanceHeaderDTO();
			productInstanceHeaderDTO.setProductId(productInstance.getIdProductInstance().longValue());
			productInstanceHeaderDTO.setProductModel(productInstance.getProductModel().getRetailerKey());
			productInstanceHeaderDTO.setProviderProductId(productInstance.getProviderProductId());

			List<ProductInstanceReferenceDTO> productInstanceReferenceDTOs = new ArrayList<ProductInstanceReferenceDTO>();
			for (ProductInstanceReference productInstanceReference : productInstance.getProductInstanceReferences()) {
				productInstanceReferenceDTOs
						.add(productInstanceReferenceService.convertToDTO(productInstanceReference));
			}
			productInstanceHeaderDTO.setReferences(productInstanceReferenceDTOs);

			List<ProductInstanceDiagnosticDTO> productInstanceDiagnosticDTOs = new ArrayList<ProductInstanceDiagnosticDTO>();
			for (ProductInstanceDiagnostic productInstanceDiagnostic : productInstance
					.getProductInstanceDiagnostics()) {
				productInstanceDiagnosticDTOs
						.add(productInstanceDiagnosticService.convertToDTO(productInstanceDiagnostic));
			}
			productInstanceHeaderDTO.setDiagnostics(productInstanceDiagnosticDTOs);
			products.add(productInstanceHeaderDTO);
		}
		packagerInstanceHeaderDTO.setProducts(products);

		return packagerInstanceHeaderDTO;
	}

	@Override
	public void importExistingPackager(PackagerRequest request)
			throws PackagerException, NotFoundException, DataSourceException, NotRespectedRulesException {

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		List<PackagerRequest> asList = new ArrayList<PackagerRequest>();
		asList.add(request);

		request = prepareRequestsToImportExistingPackagers(asList).get(0);

		PackagerModel packagerModel = packagerModelService.findByRetailerKey(request.getModel());
		PackagerActionHistory actionHistory = new PackagerActionHistory(PackagerInstanceAction.IMPORT,
				webServiceUserService);

		PackagerInstance pi = new PackagerInstance();
		pi.setRetailerPackagerId(request.getRetailerPackagerId());
		pi.setPackagerModel(packagerModel);
		pi.setRetailer(webServiceUserService.getWebserviceUser().getRetailer());
		pi.setCreationDate(new Date());
		pi.setLastUpdate(new Date());

		actionHistory.addDestination(pi);

		for (ProductRequest pr : request.getProducts()) {
			ProductModel productModel = productModelService.findByRetailerKey(pr.getModel());

			ProductInstance pri = new ProductInstance();
			pri.setProviderProductId(pr.getProviderProductId());
			pri.setProductModel(productModel);
			pri.setCreationDate(new Date());
			pri.setLastUpdate(new Date());

			actionHistory.addProductAction(new ProductActionHistory(ProductInstanceAction.IMPORT, null, pri,
					pr.getProperties(), webServiceUserService, productInstanceService));

			pi.addProductInstance(pri);
		}

		saveOrUpdate(pi);
		packagerActionHistoryService.saveOrUpdate(actionHistory);

	}

	private List<PackagerRequest> prepareRequestsToImportExistingPackagers(List<PackagerRequest> requests)
			throws PackagerException, DataSourceException, NotRespectedRulesException, NotFoundException {

		if (requests == null) {
			throw new NullException(NullCases.NULL, "requests list parameter");
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Preparing requests to import existing packagers...");
		}

		Set<String> retailerPackagerIds = new HashSet<String>();
		// Map<ProductModel, Set<providerProductIds>>
		Map<ProductModel, Set<String>> products = new HashMap<ProductModel, Set<String>>();

		// --- preparing advancement logging

		float requestsLenght = requests.size();
		float currentRequestIndex = 0;
		double lastRounded = -10;

		// --- browsing all requests

		for (PackagerRequest request : requests) {

			if (request == null) {
				throw new NullException(NullCases.NULL, "packager request");
			}

			request.validate(PackagerInstanceAction.IMPORT);

			if (!retailerPackagerIds.contains(request.getRetailerPackagerId())) {
				retailerPackagerIds.add(request.getRetailerPackagerId());
			} else {
				// We have 2 packager requests with the same retailer packager
				// id, in csv file
				throw new NotRespectedRulesException(new ErrorCode("0.2.1.1.9"),
						new Object[] { request.getRetailerPackagerId() });
			}

			PackagerModel packagerModel = packagerModelService.findByRetailerKey(request.getModel());
			packagerModel.verifyProductOccurences(new ArrayList<ProductRequest>(request.getProducts()));

			for (ProductRequest productRequest : request.getProducts()) {
				ProductModel productModel = productModelService.findByRetailerKey(productRequest.getModel());
				if (products.get(productModel) == null) {
					products.put(productModel, new HashSet<String>());
				}
				if (!products.get(productModel).contains(productRequest.getProviderProductId())) {
					products.get(productModel).add(productRequest.getProviderProductId());
				} else {
					// We have 2 product requests with the same product model
					// and provider product id.
					// This behavior could appear in 2 different packager
					// request.
					throw new NotRespectedRulesException(new ErrorCode("0.2.1.1.9"),
							new Object[] { productRequest.getProviderProductId() });
				}
			}

			// --- logging percentage advancement

			currentRequestIndex++;
			double rounded = Math.floor((currentRequestIndex / requestsLenght) * 100);

			if (rounded % 10 == 0 && rounded != lastRounded) {

				lastRounded = rounded;

				if (LOGGER.isInfoEnabled()) {
					LOGGER.info(rounded + "% requests prepared.");
				}
			}
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Requests prepared.");
		}

		areRetailerPackagerIdsFree(retailerPackagerIds);
		areProviderProductIdsFree(products);

		return requests;

	}

	private void areProviderProductIdsFree(Map<ProductModel, Set<String>> products)
			throws DataSourceException, NotRespectedRulesException {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Verify if all [provider product id, product model] pairs are free.");
		}

		for (ProductModel productModel : products.keySet()) {
			int toIndex = 0;
			int fromIndex = 0;
			List<String> providerProductIds = Arrays
					.asList(products.get(productModel).toArray(new String[products.get(productModel).size()]));
			// this is used for pagination
			do {
				fromIndex = toIndex;
				toIndex = Math.min((fromIndex + searchFrequency), providerProductIds.size());

				List<ProductInstance> productInstances = productInstanceService
						.findByProviderProductIds(providerProductIds.subList(fromIndex, toIndex), productModel);
				if (productInstances != null && productInstances.size() > 0) {
					throw new NotRespectedRulesException(new ErrorCode("0.2.1.1.9"),
							new Object[] { productInstances.get(0).getProviderProductId() });
				}
			} while (toIndex < providerProductIds.size());
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("All [provider product id, product model] pairs are free.");
		}
	}

	private void areRetailerPackagerIdsFree(Set<String> retailerPackagerIds)
			throws DataSourceException, NotRespectedRulesException {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Verify if all retailer packager ids are free.");
		}

		int toIndex = 0;
		int fromIndex = 0;
		List<String> ids = Arrays.asList(retailerPackagerIds.toArray(new String[retailerPackagerIds.size()]));
		// this is used for pagination
		do {
			fromIndex = toIndex;
			toIndex = Math.min((fromIndex + searchFrequency), retailerPackagerIds.size());
			List<String> rpis = ids.subList(fromIndex, toIndex);
			Integer countExistingPackagerIn = this.countExistingPackagersIn(rpis);
			if (countExistingPackagerIn != null && countExistingPackagerIn > 0) {
				if (LOGGER.isInfoEnabled()) {
					String rpiAsString = "";
					for (String rpi : rpis) {
						rpiAsString += (rpi + " ");
					}
					LOGGER.info("One of the following retailer packager ids is already used. [" + rpiAsString + "]");
				}
				throw new NotRespectedRulesException(new ErrorCode("0.2.1.3.4"), new Object[] { "Packagers" });
			}

		} while (toIndex < ids.size());

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("All retailer packager ids are free.");
		}

	}

	public Integer countExistingPackagersIn(List<String> retailerPackagerIds) throws DataSourceException {
		if ((retailerPackagerIds == null) || (retailerPackagerIds.size() == 0)) {
			return 0;
		}
		Integer result = 0;
		for (String retailerPackagerId : retailerPackagerIds)
			result = result + packagerInstanceDao.countByRetailerPackagerId(retailerPackagerId);
		return result;
	}

	@Override
	public void generateSqlScriptToImportExistingPackagers(List<PackagerRequest> requests, String workspace,
			String finalName)
			throws PackagerException, DataSourceException, NotFoundException, NotRespectedRulesException {

		if ((requests == null) || (requests.size() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "packager requests");
		}
		if (workspace == null || workspace.trim().length() == 0) {
			throw new NullException(NullCases.NULL_EMPTY, "workspace");
		}
		if (finalName == null || finalName.trim().length() == 0) {
			throw new NullException(NullCases.NULL_EMPTY, "finalName");
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Preparing requests to import...");
		}

		requests = this.prepareRequestsToImportExistingPackagers(requests);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Starting SQL export...");
		}

		exporterPackager.setWorkspace(workspace);
		exporterPackager.setFinalName(finalName);
		exporterPackager.setIdPackagerInstance(packagerInstanceDao.getMaxIdPackagerInstance());
		exporterPackager.setIdPackagerActionHistory(packagerActionHistoryService.getMaxIdPackagerActionHistory());
		exporterPackager.setIdProductInstance(productInstanceService.getMaxIdProductInstance());

		// --- preparing advancement logging

		float requestsLenght = requests.size();
		float currentRequestIndex = 0;
		double lastRounded = -10;

		// --- browsing all requests to import

		for (PackagerRequest request : requests) {

			// --- importing current request

			exporterPackager.append(request);

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

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Requests imported.");
			LOGGER.info("Closing files...");
		}

		exporterPackager.close();

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Files closed.");
		}
	}

	@Override
	public void generateSqlScriptToImportProductReferences(List<PackagerRequest> requests, String workspace,
			String finalName)
			throws PackagerException, DataSourceException, NotFoundException, NotRespectedRulesException {

		if ((requests == null) || (requests.size() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "packager requests");
		}
		if (workspace == null || workspace.trim().length() == 0) {
			throw new NullException(NullCases.NULL_EMPTY, "workspace");
		}
		if (finalName == null || finalName.trim().length() == 0) {
			throw new NullException(NullCases.NULL_EMPTY, "finalName");
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Preparing requests to import...");
		}

		Map<String, Map<String, ProductRequest>> map = null;

		map = this.prepareRequestsToImportProductReferences(requests);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Starting SQL export...");
		}

		exporterProductReferences.setSearchFrequency(1);
		exporterProductReferences.setWorkspace(workspace);
		exporterProductReferences.setFinalName(finalName);

		// --- browsing all requests to import

		for (String productModel : map.keySet()) {

			// --- importing current product requests list

			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Starting importing of " + productModel + " requests.");
			}

			exporterProductReferences.append(productModel, map.get(productModel));

			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Finishing importing of " + productModel + " requests.");
			}

		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Requests imported.");
			LOGGER.info("Closing files...");
		}

		exporterProductReferences.close();

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Files closed.");
		}
	}

	private Map<String, Map<String, ProductRequest>> prepareRequestsToImportProductReferences(
			List<PackagerRequest> requests)
			throws PackagerException, DataSourceException, NotFoundException, NotRespectedRulesException {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Preparing requests to import product references...");
		}

		// Map<productModelKey, Map<providerProductId, List<ProductRequest>>>
		Map<String, Map<String, ProductRequest>> result = new HashMap<String, Map<String, ProductRequest>>();

		// --- preparing advancement logging

		float requestsLenght = requests.size();
		float currentRequestIndex = 0;
		double lastRounded = -10;

		// --- browsing all requests

		for (PackagerRequest request : requests) {

			request.validate(PackagerInstanceAction.IMPORT_REFERENCES);

			// --- logging percentage advancement

			currentRequestIndex++;
			double rounded = Math.floor((currentRequestIndex / requestsLenght) * 100);

			// filter the result map by productModelKey and providerProductId
			for (ProductRequest pr : request.getProducts()) {
				if (result.get(pr.getModel()) == null) {
					result.put(pr.getModel(), new HashMap<String, ProductRequest>());
				}
				if (result.get(pr.getModel()).get(pr.getProviderProductId()) == null) {
					result.get(pr.getModel()).put(pr.getProviderProductId(), pr);
				} else {
					throw new NotRespectedRulesException(new ErrorCode("1.2.1.1.19"),
							new Object[] { pr.getProviderProductId(), pr.getModel() });
				}
			}

			if (rounded % 10 == 0 && rounded != lastRounded) {

				lastRounded = rounded;

				if (LOGGER.isInfoEnabled()) {
					LOGGER.info(rounded + "% requests prepared.");
				}
			}
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Requests prepared.");
		}

		return result;
	}

	@Override
	public Map<Long, List<Long>> getPackagerInstanceIdsWithProductInstanceIds(List<String> retailerPackagerIds,
			Integer searchFrequency) throws DataSourceException {
		if ((retailerPackagerIds == null) || (retailerPackagerIds.size() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "packager requests");
		}

		Map<Long, List<Long>> result = new HashMap<Long, List<Long>>();

		int toIndex = 0;
		int fromIndex = 0;

		// --- preparing advancement logging

		float requestsLenght = retailerPackagerIds.size();
		float currentRequestIndex = 0;
		double lastRounded = -10;

		// --- browsing all requests

		do {
			fromIndex = toIndex;
			toIndex = Math.min((fromIndex + searchFrequency), retailerPackagerIds.size());

			List<Long[]> packs = getPackagerIdsWithProductIds(retailerPackagerIds.subList(fromIndex, toIndex));

			for (Long[] pack : packs) {

				if (result.containsKey(pack[0])) {
					result.get(pack[0]).add(pack[1]);
				} else {
					List<Long> productsIds = new ArrayList<Long>();
					productsIds.add(pack[1]);
					result.put(pack[0], productsIds);
				}
			}

			// --- logging percentage advancement

			currentRequestIndex = toIndex;
			double rounded = Math.floor((currentRequestIndex / requestsLenght) * 10);

			if (rounded % 1 == 0 && rounded != lastRounded) {

				lastRounded = rounded;

				if (LOGGER.isInfoEnabled()) {
					LOGGER.info((rounded * 10) + "% requests prepared.");
				}
			}

		} while (toIndex < retailerPackagerIds.size());

		return result;
	}

	private List<Long[]> getPackagerIdsWithProductIds(List<String> retailerPackagerIds) {

		List<Long[]> result = new ArrayList<Long[]>();
		for (String retailerPackagerId : retailerPackagerIds) {
			PackagerInstance packagerInstance = packagerInstanceDao.findByRetailerPackagerId(retailerPackagerId);
			Set<ProductInstance> productInstances = packagerInstance.getProducts();
			for (ProductInstance productInstance : productInstances) {
				Long[] pack = new Long[2];
				pack[0] = (long) packagerInstance.getIdPackagerInstance();
				pack[1] = (long) productInstance.getIdProductInstance();
				result.add(pack);
			}
		}

		return result;
	}

	@Override
	public void generateSqlScriptToDeletePackagers(List<PackagerRequest> requests, String workspace, String finalName)
			throws PackagerException, DataSourceException, NotFoundException, NotRespectedRulesException {
		if ((requests == null) || (requests.size() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "packager requests");
		}
		if (workspace == null || workspace.trim().length() == 0) {
			throw new NullException(NullCases.NULL_EMPTY, "workspace");
		}
		if (finalName == null || finalName.trim().length() == 0) {
			throw new NullException(NullCases.NULL_EMPTY, "finalName");
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Preparing requests to delete...");
		}

		List<String> retailerPackagerIds = this.prepareRequestsToDeletePackagers(requests);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Starting SQL export...");
		}

		exporterDeletePackagers.setWorkspace(workspace);
		exporterDeletePackagers.setFinalName(finalName);
		exporterDeletePackagers.append(retailerPackagerIds);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Requests imported.");
			LOGGER.info("Closing files...");
		}

		exporterDeletePackagers.close();

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Files closed.");
		}

	}

	private List<String> prepareRequestsToDeletePackagers(List<PackagerRequest> requests)
			throws NotRespectedRulesException {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Preparing requests to delete packagers...");
		}

		List<String> retailerPackagerIds = new ArrayList<String>();

		for (PackagerRequest request : requests) {
			request.validate(PackagerInstanceAction.DELETE);
			retailerPackagerIds.add(request.getRetailerPackagerId());
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Requests prepared.");
		}

		return retailerPackagerIds;
	}

}
