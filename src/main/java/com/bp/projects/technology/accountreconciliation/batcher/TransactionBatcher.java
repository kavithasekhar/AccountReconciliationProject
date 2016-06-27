package com.bp.projects.technology.accountreconciliation.batcher;

import com.bp.projects.technology.accountreconciliation.domain.AccountTransaction;

/**
 * Interface for handling the batch execution of transactions
 * 
 * @author Kavitha
 *
 */
public interface TransactionBatcher {

	/**
	 * Create a batch of transactions and puts into blocking queue for threads
	 * to process
	 * 
	 * @param txn
	 */
	void createAndExecuteBatch(AccountTransaction txn);

	/**
	 * Shutdown the thread executor service once after all threads completes
	 * their executions
	 */
	void shutdownExecution();

}
