package br.ufrrj.labweb.campussocial.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.GeoDistanceOrder;
import org.springframework.data.elasticsearch.core.query.Query;

import br.ufrrj.labweb.campussocial.model.Interest;

import java.util.List;

@SuppressWarnings("unused")
public class InterestRepositoryCustomImpl implements InterestRepositoryCustom {

    private final ElasticsearchOperations operations;

    public InterestRepositoryCustomImpl(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @Override
    public List<SearchHit<Interest>> searchByName(String name) {
        String[] splitName = name.split(" ");
        Criteria criteria = new Criteria("name").contains(splitName[0]).or(new Criteria("name").expression(name));
        Query query = new CriteriaQuery(criteria);

        return operations.search(query, Interest.class).getSearchHits();
    }
}
