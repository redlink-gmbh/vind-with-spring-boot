package at.redlink.vinddemo.transport;

public class TagFacet {

    private long count;
    private String value;

    public long getCount() {
        return count;
    }

    public TagFacet setCount(long count) {
        this.count = count;
        return this;
    }

    public String getValue() {
        return value;
    }

    public TagFacet setValue(String value) {
        this.value = value;
        return this;
    }
}
