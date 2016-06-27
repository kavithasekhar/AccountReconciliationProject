package com.bp.projects.technology.accountreconciliation.repository;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.bp.projects.technology.accountreconciliation.domain.AccountTransaction;

/**
 * Blocking Queue Implementation for inter-thread communication
 * 
 * @author Kavitha
 *
 */
@Repository
public class BlockingQueueRepositoryImpl implements BlockingQueueRepository {
	private BlockingQueue<List<AccountTransaction>> blockingQueue;

	@Autowired
	BlockingQueueRepositoryImpl(
			@Value("${blockingqueue.capacity}") int capacity) {
		blockingQueue = new ArrayBlockingQueue<List<AccountTransaction>>(
				capacity);
	}

	@Override
	public void putToQueue(List<AccountTransaction> transactions)
			throws InterruptedException {
		blockingQueue.put(transactions);
	}

	@Override
	public List<AccountTransaction> takeFromQueue() throws InterruptedException {
		return blockingQueue.take();
	}

	@Override
	public BlockingQueue<List<AccountTransaction>> getQueue() {
		return blockingQueue;
	}

}
