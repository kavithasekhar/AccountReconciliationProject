package com.bp.projects.technology.accountreconciliation.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bp.projects.technology.accountreconciliation.domain.AccountTransaction;

/**
 * Implementation for handling some or all of the CRUD operations on Transactions
 * 
 * @author Kavitha
 *
 */
@Repository
public class TransactionRepositoryImpl implements TransactionRepository {
	
	private List<AccountTransaction> transactions = new ArrayList<AccountTransaction>();

	@Override
	public void addTransaction(AccountTransaction txn) {
		transactions.add(txn);
	}

	@Override
	public List<AccountTransaction> getTransactions() {
		return transactions;
	}

	public void clearTransactions() {
		transactions.clear();
	}

}
