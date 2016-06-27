package com.bp.projects.technology.accountreconciliation.reader;

import com.bp.projects.technology.accountreconciliation.domain.AccountTransaction;

/**
 * Interface for handling the transaction message of type string
 * 
 * @author Kavitha
 *
 */
public interface TransactionReader {

	/**
	 * Parse the given string to AccountTransaction object
	 * 
	 * @param line
	 * @param lineNumber
	 * @return
	 */
	AccountTransaction parse(String line, String lineNumber);

}
