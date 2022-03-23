package br.ufrrj.labweb.campussocial.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import br.ufrrj.labweb.campussocial.model.TopicResultData;
import br.ufrrj.labweb.campussocial.model.Topic;
import br.ufrrj.labweb.campussocial.repositories.TopicRepository;

@Service
public class TopicService {

    private final TopicRepository repository;

    public TopicService(TopicRepository repository) {
        this.repository = repository;
    }

    public List<SearchHit<Topic>> getWithinDistance(double centerLat, double centerLon, double distance, String unit) {

        GeoPoint location = new GeoPoint(centerLat, centerLon);

        List<SearchHit<Topic>> searchHits = repository.searchWithin(location, distance, unit);

        return searchHits;
    }

    public List<SearchHit<Topic>> getWithinSquare(double topLeftLat, double topLeftLon, double bottomRightLat,
            double bottomRightLon, double centerLat, double centerLon, String unit, long lowerBound, long upperBound) {

        GeoPoint centerPoint = new GeoPoint(centerLat, centerLon);

        GeoPoint topLeftPoint = new GeoPoint(topLeftLat, topLeftLon);
        GeoPoint bottomRightPoint = new GeoPoint(bottomRightLat, bottomRightLon);

        List<SearchHit<Topic>> searchHits = repository.searchWithinSquare(topLeftPoint, bottomRightPoint,
                centerPoint, unit, lowerBound, upperBound);

        return searchHits;
    }

    public List<SearchHit<Topic>> getByTitle(String title) {

        List<SearchHit<Topic>> searchHits = repository.searchByTitle(title);

        return searchHits;
    }

    public List<TopicResultData> toResultData(List<SearchHit<Topic>> searchHits) {
        return searchHits.stream().map(searchHit -> {
            Topic topicPOI = searchHit.getContent();
            return new TopicResultData(topicPOI.getId(), topicPOI.getTitle(), topicPOI.getText(),
                    topicPOI.getLocation());
        }).collect(Collectors.toList());
    }

}
