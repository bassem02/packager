package tn.wevioo.packager.multithreading.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import tn.wevioo.packager.controller.BackOfficePackagerManagementController;
import tn.wevioo.packager.entities.WebServiceUser;
import tn.wevioo.packager.model.request.PackagerRequest;
import tn.wevioo.packager.multithreading.PackagerRequestBatchTaskExecutor;
import tn.wevioo.packager.multithreading.TaskController;
import tn.wevioo.packager.service.WebServiceUserService;

/**
 * The class ImportExistingPackagerTaskExecutor implements the packager request
 * batch task executor required to import an existing packager instance from a
 * packager request.
 */

@Component
public class ImportExistingPackagerTaskExecutor implements PackagerRequestBatchTaskExecutor, Runnable {

	/**
	 * {@link ImportExistingPackagerTaskExecutor}'s Logger.
	 */
	private static final Log LOGGER = LogFactory.getLog(ImportExistingPackagerTaskExecutor.class);

	/**
	 * This attribute contains the controller to warn when the action is
	 * starting or ending.
	 */
	private TaskController controller;

	/**
	 * The attribute request contains the request to perform.
	 */
	private PackagerRequest request;

	/**
	 * User authenticated at the task initialization.
	 */
	private WebServiceUser authenticatedUser = null;

	@Autowired
	private WebServiceUserService webServiceUserService;

	@Autowired
	private BackOfficePackagerManagementController backOfficePackagerManagementController;

	/**
	 * {@inheritDoc}
	 */
	public void initialize(TaskController controller, PackagerRequest request) {
		if (controller == null) {
			throw new NullException(NullCases.NULL, "controller");
		}
		if (request == null) {
			throw new NullException(NullCases.NULL, "request");
		}
		this.controller = controller;
		authenticatedUser = webServiceUserService.getWebserviceUser();
		this.request = request;
		controller.startNewTask();
	}

	/**
	 * The method run applies the targeted action.
	 */
	public void run() {
		try {
			backOfficePackagerManagementController.importExistingPackager(request);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Import of request [" + request.getRetailerPackagerId() + "] has failed.", e);
			}
		} finally {
			this.controller.endNewTask();
		}
	}

}
