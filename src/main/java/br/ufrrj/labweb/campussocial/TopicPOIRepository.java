package br.ufrrj.labweb.campussocial;

import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface TopicPOIRepository extends ElasticsearchRepository<TopicPOI, String>, TopicPOIRepositoryCustom {

}
