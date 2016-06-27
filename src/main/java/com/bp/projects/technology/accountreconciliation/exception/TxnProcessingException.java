package com.bp.projects.technology.accountreconciliation.exception;

/**
 * Wrapper Exception class for application failures
 * 
 * @author Kavitha
 *
 */
public class TxnProcessingException extends RuntimeException {
	
	public TxnProcessingException(String message) {
		super(message);
	}
}
