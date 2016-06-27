package com.bp.projects.technology.accountreconciliation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.bp.projects.technology.accountreconciliation.domain.Account;
import com.bp.projects.technology.accountreconciliation.domain.TransactionFile;
import com.bp.projects.technology.accountreconciliation.reader.FileTransactionReader;
import com.bp.projects.technology.accountreconciliation.repository.AccountRepository;

/**
 * Test the offline application mode of processing the transactions in a given
 * file
 * 
 * @author Kavitha
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@ActiveProfiles(profiles = "offline")
public class FileTransactionReaderIT {

	@Mock
	TransactionFile transactionFile;

	@Autowired
	AccountTxService accountTxService;

	@Autowired
	FileTransactionReader fileTransactionReader;

	@Autowired
	AccountRepository accountRepo;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void cleanUp() {
		accountRepo.clearAccountsMap();
	}

	@Configuration
	@PropertySource("classpath:/application-offline-test.properties")
	@ImportResource({ "classpath:spring-test.xml" })
	public static class MyContextConfiguration {

	}

	private File getFile(String pathname) {
		URL url = getClass().getClassLoader().getResource(pathname);
		System.out.println(url);
		assertNotNull(url);
		File file = new File(url.getFile());
		return file;
	}

	@Test
	public void testAccountReconciliationHuge() throws IOException,
			InterruptedException {
		String pathname = "offline/test-transactions-huge.txt";
		when(transactionFile.getFile()).thenReturn(getFile(pathname));
		ReflectionTestUtils.setField(fileTransactionReader, "transactionFile",
				transactionFile);
		accountTxService.startProcessing();

		Account account1 = new Account(100225, new BigDecimal("-5709715.20"));
		Account account2 = new Account(201444, new BigDecimal("5709715.20"));
		Account account3 = new Account(100226, new BigDecimal("0.00"));
		assertTrue(accountRepo.getAccounts().contains(account1));
		assertTrue(accountRepo.getAccounts().contains(account2));
		assertTrue(accountRepo.getAccounts().contains(account3));
		assertTrue(accountRepo.getAccounts().size() == 3);
	}
}
