package com.bp.projects.technology.accountreconciliation.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.bp.projects.technology.accountreconciliation.batcher.TransactionBatcher;
import com.bp.projects.technology.accountreconciliation.domain.AccountTransaction;
import com.bp.projects.technology.accountreconciliation.domain.TransactionFile;
import com.bp.projects.technology.accountreconciliation.exception.TxnProcessingException;

/**
 * Class that reads the given file for offline mode, creates a batch of
 * transactions and puts into blocking queue for threads to process
 * 
 * @author Kavitha
 *
 */
@Configuration
@Profile("offline")
public class FileTransactionReader {
	@Autowired
	private TransactionReader reader;

	@Autowired
	private TransactionBatcher batcher;

	private static final Logger LOGGER = Logger
			.getLogger(FileTransactionReader.class.getName());

	private TransactionFile transactionFile;

	private BufferedReader bufferedReader;

	@Autowired
	FileTransactionReader(TransactionFile transactionFile)
			throws FileNotFoundException {
		this.transactionFile = transactionFile;
	}

	public void startReading() {
		String line = null;
		try {
			LOGGER.log(Level.INFO, "Started Processing File: "
					+ transactionFile.getFile().getName());
			bufferedReader = new LineNumberReader(new FileReader(
					this.transactionFile.getFile()));
			BigInteger lineNumber = new BigInteger("0");

			while ((line = bufferedReader.readLine()) != null) {
				lineNumber = lineNumber.add(new BigInteger("1"));
				AccountTransaction txn = reader.parse(line,
						lineNumber.toString());
				if (txn != null) {
					batcher.createAndExecuteBatch(txn);
				}
			}
			batcher.shutdownExecution();
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, e.getMessage() + e);
			throw new TxnProcessingException(e.getMessage());
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage() + e);
			throw new TxnProcessingException(e.getMessage());
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, e.getMessage() + e);
					throw new TxnProcessingException(e.getMessage());
				}
			}
		}
	}

}
