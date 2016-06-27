package com.bp.projects.technology.accountreconciliation.batcher;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.bp.projects.technology.accountreconciliation.domain.AccountTransaction;
import com.bp.projects.technology.accountreconciliation.processor.Task;
import com.bp.projects.technology.accountreconciliation.processor.TaskExecutorService;
import com.bp.projects.technology.accountreconciliation.repository.AccountRepository;
import com.bp.projects.technology.accountreconciliation.repository.BlockingQueueRepository;
import com.bp.projects.technology.accountreconciliation.repository.TransactionRepository;

/**
 * Implementation for creating a batch, starting and shutdown batch execution of
 * transactions
 * 
 * @author Kavitha
 *
 */
@Component
public class TransactionBatcherImpl implements TransactionBatcher {
	private static final Logger LOGGER = Logger
			.getLogger(TransactionBatcherImpl.class.getName());
	private static final String BATCH_SIZE = "transaction.batchprocessing.size";
	private static final String RETRY_LIMIT = "transaction.batchprocessing.retry_limit";

	@Autowired
	private TransactionRepository transactionRepo;

	@Autowired
	private BlockingQueueRepository blockingQueueRepo;

	@Autowired
	private TaskExecutorService taskExecution;

	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private Environment env;

	@Override
	public void createAndExecuteBatch(AccountTransaction txn) {
		transactionRepo.addTransaction(txn);
		if (transactionRepo.getTransactions().size() == Integer.parseInt(env
				.getProperty(BATCH_SIZE))) {
			putToQueue();
		}
	}

	private void putToQueue() {
		boolean putToQueueStatus = false;
		boolean success = false;
		int count = 0;
		List<AccountTransaction> transactions = new ArrayList<AccountTransaction>();
		transactions.addAll(transactionRepo.getTransactions());
		transactionRepo.clearTransactions();
		while (!success
				&& count <= Integer.parseInt(env.getProperty(RETRY_LIMIT))) {
			try {
				count++;
				if(!putToQueueStatus) {
					blockingQueueRepo.putToQueue(transactions);
					putToQueueStatus = true;
				}
				taskExecution.execute(new Task(blockingQueueRepo, accountRepo));
				success = true;
			} catch (InterruptedException e) {
				LOGGER.log(Level.SEVERE, "Problem with Batch Creation and Execution" + e);
			}
		}
		LOGGER.log(Level.FINE, "Successful Batch Creation and Execution");
	}

	@Override
	public void shutdownExecution() {
		if (transactionRepo.getTransactions().size() > 0) {
			putToQueue();
		}
		boolean success = false;
		int count = 0;
		while (!success
				&& count <= Integer.parseInt(env.getProperty(RETRY_LIMIT))) {
			try {
				count++;
				taskExecution.shutdown();
				success = true;
			} catch (InterruptedException e) {
				LOGGER.log(Level.SEVERE, "Problem with ExecutorService Shutdown" + e);
			}
		}
		LOGGER.log(Level.FINE, "Successful ExecutorService Shutdown");
	}
}
