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

    @Nullable
    long timestampMin;

    @Nullable
    long timestampMax;

    @Nullable
    int pageStart;

    @Nullable
    int pageOffset;

    double searchAfter;

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

    public long getTimestampMin() {
        return timestampMin;
    }

    public void setTimestampMin(long timestampMin) {
        this.timestampMin = timestampMin;
    }

    public long getTimestampMax() {
        return timestampMax;
    }

    public void setTimestampMax(@Nullable long timestampMax) {
        this.timestampMax = timestampMax;
    }

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public int getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(int pageOffset) {
        this.pageOffset = pageOffset;
    }

    public double getSearchAfter() {
        return searchAfter;
    }

    public void setSearchAfter(double searchAfter) {
        this.searchAfter = searchAfter;
    }
}
