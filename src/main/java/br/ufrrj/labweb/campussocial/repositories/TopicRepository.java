package br.ufrrj.labweb.campussocial.repositories;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class TopicRepository implements ITopicRepository {

  @Autowired
  private RestHighLevelClient client;

  @Override
  public SearchHits searchWithinSquare(GeoPoint geoPoint1, GeoPoint geoPoint2, GeoPoint centerPoint,
      String unit, long timestampLowerBound, long timestampUpperBound, int offset,
      double searchAfter) throws IOException {

    SearchRequest searchRequest = new SearchRequest("topic-post");

    QueryBuilder query = QueryBuilders.boolQuery()
        .must(QueryBuilders.geoBoundingBoxQuery("location").setCorners(geoPoint1, geoPoint2));

    if (timestampLowerBound > 0 && timestampUpperBound > 0) {
      ((BoolQueryBuilder) query)
          .must(QueryBuilders.rangeQuery("timestamp").gte(timestampLowerBound)
              .lte(timestampUpperBound));

    }

    SortBuilder sort = SortBuilders.geoDistanceSort("location", centerPoint).unit(DistanceUnit.KILOMETERS)
        .order(SortOrder.ASC);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(query)
        .sort(sort)
        .searchAfter(new Double[] { searchAfter })
        .size(offset);

    searchRequest.source(searchSourceBuilder);

    SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
    return response.getHits();
  }
}
