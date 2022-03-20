package br.ufrrj.labweb.campussocial.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.Nullable;

public class RecommendationRequestData {

  @Nullable
  private List<Long> interestList;

  @Nullable
  private double centerLat;
  @Nullable
  private double centerLon;

  @Nullable
  private double topLeftLat;
  @Nullable
  private double topLeftLon;

  @Nullable
  private double bottomRightLat;
  @Nullable
  private double bottomRightLon;

  @Nullable
  private Double distance;
  @Nullable
  private String unit;

  public List<Long> getInterestList() {
    return interestList;
  }

  public void setInterestList(List<Long> interestList) {
    this.interestList = interestList;
  }

  public void addInterest(Long interest) {
    if (interestList == null) {
      interestList = new ArrayList<>();
    }
    interestList.add(interest);
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

  public Double getDistance() {
    return distance;
  }

  public void setDistance(Double distance) {
    this.distance = distance;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

}
