package br.ufrrj.labweb.campussocial;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TopicPOIRepository extends ElasticsearchRepository<TopicPOI, String>, TopicPOIRepositoryCustom {

}
