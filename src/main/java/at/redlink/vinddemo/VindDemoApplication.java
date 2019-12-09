package at.redlink.vinddemo;

import at.redlink.vinddemo.service.IndexingService;
import com.google.common.collect.ImmutableSet;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.context.ApplicationContext;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@SpringBootApplication(exclude = SolrAutoConfiguration.class)
public class VindDemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(VindDemoApplication.class, args);

		//some bootstrapping
		IndexingService indexingService = (IndexingService) context.getBean("indexingService");

		indexingService.indexDoc("1", "My first article", ZonedDateTime.now(), ImmutableSet.of("cat"), 1);
		indexingService.indexDoc("2",
				"My second article",
				ZonedDateTime.now().minus(3, ChronoUnit.HOURS),
				ImmutableSet.of("cat","dog"),
				2
		);
		indexingService.indexDoc("3",
				"My third article",
				ZonedDateTime.now().minus(2, ChronoUnit.DAYS),
				ImmutableSet.of("horse"),
				2
		);

	}

}
