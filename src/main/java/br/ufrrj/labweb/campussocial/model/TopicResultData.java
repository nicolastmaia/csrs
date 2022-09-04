package br.ufrrj.labweb.campussocial.model;

import org.elasticsearch.common.geo.GeoPoint;

public class TopicResultData {

    private long id;
    private String title;
    private String text;
    private GeoPoint location;
    private Double searchAfter;

    public TopicResultData(long id, String title, String text, GeoPoint location, Double searchAfter) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.location = location;
        this.searchAfter = searchAfter;
    }

    public TopicResultData(long id, String title, String text, GeoPoint location) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.location = location;
    }

    public TopicResultData(long id, String title, GeoPoint location) {
        this.id = id;
        this.title = title;
        this.location = location;
    }

    public TopicResultData(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public Double getSearchAfter() {
        return searchAfter;
    }

    public void setSearchAfter(Double searchAfter) {
        this.searchAfter = searchAfter;
    }

}
