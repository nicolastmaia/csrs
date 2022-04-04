package br.ufrrj.labweb.campussocial.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrrj.labweb.campussocial.model.Interest;
import br.ufrrj.labweb.campussocial.model.RecommendationRequestData;
import br.ufrrj.labweb.campussocial.model.RecommendationResultData;
import br.ufrrj.labweb.campussocial.model.Topic;
import br.ufrrj.labweb.campussocial.services.InterestService;
import br.ufrrj.labweb.campussocial.services.RecommendationService;
import br.ufrrj.labweb.campussocial.services.TopicService;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

  private final RecommendationService recommendationService;
  private final TopicService topicService;
  private final InterestService interestService;

  public RecommendationController(RecommendationService recommendationService, TopicService topicService,
      InterestService interestService) {
    this.recommendationService = recommendationService;
    this.topicService = topicService;
    this.interestService = interestService;
  }

  @RequestMapping("/topics")
  List<RecommendationResultData> getRecommendedTopics(@RequestBody RecommendationRequestData requestData) {

    List<SearchHit<Topic>> recommendedTopics = new ArrayList<SearchHit<Topic>>();
    List<SearchHit<Interest>> interestSearchHits = new ArrayList<SearchHit<Interest>>();
    Boolean limitBiggerThanTotalRegistries = false;

    int pageStart = requestData.getPageStart();

    // while recommendation list size is smaller than pagination upper limit, keep
    // adding to the list.
    while (recommendedTopics.size() < requestData.getPageOffset() && !limitBiggerThanTotalRegistries) {

      pageStart++;

      // get topics within square
      List<SearchHit<Topic>> topicSearchHits = topicService.getWithinSquare(requestData.getTopLeftLat(),
          requestData.getTopLeftLon(),
          requestData.getBottomRightLat(), requestData.getBottomRightLon(), requestData.getCenterLat(),
          requestData.getCenterLon(), requestData.getUnit(), requestData.getTimestampLowerBound(),
          requestData.getTimestampUpperBound(), pageStart, requestData.getPageOffset());

      // check if pagination upper limit is bigger than total registries inside these
      // coordinates
      limitBiggerThanTotalRegistries = topicSearchHits.size() < requestData.getPageOffset() ? true : false;

      // map found topics to list of only post ids
      List<Long> postIdList = topicSearchHits.stream().map(searchHit -> {
        Topic topicPOI = searchHit.getContent();
        return topicPOI.getId();
      }).collect(Collectors.toList());

      // get user's interest list received in request
      List<Long> interestIdList = requestData.getInterestIdList();

      // get list of interests of found topics and concat with the list of the
      // previous iteration
      interestSearchHits = Stream.concat(interestSearchHits.stream(),
          interestService.getByPostIdListAndInterestIdList(postIdList,
              interestIdList).stream())
          .collect(Collectors.toList());

      // get recommended topics based on topics found in area and interests of user;
      // concat with the list of the previous iteration
      recommendedTopics = Stream.concat(recommendedTopics.stream(),
          recommendationService.recommendTopics(topicSearchHits,
              interestSearchHits).stream())
          .collect(Collectors.toList());
    }

    // if recommended topics list size is bigger than pagination upper limit, get
    // only a sublist
    if (recommendedTopics.size() > requestData.getPageOffset()) {
      recommendedTopics = recommendedTopics.subList(0, requestData.getPageOffset());
    }

    // return recommended topics with the interests that matched the user interests.
    return recommendationService.toResultData(recommendedTopics, interestSearchHits);

  }

}
