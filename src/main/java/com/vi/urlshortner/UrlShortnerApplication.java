package com.vi.urlshortner;

import com.vi.urlshortner.config.ApplicationProperties;
import com.vi.urlshortner.config.LoggingAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties({ ApplicationProperties.class })
@EnableAspectJAutoProxy
public class UrlShortnerApplication extends SpringBootServletInitializer
{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(UrlShortnerApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(UrlShortnerApplication.class, args);
	}

	@Bean
	public LoggingAspect loggingAspect(Environment environment) {
		return new LoggingAspect(environment);
	}

}
