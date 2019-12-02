package at.redlink.vinddemo;

import at.redlink.vinddemo.service.IndexingService;
import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.context.ApplicationContext;

import java.time.ZonedDateTime;

@SpringBootApplication(exclude = SolrAutoConfiguration.class)
public class VindDemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(VindDemoApplication.class, args);

		//some bootstrapping
		IndexingService indexingService = (IndexingService) context.getBean("indexingService");

		indexingService.indexDoc("1", "My first doc", ZonedDateTime.now(), ImmutableSet.of("cat1","cat2"), 1);
		indexingService.indexDoc("2", "My second doc", ZonedDateTime.now(), ImmutableSet.of("cat2","cat3"), 2);

	}

}
