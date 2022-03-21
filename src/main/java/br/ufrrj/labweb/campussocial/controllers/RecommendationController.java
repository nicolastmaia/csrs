package br.ufrrj.labweb.campussocial.controllers;

import java.util.List;
import java.util.stream.Collectors;

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

    // get topics within square
    List<SearchHit<Topic>> topicSearchHits = topicService.getWithinSquare(requestData.getTopLeftLat(),
        requestData.getTopLeftLon(),
        requestData.getBottomRightLat(), requestData.getBottomRightLon(), requestData.getCenterLat(),
        requestData.getCenterLon(), requestData.getUnit());

    // get list of post ids of found topics
    List<Long> postIdList = topicSearchHits.stream().map(searchHit -> {
      Topic topicPOI = searchHit.getContent();

      return topicPOI.getId();
    }).collect(Collectors.toList());

    // get list of interests of found topics
    List<SearchHit<Interest>> interestSearchHits = interestService.getByPostIdListAndInterestIdList(postIdList,
        requestData.getInterestList());

    // get recommended topics
    List<SearchHit<Topic>> recommendedTopics = recommendationService.recommendTopics(topicSearchHits,
        interestSearchHits);

    return recommendationService.toResultData(recommendedTopics, interestSearchHits);

  }

}
