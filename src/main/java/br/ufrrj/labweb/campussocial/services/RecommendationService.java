package br.ufrrj.labweb.campussocial.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

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

  public List<RecommendationResultData> getRecommendedTopics(double topLeftLat, double topLeftLon,
      double bottomRightLat, double bottomRightLon, double centerLat, double centerLon, String unit) {
    List<SearchHit<Topic>> topicSearchHits = topicService.getWithinSquare(topLeftLat, topLeftLon, bottomRightLat,
        bottomRightLon,
        centerLat,
        centerLon, unit);

    return null;
  }

  private List<RecommendationResultData> toResultData(List<SearchHit<Topic>> searchHits) {
    return null;
  }
}
