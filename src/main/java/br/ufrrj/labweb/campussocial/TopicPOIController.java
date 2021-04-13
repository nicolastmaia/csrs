package br.ufrrj.labweb.campussocial;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.GeoDistanceOrder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topicpois")
public class TopicPOIController {

    private final TopicPOIRepository repository;

    public TopicPOIController(TopicPOIRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/nearest3")
    List<ResultData> nearest3(@RequestBody RequestData requestData) {

        GeoPoint location = new GeoPoint(requestData.getLat(), requestData.getLon());
        Sort sort = Sort.by(new GeoDistanceOrder("location", location).withUnit("km"));

        List<SearchHit<TopicPOI>> searchHits;

        if (StringUtils.hasText(requestData.getText())) {
            searchHits = repository.searchTop3ByName(requestData.getText(), sort);
        } else {
            searchHits = repository.searchTop3By(sort);
        }

        return toResultData(searchHits);
    }

    @PostMapping("/within")
    List<ResultData> withinDistance(@RequestBody RequestData requestData) {

        GeoPoint location = new GeoPoint(requestData.getLat(), requestData.getLon());

        List<SearchHit<TopicPOI>> searchHits = repository.searchWithin(location, requestData.distance,
                requestData.unit);

        return toResultData(searchHits);
    }

    private List<ResultData> toResultData(List<SearchHit<TopicPOI>> searchHits) {
        return searchHits.stream().map(searchHit -> {
            Double distance = (Double) searchHit.getSortValues().get(0);
            TopicPOI topicPOI = searchHit.getContent();
            return new ResultData(topicPOI.getName(), topicPOI.getLocation(), distance);
        }).collect(Collectors.toList());
    }
}
