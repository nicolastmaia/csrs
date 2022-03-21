package br.ufrrj.labweb.campussocial.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrrj.labweb.campussocial.model.RecommendationRequestData;
import br.ufrrj.labweb.campussocial.model.RecommendationResultData;
import br.ufrrj.labweb.campussocial.services.RecommendationService;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

  private final RecommendationService recommendationService;

  public RecommendationController(RecommendationService recommendationService) {
    this.recommendationService = recommendationService;
  }

  @RequestMapping("/topics")
  List<RecommendationResultData> getRecommendedTopics(@RequestBody RecommendationRequestData requestData) {

    List<RecommendationResultData> recommendation = recommendationService.getRecommendedTopics(
        requestData.getInterestList(),
        requestData.getTopLeftLat(),
        requestData.getTopLeftLon(),
        requestData.getBottomRightLat(), requestData.getBottomRightLon(), requestData.getCenterLat(),
        requestData.getCenterLon(), requestData.getUnit());

    return recommendation;
  }

}
