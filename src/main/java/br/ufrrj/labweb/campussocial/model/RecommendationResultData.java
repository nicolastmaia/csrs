package br.ufrrj.labweb.campussocial.model;

import java.util.List;
import java.util.Map;

public class RecommendationResultData {

  Map<String, Object> topic;

  List<Map<String, Object>> interestList;

  private Double searchAfter;

  public RecommendationResultData(Map<String, Object> topic, List<Map<String, Object>> interestList,
      Double searchAfter) {
    this.topic = topic;
    this.interestList = interestList;
    this.searchAfter = searchAfter;
  }

  public Map<String, Object> getTopic() {
    return topic;
  }

  public void setTopic(Map<String, Object> topic) {
    this.topic = topic;
  }

  public List<Map<String, Object>> getInterestList() {
    return interestList;
  }

  public void setInterestList(List<Map<String, Object>> interestList) {
    this.interestList = interestList;
  }

  public Double getSearchAfter() {
    return searchAfter;
  }

  public void setSearchAfter(Double searchAfter) {
    this.searchAfter = searchAfter;
  }

}
