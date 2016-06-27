package com.bp.projects.technology.accountreconciliation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.bp.projects.technology.accountreconciliation.reader.FileTransactionReader;
import com.bp.projects.technology.accountreconciliation.repository.AccountRepository;

/**
 * Service class for offline application mode
 * 
 * @author Kavitha
 *
 */
@Configuration
@Profile("offline")
public class AccountTxService {

	@Autowired
	FileTransactionReader fileTransactionReader;

	@Autowired
	AccountRepository accountRepo;

	/**
	 * Entry method for offline application mode. This is invoked by Spring's
	 * MethodInvokingFactoryBean
	 * 
	 * @see org.springframework.beans.factory.config.MethodInvokingFactoryBean
	 * 
	 */
	public void startProcessing() {
		fileTransactionReader.startReading();
		accountRepo.showAccountBalance();
	}

}
