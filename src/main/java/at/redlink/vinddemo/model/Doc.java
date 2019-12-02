package at.redlink.vinddemo.model;

import java.time.ZonedDateTime;
import java.util.List;

public class Doc {

    private String id;
    private String title;
    private ZonedDateTime date;
    private List<String> tags;
    private double rating;

    public String getId() {
        return id;
    }

    public Doc setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Doc setTitle(String title) {
        this.title = title;
        return this;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Doc setDate(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public List<String> getTags() {
        return tags;
    }

    public Doc setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public double getRating() {
        return rating;
    }

    public Doc setRating(double rating) {
        this.rating = rating;
        return this;
    }
}
