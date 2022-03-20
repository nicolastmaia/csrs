package br.ufrrj.labweb.campussocial.model;

import org.springframework.lang.Nullable;

public class TopicRequestData {

    private String title;

    @Nullable
    private String text;

    private double centerLat;
    private double centerLon;

    @Nullable
    private double topLeftLat;
    @Nullable
    private double topLeftLon;

    @Nullable
    private double bottomRightLat;
    @Nullable
    private double bottomRightLon;

    // TODO: check if possible to make private
    @Nullable
    Double distance;
    @Nullable
    String unit;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Nullable
    public String getText() {
        return text;
    }

    public void setText(@Nullable String text) {
        this.text = text;
    }

    public double getCenterLat() {
        return centerLat;
    }

    public void setCenterLat(double centerLat) {
        this.centerLat = centerLat;
    }

    public double getCenterLon() {
        return centerLon;
    }

    public void setCenterLon(double centerLon) {
        this.centerLon = centerLon;
    }

    @Nullable
    public Double getDistance() {
        return distance;
    }

    public void setDistance(@Nullable Double distance) {
        this.distance = distance;
    }

    @Nullable
    public String getUnit() {
        return unit;
    }

    public void setUnit(@Nullable String unit) {
        this.unit = unit;
    }

    public double getTopLeftLat() {
        return topLeftLat;
    }

    public void setTopLeftLat(double topLeftLat) {
        this.topLeftLat = topLeftLat;
    }

    public double getTopLeftLon() {
        return topLeftLon;
    }

    public void setTopLeftLon(double topLeftLon) {
        this.topLeftLon = topLeftLon;
    }

    public double getBottomRightLat() {
        return bottomRightLat;
    }

    public void setBottomRightLat(double bottomRightLat) {
        this.bottomRightLat = bottomRightLat;
    }

    public double getBottomRightLon() {
        return bottomRightLon;
    }

    public void setBottomRightLon(double bottomRightLon) {
        this.bottomRightLon = bottomRightLon;
    }

}
