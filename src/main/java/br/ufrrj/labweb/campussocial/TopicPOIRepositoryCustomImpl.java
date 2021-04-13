/*
 * (c) Copyright 2020 sothawo
 */
package br.ufrrj.labweb.campussocial;

import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.GeoDistanceOrder;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.List;

@SuppressWarnings("unused")
public class TopicPOIRepositoryCustomImpl implements TopicPOIRepositoryCustom {

    private final ElasticsearchOperations operations;

    public TopicPOIRepositoryCustomImpl(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @Override
    public List<SearchHit<TopicPOI>> searchWithin(GeoPoint geoPoint, Double distance, String unit) {

        Query query = new CriteriaQuery(new Criteria("location").within(geoPoint, distance.toString() + unit));

        // add a sort to get the actual distance back in the sort value
        // Sort sort = Sort.by(new GeoDistanceOrder("location",
        // geoPoint).withUnit(unit));
        // query.addSort(sort);

        return operations.search(query, TopicPOI.class).getSearchHits();
    }
}