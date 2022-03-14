package br.ufrrj.labweb.campussocial.model;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;

public class TopicResultData {
    private long id;
    private String title;
    private String text;
    private GeoPoint location;

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

}
