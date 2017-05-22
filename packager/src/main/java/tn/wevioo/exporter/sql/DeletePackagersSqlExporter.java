package tn.wevioo.exporter.sql;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import nordnet.architecture.exceptions.explicit.DataSourceException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.utils.ErrorCode;
import tn.wevioo.exceptions.PackagerException;
import tn.wevioo.exporter.SqlExporter;
import tn.wevioo.model.request.PackagerRequest;
import tn.wevioo.service.PackagerInstanceService;

/**
 * The class DeletePackagersSqlExporter allows generating a SQL file deleting an
 * important set of packager instances. All related entities (product intances,
 * references, diagnostics, delivery demands...) are deleted too.
 * 
 * @author vberezan
 * @since 2.11.0
 */
public class DeletePackagersSqlExporter extends SqlExporter {

	/**
	 * {@link DeletePackagersSqlExporter}'s Logger.
	 */
	private static final Log LOGGER = LogFactory.getLog(DeletePackagersSqlExporter.class);

	/**
	 * Initial Value :"delete from packager_instance where id_packager_instance
	 * = "
	 */
	public final static String DELETE_PACKAGERS = "delete from packager_instance where id_packager_instance = ";

	/**
	 * Initial Value :"delete from packager_instance_product_instance where
	 * id_packager_instance = "
	 */
	public final static String DELETE_PACKAGER_PRODUCT_RELATION = "delete from packager_instance_product_instance where id_packager_instance = ";

	/**
	 * Initial Value :"delete from product_instance where id_product_instance =
	 * "
	 */
	public final static String DELETE_PRODUCTS = "delete from product_instance where id_product_instance = ";

	/**
	 * Initial Value :"delete from product_instance_reference where
	 * id_product_instance = "
	 */
	public final static String DELETE_PRODUCT_REFERENCES = "delete from product_instance_reference where id_product_instance = ";

	/**
	 * Initial Value :"delete from failed_shipping_demand where
	 * id_packager_instance = "
	 */
	public final static String DELETE_FAILED_DELIVERY_DEMANDS = "delete from failed_shipping_demand where id_packager_instance = ";

	/**
	 * Initial Value :"delete from product_instance_diagnostic
	 * whereid_product_instance = "
	 */
	public final static String DELETE_PRODUCT_DIAGNOSTICS = "delete from product_instance_diagnostic where id_product_instance = ";

	/**
	 * Initial Value :"delete from shipping_demand where pack_id = "
	 */
	public final static String DELETE_DELIVERY_DEMANDS = "delete from shipping_demand where pack_id = ";

	/**
	 * Initial Value :"delete from shipping_demand_product_instance where
	 * id_product_instance = "
	 */
	public final static String DELETE_PRODUCT_DELIVERY_DEMAND_RELATION = "delete from shipping_demand_product_instance where id_product_instance = ";

	/**
	 * This statement is used at the start of the final sql file, in order to
	 * select the DB to be used.
	 * <p>
	 * INEXISTENT value is used by default, to prevent executing the script
	 * everywhere.
	 */
	public final static String USE_DB_DEFAULT = "use INEXISTENT;";

	/**
	 * Packager buffer writer.
	 */
	private BufferedWriter packagersBuffer = null;

	/**
	 * Delivery demands buffer writer.
	 */
	private BufferedWriter deliveryDemandsBuffer = null;

	/**
	 * Failed delivery demands buffer writer.
	 */
	private BufferedWriter failedDeliveryDemandsBuffer = null;

	/**
	 * Packager product relation buffer writer.
	 */
	private BufferedWriter packagerProductRelationBuffer = null;

	/**
	 * Product delivery demand relation buffer writer.
	 */
	private BufferedWriter productDeliveryDemandRelationBuffer = null;

	/**
	 * Product diagnostics buffer writer.
	 */
	private BufferedWriter productDiagnoticsBuffer = null;

	/**
	 * Product references buffer writer.
	 */
	private BufferedWriter productReferencesBuffer = null;

	/**
	 * Products buffer writer.
	 */
	private BufferedWriter productsBuffer = null;

	/**
	 * Packager instance service.
	 */
	private PackagerInstanceService packagerInstanceService = null;

	/**
	 * Used on formatting final file;
	 */
	private int countPackagers = 0;

