package br.ufrrj.labweb.campussocial.services;

import java.util.ArrayList;
import java.util.HashMap;
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

  private static final double MUI_PERCENT = 0.8;
  private static final double NMUI_PERCENT = 0.2;
  private static final double EXTRA_TOTAL_PERCENT = 1.2;

  public List<RecommendationResultData> recommendedTopicsWithinSquare(
      RecommendationRequestData requestData) {

    Map<Boolean, List<SearchHit<Topic>>> separatedTopics = new HashMap<Boolean, List<SearchHit<Topic>>>();
    List<SearchHit<Topic>> recommendedTopics = new ArrayList<SearchHit<Topic>>();
    List<SearchHit<Topic>> muiTopics = new ArrayList<SearchHit<Topic>>();
    List<SearchHit<Topic>> nmuiTopics = new ArrayList<SearchHit<Topic>>();
    List<SearchHit<Topic>> tempList;
    List<Map<String, Object>> interestRows = new ArrayList<Map<String, Object>>();

    int totalAmount = (int) (requestData.getPageOffset() * EXTRA_TOTAL_PERCENT);
    int muiAmount = (int) (requestData.getPageOffset() * MUI_PERCENT);
    int nmuiAmount = (int) (requestData.getPageOffset() * NMUI_PERCENT);

    int pageStart = requestData.getPageStart();

    Boolean isOffsetBiggerThanTotalRegistries = false;
    Boolean topicSearchHitsIsEmpty = false;

    // while recommendation list size is smaller than pagination upper limit, keep
    // adding to the list.
    while ((muiTopics.size() < muiAmount || nmuiTopics.size() < nmuiAmount) &&
        !isOffsetBiggerThanTotalRegistries && !topicSearchHitsIsEmpty) {

      // get user's interest list received in request
      List<Long> interestIdList = requestData.getInterestIdList();

      // get topics within square
      List<SearchHit<Topic>> topicSearchHits = topicService.getWithinSquare(requestData.getTopLeftLat(),
          requestData.getTopLeftLon(),
          requestData.getBottomRightLat(), requestData.getBottomRightLon(), requestData.getCenterLat(),
          requestData.getCenterLon(), requestData.getUnit(), requestData.getTimestampLowerBound(),
          requestData.getTimestampUpperBound(), pageStart, totalAmount);

      // check if pagination upper limit is bigger than total registries inside these
      // coordinates
      isOffsetBiggerThanTotalRegistries = topicSearchHits.size() < requestData.getPageOffset() ? true : false;

      // check if topicSearchHits is empty
      topicSearchHitsIsEmpty = topicSearchHits.isEmpty();

      if (!topicSearchHitsIsEmpty) {
        // map found topics to list of only post ids
        List<Long> postIdList = topicSearchHits.stream().map(searchHit -> {
          Topic topicPOI = searchHit.getContent();
          return topicPOI.getId();
        }).collect(Collectors.toList());

        // get post ids that have user's interests associated
        interestRows = Stream.concat(interestRows.stream(),
            interestService.getByPostIdListAndInterestIdList(postIdList, interestIdList).stream())
            .collect(Collectors.toList());

        // concat with the list of the previous iteration
        separatedTopics = separateMUIandNMUI(topicSearchHits,
            interestRows);

        tempList = separatedTopics.get(true);
        muiTopics = Stream.concat(muiTopics.stream(), tempList.stream()).collect(Collectors.toList());

        tempList = separatedTopics.get(false);
        nmuiTopics = Stream.concat(nmuiTopics.stream(), tempList.stream()).collect(Collectors.toList());

        // add +1 to pageStart to avoid getting the same topics again on next iteration
        pageStart++;
      }
    }

    // if recommended topics list size is bigger than pagination upper limit, get
    // only a sublist

    if (muiTopics.size() > muiAmount) {
      muiTopics = muiTopics.subList(0, muiAmount);
    }
    if (nmuiTopics.size() > nmuiAmount) {
      nmuiTopics = nmuiTopics.subList(0, nmuiAmount);
    }

    recommendedTopics = Stream.concat(recommendedTopics.stream(), muiTopics.stream()).collect(Collectors.toList());
    recommendedTopics = Stream.concat(recommendedTopics.stream(), nmuiTopics.stream()).collect(Collectors.toList());

    // return recommended topics with the interests that matched the user interests.
    return toResultData(recommendedTopics, interestRows);

  }

  public Map<Boolean, List<SearchHit<Topic>>> separateMUIandNMUI(List<SearchHit<Topic>> topicSearchHits,
      List<Map<String, Object>> interestRows) {

    List<Long> topicsInterestsPostIds = interestRows.stream()
        .map(row -> (Long) row.get("post_id"))
        .collect(Collectors.toList());

    Set<Long> topicsInterestsPostIdsSet = new HashSet<>(topicsInterestsPostIds);

    // WAY TO SEPARATE INTO TWO GROUPS: MUI and NMUI
    Map<Boolean, List<SearchHit<Topic>>> separatedTopics = topicSearchHits.stream()
        .collect(Collectors.groupingBy(hit -> {
          return topicsInterestsPostIdsSet.contains(hit.getContent().getId());
        }));

    return separatedTopics;
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
