package br.ufrrj.labweb.campussocial.repositories;

import org.springframework.data.elasticsearch.core.SearchHit;

import br.ufrrj.labweb.campussocial.model.Interest;

import java.util.List;

public interface InterestRepositoryCustom {

    List<SearchHit<Interest>> searchByNameList(List<String> nameList);

    List<SearchHit<Interest>> searchByPostIdList(List<Long> postIdList);

}
