package com.dress2Impress;

import com.dress2Impress.constants.ApplicationConstants;
import com.dress2Impress.logging.annotation.InjectLogger;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SpringBootApplication(
		scanBasePackages = ApplicationConstants.ROOT_PACKAGE,
		exclude = {
				DataSourceAutoConfiguration.class,
				HibernateJpaAutoConfiguration.class,
				LiquibaseAutoConfiguration.class
		}
)
public class Dress2Impress implements ApplicationRunner, ApplicationContextAware {

	@InjectLogger
	private static Logger LOG;

	private ApplicationContext applicationContext;

	public static void main(String args[]) {
		System.exit(SpringApplication.exit(new SpringApplicationBuilder()
				.sources(Dress2Impress.class)
				.web(false)
				.bannerMode(Banner.Mode.OFF)
				.run(args)));
	}

	@Override
	public void run(final ApplicationArguments applicationArguments) throws Exception {
		LOG.info("hello world");
	}


	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
