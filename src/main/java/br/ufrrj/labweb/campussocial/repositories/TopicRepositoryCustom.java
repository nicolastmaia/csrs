package br.ufrrj.labweb.campussocial.repositories;

import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.search.SearchHits;

import java.io.IOException;

public interface TopicRepositoryCustom {

    SearchHits searchWithinSquare(GeoPoint geoPoint1, GeoPoint geoPoint2, GeoPoint centerPoint,
            String unit, long timestampLowerBound, long timestampUpperBound, int offset,
            double searchAfter) throws IOException;
}
