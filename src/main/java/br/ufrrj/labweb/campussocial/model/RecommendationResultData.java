package br.ufrrj.labweb.campussocial.model;

import java.util.List;

public class RecommendationResultData {

  Topic topic;

  List<Interest> interestList;

  public RecommendationResultData(Topic topic, List<Interest> interestList) {
    this.topic = topic;
    this.interestList = interestList;
  }

  public Topic getTopic() {
    return topic;
  }

  public void setTopic(Topic topic) {
    this.topic = topic;
  }

  public List<Interest> getInterestList() {
    return interestList;
  }

  public void setInterestList(List<Interest> interestList) {
    this.interestList = interestList;
  }

}
