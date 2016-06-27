package com.bp.projects.technology.accountreconciliation.reader;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.bp.projects.technology.accountreconciliation.processor.TaskExecutorService;
import com.bp.projects.technology.accountreconciliation.repository.AccountRepository;

/**
 * Enables the thread to read any input command during online mode
 * 
 * @author Kavitha
 *
 */
@Configuration
@Profile("online")
public class InputCommandReader {
	private static final Logger LOGGER = Logger
			.getLogger(InputCommandReader.class.getName());
	protected static final String PRINT_ACCT_BALANCES = "online.account.balance.print.command";

	@Autowired
	private TaskExecutorService taskExecution;

	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private Environment env;

	/**
	 * Check for any user input for PrintAcctBalances and if so, then prints the
	 * latest account balances
	 * 
	 * @throws InterruptedException
	 */
	public void readInputCommand() throws InterruptedException {
		taskExecution.execute(new Runnable() {

			@Override
			public void run() {
				LOGGER.log(Level.INFO,
						"Please enter PrintAcctBalances command to know the latest account balances");
				Scanner scanner = new Scanner(System.in);
				while (scanner.hasNext()) {
					String command = scanner.next();
					if (env.getProperty(PRINT_ACCT_BALANCES).equals(command)) {
						accountRepo.showAccountBalance();
					} else {
						LOGGER.log(Level.WARNING,
								"Incorrect Command. Please check!");
					}
					scanner = new Scanner(System.in);
				}
				scanner.close();
			}
		});
	}
}
