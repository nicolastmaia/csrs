package br.ufrrj.labweb.campussocial.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrrj.labweb.campussocial.model.RecommendationRequestData;
import br.ufrrj.labweb.campussocial.model.RecommendationResultData;
import br.ufrrj.labweb.campussocial.model.Topic;
import br.ufrrj.labweb.campussocial.services.InterestService;
import br.ufrrj.labweb.campussocial.services.RecommendationService;
import br.ufrrj.labweb.campussocial.services.TopicService;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

  @Autowired
  private RecommendationService recommendationService;
  @Autowired
  private TopicService topicService;
  @Autowired
  private InterestService interestService;

  @RequestMapping("/topics/square")
  List<RecommendationResultData> getRecommendedTopicsWithinSquare(@RequestBody RecommendationRequestData requestData) {

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
        interestRows = Stream.concat(interestRows.stream(),
            interestService.getByPostIdListAndInterestIdList(postIdList, interestIdList).stream())
            .collect(Collectors.toList());

        // get recommended topics based on topics found in area and interests of user;
        // concat with the list of the previous iteration
        recommendedTopics = Stream.concat(recommendedTopics.stream(),
            recommendationService.recommendTopics(topicSearchHits,
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
    return recommendationService.toResultData(recommendedTopics, interestRows);

  }

}
