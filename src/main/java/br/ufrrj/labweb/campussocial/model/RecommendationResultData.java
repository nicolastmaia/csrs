package br.ufrrj.labweb.campussocial.model;

import java.util.List;
import java.util.Map;

public class RecommendationResultData {

  Topic topic;

  List<Map<String, Object>> interestList;

  public RecommendationResultData(Topic topic, List<Map<String, Object>> interestList) {
    this.topic = topic;
    this.interestList = interestList;
  }

  public Topic getTopic() {
    return topic;
  }

  public void setTopic(Topic topic) {
    this.topic = topic;
  }

  public List<Map<String, Object>> getInterestList() {
    return interestList;
  }

  public void setInterestList(List<Map<String, Object>> interestList) {
    this.interestList = interestList;
  }

}
