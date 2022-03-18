package br.ufrrj.labweb.campussocial.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import br.ufrrj.labweb.campussocial.model.Interest;

public interface InterestRepository extends ElasticsearchRepository<Interest, String>, InterestRepositoryCustom {
}
