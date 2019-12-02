package at.redlink.vinddemo.transport;

import at.redlink.vinddemo.model.Doc;

import java.util.List;

public class DocSearchResult {

    private int limit;
    private int offset;
    private long numOfResults;

    private List<Doc> docs;

    private List<TagFacet> tags;

    public int getLimit() {
        return limit;
    }

    public DocSearchResult setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public int getOffset() {
        return offset;
    }

    public DocSearchResult setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public long getNumOfResults() {
        return numOfResults;
    }

    public DocSearchResult setNumOfResults(long numOfResults) {
        this.numOfResults = numOfResults;
        return this;
    }

    public List<Doc> getDocs() {
        return docs;
    }

    public DocSearchResult setDocs(List<Doc> docs) {
        this.docs = docs;
        return this;
    }

    public List<TagFacet> getTags() {
        return tags;
    }

    public DocSearchResult setTags(List<TagFacet> tags) {
        this.tags = tags;
        return this;
    }
}
