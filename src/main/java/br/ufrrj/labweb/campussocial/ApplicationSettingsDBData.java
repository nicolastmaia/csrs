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

		if (repository.count() == 0) {
			LOGGER.info("Loading POI data...");
			AtomicLong count = new AtomicLong();
			Stream<String> lines = Files.lines(Paths.get(ClassLoader.getSystemResource("cs_topics.csv").toURI()));
			Observable.fromIterable(lines::iterator).filter(StringUtils::hasLength).map(this::getTopicPOI)
					.filter(Optional::isPresent).map(Optional::get).window(1000)
					.map(topicPOIObservable -> topicPOIObservable.toList().subscribe(topicPOI -> {
						count.addAndGet(topicPOI.size());
						LOGGER.info("saving {} POIs, total {}", topicPOI.size(), count.get());
						repository.saveAll(topicPOI);
					})).blockingSubscribe();
			LOGGER.info("Finished loading POI data.");
		}
	}

	private Optional<TopicPOI> getTopicPOI(String line) {
		try {
			final String[] fields = line.split("\\|");
			if (fields.length != 6) {
				System.out.println("########################3");
				System.out.println(fields.length);
				System.out.println("########################3");
				throw new IllegalArgumentException("no 6 fields in line");
			}
			Integer id = Integer.valueOf(fields[0]);
			String name = fields[1];
			String text = fields[2];
			String comments = fields[3];
			double lat = Double.parseDouble(fields[4]);
			double lon = Double.parseDouble(fields[6]);
			GeoPoint location = new GeoPoint(lat, lon);
			return Optional.of(new TopicPOI(id, name, text, comments, location));
		} catch (Exception e) {
			LOGGER.error("error in line: \"{}\", {}", line, e.getMessage());
			return Optional.empty();
		}
	}

}