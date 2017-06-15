package tn.wevioo.packager.multithreading;

import tn.wevioo.packager.model.request.PackagerRequest;

/**
 * The interface BatchTaskExecutor defines all the methods required to implement
 * an auto-executable task executor, dedicated to batch execution.
 */
public interface PackagerRequestBatchTaskExecutor {

	/**
	 * The method initialize allows setting all required parameters to the task
	 * executor.
	 * 
	 * @param controller
	 *            Controller to warn. Cannot be null.
	 * @param request
	 *            Request to perform. Cannot be null.
	 */
	public void initialize(TaskController controller, PackagerRequest request);

}
