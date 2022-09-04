package br.ufrrj.labweb.campussocial.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.Nullable;

public class RecommendationRequestData {

  @Nullable
  private List<Long> interestIdList;

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

  @Nullable
  private long timestampMin;

  @Nullable
  private long timestampMax;

  @Nullable
  private int pageOffset = 10;

  private double searchAfter = 0.0;

  public List<Long> getInterestIdList() {
    return interestIdList;
  }

  public void setInterestIdList(List<Long> interestIdList) {
    this.interestIdList = interestIdList;
  }

  public void addInterestId(Long interestId) {
    if (interestIdList == null) {
      interestIdList = new ArrayList<>();
    }
    interestIdList.add(interestId);
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

  public long getTimestampMin() {
    return timestampMin;
  }

  public void setTimestampMin(long timestampMin) {
    this.timestampMin = timestampMin;
  }

  public long getTimestampMax() {
    return timestampMax;
  }

  public void setTimestampMax(long timestampMax) {
    this.timestampMax = timestampMax;
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
