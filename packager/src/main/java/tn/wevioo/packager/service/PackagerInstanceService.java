package tn.wevioo.packager.service;

import java.util.List;
import java.util.Map;

import nordnet.architecture.exceptions.explicit.DataSourceException;
import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.drivers.contract.exceptions.DriverException;
import tn.wevioo.packager.dto.packager.PackagerInstanceDTO;
import tn.wevioo.packager.dto.packager.PackagerInstanceHeaderDTO;
import tn.wevioo.packager.entities.PackagerInstance;
import tn.wevioo.packager.exceptions.PackagerException;
import tn.wevioo.packager.exceptions.RestTemplateException;
import tn.wevioo.packager.model.request.PackagerRequest;

public interface PackagerInstanceService extends CrudService<PackagerInstance, Integer> {

	public PackagerInstance findByRetailerPackagerId(String retailerPackagerId) throws NotFoundException;

	public PackagerInstanceDTO convertToDTO(PackagerInstance packagerInstance)
			throws DriverException, RestTemplateException, NotFoundException;

	public Boolean isRetailerPackagerIdFree(String retailerPackagerId);

	public PackagerInstanceHeaderDTO convertToHeaderDTO(PackagerInstance packagerInstance);

	public void importExistingPackager(PackagerRequest request)
			throws PackagerException, NotFoundException, DataSourceException, NotRespectedRulesException;

	public void setSearchFrequency(Integer searchFrequency);

	public void generateSqlScriptToImportExistingPackagers(List<PackagerRequest> requests, String workspace,
			String finalName)
			throws PackagerException, DataSourceException, NotFoundException, NotRespectedRulesException;

	public void generateSqlScriptToImportProductReferences(List<PackagerRequest> requests, String workspace,
			String finalName)
			throws PackagerException, DataSourceException, NotFoundException, NotRespectedRulesException;

	public Map<Long, List<Long>> getPackagerInstanceIdsWithProductInstanceIds(List<String> retailerPackagerIds,
			Integer searchFrequency) throws DataSourceException;

	public void generateSqlScriptToDeletePackagers(List<PackagerRequest> requests, String workspace, String finalName)
			throws PackagerException, DataSourceException, NotFoundException, NotRespectedRulesException;

}
