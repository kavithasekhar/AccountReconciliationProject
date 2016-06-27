package com.bp.projects.technology.accountreconciliation.properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * 
 * Class that loads the application properties to spring environment and @value
 * based on the offline or online profile
 * 
 * @author Kavitha
 *
 */
@Configuration
@ComponentScan(basePackages = "com.bp.projects.technology.accountreconciliation")
@PropertySource(value = { "classpath:application-${spring.profiles.active}.properties" })
public class ApplicationPropertyConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}