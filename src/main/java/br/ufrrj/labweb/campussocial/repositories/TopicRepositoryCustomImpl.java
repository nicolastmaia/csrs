package br.ufrrj.labweb.campussocial.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.GeoDistanceOrder;
import org.springframework.data.elasticsearch.core.query.Query;

import br.ufrrj.labweb.campussocial.model.Topic;

import java.util.List;

public class TopicRepositoryCustomImpl implements TopicRepositoryCustom {

    @Autowired
    private ElasticsearchOperations operations;

    @Override
    public List<SearchHit<Topic>> searchWithinCircle(GeoPoint geoPoint, Double distance, String unit) {

        Query query = new CriteriaQuery(new Criteria("location").within(geoPoint, distance.toString() + unit));

        // add a sort to get the actual distance back in the sort value
        Sort sort = Sort.by(new GeoDistanceOrder("location", geoPoint).withUnit(unit));
        query.addSort(sort);

        return operations.search(query, Topic.class).getSearchHits();
    }

    @Override
    public List<SearchHit<Topic>> searchWithinSquare(GeoPoint geoPoint1, GeoPoint geoPoint2, GeoPoint centerPoint,
            String unit, long timestampLowerBound, long timestampUpperBound, int pageStart, int offset) {

        Query query = new CriteriaQuery(new Criteria("location").boundedBy(geoPoint1, geoPoint2));

        if (timestampLowerBound > 0 && timestampUpperBound > 0) {
            addTimestampLimit(query, timestampLowerBound, timestampUpperBound);
        }

        // add a sort to get the actual distance back in the sort value
        Sort sort = Sort.by(new GeoDistanceOrder("location", centerPoint).withUnit(unit));

        // add pageable option
        if (offset > 0) {
            Pageable pageable = PageRequest.of(pageStart, offset, sort);
            query.setPageable(pageable);
        } else {
            query.addSort(sort);
        }

        return operations.search(query, Topic.class).getSearchHits();
    }

    @Override
    public List<SearchHit<Topic>> searchByTitle(String title) {
        String[] splitTitle = title.split(" ");
        Criteria criteria = new Criteria("title").contains(splitTitle[0]).or(new Criteria("title").expression(title));
        Query query = new CriteriaQuery(criteria);

        return operations.search(query, Topic.class).getSearchHits();
    }

    private Query addTimestampLimit(Query query, long timestampLowerBound, long timestampUpperBound) {
        Criteria timestampCriteria = new Criteria("modified_at").between(timestampLowerBound, timestampUpperBound);

        ((CriteriaQuery) query).addCriteria(timestampCriteria);

        return query;
    }
}
