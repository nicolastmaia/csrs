package br.ufrrj.labweb.campussocial.repositories;

import java.io.IOException;

import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.search.SearchHits;

public interface ITopicRepository {

    SearchHits getWithinSquare(GeoPoint topLeftPoint, GeoPoint bottomRightPoint, GeoPoint centerPoint,
            String unit, long timestampMin, long timestampMax, int offset,
            double searchAfter) throws IOException;
}
