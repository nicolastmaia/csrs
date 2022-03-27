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
    Boolean limitBiggerThanRegistries = false;

    while (recommendedTopics.size() < requestData.getPageUpperBound() && !limitBiggerThanRegistries) {

      // get topics within square
      List<SearchHit<Topic>> topicSearchHits = topicService.getWithinSquare(requestData.getTopLeftLat(),
          requestData.getTopLeftLon(),
          requestData.getBottomRightLat(), requestData.getBottomRightLon(), requestData.getCenterLat(),
          requestData.getCenterLon(), requestData.getUnit(), requestData.getTimestampLowerBound(),
          requestData.getTimestampUpperBound(), requestData.getPageLowerBound(), requestData.getPageUpperBound());

      limitBiggerThanRegistries = topicSearchHits.size() < requestData.getPageUpperBound() ? true : false;

      // get list of post ids of found topics
      List<Long> postIdList = topicSearchHits.stream().map(searchHit -> {
        Topic topicPOI = searchHit.getContent();
        return topicPOI.getId();
      }).collect(Collectors.toList());

      // get list of interests of found topics
      interestSearchHits = Stream.concat(interestSearchHits.stream(),
          interestService.getByPostIdListAndInterestIdList(postIdList,
              requestData.getInterestList()).stream())
          .collect(Collectors.toList());

      // get recommended topics
      recommendedTopics = Stream.concat(recommendedTopics.stream(),
          recommendationService.recommendTopics(topicSearchHits,
              interestSearchHits).stream())
          .collect(Collectors.toList());
    }

    if (recommendedTopics.size() > requestData.getPageUpperBound()) {
      recommendedTopics = recommendedTopics.subList(0, requestData.getPageUpperBound());
    }

    return recommendationService.toResultData(recommendedTopics, interestSearchHits);

  }

}
