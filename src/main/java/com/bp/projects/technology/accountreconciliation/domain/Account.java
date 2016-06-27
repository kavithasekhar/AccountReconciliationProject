package com.bp.projects.technology.accountreconciliation.domain;

import java.math.BigDecimal;

/**
 * Domain Object for Account that holds accountId and balance as member variables
 * 
 * @author Kavitha
 *
 */
public class Account {
	private static final BigDecimal INITAL_BALANCE = new BigDecimal(0);
	private Integer accountId;
	private BigDecimal balance;

	public Account(Integer accountId, BigDecimal balance) {
		this.accountId = accountId;
		this.balance = balance;
	}
	
	public Account(Integer accountId) {
		this.accountId = accountId;
		this.balance = INITAL_BALANCE;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n").append("AccountId: ").append(accountId).append(", Balance: ")
				.append(balance).append("\n");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		return true;
	}
}
