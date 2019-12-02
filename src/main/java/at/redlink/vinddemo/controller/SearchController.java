package at.redlink.vinddemo.controller;

import at.redlink.vinddemo.service.SearchService;
import at.redlink.vinddemo.transport.DocSearchResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(produces = "application/json")
    public DocSearchResult search(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(name = "q", defaultValue = "*") String searchTerm,
            @RequestParam(name = "tag", required = false) List<String> tagFilter
    ) {
        return searchService.search(limit, offset, searchTerm, tagFilter);
    }

}
