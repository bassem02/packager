package tn.wevioo.packager.multithreading.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.utils.ErrorCode;
import tn.wevioo.packager.entities.PackagerActionHistory;
import tn.wevioo.packager.entities.PackagerInstance;
import tn.wevioo.packager.entities.ProductInstance;
import tn.wevioo.packager.exceptions.PackagerException;
import tn.wevioo.packager.model.request.PackagerRequest;
import tn.wevioo.packager.model.request.ProductRequest;
import tn.wevioo.packager.multithreading.PackagerTaskExecutor;
import tn.wevioo.packager.multithreading.ProductTaskExecutor;

/**
 * The class MultiProductsPackagerTaskExecutor allows performing a certain
 * action on all the products the initialized packager owns.
 * <p>
 * The same action, performed using the same product task executor
 * implementation, will be applied on all the products. Indeed, during its
 * initialization, for each product, this packager task executor will retrieve
 * the same Spring bean (using the attribute productTaskExecutorBeanName's
 * value) and will initialize it with the current product.
 * <p>
 * In this implementation, the method execute implements convenience actions in
 * order to make its use easier: the method will end only when all the inner
 * product task executors will have finished to run, the method will throw a
 * PackagerException if at least one of the product task executors has failed.
 * <p>
 * This implementation must NOT be configured as a singleton in Spring
 * configuration, but as prototype scope.
 */

@Component
public class MultiProductsPackagerTaskExecutor implements PackagerTaskExecutor {

	/**
	 * {@link MultiProductsPackagerTaskExecutor}'s Logger.
	 */
	private static final Log LOGGER = LogFactory.getLog(MultiProductsPackagerTaskExecutor.class);

	/**
	 * The attribute {@link #DEFAULT_TIME_TO_WAIT} defines the time to wait for
	 * each Thread.sleep between each termination verification.
	 */
	private static final long DEFAULT_TIME_TO_WAIT = 50;

	/**
	 * This attribute contains the name of the Spring bean to use to instantiate
	 * a new product task executor, and to initialize it with one of the
	 * products the given packager owns.
	 */
	private String productTaskExecutorBeanName;

	/**
	 * This attribute contains all the product task executors which are used
	 * inside the method execute, in order to perform one task on one product.
	 */
	private List<ProductTaskExecutor> productTaskExecutors;

	/**
	 * {@inheritDoc}
	 */
	public void initialize(PackagerInstance packager, PackagerRequest request, PackagerActionHistory packagerHistory,
			Boolean ignoreProviderException, ProductTaskExecutor productTaskExecutor)
			throws NotRespectedRulesException {

		if (request != null && !packager.getRetailerPackagerId().equals(request.getRetailerPackagerId())) {
			throw new NotRespectedRulesException(new ErrorCode("1.2.1.1.8"), "packager");
		}

		this.productTaskExecutors = new ArrayList<ProductTaskExecutor>();

		if (packager.getProducts() != null) {
			for (ProductInstance product : packager.getProducts()) {
				// ProductTaskExecutor pte = (ProductTaskExecutor)
				// PackagerLogicTierBeanFactory.getInstance()
				// .getBean(this.productTaskExecutorBeanName);

				productTaskExecutor.initialize(product,
						this.findProductRequest(request, (long) product.getIdProductInstance()), packagerHistory,
						ignoreProviderException);
				this.productTaskExecutors.add(productTaskExecutor);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Object> execute() throws PackagerException {
		for (ProductTaskExecutor pte : this.productTaskExecutors) {
			pte.execute();
		}

		while (!this.isTaskFinished()) {
			try {
				Thread.sleep(DEFAULT_TIME_TO_WAIT);
			} catch (InterruptedException ex) {
				throw new PackagerException(new ErrorCode("0.1.4.1.2"), ex);
			}
		}

		if (this.getFailureCauses().size() > 0) {
			throw new PackagerException(new ErrorCode("1.2.2.16"), getFailureCauses());
		}

		return getResult();
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean isTaskFinished() {
		synchronized (this.productTaskExecutors) {
			for (ProductTaskExecutor pte : this.productTaskExecutors) {
				if (!pte.isTaskFinished()) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * The method findProductRequest returns the product request corresponding
	 * to the given product identifier.
	 * <p>
	 * If the given request is null, the method returns null. If no product
	 * request corresponds, the method returns null.
	 * 
	 * @param request
	 *            The packager request on which looking for the corresponding
	 *            product request. If null, the method returns null.
	 * @param productId
	 *            The product identifier for which looking for the product
	 *            request. Cannot be null.
	 * 
	 * @return The product request corresponding to the given product
	 *         identifier.
	 */
	protected ProductRequest findProductRequest(PackagerRequest request, Long productId) {
		if (request == null) {
			return null;
		}

		for (ProductRequest productRequest : request.getProducts()) {
			if (productRequest.getProductId().equals(productId)) {
				return productRequest;
			}
		}

		return null;
	}

	public void setProductTaskExecutorBeanName(String productTaskExecutorBeanName) {
		this.productTaskExecutorBeanName = productTaskExecutorBeanName;
	}

	public String getProductTaskExecutorBeanName() {
		return this.productTaskExecutorBeanName;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Throwable> getFailureCauses() {
		List<Throwable> failureCauses = new ArrayList<Throwable>();

		for (ProductTaskExecutor pte : this.productTaskExecutors) {
			failureCauses.addAll(pte.getFailureCauses());
		}

		return failureCauses;
	}

	/**
	 * {@inheritDoc}. This method returns all the results from the products that
	 * don't throw UnsupportedOperationException.
	 */
	public List<Object> getResult() {
		List<Object> results = new ArrayList<Object>();

		List<Object> result = null;
		for (ProductTaskExecutor pte : this.productTaskExecutors) {
			try {
				result = pte.getResult();
				results.addAll(result);
			} catch (UnsupportedOperationException ex) {

				if (LOGGER.isWarnEnabled()) {
					LOGGER.warn("This product task executor does not support the getResult method.");
				}
			}
		}

		return results;
	}

	@Override
	public void initialize(PackagerInstance packager, PackagerRequest request, PackagerActionHistory packagerHistory,
			Boolean ignoreProviderExcetion) throws NotRespectedRulesException {
		// TODO Auto-generated method stub

	}
}