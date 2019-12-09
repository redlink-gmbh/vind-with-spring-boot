package at.redlink.vinddemo.service;

import at.redlink.vinddemo.model.DocFactory;
import com.rbmhtechnology.vind.api.Document;
import com.rbmhtechnology.vind.api.SearchServer;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Set;

@Service
public class IndexingService {

    private final static int COMMIT_WITHIN_MS = 1000;

    private final SearchServer searchServer;

    public IndexingService(SearchServer searchServer) {
        this.searchServer = searchServer;
    }

    public void indexDoc(String id, String title, ZonedDateTime date, Set<String> tags, double rating) {
        Document document = DocFactory.createDocument(id, title, date, tags, rating);
        this.searchServer.indexWithin(document, COMMIT_WITHIN_MS);
    }

    public void clear() {
        this.searchServer.clearIndex();
        this.searchServer.commit();
    }
}
