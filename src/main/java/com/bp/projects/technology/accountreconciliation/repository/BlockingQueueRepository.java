package com.bp.projects.technology.accountreconciliation.repository;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.bp.projects.technology.accountreconciliation.domain.AccountTransaction;

/**
 * Repository interface for handling blocking queues
 * 
 * @author Kavitha
 *
 */
public interface BlockingQueueRepository {

	/**
	 * Add a list of transactions to the blocking queue
	 * 
	 * @see java.util.concurrent.BlockingQueue#put(E e)
	 * @param transactions
	 * @throws InterruptedException
	 */
	void putToQueue(List<AccountTransaction> transactions) throws InterruptedException;

	/**
	 * Removes the head of the blocking queue
	 * 
	 * @see java.util.concurrent.BlockingQueue#take()
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	List<AccountTransaction> takeFromQueue() throws InterruptedException;

	/**
	 * Get the blocking queue
	 * 
	 * @return
	 */
	BlockingQueue<List<AccountTransaction>> getQueue();

}
