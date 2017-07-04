package com.dress2Impress.dress.config;

import com.dress2Impress.dress.DressConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DressPropertiesConfig {

	@Bean
	@ConfigurationProperties(prefix = DressConstants.PropertiesPrefix.JACKET_CHECK)
	public JacketTempProperties jacketTempProperties() {
		return new JacketTempProperties();
	}

}
