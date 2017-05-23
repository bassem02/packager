package tn.wevioo.multithreading;

import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import tn.wevioo.entities.PackagerActionHistory;
import tn.wevioo.entities.PackagerInstance;
import tn.wevioo.model.request.PackagerRequest;

/**
 * The interface PackagerTaskExecutor defines all the methods required to
 * perform a task on a packager instance.
 * 
 * @author THUGUERRE
 * @since 2.0.0
 */
public interface PackagerTaskExecutor extends TaskExecutor {

	/**
	 * The method initialize allows setting all the resources required to
	 * perform this packager task. Depending on the implementation, some inner
	 * statements could also be performed in order to prepare the task
	 * execution.
	 * 
	 * @param packager
	 *            The packager on which the current task will be performed.
	 *            Cannot be null.
	 * @param request
	 *            The packager request which has been asked on the given
	 *            packager. This can contain the different elements required to
	 *            perform the current task (Xml properties, destination model,
	 *            ...).
	 *            <p>
	 *            Please verify the chosen implementation in order to be aware
	 *            of the required values. In each case, the given retailer
	 *            packager identifier must equals those of the given packager.
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
	 *             Thrown if the given packager and request are not correctly
	 *             filled.
	 */
	public void initialize(PackagerInstance packager, PackagerRequest request, PackagerActionHistory packagerHistory,
			Boolean ignoreProviderExcetion) throws NotRespectedRulesException;
}