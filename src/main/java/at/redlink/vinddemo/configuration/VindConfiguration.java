package at.redlink.vinddemo.configuration;

import at.redlink.vinddemo.properties.VindProperties;
import com.rbmhtechnology.vind.api.SearchServer;
import com.rbmhtechnology.vind.configure.SearchConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VindConfiguration {

    private SearchServer searchServer;

    public VindConfiguration(VindProperties vindProperties) {

        SearchConfiguration.set(SearchConfiguration.SERVER_PROVIDER, "com.rbmhtechnology.vind.solr.backend.RemoteSolrServerProvider");
        SearchConfiguration.set(SearchConfiguration.SERVER_SOLR_CLOUD, vindProperties.isCloud());
        SearchConfiguration.set(SearchConfiguration.SERVER_HOST, vindProperties.getHost());

        this.searchServer = SearchServer.getInstance(vindProperties.getCollection());
    }

    @Bean
    public SearchServer getSearchServer() {
        return searchServer;
    }
}
