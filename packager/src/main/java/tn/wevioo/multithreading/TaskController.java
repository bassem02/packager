package tn.wevioo.multithreading;

/**
 * The interface TaskController defines all the methods required to control a
 * set of tasks which are performed in a multithreaded way.
 */
public interface TaskController {

	/**
	 * The method startNewTask allows warning the controller that a new task is
	 * starting and should be waited for before continuing the execution.
	 */
	public void startNewTask();

	/**
	 * The method endTask allows warning the controller that a task has ended.
	 */
	public void endNewTask();

	/**
	 * The method areTaskFinished returns true if all tasks which have started
	 * have now ended. False else.
	 * 
	 * @return true if all tasks which have started have now ended. False else.
	 */
	public boolean areTaskFinished();

	/**
	 * The method getNbrTasksToEnd returns the number of task which are
	 * currently running and are not finished yet.
	 * 
	 * @return returns the number of task which are currently running and are
	 *         not finished yet.
	 */
	public Integer getNbrTasksToEnd();

}