	/**
	 * The method append allows adding a new request to export. This method
	 * recieves a list of retailer packager identifiers used to get the pairs of
	 * [packager-id][product-id].
	 * 
	 * @param retailerPackagerIds
	 *            The list of retailer packager ids.
	 * @throws PackagerException
	 *             Custom exception.
	 * @throws NotRespectedRulesException
	 *             Custom exception.
	 */
	public void append(List<String> retailerPackagerIds) throws PackagerException, NotRespectedRulesException {

		if (packagersBuffer == null) {
			productDeliveryDemandRelationBuffer = getTemporaryFile();
			packagerProductRelationBuffer = getTemporaryFile();
			productDiagnoticsBuffer = getTemporaryFile();
			productReferencesBuffer = getTemporaryFile();
			productsBuffer = getTemporaryFile();
			deliveryDemandsBuffer = getTemporaryFile();
			failedDeliveryDemandsBuffer = getTemporaryFile();
			packagersBuffer = getTemporaryFile();
		}

		Map<Long, List<Long>> preparedIds = null;
		try {
			preparedIds = packagerInstanceService.getPackagerInstanceIdsWithProductInstanceIds(retailerPackagerIds,
					searchFrequency);
		} catch (DataSourceException e) {
			throw new PackagerException(e);
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Starting file export...");
		}

		// --- preparing advancement logging

		float packagersLenght = retailerPackagerIds.size();
		float currentPackagerIndex = 0;
		double lastRounded = -10;

		// --- browsing all packagers

		for (Long packagerId : preparedIds.keySet()) {
			countPackagers++;
			try {

				packagersBuffer.append(DELETE_PACKAGERS);
				packagersBuffer.append(String.valueOf(packagerId));
				packagersBuffer.append(";");
				packagersBuffer.newLine();

				deliveryDemandsBuffer.append(DELETE_DELIVERY_DEMANDS);
				deliveryDemandsBuffer.append(String.valueOf(packagerId));
				deliveryDemandsBuffer.append(";");
				deliveryDemandsBuffer.newLine();

				failedDeliveryDemandsBuffer.append(DELETE_FAILED_DELIVERY_DEMANDS);
				failedDeliveryDemandsBuffer.append(String.valueOf(packagerId));
				failedDeliveryDemandsBuffer.append(";");
				failedDeliveryDemandsBuffer.newLine();

				packagerProductRelationBuffer.append(DELETE_PACKAGER_PRODUCT_RELATION);
				packagerProductRelationBuffer.append(String.valueOf(packagerId));
				packagerProductRelationBuffer.append(";");
				packagerProductRelationBuffer.newLine();

				for (Long productId : preparedIds.get(packagerId)) {
					if (productId != null) {

						productsBuffer.append(DELETE_PRODUCTS);
						productsBuffer.append(String.valueOf(productId));
						productsBuffer.append(";");
						productsBuffer.newLine();

						productDeliveryDemandRelationBuffer.append(DELETE_PRODUCT_DELIVERY_DEMAND_RELATION);
						productDeliveryDemandRelationBuffer.append(String.valueOf(productId));
						productDeliveryDemandRelationBuffer.append(";");
						productDeliveryDemandRelationBuffer.newLine();

						productDiagnoticsBuffer.append(DELETE_PRODUCT_DIAGNOSTICS);
						productDiagnoticsBuffer.append(String.valueOf(productId));
						productDiagnoticsBuffer.append(";");
						productDiagnoticsBuffer.newLine();

						productReferencesBuffer.append(DELETE_PRODUCT_REFERENCES);
						productReferencesBuffer.append(String.valueOf(productId));
						productReferencesBuffer.append(";");
						productReferencesBuffer.newLine();

						nbrAppendedEntities++;
						if (nbrAppendedEntities.equals(flushFrequency)) {
							this.flush();
						}
					}
				}

				// --- logging percentage advancement

				currentPackagerIndex++;
				double rounded = Math.floor((currentPackagerIndex / packagersLenght) * 100);

				if (rounded % 10 == 0 && rounded != lastRounded) {

					lastRounded = rounded;

					if (LOGGER.isInfoEnabled()) {
						LOGGER.info(rounded + "% packagers exported.");
					}
				}

			} catch (IOException e) {
				throw new PackagerException(new ErrorCode("0.1.1.2.6"), new Object[] { getFinalName() }, e);
			}
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Files exported.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void flush() throws PackagerException {

		nbrAppendedEntities = 0;
		if (!nbrAppendedEntities.equals(0)) {
			try {
				deliveryDemandsBuffer.append(";");
				deliveryDemandsBuffer.newLine();
				deliveryDemandsBuffer.newLine();
				deliveryDemandsBuffer.flush();

				failedDeliveryDemandsBuffer.append(";");
				failedDeliveryDemandsBuffer.newLine();
				failedDeliveryDemandsBuffer.newLine();
				failedDeliveryDemandsBuffer.flush();

				packagerProductRelationBuffer.append(";");
				packagerProductRelationBuffer.newLine();
				packagerProductRelationBuffer.newLine();
				packagerProductRelationBuffer.flush();

				packagersBuffer.append(";");
				packagersBuffer.newLine();
				packagersBuffer.newLine();
				packagersBuffer.flush();

				productDeliveryDemandRelationBuffer.append(";");
				productDeliveryDemandRelationBuffer.newLine();
				productDeliveryDemandRelationBuffer.newLine();
				productDeliveryDemandRelationBuffer.flush();

				productDiagnoticsBuffer.append(";");
				productDiagnoticsBuffer.newLine();
				productDiagnoticsBuffer.newLine();
				productDiagnoticsBuffer.flush();

				productReferencesBuffer.append(";");
				productReferencesBuffer.newLine();
				productReferencesBuffer.newLine();
				productReferencesBuffer.flush();

				productsBuffer.append(";");
				productsBuffer.newLine();
				productsBuffer.newLine();
				productsBuffer.flush();

				nbrAppendedEntities = 0;
			} catch (IOException e) {
				throw new PackagerException(new ErrorCode("0.1.1.2.6"), new Object[] { getFinalName() }, e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void close() throws PackagerException {

		try {
			if (nbrAppendedEntities != 0) {
				this.flush();
			}

			deliveryDemandsBuffer.newLine();
			deliveryDemandsBuffer.close();

			failedDeliveryDemandsBuffer.newLine();
			failedDeliveryDemandsBuffer.close();

			packagerProductRelationBuffer.newLine();
			packagerProductRelationBuffer.close();

			packagersBuffer.newLine();
			packagersBuffer.close();

			productDeliveryDemandRelationBuffer.newLine();
			productDeliveryDemandRelationBuffer.close();

			productDiagnoticsBuffer.newLine();
			productDiagnoticsBuffer.close();

			productReferencesBuffer.newLine();
			productReferencesBuffer.close();

			productsBuffer.newLine();
			productsBuffer.close();

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

	public void setPackagerInstanceService(PackagerInstanceService packagerInstanceService) {
		this.packagerInstanceService = packagerInstanceService;
	}

	public void append(PackagerRequest request) throws PackagerException, NotRespectedRulesException {

	}

}
