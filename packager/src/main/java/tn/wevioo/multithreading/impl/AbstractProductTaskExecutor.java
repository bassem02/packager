package tn.wevioo.multithreading.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.utils.ErrorCode;
import tn.wevioo.entities.PackagerActionHistory;
import tn.wevioo.entities.ProductInstance;
import tn.wevioo.entities.WebServiceUser;
import tn.wevioo.exceptions.PackagerException;
import tn.wevioo.exceptions.RestTemplateException;
import tn.wevioo.model.request.ProductRequest;
import tn.wevioo.multithreading.ProductTaskExecutor;
import tn.wevioo.service.WebServiceUserService;

/**
 * The class AbstractProductTaskExecutor provides a convenience implementation
 * to perform one threaded action on a single product instance.
 * <p>
 * It must NOT be defined as a singleton in the Spring configuration, but as
 * scope prototype.
 * <p>
 * This implementation is fully designed to be integrated into multithread
 * actions. The method execute only calls the upper method start, inherited from
 * the class Thread. Consequently, the method execute will give back control
 * whereas the true action is still running. The user must ensure to wait the
 * action end, calling the method isTaskFinished at regular interval.
 * <p>
 * Due to its separate-thread pattern, this class does not have an access to the
 * Hibernate session in its method run (inherited from the class Thread).
 * Indeed, the Hibernate session is only available in the scope session, in
 * order to perform safely commit and rollback action. Consequently, this class
 * and all its inheriting children are not designed to perform operation on the
 * packager database. However, if required, some solutions exist.
 */

@Component
public abstract class AbstractProductTaskExecutor implements ProductTaskExecutor, Runnable {

	/**
	 * This attribute has to be used has a flag in order to keep in mind if the
	 * current task has finished to run.
	 */
	protected Boolean finished = false;

	/**
	 * This attribute contains the product instance on which the current task
	 * will be applied.
	 */
	protected ProductInstance product = null;

	/**
	 * This attribute contains the exception which has been throw while
	 * performing the targeted task.
	 */
	protected Throwable failureCause = null;

	/**
	 * This attribute contains the Xml properties to use to perform the action
	 * on the given product instance.
	 */
	protected ProductRequest request = null;

	/**
	 * If true and if an exception occurs while trying to delete the product
	 * from provider systems, the exception is ignored and the product history
	 * is correctly managed. This option should be used only in a particular
	 * way, in order to clean packager database when it is impossible to delete
	 * product from provider systems.
	 * <p>
	 * Due to its huge consequence, only selected actions uses this parameter.
	 * Please refer to each documentation.
	 */
	protected Boolean ignoreProviderException = null;

	/**
	 * The task executor.
	 */
	private ThreadPoolTaskExecutor taskExecutor = null;

	/**
	 * This attribute contains the packager history on which adding the current
	 * product action history.
	 */
	protected PackagerActionHistory packagerHistory;

	/**
	 * User authenticated at the task initialization.
	 */
	private WebServiceUser authenticatedUser = null;

	@Autowired
	WebServiceUserService webServiceUserService;

	/**
	 * {@inheritDoc}
	 */
	public void initialize(ProductInstance product, ProductRequest request, PackagerActionHistory packagerHistory,
			Boolean ignoreProviderException) throws NotRespectedRulesException {

		this.packagerHistory = packagerHistory;
		this.ignoreProviderException = ignoreProviderException;
		this.product = product;
		authenticatedUser = webServiceUserService.getWebserviceUser();
		if (request != null) {
			if (request.getProductId().equals(product.getIdProductInstance())) {
				this.request = request;
			} else {
				throw new NotRespectedRulesException(new ErrorCode("1.2.1.1.8"), "product");
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean isTaskFinished() {
		synchronized (this.finished) {
			return this.finished;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Throwable> getFailureCauses() {
		if (!this.isTaskFinished()) {
			throw new UnsupportedOperationException("The task is not finished yet");
		}

		List<Throwable> result = new ArrayList<Throwable>();

		if (this.failureCause != null) {
			result.add(this.failureCause);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Object> getResult() {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Object> execute() throws PackagerException {
		taskExecutor.execute(this);

		return null;
	}

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	/**
	 * Running the action, by invoking commons initializing steps following with
	 * the true action by method doRun.
	 */
	public void run() {
		// if (this.authenticatedUser != null) {
		// try {
		// PackagerLogicTierBeanFactory.getDefaultPackagerManagement()
		// .authenticate(this.authenticatedUser.getLogin(),
		// this.authenticatedUser.getPassword());
		// } catch (Exception e) {
		// this.failureCause = e;
		// synchronized (this.finished) {
		// this.finished = true;
		// }
		// return;
		// }
		// } else {
		// this.failureCause = new PackagerException(new ErrorCode("0.2.1.1.1"),
		// new Object[] { "authenticatedUser" });
		// synchronized (this.finished) {
		// this.finished = true;
		// }
		// return;
		// }
		try {
			this.doRun();
		} catch (NotFoundException | RestTemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Running the true action of the child thread.
	 * 
	 * @throws RestTemplateException
	 * @throws NotFoundException
	 */
	protected abstract void doRun() throws NotFoundException, RestTemplateException;
}