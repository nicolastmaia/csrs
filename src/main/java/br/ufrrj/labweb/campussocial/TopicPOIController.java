package br.ufrrj.labweb.campussocial;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
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

    @PostMapping("/within/circle")
    List<ResultData> withinDistance(@RequestBody RequestData requestData) {

        GeoPoint location = new GeoPoint(requestData.getCenterLat(), requestData.getCenterLon());

        List<SearchHit<TopicPOI>> searchHits = repository.searchWithin(location, requestData.distance,
                requestData.unit);

        return toResultData(searchHits);
    }

    @PostMapping("/within/square")
    List<ResultData> withinSquare(@RequestBody RequestData requestData) {

        GeoPoint centerPoint = new GeoPoint(requestData.getCenterLat(), requestData.getCenterLon());

        GeoPoint topLeftPoint = new GeoPoint(requestData.getTopLeftLat(), requestData.getTopLeftLon());
        GeoPoint bottomRightPoint = new GeoPoint(requestData.getBottomRightLat(), requestData.getBottomRightLon());

        List<SearchHit<TopicPOI>> searchHits = repository.searchWithinSquare(topLeftPoint, bottomRightPoint,
                centerPoint, requestData.unit);

        return toResultData(searchHits);
    }

    @PostMapping("/bytitle")
    List<ResultData> byTitle(@RequestBody RequestData requestData) {
        String title = requestData.getTitle();

        List<SearchHit<TopicPOI>> searchHits = repository.searchByTitle(title);

        return toResultData(searchHits);
    }

    private List<ResultData> toResultData(List<SearchHit<TopicPOI>> searchHits) {
        return searchHits.stream().map(searchHit -> {
            TopicPOI topicPOI = searchHit.getContent();
            return new ResultData(topicPOI.getId(), topicPOI.getTitle(),topicPOI.getText(), topicPOI.getLocation());
        }).collect(Collectors.toList());
    }
}
