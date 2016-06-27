package com.bp.projects.technology.accountreconciliation.processor;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sourceforge.jeval.EvaluationException;

import com.bp.projects.technology.accountreconciliation.domain.Account;
import com.bp.projects.technology.accountreconciliation.domain.AccountTransaction;
import com.bp.projects.technology.accountreconciliation.domain.TransactionType;
import com.bp.projects.technology.accountreconciliation.exception.TxnProcessingException;
import com.bp.projects.technology.accountreconciliation.repository.AccountRepository;
import com.bp.projects.technology.accountreconciliation.repository.BlockingQueueRepository;
import com.bp.projects.technology.accountreconciliation.util.ExpressionUtils;

/**
 * 
 * Task to be performed by multiple threads. Take the list of transactions from
 * the blocking queue, process all those transactions and accordingly add or
 * update the Account balances for the accounts involved in those processed
 * transactions
 * 
 * @author Kavitha
 *
 */
public class Task implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(Task.class.getName());

	private BlockingQueueRepository blockingQueueRepo;

	private AccountRepository accountRepo;

	public Task(BlockingQueueRepository blockingQueueRepo,
			AccountRepository accountRepo) {
		this.blockingQueueRepo = blockingQueueRepo;
		this.accountRepo = accountRepo;
	}

	@Override
	public void run() {
		LOGGER.log(Level.FINE, "**********************");
		LOGGER.log(Level.FINE, Thread.currentThread().getName());

		List<AccountTransaction> transactions = null;
		try {
			transactions = blockingQueueRepo.takeFromQueue();
		} catch (InterruptedException e) {
			StringBuilder builder = new StringBuilder();
			builder.append("Unable to obtain transactions from Blocking Queue")
					.append("\n").append(e);
			LOGGER.log(Level.SEVERE, builder.toString());
			throw new TxnProcessingException(builder.toString());
		}

		if (transactions != null) {
			for (AccountTransaction transaction : transactions) {
				LOGGER.log(Level.FINE,
						Thread.currentThread().getName()
								+ " processing account transaction id: "
								+ transaction.getId());
				BigDecimal amount = null;
				try {
					amount = ExpressionUtils.resolveExpressionValue(transaction
							.getAmount());
				} catch (EvaluationException e) {
					StringBuilder builder = new StringBuilder();
					builder.append(
							"Amount Expression: " + transaction
							.getAmount() + " cannot be evaluated for Transaction Id: ")
							.append(transaction.getId()).append("\n").append(e);
					LOGGER.log(Level.SEVERE, builder.toString());
				}

				if (amount != null) {
					boolean isUpdatedAccount1 = false;
					boolean isUpdatedAccount2 = false;
					do {
						Account oldAccount1 = accountRepo
								.getAccountForId(transaction.getAccountId1());
						Account newAccount1 = new Account(
								transaction.getAccountId1());
						if (oldAccount1 != null) {
							newAccount1 = new Account(
									oldAccount1.getAccountId(),
									oldAccount1.getBalance());
						}

						Account oldAccount2 = accountRepo
								.getAccountForId(transaction.getAccountId2());
						Account newAccount2 = new Account(
								transaction.getAccountId2());
						if (oldAccount2 != null) {
							newAccount2 = new Account(
									oldAccount2.getAccountId(),
									oldAccount2.getBalance());
						}

						if (TransactionType.DEBIT.equals(transaction.getType())) {
							newAccount1 = new Account(
									newAccount1.getAccountId(), newAccount1
											.getBalance().subtract(amount));
							newAccount2 = new Account(
									newAccount2.getAccountId(), newAccount2
											.getBalance().add(amount));
						} else if (TransactionType.CREDIT.equals(transaction
								.getType())) {
							newAccount1 = new Account(
									newAccount1.getAccountId(), newAccount1
											.getBalance().add(amount));
							newAccount2 = new Account(
									newAccount2.getAccountId(), newAccount2
											.getBalance().subtract(amount));
						}

						if (!isUpdatedAccount1) {
							isUpdatedAccount1 = accountRepo.addAccount(
									newAccount1.getAccountId(), oldAccount1,
									newAccount1);
						}
						if (!isUpdatedAccount2) {
							isUpdatedAccount2 = accountRepo.addAccount(
									newAccount2.getAccountId(), oldAccount2,
									newAccount2);
						}
					} while (!isUpdatedAccount1 || !isUpdatedAccount2);
				}
			}
		}
	}
}
