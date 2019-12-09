package at.redlink.vinddemo.service;

import at.redlink.vinddemo.transport.DocSearchResult;
import com.rbmhtechnology.vind.test.TestSearchServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.Collections;

public class ServiceTest {

    @ClassRule
    public static TestSearchServer testSearchServer = new TestSearchServer();

    private static IndexingService indexingService;
    private static SearchService searchService;

    @BeforeClass
    public static void beforeClass() {
        searchService = new SearchService(testSearchServer.getSearchServer());
        indexingService = new IndexingService(testSearchServer.getSearchServer());
    }

    @Before
    public void before() {
        testSearchServer.getSearchServer().clearIndex();
        testSearchServer.getSearchServer().commit();
    }

    @Test
    public void testIndexAndSearch() throws InterruptedException {
        DocSearchResult result = searchService.search(10, 0 , "*", null, null);
        Assert.assertEquals(0, result.getNumOfResults());

        indexingService.indexDoc("1", "Some title", ZonedDateTime.now(), Collections.emptySet(), 1);

        result = searchService.search(10, 0 , "*", null, null);

        Thread.sleep(1100);

        result = searchService.search(10, 0 , "*", null, null);
        Assert.assertEquals(1, result.getNumOfResults());
    }

}
