package br.ufrrj.labweb.campussocial.controllers;

import java.util.List;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrrj.labweb.campussocial.model.RecommendationRequestData;
import br.ufrrj.labweb.campussocial.model.RecommendationResultData;
import br.ufrrj.labweb.campussocial.model.Topic;
import br.ufrrj.labweb.campussocial.model.TopicResultData;
import br.ufrrj.labweb.campussocial.services.RecommendationService;
import br.ufrrj.labweb.campussocial.services.TopicService;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

  private final RecommendationService recommendationService;
  private final TopicService topicService;

  public RecommendationController(RecommendationService recommendationService, TopicService topicService) {
    this.recommendationService = recommendationService;
    this.topicService = topicService;
  }

  @RequestMapping("/topics")
  List<TopicResultData> getRecommendedTopics(@RequestBody RecommendationRequestData requestData) {
    List<SearchHit<Topic>> searchHits = recommendationService.getRecommendedTopics(requestData.getInterestList(),
        requestData.getTopLeftLat(),
        requestData.getTopLeftLon(),
        requestData.getBottomRightLat(), requestData.getBottomRightLon(), requestData.getCenterLat(),
        requestData.getCenterLon(), requestData.getUnit());

    return topicService.toResultData(searchHits);
  }

}
