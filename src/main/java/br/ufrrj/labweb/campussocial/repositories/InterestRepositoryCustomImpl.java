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
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class InterestRepositoryCustomImpl implements InterestRepositoryCustom {

    private final ElasticsearchOperations operations;

    public InterestRepositoryCustomImpl(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @Override
    public List<SearchHit<Interest>> searchByNameList(List<String> interestNameList) {
        Criteria criteria = new Criteria("interest_name").in(interestNameList);
        Query query = new CriteriaQuery(criteria);

        return operations.search(query, Interest.class).getSearchHits();
    }

    @Override
    public List<SearchHit<Interest>> searchByPostIdList(List<Long> postIdList) {
        Criteria criteria = new Criteria("post_id").in(postIdList);
        Query query = new CriteriaQuery(criteria);

        return operations.search(query, Interest.class).getSearchHits();
    }

    @Override
    public List<SearchHit<Interest>> searchByInterestIdList(List<Long> interestIdList) {
        Criteria criteria = new Criteria("interest_id").in(interestIdList);
        Query query = new CriteriaQuery(criteria);

        return operations.search(query, Interest.class).getSearchHits();
    }

    @Override
    public List<SearchHit<Interest>> searchByPostIdListAndInterestIdList(List<Long> postIdList,
            List<Long> interestIdList) {

        // fetch all interests with the given post ids and given interest ids
        Criteria criteria1 = new Criteria("post_id").in(postIdList).and("interest_id").in(interestIdList);
        Query query1 = new CriteriaQuery(criteria1);
        List<SearchHit<Interest>> queryResult1 = operations.search(query1, Interest.class).getSearchHits();

        // filter only the ids of the posts from previous query
        List<Long> filterPostIdList = queryResult1.stream().map(searchHit -> {
            Interest interest = searchHit.getContent();
            return interest.getPost_id();
        }).collect(Collectors.toList());

        // fetch all interests of the posts which we already know have the desired
        // interests
        Criteria criteria2 = new Criteria("post_id").in(filterPostIdList);
        Query query2 = new CriteriaQuery(criteria2);
        List<SearchHit<Interest>> queryResult2 = operations.search(query2, Interest.class).getSearchHits();

        return queryResult2;

    }

}
