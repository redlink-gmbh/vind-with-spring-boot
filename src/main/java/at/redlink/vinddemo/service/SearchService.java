package at.redlink.vinddemo.service;

import at.redlink.vinddemo.model.DocFactory;
import at.redlink.vinddemo.transport.DocSearchResult;
import at.redlink.vinddemo.transport.TagFacet;
import at.redlink.vinddemo.transport.TimeRange;
import com.rbmhtechnology.vind.api.SearchServer;
import com.rbmhtechnology.vind.api.query.FulltextSearch;
import com.rbmhtechnology.vind.api.query.Search;
import com.rbmhtechnology.vind.api.query.datemath.DateMathExpression;
import com.rbmhtechnology.vind.api.query.filter.Filter;
import com.rbmhtechnology.vind.api.query.sort.Sort;
import com.rbmhtechnology.vind.api.result.FacetResults;
import com.rbmhtechnology.vind.api.result.SearchResult;
import com.rbmhtechnology.vind.api.result.facet.FacetValue;
import com.rbmhtechnology.vind.api.result.facet.TermFacetResult;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final SearchServer searchServer;

    public SearchService(SearchServer searchServer) {
        this.searchServer = searchServer;
    }

    public DocSearchResult search(
            int limit,
            int offset,
            String searchTerm,
            List<String> tagFilter,
            TimeRange timeRange
    ) {
        FulltextSearch search = Search.fulltext(searchTerm).slice(offset, limit).sort(Sort.desc(DocFactory.DATE_FIELD));

        search.facet(DocFactory.TAGS_FIELD);

        if(tagFilter != null && !tagFilter.isEmpty()) {
            search.filter(
                    tagFilter.stream()
                            .map((String tag) -> Filter.eq(DocFactory.TAGS_FIELD, tag))
                            .collect(Filter.AndCollector)
            );
        }

        if(timeRange != null) {
            switch (timeRange) {
                case PAST_HOUR: search.filter(DocFactory.DATE_FIELD.after(new DateMathExpression().sub(1, DateMathExpression.TimeUnit.HOUR)));break;
                case PAST_DAY: search.filter(DocFactory.DATE_FIELD.after(new DateMathExpression().sub(1, DateMathExpression.TimeUnit.DAY)));break;
                case PAST_WEEK: search.filter(DocFactory.DATE_FIELD.after(new DateMathExpression().sub(7, DateMathExpression.TimeUnit.DAYS)));break;
            }
        }

        SearchResult vindResult = searchServer.execute(search, DocFactory.FACTORY);

        return new DocSearchResult()
                .setDocs(vindResult.getResults().stream().map(DocFactory::createDoc).collect(Collectors.toList()))
                .setLimit(limit)
                .setOffset(offset)
                .setNumOfResults(vindResult.getNumOfResults())
                .setTags(getTagFacet(vindResult.getFacetResults()));
    }

    private List<TagFacet> getTagFacet(FacetResults facetResults) {
        TermFacetResult<String> tagFacetResults = facetResults.getTermFacet(DocFactory.TAGS_FIELD);
        if(tagFacetResults != null && tagFacetResults.getValues().size() > 0) {
            return tagFacetResults.getValues().stream().map((FacetValue<String> facet) -> {
                return new TagFacet().setValue(facet.getValue()).setCount(facet.getCount());
            }).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

}
