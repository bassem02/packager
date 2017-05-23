package tn.wevioo.multithreading.impl;

import org.springframework.stereotype.Component;

import tn.wevioo.multithreading.TaskController;

/**
 * The class BatchController provides a full implementation of a task
 * controller, specifically dedicated to batch execution.
 */

@Component
public class BatchController implements TaskController {

	/**
	 * The attribute nbrTaskStarted contains the number of tasks have started.
	 */
	private Integer nbrTaskStarted = 0;

	/**
	 * The attribute nbrTaskEnded contains the number of tasks which have ended.
	 */
	private Integer nbrTaskEnded = 0;

	/**
	 * The method startNewTask allows warning the controller that a new task is
	 * starting and should be waited for before continuing the execution.
	 */
	public void startNewTask() {
		synchronized (this.nbrTaskStarted) {
			this.nbrTaskStarted++;
		}
	}

	/**
	 * The method endTask allows warning the controller that a task has ended.
	 */
	public void endNewTask() {
		synchronized (this.nbrTaskEnded) {
			this.nbrTaskEnded++;
		}
	}

	/**
	 * The method areTaskFinished returns true if all tasks which have started
	 * have now ended. False else.
	 * 
	 * @return true if all tasks which have started have now ended. False else.
	 */
	public boolean areTaskFinished() {
		synchronized (this.nbrTaskStarted) {
			synchronized (this.nbrTaskEnded) {
				return this.nbrTaskStarted.equals(this.nbrTaskEnded);
			}
		}
	}

	/**
	 * The method getNbrTasksToEnd returns the number of task which are
	 * currently running and are not finished yet.
	 * 
	 * @return returns the number of task which are currently running and are
	 *         not finished yet.
	 */
	public Integer getNbrTasksToEnd() {
		synchronized (this.nbrTaskStarted) {
			synchronized (this.nbrTaskEnded) {
				return this.nbrTaskStarted - this.nbrTaskEnded;
			}
		}
	}

}
