package br.ufrrj.labweb.campussocial.repositories;

import java.io.IOException;

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

@Repository
public class TopicRepository implements ITopicRepository {

  @Autowired
  private RestHighLevelClient client;

  @Override
  public SearchHits getWithinSquare(GeoPoint topLeftPoint, GeoPoint bottomRightPoint, GeoPoint centerPoint,
      String unit, long timestampMin, long timestampMax, int offset,
      double searchAfter) throws IOException {

    SearchRequest searchRequest = new SearchRequest("topic-post");

    QueryBuilder query = QueryBuilders.boolQuery()
        .must(QueryBuilders.geoBoundingBoxQuery("location").setCorners(topLeftPoint, bottomRightPoint));

    // TODO: implementar filtro de maneira que a data minima ou maxima
    // possam ser nulos e isso represente um intervalo de tempo aberto para cima ou
    // para baixo.
    if (timestampMin > 0 && timestampMax > 0) {
      ((BoolQueryBuilder) query)
          .must(QueryBuilders.rangeQuery("timestamp").gte(timestampMin)
              .lte(timestampMax));
    }

    SortBuilder sort = SortBuilders.geoDistanceSort("location", centerPoint).unit(DistanceUnit.KILOMETERS)
        .order(SortOrder.ASC);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(query)
        .sort(sort)
        .size(offset)
        .searchAfter(new Double[] { searchAfter });

    searchRequest.source(searchSourceBuilder);

    SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

    return response.getHits();
  }
}
