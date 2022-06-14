package br.ufrrj.labweb.campussocial.services;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

import br.ufrrj.labweb.campussocial.model.RecommendationResultData;
import br.ufrrj.labweb.campussocial.model.Topic;

@Service
public class RecommendationService {

  public List<SearchHit<Topic>> recommendTopics(List<SearchHit<Topic>> topicSearchHits,
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
