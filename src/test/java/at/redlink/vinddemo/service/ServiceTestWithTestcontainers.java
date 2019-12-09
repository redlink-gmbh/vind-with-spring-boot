package at.redlink.vinddemo.service;

import at.redlink.vinddemo.configuration.VindConfiguration;
import at.redlink.vinddemo.properties.VindProperties;
import io.redlink.utils.test.testcontainers.VindContainer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.TestPropertySourceUtils;

import java.time.ZonedDateTime;
import java.util.Collections;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {VindConfiguration.class, SearchService.class, IndexingService.class, VindProperties.class},
        initializers = ServiceTestWithTestcontainers.class
)
public class ServiceTestWithTestcontainers implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Autowired
    private SearchService searchService;

    @Autowired
    private IndexingService indexingService;

    //creates collection named 'vind' by default
    @ClassRule
    public static VindContainer vindContainer = VindContainer.create();

    @Before
    public void before() {
        indexingService.clear();
    }

    @Test
    public void simpleVindTest() throws InterruptedException {
        indexingService.indexDoc("1", "my title", ZonedDateTime.now(), Collections.emptySet(), 0);

        Thread.sleep(1100);

        long numFound = searchService.search(10, 0, "", null, null).getNumOfResults();
        Assert.assertEquals(1, numFound);
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                applicationContext,
                "vind.host=" + vindContainer.getSolrUrl(),
                "vind.collection=vind");
    }
}
