package br.ufrrj.labweb.campussocial.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrrj.labweb.campussocial.model.RecommendationRequestData;
import br.ufrrj.labweb.campussocial.model.RecommendationResultData;

@Service
public class RecommendationService {

  @Autowired
  private TopicService topicService;
  @Autowired
  private InterestService interestService;

  private static final double MUI_PERCENT = 0.8;
  private static final double NMUI_PERCENT = 0.2;
  private static final double EXTRA_TOTAL_PERCENT = 1.2;

  public List<RecommendationResultData> recommendTopicsWithinSquare(
      RecommendationRequestData requestData) throws ElasticsearchStatusException {

    // initialize lists to use
    List<Map<String, Object>> interestRows = new ArrayList<Map<String, Object>>();
    Map<Boolean, List<SearchHit>> groupedTopics = new HashMap<Boolean, List<SearchHit>>();
    List<SearchHit> muiTopics = new ArrayList<SearchHit>();
    List<SearchHit> nmuiTopics = new ArrayList<SearchHit>();
    List<SearchHit> tempList = new ArrayList<SearchHit>();
    List<SearchHit> recommendedTopics = new ArrayList<SearchHit>();
    SearchHits topicSearchHits;

    // initialize flags needed for do-while loop
    Boolean isOffsetBiggerThanTotalRegistries = false;
    Boolean isEnoughTopics = false;

    // initialize amounts of topics to use
    int totalAmount = (int) (requestData.getPageOffset() * EXTRA_TOTAL_PERCENT);
    int muiAmount = (int) (requestData.getPageOffset() * MUI_PERCENT);
    int nmuiAmount = (int) (requestData.getPageOffset() * NMUI_PERCENT);

    // get user's interest list received in request
    List<Long> interestIdList = requestData.getInterestIdList();

    // get the searchAfter value to use in the next query to elasticsearch
    double searchAfter = requestData.getSearchAfter();

    // while criteria is met, continue to search for topics
    do {
      // get topics within square
      try {
        topicSearchHits = topicService.getWithinSquare(requestData.getTopLeftLat(),
            requestData.getTopLeftLon(),
            requestData.getBottomRightLat(), requestData.getBottomRightLon(), requestData.getCenterLat(),
            requestData.getCenterLon(), requestData.getUnit(), requestData.getTimestampMin(),
            requestData.getTimestampMax(), totalAmount, searchAfter);
      } catch (Exception e) {
        ElasticsearchStatusException ese = new ElasticsearchStatusException(
            "Unable to communicate with Elastic Search Host",
            RestStatus.SERVICE_UNAVAILABLE, e, new Object());
        throw ese;
      }

      SearchHit[] topics = topicSearchHits.getHits();

      if (topics.length > 0) {
        // map found topics to list of only post ids
        List<Long> postIdList = new ArrayList<Long>();
        for (SearchHit searchHit : topics) {
          Long id = Long.parseLong(searchHit.getSourceAsMap().get("id_post").toString());
          postIdList.add(id);
        }

        // get post ids that have user's interests associated
        interestRows = Stream.concat(interestRows.stream(),
            interestService.getByPostIdListAndInterestIdList(postIdList, interestIdList).stream())
            .collect(Collectors.toList());

        // group found topics by whether they (M)atch (U)ser's (I)nterests (MUI) or
        // (N)ot (M)atch (U)ser's (I)nterests (NMUI)
        groupedTopics = groupMUIandNMUI(topics,
            interestRows);
        tempList = groupedTopics.get(true);
        muiTopics = Stream.concat(muiTopics.stream(), tempList.stream()).collect(Collectors.toList());
        tempList = groupedTopics.get(false);
        nmuiTopics = Stream.concat(nmuiTopics.stream(), tempList.stream()).collect(Collectors.toList());

        // get the searchAfter of the last item in returned topics in order to use it in
        // the next query to elasticsearch
        searchAfter = Double
            .parseDouble(topics[topics.length - 1].getSortValues()[0].toString());
      }

      // check if pagination upper limit is bigger than total registries inside these
      // coordinates
      isOffsetBiggerThanTotalRegistries = requestData.getPageOffset() > topics.length ? true
          : false;

      // check if there are enough MUI and NMUI topics to recommend
      isEnoughTopics = muiTopics.size() >= muiAmount && nmuiTopics.size() >= nmuiAmount;

    } while (!isEnoughTopics &&
        !isOffsetBiggerThanTotalRegistries && !interestIdList.isEmpty());

    // if MUI and NMUI topics lists are bigger than respective amounts, get
    // only a sublist of them
    if (muiTopics.size() > muiAmount) {
      muiTopics = muiTopics.subList(0, muiAmount);
    }
    if (nmuiTopics.size() > nmuiAmount) {
      nmuiTopics = nmuiTopics.subList(0, nmuiAmount);
    }

    // concat MUI and NMUI topics lists
    recommendedTopics = Stream.concat(recommendedTopics.stream(), muiTopics.stream()).collect(Collectors.toList());
    recommendedTopics = Stream.concat(recommendedTopics.stream(), nmuiTopics.stream()).collect(Collectors.toList());

    // return recommended topics paired with the interests that matched the user's
    // interests.
    return toResultData(recommendedTopics, interestRows);
  }

  private Map<Boolean, List<SearchHit>> groupMUIandNMUI(SearchHit[] topics,
      List<Map<String, Object>> interestRows) {

    final Map<Boolean, List<SearchHit>> groupedTopics = new HashMap<Boolean, List<SearchHit>>();

    // foolproof check to make sure that MUI and NMUI lists exist
    groupedTopics.computeIfAbsent(false, value -> new ArrayList<SearchHit>());
    groupedTopics.computeIfAbsent(true, value -> new ArrayList<SearchHit>());

    // get only post_id column of each interest and remove duplicates
    Set<Long> topicsInterestsPostIds = interestRows.stream()
        .map(row -> (Long) row.get("post_id"))
        .collect(Collectors.toSet());

    Arrays.asList(topics).forEach(hit -> {
      Long id = Long.parseLong(hit.getSourceAsMap().get("id_post").toString());
      if (topicsInterestsPostIds.contains(id)) {
        List<SearchHit> tempList = groupedTopics.get(true);
        tempList.add(hit);
        groupedTopics.put(true, tempList);
      } else {
        List<SearchHit> tempList = groupedTopics.get(false);
        tempList.add(hit);
        groupedTopics.put(false, tempList);
      }
    });

    return groupedTopics;
  }

  private List<RecommendationResultData> toResultData(List<SearchHit> recommendedTopics,
      List<Map<String, Object>> interestRows) {
    List<RecommendationResultData> recommendationList = new ArrayList<RecommendationResultData>();

    recommendedTopics.forEach(hit -> {
      List<Map<String, Object>> topicInterestList = interestRows.stream()
          .filter(row -> (Long) row.get("post_id") == Long
              .parseLong(hit.getSourceAsMap().get("id_post").toString()))
          .collect(Collectors.toList());

      Double searchAfter = Double.parseDouble(hit.getSortValues()[0].toString());

      RecommendationResultData tmpResultData = new RecommendationResultData(hit.getSourceAsMap(), topicInterestList,
          searchAfter);

      recommendationList.add(tmpResultData);

    });

    return recommendationList;
  }

}
