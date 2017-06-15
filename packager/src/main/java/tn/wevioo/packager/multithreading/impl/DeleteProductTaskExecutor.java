package tn.wevioo.packager.multithreading.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nordnet.architecture.exceptions.implicit.UnsupportedActionException;
import tn.wevioo.packager.service.ProductInstanceService;
import tn.wevioo.packager.service.ProductModelProductDriverPortService;

/**
 * The class DeleteProductTaskExecutor allows deleting a ProductInstance into a
 * separated thread, calling the method delete on the given product instance.
 * <p>
 * If available (request not null), the Xml properties are given as parameter of
 * the method delete. Else, null is given.
 * <p>
 * This class is fully designed to be multithreaded in an upper task executor.
 * Please refer to the documentation of the class AbstractProductTaskExecutor to
 * be aware of the method execute's behavior.
 * <p>
 * This class is fully designed to be multithreaded in an upper task executor.
 * It must NOT be defined as a singleton in the Spring configuration, but as
 * scope prototype.
 */

@Component
public class DeleteProductTaskExecutor extends AbstractProductTaskExecutor {

	/**
	 * {@link DeleteProductTaskExecutor}'s Logger.
	 */
	private static final Log LOGGER = LogFactory.getLog(DeleteProductTaskExecutor.class);

	/**
	 * The method run performs the deletion on the given product.
	 */

	@Autowired
	private ProductInstanceService productInstanceService;

	@Autowired
	private ProductModelProductDriverPortService productModelProductDriverPortService;

	protected void doRun() {
		try {
			// -- packagerHistory is synchronized because it is used by all
			// products at the same time
			if (this.request == null) {
				synchronized (this.packagerHistory) {
					this.product.delete(null, packagerHistory, ignoreProviderException, webServiceUserService,
							productInstanceService, productModelProductDriverPortService);
				}
			} else {
				synchronized (this.packagerHistory) {
					this.product.delete(request.getProperties(), packagerHistory, ignoreProviderException,
							webServiceUserService, productInstanceService, productModelProductDriverPortService);
				}
			}
		} catch (UnsupportedActionException ex) {
			// If the operation is not supported the process should continue.
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn("Delete action not supported for product [" + this.product.getIdProductInstance() + "]");
			}
		} catch (Exception ex) {
			this.failureCause = ex;
		}

		synchronized (this.finished) {
			this.finished = true;
		}
	}
}