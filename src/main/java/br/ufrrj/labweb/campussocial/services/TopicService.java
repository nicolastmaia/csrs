package br.ufrrj.labweb.campussocial.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrrj.labweb.campussocial.model.TopicResultData;
import br.ufrrj.labweb.campussocial.repositories.TopicRepository;

@Service
public class TopicService {

    @Autowired
    private TopicRepository repository;

    public SearchHits getWithinSquare(double topLeftLat, double topLeftLon, double bottomRightLat,
            double bottomRightLon, double centerLat, double centerLon, String unit, long timestampLowerBound,
            long timestampUpperBound,
            int offset, double searchAfter) throws IOException {

        GeoPoint centerPoint = new GeoPoint(centerLat, centerLon);

        GeoPoint topLeftPoint = new GeoPoint(topLeftLat, topLeftLon);
        GeoPoint bottomRightPoint = new GeoPoint(bottomRightLat, bottomRightLon);

        SearchHits searchHits = repository.searchWithinSquare(topLeftPoint, bottomRightPoint,
                centerPoint, unit, timestampLowerBound, timestampUpperBound, offset, searchAfter);

        return searchHits;
    }

    public List<TopicResultData> toResultData(SearchHits searchHits) {
        List<TopicResultData> resultData = new ArrayList<>();

        SearchHit[] hits = searchHits.getHits();

        for (SearchHit hit : hits) {
            Map<String, Object> hitAsMap = hit.getSourceAsMap();
            Long id = Long.parseLong(hitAsMap.get("id_post").toString());
            String title = hitAsMap.get("title").toString();
            String text = hitAsMap.get("text").toString();
            Map<String, Double> location = (Map<String, Double>) hitAsMap.get("location");
            Double lat = (Double) location.get("lat");
            Double lon = (Double) location.get("lon");
            Double searchAfter = (Double) hit.getSortValues()[0];

            GeoPoint locationGeoPoint = new GeoPoint(lat, lon);

            resultData.add(new TopicResultData(id, title, text, locationGeoPoint, searchAfter));
        }

        return resultData;
    }

}
