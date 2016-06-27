package com.bp.projects.technology.accountreconciliation.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import com.bp.projects.technology.accountreconciliation.domain.Account;

/**
 * Some or all of the CRUD implementations on account domain object
 * 
 * @author Kavitha
 *
 */
@Repository
public class AccountRepositoryImpl implements AccountRepository {
	private static Logger LOGGER = Logger.getLogger(AccountRepositoryImpl.class
			.getName());
	private Map<Integer, Account> accountsMap = new ConcurrentHashMap<Integer, Account>();

	@Override
	public List<Account> getAccounts() {
		List<Account> accounts = new ArrayList<Account>();
		Set<Integer> accountIds = accountsMap.keySet();
		for (Integer integer : accountIds) {
			Account account = accountsMap.get(integer);
			accounts.add(account);
		}
		return accounts;
	}

	@Override
	public boolean addAccount(Integer accountId, Account oldAccount, Account newAccount) {
		if(oldAccount == null) {
			Account previousValue = accountsMap.putIfAbsent(accountId, newAccount);
			return (previousValue == null);
		}
		return accountsMap.replace(accountId, oldAccount, newAccount);
	}

	@Override
	public Account getAccountForId(Integer accountId) {
		return accountsMap.get(accountId);
	}

	@Override
	public void showAccountBalance() {
		LOGGER.log(Level.INFO, getAccounts().toString());
	}
	
	public void clearAccountsMap() {
		accountsMap.clear();
	}
}
