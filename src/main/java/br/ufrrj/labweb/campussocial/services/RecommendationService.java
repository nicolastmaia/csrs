package br.ufrrj.labweb.campussocial.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

import br.ufrrj.labweb.campussocial.model.RecommendationRequestData;
import br.ufrrj.labweb.campussocial.model.RecommendationResultData;
import br.ufrrj.labweb.campussocial.model.Topic;

@Service
public class RecommendationService {

  @Autowired
  private TopicService topicService;
  @Autowired
  private InterestService interestService;

  public List<RecommendationResultData> recommendedTopicsWithinSquare(
      RecommendationRequestData requestData) {

    List<SearchHit<Topic>> recommendedTopics = new ArrayList<SearchHit<Topic>>();
    List<Map<String, Object>> interestRows = new ArrayList<Map<String, Object>>();

    Boolean isOffsetBiggerThanTotalRegistries = false;
    Boolean topicSearchHitsIsEmpty = false;
    int pageStart = requestData.getPageStart();

    // while recommendation list size is smaller than pagination upper limit, keep
    // adding to the list.
    while (recommendedTopics.size() < requestData.getPageOffset() &&
        !isOffsetBiggerThanTotalRegistries && !topicSearchHitsIsEmpty) {

      // get topics within square
      List<SearchHit<Topic>> topicSearchHits = topicService.getWithinSquare(requestData.getTopLeftLat(),
          requestData.getTopLeftLon(),
          requestData.getBottomRightLat(), requestData.getBottomRightLon(), requestData.getCenterLat(),
          requestData.getCenterLon(), requestData.getUnit(), requestData.getTimestampLowerBound(),
          requestData.getTimestampUpperBound(), pageStart, requestData.getPageOffset());

      // check if pagination upper limit is bigger than total registries inside these
      // coordinates
      isOffsetBiggerThanTotalRegistries = topicSearchHits.size() < requestData.getPageOffset() ? true : false;
      topicSearchHitsIsEmpty = topicSearchHits.isEmpty();

      if (!topicSearchHitsIsEmpty) {
        // map found topics to list of only post ids
        List<Long> postIdList = topicSearchHits.stream().map(searchHit -> {
          Topic topicPOI = searchHit.getContent();
          return topicPOI.getId();
        }).collect(Collectors.toList());

        // get user's interest list received in request
        List<Long> interestIdList = requestData.getInterestIdList();

        // get post ids that have user's interests associated
        interestRows = Stream.concat(interestRows.stream(),
            interestService.getByPostIdListAndInterestIdList(postIdList, interestIdList).stream())
            .collect(Collectors.toList());

        // concat with the list of the previous iteration
        recommendedTopics = Stream.concat(recommendedTopics.stream(),
            mergeTopicsWithInterests(topicSearchHits,
                interestRows).stream())
            .collect(Collectors.toList());

        // add +1 to pageStart to avoid getting the same topics again on next iteration
        pageStart++;
      }
    }

    // if recommended topics list size is bigger than pagination upper limit, get
    // only a sublist
    if (recommendedTopics.size() > requestData.getPageOffset()) {
      recommendedTopics = recommendedTopics.subList(0, requestData.getPageOffset());
    }

    // return recommended topics with the interests that matched the user interests.
    return toResultData(recommendedTopics, interestRows);

  }

  public List<SearchHit<Topic>> mergeTopicsWithInterests(List<SearchHit<Topic>> topicSearchHits,
      List<Map<String, Object>> interestRows) {

    List<Long> topicsInterestsPostIds = interestRows.stream()
        .map(row -> (Long) row.get("post_id"))
        .collect(Collectors.toList());

    Set<Long> topicsInterestsPostIdsSet = new HashSet<>(topicsInterestsPostIds);

    List<SearchHit<Topic>> recommendedTopics = topicSearchHits.stream().filter(searchHit -> {
      Topic topicPOI = searchHit.getContent();

      return topicsInterestsPostIdsSet.contains(topicPOI.getId());
    }).collect(Collectors.toList());

    return recommendedTopics;
  }

  public List<RecommendationResultData> toResultData(List<SearchHit<Topic>> recommendedTopics,
      List<Map<String, Object>> interestRows) {
    List<RecommendationResultData> recommendation = recommendedTopics.stream().map(topic -> {

      List<Map<String, Object>> topicInterestList = interestRows.stream()
          .filter(row -> (Long) row.get("post_id") == topic.getContent().getId()).collect(Collectors.toList());

      Topic topicPOI = topic.getContent();

      return new RecommendationResultData(topicPOI, topicInterestList);

    }).collect(Collectors.toList());

    return recommendation;
  }

}
