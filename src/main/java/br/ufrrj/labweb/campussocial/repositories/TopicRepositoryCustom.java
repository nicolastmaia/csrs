package br.ufrrj.labweb.campussocial.repositories;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import br.ufrrj.labweb.campussocial.model.Topic;

import java.util.List;

public interface TopicRepositoryCustom {

    /**
     * search all {@link Topic} that are within a given distance of a point
     *
     * @param geoPoint the center point
     * @param distance the distance
     * @param unit     the distance unit
     * @return the found entities
     */
    List<SearchHit<Topic>> searchWithinCircle(GeoPoint geoPoint, Double distance, String unit);

    List<SearchHit<Topic>> searchWithinSquare(GeoPoint geoPoint1, GeoPoint geoPoint2, GeoPoint centerPoint,
            String unit, long timestampLowerBound, long timestampUpperBound, int pageStart, int offset);

    List<SearchHit<Topic>> searchByTitle(String title);

}
