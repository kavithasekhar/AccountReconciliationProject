package com.bp.projects.technology.accountreconciliation.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * Wrapper class for ThreadPoolTaskExecutor
 * 
 * @author Kavitha
 *
 */
@Component
public class TaskExecutorService {
	@Autowired
	ThreadPoolTaskExecutor threadPoolTaskExecutor;

	/**
	 * Executes the runnable task
	 * @see java.util.concurrent.ThreadPoolExecutor#execute(Runnable task)
	 * 
	 * @param runnableTask
	 * @throws InterruptedException
	 */
	public void execute(Runnable runnableTask) throws InterruptedException {
		threadPoolTaskExecutor.execute(runnableTask);
	}

	/**
	 * Perform a shutdown on the underlying ExecutorService.
	 * @see java.util.concurrent.ExecutorService#shutdown()
	 * 
	 * @throws InterruptedException
	 */
	public void shutdown() throws InterruptedException {
		threadPoolTaskExecutor.shutdown();
	}

}