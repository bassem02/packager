package tn.wevioo.multithreading;

import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import tn.wevioo.entities.PackagerActionHistory;
import tn.wevioo.entities.ProductInstance;
import tn.wevioo.model.request.ProductRequest;

/**
 * The interface ProductTaskExecutor defines all the methods required to perform
 * a task on a product instance.
 */
public interface ProductTaskExecutor extends TaskExecutor {

	/**
	 * The method initialize allows setting all the resources required to
	 * perform this product task. Depending on the implementation, some inner
	 * statements could also be performed in order to prepare the task
	 * execution.
	 * 
	 * @param product
	 *            The product on which the task will be performed. Cannot be
	 *            null.
	 * @param request
	 *            The product request which has been asked on the given product.
	 *            This can contain the different elements required to perform
	 *            the current task (Xml properties, destination model, ...).
	 *            Please verify the chosen implementation in order to be aware
	 *            of the required values.
	 *            <p>
	 *            In each case, the given product id must equals those of the
	 *            given product.
	 *            <p>
	 *            This parameter can be null if no parameter is required and
	 *            needed.
	 * @param packagerHistory
	 *            Packager action history to complete with the current product
	 *            action. Cannot be null. This argument is supposed to work in
	 *            an in/out way : the method will not return the packager action
	 *            history, but method caller should be able to use
	 *            modifications.
	 * @param ignoreProviderExcetion
	 *            If true and if an exception occurs while trying to delete the
	 *            packager from provider systems, the exception is ignored and
	 *            the packager history is correctly managed. This option should
	 *            be used only in a particular way, in order to clean packager
	 *            database when it is impossible to delete product from provider
	 *            systems.
	 *            <p>
	 *            Due to its huge consequence, only selected actions uses this
	 *            parameter. Please refer to each documentation.
	 * @throws NotRespectedRulesException
	 *             Thrown if the given product and request are not correctly
	 *             filled.
	 */
	public void initialize(ProductInstance product, final ProductRequest request, PackagerActionHistory packagerHistory,
			Boolean ignoreProviderExcetion) throws NotRespectedRulesException;
}