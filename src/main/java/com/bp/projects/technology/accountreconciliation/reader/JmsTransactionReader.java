package com.bp.projects.technology.accountreconciliation.reader;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.bp.projects.technology.accountreconciliation.batcher.TransactionBatcher;
import com.bp.projects.technology.accountreconciliation.domain.AccountTransaction;

/**
 * Class that listens for messages from messaging queue configured, creates a
 * batch of transactions and puts into blocking queue for threads to process
 * 
 * @author Kavitha
 *
 */
@Configuration
@Profile("online")
public class JmsTransactionReader implements MessageListener {
	private static final Logger LOGGER = Logger
			.getLogger(JmsTransactionReader.class.getName());

	@Autowired
	private TransactionReader reader;

	@Autowired
	private TransactionBatcher batcher;

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			TextMessage tm = (TextMessage) message;
			String id = null;
			try {
				id = tm.getJMSCorrelationID();
				String msg = tm.getText();
				LOGGER.log(Level.INFO, "Received Account Transaction Id: " + id);
				AccountTransaction txn = reader.parse(msg, id);
				if (txn != null) {
					batcher.createAndExecuteBatch(txn);
				}
			} catch (JMSException e) {
				LOGGER.log(Level.SEVERE, "Failed to receive the message: " + id);
			}
		} else {
			LOGGER.log(Level.SEVERE, "Received a invalid text message");
		}
	}

}
