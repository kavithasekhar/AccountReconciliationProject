package com.bp.projects.technology.accountreconciliation.reader;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.bp.projects.technology.accountreconciliation.domain.AccountTransaction;
import com.bp.projects.technology.accountreconciliation.domain.TransactionType;

/**
 * Implementation to parse the given transaction message to transaction object
 * 
 * @author Kavitha
 *
 */
@Component
public class TransactionReaderImpl implements TransactionReader {
	private static final Logger LOGGER = Logger
			.getLogger(TransactionReaderImpl.class.getName());
	private static final String DELIMITER = ",";
	private static final Integer DEFAULT_COLUMN_COUNT = 4;

	@Override
	public AccountTransaction parse(String line, String lineNumber) {

		AccountTransaction txn = null;

		try {
			String[] txnLine = line.split(DELIMITER);
			if (txnLine != null && txnLine.length == DEFAULT_COLUMN_COUNT) {
				String accId1 = txnLine[0];
				String accId2 = txnLine[1];
				String txnTypeCode = txnLine[2];
				String amtExpr = txnLine[3];

				Integer accountId1 = new Integer(accId1.trim());
				Integer accountId2 = new Integer(accId2.trim());
				TransactionType type = TransactionType
						.getTransactionType(txnTypeCode.trim());
				txn = new AccountTransaction(lineNumber, accountId1,
						accountId2, type, amtExpr.trim());

			}
		} catch (ArrayIndexOutOfBoundsException e) {
			LOGGER.log(Level.SEVERE, e.getMessage()
					+ " while parsing the transaction id: " + lineNumber + "\n"
					+ e);
		} catch (NumberFormatException e) {
			LOGGER.log(Level.SEVERE, e.getMessage()
					+ " while parsing the transaction id: " + lineNumber + "\n"
					+ e);
		}
		return txn;
	}
}
