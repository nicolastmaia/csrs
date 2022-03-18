package br.ufrrj.labweb.campussocial.repositories;

import org.springframework.data.elasticsearch.core.SearchHit;

import br.ufrrj.labweb.campussocial.model.Interest;

import java.util.List;

public interface InterestRepositoryCustom {

    List<SearchHit<Interest>> searchByName(String name);
}
