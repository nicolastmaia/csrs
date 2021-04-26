package br.ufrrj.labweb.campussocial;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;

public class ResultData {
    private String title;
    private String text;
    private GeoPoint location;

    public ResultData(String title, String text, GeoPoint location) {
        this.title = title;
        this.text = text;
        this.location = location;
    }

    public ResultData(String title, GeoPoint location) {
        this.title = title;
        this.location = location;
    }

    public ResultData(String title) {
        this.title = title;
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
