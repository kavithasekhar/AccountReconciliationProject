package com.bp.projects.technology.accountreconciliation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import scala.actors.threadpool.Arrays;

/**
 * Main class that loads the spring application context using xml config
 * 
 * @author Kavitha
 *
 */
public class AccountTxEngine {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws InterruptedException {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		String[] activeProfiles = context.getEnvironment().getActiveProfiles();
		if(Arrays.asList(activeProfiles).contains("offline")) {
			AccountTxOfflineController controller = context.getBean(AccountTxOfflineController.class);
			controller.startProcessing();
		}
	}
}
