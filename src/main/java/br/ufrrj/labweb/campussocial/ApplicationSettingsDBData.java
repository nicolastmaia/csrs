package br.ufrrj.labweb.campussocial;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.util.StringUtils;

import io.reactivex.Observable;

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