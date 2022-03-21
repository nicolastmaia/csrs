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

@Service
public class RecommendationService {

  private final TopicService topicService;
  private final InterestService interestService;

  public RecommendationService(TopicService topicService, InterestService interestService) {
    this.topicService = topicService;
    this.interestService = interestService;
  }

  public List<RecommendationResultData> getRecommendedTopics(List<Long> interestIdList, double topLeftLat,
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
    List<SearchHit<Interest>> topicsInterests = interestService.getByPostIdListAndInterestIdList(postIdList,
        interestIdList);

    List<Long> topicsInterestsPostIds = topicsInterests.stream().map(searchHit -> {
      Interest interestPOI = searchHit.getContent();

      return interestPOI.getPost_id();
    }).collect(Collectors.toList());

    Set<Long> topicsInterestsPostIdsSet = new HashSet<>(topicsInterestsPostIds);

    List<SearchHit<Topic>> recommendedTopics = topicSearchHits.stream().filter(searchHit -> {
      Topic topicPOI = searchHit.getContent();

      return topicsInterestsPostIdsSet.contains(topicPOI.getId());
    }).collect(Collectors.toList());

    List<RecommendationResultData> recommendation = recommendedTopics.stream().map(topic -> {

      List<Interest> topicInterestList = topicsInterests.stream().filter(topicInterest -> {
        return topicInterest.getContent().getPost_id() == topic.getContent().getId();
      }).map(topicInterest -> topicInterest.getContent()).collect(Collectors.toList());

      Topic topicPOI = topic.getContent();

      return new RecommendationResultData(topicPOI, topicInterestList);

    }).collect(Collectors.toList());

    return recommendation;
  }

}
