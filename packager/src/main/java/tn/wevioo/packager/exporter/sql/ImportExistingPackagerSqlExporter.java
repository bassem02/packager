package tn.wevioo.packager.exporter.sql;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import tn.wevioo.packager.exceptions.PackagerException;
import tn.wevioo.packager.exporter.SqlExporter;
import tn.wevioo.packager.model.request.PackagerRequest;
import tn.wevioo.packager.model.request.ProductRequest;
import tn.wevioo.packager.service.PackagerModelService;
import tn.wevioo.packager.service.ProductModelService;
import tn.wevioo.packager.service.WebServiceUserService;

/**
 * The class ImportExistingPackagerSqlExporter implements all the actions
 * required to export as an Sql script all the sql requests required to perform
 * imports of existing packagers.
 * 
 */
@Component
public class ImportExistingPackagerSqlExporter extends SqlExporter {

	/**
	 * Initial Value :"insert into packager_instance (id_packager_instance,
	 * retailer_packager_id, creation_date, id_packager_model, id_retailer)
	 * values "
	 */
	public final static String INSERT_PACKAGERS = "insert into packager_instance (id_packager_instance, retailer_packager_id, creation_date, id_packager_model, id_retailer) values";

	/**
	 * Initial Value : "insert into product_instance (provider_product_id,
	 * creation_date, id_product_model) values \n"
	 */
	public final static String INSERT_PRODUCTS = "insert into product_instance (id_product_instance, provider_product_id, creation_date, id_product_model) values";

	/**
	 * Initial Value : "insert into packager_instance_product_instance
	 * (id_product_instance, id_packager_instance) values "
	 */
	public final static String INSERT_PACKAGER_PRODUCT_RELATION = "insert into packager_instance_product_instance (id_packager_instance, id_product_instance) values";

	/**
	 * Initial Value : "insert into packager_action_history (id, creation_date,
	 * packager_action, id_webservice_user) values "
	 */
	public final static String INSERT_PACKAGER_HISTORY = "insert into packager_action_history (id, creation_date, packager_action, id_webservice_user) values";

	/**
	 * Initial Value : "insert into packager_action_packager_header_destination
	 * (retailer_packager_id, packager_model_key, id_packager_action_history)
	 * values "
	 */
	public final static String INSERT_PACKAGER_HISTORY_DESTINATION = "insert into packager_action_packager_header_destination (retailer_packager_id, packager_model_key, id_packager_action_history) values";

	/**
	 * Initial Value : "insert into product_action_history (creation_date,
	 * product_action, properties, dest_product_id, dest_product_model,
	 * src_product_id, src_product_model, id_webservice_user,
	 * id_packager_action_history) values "
	 */
	public final static String INSERT_PRODUCT_HISTORY = "insert into product_action_history (creation_date, product_action, properties, dest_product_id, dest_product_model, src_product_id, src_product_model, id_webservice_user, id_packager_action_history) values";

	/**
	 * This statement is used at the start of the final sql file, in order to
	 * select the DB to be used.
	 * <p>
	 * INEXISTENT value is used by default, to prevent executing the script
	 * everywhere.
	 */
	public final static String USE_DB_DEFAULT = "use nn_packager_management_recette;";

	/**
	 * Contains the buffer to use to write all packager instance insertions in a
	 * temporary file.
	 */
	private BufferedWriter packagersBuffer = null;

	/**
	 * Contains the buffer to use to write all product instance insertions in a
	 * temporary file.
	 */
	private BufferedWriter productsBuffer = null;

	/**
	 * WebServiceUserService service.
	 */
	@Autowired
	private WebServiceUserService webServiceUserService;

	/**
	 * PackagerModelService service.
	 */
	@Autowired
	private PackagerModelService packagerModelService;

	/**
	 * ProductModelService service.
	 */
	@Autowired
	private ProductModelService productModelService;

	/**
	 * Contains the last inserted packager instance's identifier.
	 */
	private Long idPackagerInstance;

	/**
	 * Contains the last inserted product instance's identifier.
	 */
	private Long idProductInstance;

	/**
	 * Contains the buffer to use to write all packager and product instance
	 * relation insertions in a temporary file.
	 */
	private BufferedWriter packagerProductRelationBuffer = null;

	/**
	 * Contains the last inserted packager action history's identifier.
	 */
	private Long idPackagerActionHistory;

	/**
	 * Contains the buffer to use to write all packager action history
	 * insertions in a temporary file.
	 */
	private BufferedWriter packagerActionHistoryBuffer = null;

	/**
	 * Contains the buffer to use to write all packager action history
	 * destination insertions in a temporary file.
	 */
	private BufferedWriter packagerActionHistoryDestinationBuffer = null;

	/**
	 * Contains the buffer to use to write all product action history insertions
	 * in a temporary file.
	 */
	private BufferedWriter productActionHistoryBuffer = null;

