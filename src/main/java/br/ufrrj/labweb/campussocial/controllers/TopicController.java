package br.ufrrj.labweb.campussocial.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrrj.labweb.campussocial.model.TopicRequestData;
import br.ufrrj.labweb.campussocial.model.TopicResultData;
import br.ufrrj.labweb.campussocial.model.Topic;
import br.ufrrj.labweb.campussocial.repositories.TopicRepository;

@RestController
@RequestMapping("/topicpois")
public class TopicController {

    private final TopicRepository repository;

    public TopicController(TopicRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/within/circle")
    List<TopicResultData> withinDistance(@RequestBody TopicRequestData requestData) {

        GeoPoint location = new GeoPoint(requestData.getCenterLat(), requestData.getCenterLon());

        List<SearchHit<Topic>> searchHits = repository.searchWithin(location, requestData.getDistance(),
                requestData.getUnit());

        return toResultData(searchHits);
    }

    @PostMapping("/within/square")
    List<TopicResultData> withinSquare(@RequestBody TopicRequestData requestData) {

        GeoPoint centerPoint = new GeoPoint(requestData.getCenterLat(), requestData.getCenterLon());

        GeoPoint topLeftPoint = new GeoPoint(requestData.getTopLeftLat(), requestData.getTopLeftLon());
        GeoPoint bottomRightPoint = new GeoPoint(requestData.getBottomRightLat(), requestData.getBottomRightLon());

        List<SearchHit<Topic>> searchHits = repository.searchWithinSquare(topLeftPoint, bottomRightPoint,
                centerPoint, requestData.getUnit());

        return toResultData(searchHits);
    }

    @PostMapping("/bytitle")
    List<TopicResultData> byTitle(@RequestBody TopicRequestData requestData) {
        String title = requestData.getTitle();

        List<SearchHit<Topic>> searchHits = repository.searchByTitle(title);

        return toResultData(searchHits);
    }

    private List<TopicResultData> toResultData(List<SearchHit<Topic>> searchHits) {
        return searchHits.stream().map(searchHit -> {
            Topic topicPOI = searchHit.getContent();
            return new TopicResultData(topicPOI.getId(), topicPOI.getTitle(),topicPOI.getText(), topicPOI.getLocation());
        }).collect(Collectors.toList());
    }
}
