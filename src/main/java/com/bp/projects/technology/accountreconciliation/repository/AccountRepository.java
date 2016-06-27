package com.bp.projects.technology.accountreconciliation.repository;

import java.util.List;

import com.bp.projects.technology.accountreconciliation.domain.Account;

/**
 * Repository class that handles the accounts
 * 
 * @author Kavitha
 *
 */
public interface AccountRepository {
	/**
	 * Get all accounts having accountId and latest balance
	 * 
	 * @return
	 */
	List<Account> getAccounts();
	
	/**
	 * Adds a new account to repository, if already exists, then updates the account
	 * Returns true or false depending on successful insertion or update
	 * 
	 * @param accountId
	 * @param oldAccount
	 * @param newAccount
	 * @return
	 */
	boolean addAccount(Integer accountId, Account oldAccount, Account newAccount);
	
	/**
	 * Get Account object for the given accountId key
	 * 
	 * @param accountId
	 * @return
	 */
	Account getAccountForId(Integer accountId);
	
	/**
	 * Prints the latest account balances for all accounts in the repository
	 */
	void showAccountBalance();

	/**
	 * Clears the Account Map Collection
	 */
	void clearAccountsMap();
}
