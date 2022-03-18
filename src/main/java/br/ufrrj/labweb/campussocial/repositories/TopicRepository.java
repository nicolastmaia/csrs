package br.ufrrj.labweb.campussocial.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import br.ufrrj.labweb.campussocial.model.Topic;

public interface TopicRepository extends ElasticsearchRepository<Topic, String>, TopicRepositoryCustom {

}
