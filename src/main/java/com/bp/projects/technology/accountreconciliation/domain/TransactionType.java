package com.bp.projects.technology.accountreconciliation.domain;

import java.util.Objects;

/**
 * Enum for different transaction types
 * 
 * @author Kavitha
 *
 */
public enum TransactionType {
	DEBIT("D"), CREDIT("C"), UNKNOWN("NA");
	
	private String code;
	
	TransactionType(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public static TransactionType getTransactionType(String code) {
		Objects.requireNonNull(code);
		for (TransactionType type : TransactionType.values()) {
			if(type.getCode().equals(code.toUpperCase())) {
				return type;
			}
		}
		return UNKNOWN;
	}
}
