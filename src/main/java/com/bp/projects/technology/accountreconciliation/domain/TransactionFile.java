package com.bp.projects.technology.accountreconciliation.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Wrapper class for file that is being read in the offline mode
 * 
 * @author Kavitha
 *
 */
@Configuration
@Profile("offline")
public class TransactionFile {
	private File file;

	@Autowired
	public TransactionFile(@Value("${offline.transaction.file}") String pathname)
			throws FileNotFoundException {
		URI uri = null;
		try {
			uri = new URI(pathname);
		} catch (URISyntaxException e) {
			throw new FileNotFoundException();
		}
		this.file = new File(uri);
	}

	public File getFile() {
		return file;
	}
}
