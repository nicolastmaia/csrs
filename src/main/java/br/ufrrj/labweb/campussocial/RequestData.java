package br.ufrrj.labweb.campussocial;

import org.springframework.lang.Nullable;

public class RequestData {

    @Nullable
    private String text;
    private double lat;
    private double lon;
    @Nullable
    Double distance;
    @Nullable
    String unit;

    @Nullable
    public String getText() {
        return text;
    }

    public void setText(@Nullable String text) {
        this.text = text;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
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
}
