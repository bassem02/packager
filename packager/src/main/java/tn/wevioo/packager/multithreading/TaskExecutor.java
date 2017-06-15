package tn.wevioo.packager.multithreading;

import java.util.List;

import tn.wevioo.packager.exceptions.PackagerException;

/**
 * The interface TaskExecutor defines all the methods required to execute a
 * certain task, whatever its final implementation or aim. It has been designed
 * in order to provide a base for multithreading actions.
 */
public interface TaskExecutor {

	/**
	 * The method execute performs the action the current task executor has been
	 * designed for. Depending on the task executor implementation, this method
	 * can return the result of the action or throw the failure cause, or not.
	 * Please refer to the chosen implementation's documentation.
	 * 
	 * @return The result of the action, if available.
	 * 
	 * @throws PackagerException
	 *             Thrown if an error occurs while trying to perform the action.
	 */
	public List<Object> execute() throws PackagerException;

	/**
	 * The method isTaskFinished returns true if the current task has ended, and
	 * if its results can be read by the user. Else, false.
	 * 
	 * @return <code>true</code> if the current task has ended, and if its
	 *         results can be read by the user. Else, <code>false</code>.
	 */
	public Boolean isTaskFinished();

	/**
	 * The method getFailureCauses returns all the causes (exception) which has
	 * caused the failure of the current task. In a major cases, this list will
	 * contain only one element. However, if a task performs several other
	 * tasks, this method will return the concatenation of all the causes.
	 * 
	 * @return All the failure causes.
	 */
	public List<Throwable> getFailureCauses();

	/**
	 * The method getResult returns the result of the performed action. This
	 * result is the same of the one returned by the method execute. Depending
	 * on the implementation, the method execute could give back control without
	 * returning the result. This method getResult is consequently the only way
	 * to access it.
	 * <p>
	 * Depending on the implementation, this method can return the null value.
	 * <p>
	 * Moreover, if the action is not yet finished or if the action returns
	 * void, this method could throw an UnsupportedActionException. Please refer
	 * to the documentation of your implementation to have further details.
	 * 
	 * @return All the results of the action.
	 */
	public List<Object> getResult();
}