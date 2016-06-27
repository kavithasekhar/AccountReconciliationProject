package com.bp.projects.technology.accountreconciliation.repository;

import java.util.List;

import com.bp.projects.technology.accountreconciliation.domain.AccountTransaction;

/**
 * Repository Interface for handling transactions
 * 
 * @author Kavitha
 *
 */
public interface TransactionRepository {
	/**
	 * Adds the given transactions to the repository
	 * 
	 * @param txn
	 */
	void addTransaction(AccountTransaction txn);
	
	/**
	 * Gets all transactions from the repository
	 * @return
	 */
	List<AccountTransaction> getTransactions();
	
	/**
	 * Clears all transactions from the repository
	 */
	void clearTransactions();

}
