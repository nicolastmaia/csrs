package br.ufrrj.labweb.campussocial.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrrj.labweb.campussocial.model.RecommendationRequestData;
import br.ufrrj.labweb.campussocial.model.RecommendationResultData;
import br.ufrrj.labweb.campussocial.services.RecommendationService;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

  @Autowired
  private RecommendationService recommendationService;

  @RequestMapping("/topics/square")
  List<RecommendationResultData> recommendedTopicsWithinSquare(@RequestBody RecommendationRequestData requestData) {
    return recommendationService.recommendedTopicsWithinSquare(requestData);
  }

}
