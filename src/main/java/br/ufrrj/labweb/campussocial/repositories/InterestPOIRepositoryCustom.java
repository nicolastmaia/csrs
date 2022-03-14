package br.ufrrj.labweb.campussocial.repositories;

import org.springframework.data.elasticsearch.core.SearchHit;

import br.ufrrj.labweb.campussocial.model.InterestPOI;

import java.util.List;

public interface InterestPOIRepositoryCustom {

    List<SearchHit<InterestPOI>> searchByName(String name);
}
