package br.ufrrj.labweb.campussocial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApplicationSettingsDBData implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationSettingsDBData.class);

	private final TopicPOIRepository repository;

	public ApplicationSettingsDBData(TopicPOIRepository repository) {
		this.repository = repository;
	}

	public static void main(String[] args) {
		SpringApplication.run(ApplicationSettingsDBData.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}