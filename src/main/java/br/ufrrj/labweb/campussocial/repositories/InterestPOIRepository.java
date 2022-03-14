package br.ufrrj.labweb.campussocial.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import br.ufrrj.labweb.campussocial.model.InterestPOI;

public interface InterestPOIRepository extends ElasticsearchRepository<InterestPOI, String>, InterestPOIRepositoryCustom {

}
