package com.bp.projects.technology.accountreconciliation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("offline")
public class AccountTxOfflineController {
	@Autowired
	AccountTxService accountTxService;

	public void startProcessing() {
		accountTxService.startProcessing();
	}
}
