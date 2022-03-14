package br.ufrrj.labweb.campussocial.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import br.ufrrj.labweb.campussocial.model.TopicPOI;

public interface TopicPOIRepository extends ElasticsearchRepository<TopicPOI, String>, TopicPOIRepositoryCustom {

}
