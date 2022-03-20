package br.ufrrj.labweb.campussocial.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

import br.ufrrj.labweb.campussocial.model.Interest;
import br.ufrrj.labweb.campussocial.model.RecommendationResultData;
import br.ufrrj.labweb.campussocial.model.Topic;
import br.ufrrj.labweb.campussocial.model.TopicResultData;

@Service
public class RecommendationService {

  private final TopicService topicService;
  private final InterestService interestService;

  public RecommendationService(TopicService topicService, InterestService interestService) {
    this.topicService = topicService;
    this.interestService = interestService;
  }

  public List<SearchHit<Topic>> getRecommendedTopics(List<Long> interestList, double topLeftLat,
      double topLeftLon,
      double bottomRightLat, double bottomRightLon, double centerLat, double centerLon, String unit) {

    // get topics within square
    List<SearchHit<Topic>> topicSearchHits = topicService.getWithinSquare(topLeftLat, topLeftLon, bottomRightLat,
        bottomRightLon,
        centerLat,
        centerLon, unit);

    // get list of post ids of found topics
    List<Long> postIdList = topicSearchHits.stream().map(searchHit -> {
      Topic topicPOI = searchHit.getContent();

      return topicPOI.getId();
    }).collect(Collectors.toList());

    // get list of interests of found topics
    List<SearchHit<Interest>> topicsInterests = interestService.getByPostIdList(postIdList);

    // filter topicsInterests whose name is present in interestsList
    List<SearchHit<Interest>> filteredTopicsInterests = topicsInterests.stream().filter(searchHit -> {
      Interest interestPOI = searchHit.getContent();

      return interestList.contains(interestPOI.getInterest_id());
    }).collect(Collectors.toList());

    List<Long> filteredTopicsInterestsPostIds = filteredTopicsInterests.stream().map(searchHit -> {
      Interest interestPOI = searchHit.getContent();

      return interestPOI.getPost_id();
    }).collect(Collectors.toList());

    Set<Long> filteredTopicsInterestsPostIdsSet = new HashSet<>(filteredTopicsInterestsPostIds);

    // filter topicsInterests that are present in interesList
    List<SearchHit<Topic>> recommendedTopics = topicSearchHits.stream().filter(searchHit -> {
      Topic topicPOI = searchHit.getContent();

      return filteredTopicsInterestsPostIdsSet.contains(topicPOI.getId());
    }).collect(Collectors.toList());

    return recommendedTopics;
  }

  private List<RecommendationResultData> toResultData(List<SearchHit<Topic>> searchHits) {
    return null;
  }
}
