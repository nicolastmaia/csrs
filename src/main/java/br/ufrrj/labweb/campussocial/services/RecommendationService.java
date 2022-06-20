package br.ufrrj.labweb.campussocial.services;

import java.util.ArrayList;
import java.util.HashMap;
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

    // initialize lists to use
    List<Map<String, Object>> interestRows = new ArrayList<Map<String, Object>>();
    Map<Boolean, List<SearchHit<Topic>>> groupedTopics = new HashMap<Boolean, List<SearchHit<Topic>>>();
    List<SearchHit<Topic>> muiTopics = new ArrayList<SearchHit<Topic>>();
    List<SearchHit<Topic>> nmuiTopics = new ArrayList<SearchHit<Topic>>();
    List<SearchHit<Topic>> tempList = new ArrayList<SearchHit<Topic>>();
    List<SearchHit<Topic>> recommendedTopics = new ArrayList<SearchHit<Topic>>();

    // initialize flags needed for do-while loop
    Boolean isOffsetSmallerThanTotalRegistries = true;
    Boolean isNotEnoughTopics = true;

    // initialize amounts of topics to use
    int totalAmount = (int) (requestData.getPageOffset() * EXTRA_TOTAL_PERCENT);
    int muiAmount = (int) (requestData.getPageOffset() * MUI_PERCENT);
    int nmuiAmount = (int) (requestData.getPageOffset() * NMUI_PERCENT);

    // get page start to search for topics
    int page = requestData.getPageStart();

    // get user's interest list received in request
    List<Long> interestIdList = requestData.getInterestIdList();

    // while criteria is met, continue to search for topics
    do {
      // get topics within square
      List<SearchHit<Topic>> topicSearchHits = topicService.getWithinSquare(requestData.getTopLeftLat(),
          requestData.getTopLeftLon(),
          requestData.getBottomRightLat(), requestData.getBottomRightLon(), requestData.getCenterLat(),
          requestData.getCenterLon(), requestData.getUnit(), requestData.getTimestampLowerBound(),
          requestData.getTimestampUpperBound(), page, totalAmount);

      if (!topicSearchHits.isEmpty()) {

        // map found topics to list of only post ids
        List<Long> postIdList = topicSearchHits.stream().map(searchHit -> {
          Topic topicPOI = searchHit.getContent();
          return topicPOI.getId();
        }).collect(Collectors.toList());

        // get post ids that have user's interests associated
        interestRows = Stream.concat(interestRows.stream(),
            interestService.getByPostIdListAndInterestIdList(postIdList, interestIdList).stream())
            .collect(Collectors.toList());

        // group found topics by whether they have user's interests associated (MUI) or
        // not (NMUI)
        groupedTopics = groupMUIandNMUI(topicSearchHits,
            interestRows);

        tempList = groupedTopics.get(true);
        muiTopics = Stream.concat(muiTopics.stream(), tempList.stream()).collect(Collectors.toList());

        tempList = groupedTopics.get(false);
        nmuiTopics = Stream.concat(nmuiTopics.stream(), tempList.stream()).collect(Collectors.toList());

        // add +1 to page to avoid getting the same topics again on next iteration
        page++;
      }

      // check if pagination upper limit is bigger than total registries inside these
      // coordinates
      isOffsetSmallerThanTotalRegistries = requestData.getPageOffset() < topicSearchHits.size() ? true : false;

      // check if there are enough MUI and NMUI topics to recommend
      isNotEnoughTopics = muiTopics.size() < muiAmount || nmuiTopics.size() < nmuiAmount;

    } while (isNotEnoughTopics &&
        isOffsetSmallerThanTotalRegistries && !interestIdList.isEmpty());

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

    // return recommended topics with the interests that matched the user interests.
    return toResultData(recommendedTopics, interestRows);

  }

  public Map<Boolean, List<SearchHit<Topic>>> groupMUIandNMUI(List<SearchHit<Topic>> topicSearchHits,
      List<Map<String, Object>> interestRows) {

    Map<Boolean, List<SearchHit<Topic>>> groupedTopics = new HashMap<Boolean, List<SearchHit<Topic>>>();

    // get only post_id column of each interest and remove duplicates
    Set<Long> topicsInterestsPostIds = interestRows.stream()
        .map(row -> (Long) row.get("post_id"))
        .collect(Collectors.toSet());

    // separate topics into MUI and NMUI
    groupedTopics = topicSearchHits.stream()
        .collect(Collectors.groupingBy(hit -> {
          return topicsInterestsPostIds.contains(hit.getContent().getId());
        }));

    // foolproof check to make sure that MUI and NMUI lists exist
    groupedTopics.computeIfAbsent(false, value -> new ArrayList<SearchHit<Topic>>());
    groupedTopics.computeIfAbsent(true, value -> new ArrayList<SearchHit<Topic>>());

    return groupedTopics;
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
