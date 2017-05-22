package tn.wevioo.exporter.sql;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.utils.ErrorCode;
import tn.wevioo.entities.ProductInstance;
import tn.wevioo.entities.ProductModel;
import tn.wevioo.exceptions.PackagerException;
import tn.wevioo.exporter.SqlExporter;
import tn.wevioo.model.request.PackagerRequest;
import tn.wevioo.model.request.ProductReferenceRequest;
import tn.wevioo.model.request.ProductRequest;
import tn.wevioo.service.ProductInstanceService;
import tn.wevioo.service.ProductModelService;

/**
 * The class ImportProductReferencesSqlExporter allows generating a SQL file
 * importing an important set of product references. It first removes all
 * existing references for all regarded products, and then add all references.
 */

@Component
public class ImportProductReferencesSqlExporter extends SqlExporter {

	/**
	 * {@link ImportProductReferencesSqlExporter}'s Logger.
	 */
	private static final Log LOGGER = LogFactory.getLog(ImportProductReferencesSqlExporter.class);

	/**
	 * Initial Value :"delete from product_instance_reference where
	 * id_product_instance_reference = "
	 */
	public final static String DELETE_REFERENCES = "delete from product_instance_reference where id_product_instance = ";

	/**
	 * Initial Value : "insert into product_instance_reference
	 * (discriminator_type, discriminator_value, id_product_instance) values "
	 */
	public final static String INSERT_REFERENCES = "insert into product_instance_reference (discriminator_type, discriminator_value, id_product_instance) values";

	/**
	 * This statement is used at the start of the final sql file, in order to
	 * select the DB to be used.
	 * <p>
	 * INEXISTENT value is used by default, to prevent executing the script
	 * everywhere.
	 */
	public final static String USE_DB_DEFAULT = "use nn_packager_management_recette;";

	/**
	 * Contains the buffer to use to export reference deletions in a temporary
	 * file.
	 */
	private BufferedWriter deleteReferencesBuffer = null;

	/**
	 * Contains the buffer to use to export reference insertions in a temporary
	 * file.
	 */
	private BufferedWriter insertReferencesBuffer = null;

	/**
	 * The product model service.
	 */
	@Autowired
	private ProductModelService productModelService;

	/**
	 * The product instance service.
	 */
	@Autowired
	private ProductInstanceService productInstanceService;

	/**
	 * {@inheritDoc}
	 */
	protected void flush() throws PackagerException {
		if (!nbrAppendedEntities.equals(0)) {
			try {

				insertReferencesBuffer.append(";");
				insertReferencesBuffer.newLine();
				insertReferencesBuffer.newLine();

			} catch (IOException e) {
				throw new PackagerException(new ErrorCode("0.1.1.2.6"), new Object[] { getFinalName() }, e);
			}

			nbrAppendedEntities = 0;
		}
	}

	/**
	 * The method append allows adding new requests to export. The received
	 * requests will be appended to the already exported requests, depending on
	 * the excepted format and action.
	 * 
	 * @param productModelKey
	 *            Product model key of all product requests. Cannot be null or
	 *            empty.
	 * @param requests
	 *            Requests to export. This is a collection of ProductRequest
	 *            filtered by providerProductId. If is null the method do
	 *            nothing.
	 * @throws PackagerException
	 *             custom exception.
	 * @throws NotRespectedRulesException
	 *             custom exception.
	 */
	public void append(String productModelKey, Map<String, ProductRequest> requests)
			throws PackagerException, NotRespectedRulesException {

		ProductModel productModel = null;
		List<ProductInstance> productInstances = null;
		if (deleteReferencesBuffer == null) {
			deleteReferencesBuffer = getTemporaryFile();
		}
		if (insertReferencesBuffer == null) {
			insertReferencesBuffer = getTemporaryFile();
		}
		if (requests == null) {
			return;
		}
		try {
			productModel = productModelService.findByRetailerKey(productModelKey);
			List<String> ids = Arrays.asList(requests.keySet().toArray(new String[requests.keySet().size()]));

			// --- preparing advancement logging

			float requestsLenght = ids.size();
			float currentRequestIndex = 0;
			double lastRounded = -10;

			int toIndex = 0;
			int fromIndex = 0;
			// this is used for pagination
			do {
				fromIndex = toIndex;
				toIndex = Math.min((fromIndex + searchFrequency), ids.size());

				productInstances = productInstanceService.findByProviderProductIds(ids.subList(fromIndex, toIndex),
						productModel);
				for (ProductInstance productInstance : productInstances) {
					ProductRequest productRequest = requests.get(productInstance.getProviderProductId());
					if (productRequest == null) {
						throw new NotRespectedRulesException(new ErrorCode("0.2.1.3.2"),
								new Object[] { "corresponding product request", "provider product id",
										productInstance.getProviderProductId() });
					}

					try {
						deleteReferencesBuffer.append(DELETE_REFERENCES);
						deleteReferencesBuffer.append(String.valueOf(productInstance.getIdProductInstance()));
						deleteReferencesBuffer.append(";");
						deleteReferencesBuffer.newLine();
					} catch (IOException e) {
						throw new PackagerException(new ErrorCode("0.1.1.2.6"), new Object[] { getFinalName() }, e);
					}

					// int index = 0;
					for (ProductReferenceRequest reference : productRequest.getReferences()) {

						if (nbrAppendedEntities.equals(0)) {
							try {
								insertReferencesBuffer.append(INSERT_REFERENCES);
								insertReferencesBuffer.newLine();
							} catch (IOException e) {
								throw new PackagerException(new ErrorCode("0.1.1.2.6"), new Object[] { getFinalName() },
										e);
							}
						} else {
							try {
								insertReferencesBuffer.append(",");
								insertReferencesBuffer.newLine();
							} catch (IOException e) {
								throw new PackagerException(new ErrorCode("0.1.1.2.6"), new Object[] { getFinalName() },
										e);
							}
						}

						try {
							insertReferencesBuffer.append("(");
							insertReferencesBuffer.append("'" + reference.getType() + "'");
							insertReferencesBuffer.append(", ");
							insertReferencesBuffer.append("'" + reference.getValue() + "'");
							insertReferencesBuffer.append(", ");
							insertReferencesBuffer.append(String.valueOf(productInstance.getIdProductInstance()));
							insertReferencesBuffer.append(")");
						} catch (IOException e) {
							throw new PackagerException(new ErrorCode("0.1.1.2.6"), new Object[] { getFinalName() }, e);
						}

						nbrAppendedEntities++;
						if (nbrAppendedEntities.equals(flushFrequency)) {
							this.flush();
						}
					}

					// --- logging percentage advancement

					currentRequestIndex++;
					double rounded = Math.floor((currentRequestIndex / requestsLenght) * 100);

					if (rounded % 10 == 0 && rounded != lastRounded) {

						lastRounded = rounded;

						if (LOGGER.isInfoEnabled()) {
							LOGGER.info(rounded + "% requests imported for the productModel " + productModelKey + ".");
						}
					}
				}

			} while (toIndex < ids.size());
		} catch (NotFoundException e) {
			throw new PackagerException(e);
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
			deleteReferencesBuffer.close();
			insertReferencesBuffer.close();

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

	public void setProductModelService(ProductModelService productModelService) {
		this.productModelService = productModelService;
	}

	public void setProductInstanceService(ProductInstanceService productInstanceService) {
		this.productInstanceService = productInstanceService;
	}

	/**
	 * {@inheritDoc}
	 */
	public void append(PackagerRequest request) throws PackagerException, NotRespectedRulesException {

	}

}
