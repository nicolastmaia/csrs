package br.ufrrj.labweb.campussocial.repositories;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import br.ufrrj.labweb.campussocial.model.TopicPOI;

import java.util.List;

public interface TopicPOIRepositoryCustom {

    /**
     * search all {@link TopicPOI} that are within a given distance of a point
     *
     * @param geoPoint the center point
     * @param distance the distance
     * @param unit     the distance unit
     * @return the found entities
     */
    List<SearchHit<TopicPOI>> searchWithin(GeoPoint geoPoint, Double distance, String unit);

    List<SearchHit<TopicPOI>> searchWithinSquare(GeoPoint geoPoint1, GeoPoint geoPoint2, GeoPoint centerPoint,
            String unit);

    List<SearchHit<TopicPOI>> searchByTitle(String title);
}
