package com.bp.projects.technology.accountreconciliation.domain;

/**
 * Domain Object for Transaction that holds 2 accountId, transaction type and
 * the amount being transacted
 * 
 * @author Kavitha
 *
 */
public class AccountTransaction {

	private String id;
	private Integer accountId1;
	private Integer accountId2;
	private TransactionType type;
	private String amount;

	public AccountTransaction(String id, Integer accountId1, Integer accountId2,
			TransactionType type, String amount) {
		this.id = id;
		this.accountId1 = accountId1;
		this.accountId2 = accountId2;
		this.type = type;
		this.amount = amount;
	}
	
	public String getId() {
		return id;
	}

	public Integer getAccountId1() {
		return accountId1;
	}

	public Integer getAccountId2() {
		return accountId2;
	}

	public TransactionType getType() {
		return type;
	}

	public String getAmount() {
		return amount;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n").append("Transaction Id: ").append(id);
		builder.append(", ").append("AccountId1: ").append(accountId1)
				.append(", ").append("AccountId2: ").append(accountId2)
				.append(", ").append("TransactionType: ").append(type)
				.append(", ").append("AmountExpr: ").append(amount)
				.append("\n");
		return builder.toString();
	}
}
