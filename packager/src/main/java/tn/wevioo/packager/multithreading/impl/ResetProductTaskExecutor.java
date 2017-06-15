package tn.wevioo.packager.multithreading.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.implicit.UnsupportedActionException;
import nordnet.drivers.contract.exceptions.DriverException;
import nordnet.drivers.contract.types.State;
import tn.wevioo.packager.exceptions.RestTemplateException;
import tn.wevioo.packager.service.ProductInstanceService;
import tn.wevioo.packager.service.ProductModelProductDriverPortService;

/**
 * The class ResetProductTaskExecutor allows reseting a ProductInstance into a
 * separated thread, calling the method reset on the given product instance.
 * <p>
 * If available (request not null), the Xml properties are given as parameter of
 * the method reset. Else, null is given.
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
public class ResetProductTaskExecutor extends AbstractProductTaskExecutor {

	/**
	 * {@link ResetProductTaskExecutor}'s Logger.
	 */
	private static final Log LOGGER = LogFactory.getLog(ResetProductTaskExecutor.class);

	/**
	 * The method run performs the reset on the given product.
	 */

	@Autowired
	private ProductModelProductDriverPortService productModelProductDriverPortService;

	@Autowired
	private ProductInstanceService productInstanceService;

	protected void doRun() throws NotFoundException, RestTemplateException {
		try {
			if (this.product.getCurrentState(productModelProductDriverPortService).equals(State.ACTIVE)) {
				try {
					// -- packagerHistory is synchronized because it is used by
					// all products at the same time
					if (this.request == null) {
						synchronized (this.packagerHistory) {
							this.product.reset(null, packagerHistory, webServiceUserService, productInstanceService,
									productModelProductDriverPortService);
						}
					} else {
						synchronized (this.packagerHistory) {
							this.product.reset(request.getProperties(), packagerHistory, webServiceUserService,
									productInstanceService, productModelProductDriverPortService);
						}
					}
				} catch (UnsupportedActionException ex) {
					// If the operation is not supported the process should
					// continue.
					if (LOGGER.isWarnEnabled()) {
						LOGGER.warn(
								"Reset action not supported for product [" + this.product.getIdProductInstance() + "]");
					}
				} catch (Exception ex) {
					this.failureCause = ex;
				}
			}

			synchronized (this.finished) {
				this.finished = true;
			}
		} catch (DriverException ex) {
			this.failureCause = ex;
		}
	}
}