	/**
	 * {@inheritDoc}
	 */
	public void append(PackagerRequest request) throws PackagerException, NotRespectedRulesException {
		if (request == null) {
			throw new NullException(NullCases.NULL, "packager request");
		}

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (packagersBuffer == null) {
			packagersBuffer = getTemporaryFile();
			productsBuffer = getTemporaryFile();
			packagerProductRelationBuffer = getTemporaryFile();
			packagerActionHistoryBuffer = getTemporaryFile();
			packagerActionHistoryDestinationBuffer = getTemporaryFile();
			productActionHistoryBuffer = getTemporaryFile();
		}

		try {
			if (this.nbrAppendedEntities == 0) {
				packagersBuffer.append(INSERT_PACKAGERS);
				packagersBuffer.newLine();

				productsBuffer.append(INSERT_PRODUCTS);
				productsBuffer.newLine();

				packagerProductRelationBuffer.append(INSERT_PACKAGER_PRODUCT_RELATION);
				packagerProductRelationBuffer.newLine();

				packagerActionHistoryBuffer.append(INSERT_PACKAGER_HISTORY);
				packagerActionHistoryBuffer.newLine();

				packagerActionHistoryDestinationBuffer.append(INSERT_PACKAGER_HISTORY_DESTINATION);
				packagerActionHistoryDestinationBuffer.newLine();

				productActionHistoryBuffer.append(INSERT_PRODUCT_HISTORY);
				productActionHistoryBuffer.newLine();

			} else {
				packagerActionHistoryBuffer.append(",");
				packagerActionHistoryBuffer.newLine();

				packagerActionHistoryDestinationBuffer.append(",");
				packagerActionHistoryDestinationBuffer.newLine();

				packagerProductRelationBuffer.append(",");
				packagerProductRelationBuffer.newLine();

				packagersBuffer.append(",");
				packagersBuffer.newLine();

				productsBuffer.append(",");
				productsBuffer.newLine();

				productActionHistoryBuffer.append(",");
				productActionHistoryBuffer.newLine();
			}

			idPackagerInstance++;
			idPackagerActionHistory++;

			// -- first block

			packagersBuffer.append("(");
			packagersBuffer.append(String.valueOf(idPackagerInstance));
			packagersBuffer.append(", ");
			packagersBuffer.append("'" + request.getRetailerPackagerId() + "'");
			packagersBuffer.append(", ");
			packagersBuffer.append("'" + formatter.format(new Date()) + "'");
			packagersBuffer.append(", ");
			try {
				packagersBuffer.append(String
						.valueOf(packagerModelService.findByRetailerKey(request.getModel()).getIdPackagerModel()));
			} catch (NotFoundException e) {
				throw new PackagerException(e);
			}
			packagersBuffer.append(", ");
			packagersBuffer
					.append(String.valueOf(webServiceUserService.getWebserviceUser().getRetailer().getIdRetailer()));
			packagersBuffer.append(")");

			// -- second block

			packagerActionHistoryBuffer.append("(");
			packagerActionHistoryBuffer.append(String.valueOf(idPackagerActionHistory));
			packagerActionHistoryBuffer.append(", ");
			packagerActionHistoryBuffer.append("'" + formatter.format(new Date()) + "'");
			packagerActionHistoryBuffer.append(", 'IMPORT', ");
			packagerActionHistoryBuffer
					.append(String.valueOf(webServiceUserService.getWebserviceUser().getIdWebServiceUser()));
			packagerActionHistoryBuffer.append(")");

			// -- third block

			packagerActionHistoryDestinationBuffer.append("(");
			packagerActionHistoryDestinationBuffer.append("'" + request.getRetailerPackagerId() + "'");
			packagerActionHistoryDestinationBuffer.append(", ");
			packagerActionHistoryDestinationBuffer.append("'" + request.getModel() + "'");
			packagerActionHistoryDestinationBuffer.append(", ");
			packagerActionHistoryDestinationBuffer.append(String.valueOf(idPackagerActionHistory));
			packagerActionHistoryDestinationBuffer.append(")");

			int index = 0;
			for (ProductRequest productRequest : request.getProducts()) {
				idProductInstance++;

				// -- first block
				productsBuffer.append("(");
				productsBuffer.append(String.valueOf(idProductInstance));
				productsBuffer.append(", ");
				productsBuffer.append("'" + productRequest.getProviderProductId() + "'");
				productsBuffer.append(", ");
				productsBuffer.append("'" + formatter.format(new Date()) + "'");
				productsBuffer.append(", ");
				try {
					productsBuffer.append(String.valueOf(
							productModelService.findByRetailerKey(productRequest.getModel()).getIdProductModel()));
				} catch (NotFoundException e) {
					throw new PackagerException(e);
				}
				productsBuffer.append(")");

				// -- second block
				packagerProductRelationBuffer.append("(");
				packagerProductRelationBuffer.append(String.valueOf(idPackagerInstance));
				packagerProductRelationBuffer.append(", ");
				packagerProductRelationBuffer.append(String.valueOf(idProductInstance));
				packagerProductRelationBuffer.append(")");

				// -- third block
				productActionHistoryBuffer.append("(");
				productActionHistoryBuffer.append("'" + formatter.format(new Date()) + "'");
				productActionHistoryBuffer.append(", 'IMPORT', null, ");
				productActionHistoryBuffer.append(String.valueOf(idProductInstance));
				productActionHistoryBuffer.append(", ");
				productActionHistoryBuffer.append("'" + productRequest.getModel() + "'");
				productActionHistoryBuffer.append(", null, null, ");
				productActionHistoryBuffer
						.append(String.valueOf(webServiceUserService.getWebserviceUser().getIdWebServiceUser()));
				productActionHistoryBuffer.append(", ");
				productActionHistoryBuffer.append(String.valueOf(idPackagerActionHistory));
				productActionHistoryBuffer.append(")");

				// is productRequest not the last one of our collection?
				if (index != (request.getProducts().size() - 1)) {
					productsBuffer.append(",");
					productsBuffer.newLine();

					packagerProductRelationBuffer.append(",");
					packagerProductRelationBuffer.newLine();

					productActionHistoryBuffer.append(",");
					productActionHistoryBuffer.newLine();
				}

				index++;
			}
		} catch (IOException e) {
			throw new PackagerException(new ErrorCode("0.1.1.2.6"), new Object[] { getFinalName() }, e);
		}

		nbrAppendedEntities++;
		if (nbrAppendedEntities.equals(flushFrequency)) {
			this.flush();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void flush() throws PackagerException {

		try {
			packagersBuffer.append(";");
			packagersBuffer.newLine();
			packagersBuffer.newLine();
			packagersBuffer.flush();

			productsBuffer.append(";");
			productsBuffer.newLine();
			productsBuffer.newLine();
			productsBuffer.flush();

			packagerActionHistoryBuffer.append(";");
			packagerActionHistoryBuffer.newLine();
			packagerActionHistoryBuffer.newLine();
			packagerActionHistoryBuffer.flush();

			packagerActionHistoryDestinationBuffer.append(";");
			packagerActionHistoryDestinationBuffer.newLine();
			packagerActionHistoryDestinationBuffer.newLine();
			packagerActionHistoryDestinationBuffer.flush();

			packagerProductRelationBuffer.append(";");
			packagerProductRelationBuffer.newLine();
			packagerProductRelationBuffer.newLine();
			packagerProductRelationBuffer.flush();

			productActionHistoryBuffer.append(";");
			productActionHistoryBuffer.newLine();
			productActionHistoryBuffer.newLine();
			productActionHistoryBuffer.flush();
		} catch (IOException e) {
			throw new PackagerException(new ErrorCode("0.1.1.2.6"), new Object[] { getFinalName() }, e);
		}

		nbrAppendedEntities = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	public void close() throws PackagerException {

		try {
			if (nbrAppendedEntities != 0) {
				this.flush();
			}
			packagersBuffer.close();
			productsBuffer.close();
			packagerProductRelationBuffer.close();
			packagerActionHistoryDestinationBuffer.close();
			productActionHistoryBuffer.close();
			packagerActionHistoryBuffer.close();

			BufferedWriter sqlResult = getFinalFile();

			sqlResult.append(USE_DB_DEFAULT);
			sqlResult.newLine();
			sqlResult.newLine();

			// -- the order in the final file is given by the order of opening
			// files in #append method
			for (String fileName : temporaryFileNames) {
				BufferedReader reader = null;
				reader = new BufferedReader(
						new FileReader(workspace + System.getProperty("file.separator") + fileName));

				String line;
				while ((line = reader.readLine()) != null) {
					sqlResult.append(line);
					sqlResult.newLine();
				}
			}
			sqlResult.flush();
			sqlResult.close();
		} catch (Exception e) {
			throw new PackagerException(new ErrorCode("0.1.1.2.6"), new Object[] { getFinalName() }, e);
		}
	}

	public void setWebServiceUserService(WebServiceUserService webServiceUserService) {
		this.webServiceUserService = webServiceUserService;
	}

	public void setPackagerModelService(PackagerModelService packagerModelService) {
		this.packagerModelService = packagerModelService;
	}

	public void setProductModelService(ProductModelService productModelService) {
		this.productModelService = productModelService;
	}

	public Long getIdPackagerInstance() {
		return idPackagerInstance;
	}

	public void setIdPackagerInstance(Long idPackagerInstance) {
		this.idPackagerInstance = idPackagerInstance;
	}

	public Long getIdProductInstance() {
		return idProductInstance;
	}

	public void setIdProductInstance(Long idProductInstance) {
		this.idProductInstance = idProductInstance;
	}

	public Long getIdPackagerActionHistory() {
		return idPackagerActionHistory;
	}

	public void setIdPackagerActionHistory(Long idPackagerActionHistory) {
		this.idPackagerActionHistory = idPackagerActionHistory;
	}

}
