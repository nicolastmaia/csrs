package br.ufrrj.labweb.campussocial.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.GeoDistanceOrder;
import org.springframework.data.elasticsearch.core.query.Query;

import br.ufrrj.labweb.campussocial.model.TopicPOI;

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
        Sort sort = Sort.by(new GeoDistanceOrder("location", geoPoint).withUnit(unit));
        query.addSort(sort);

        return operations.search(query, TopicPOI.class).getSearchHits();
    }

    @Override
    public List<SearchHit<TopicPOI>> searchWithinSquare(GeoPoint geoPoint1, GeoPoint geoPoint2, GeoPoint centerPoint,
            String unit) {

        Query query = new CriteriaQuery(new Criteria("location").boundedBy(geoPoint1, geoPoint2));

        // add a sort to get the actual distance back in the sort value
        Sort sort = Sort.by(new GeoDistanceOrder("location", centerPoint).withUnit(unit));
        query.addSort(sort);

        return operations.search(query, TopicPOI.class).getSearchHits();
    }

    @Override
    public List<SearchHit<TopicPOI>> searchByTitle(String title) {
        String[] splitTitle = title.split(" ");
        Criteria criteria = new Criteria("title").contains(splitTitle[0]).or(new Criteria("title").expression(title));
        Query query = new CriteriaQuery(criteria);

        return operations.search(query, TopicPOI.class).getSearchHits();
    }
}
