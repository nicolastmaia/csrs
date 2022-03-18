package br.ufrrj.labweb.campussocial.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

import br.ufrrj.labweb.campussocial.model.InterestResultData;
import br.ufrrj.labweb.campussocial.model.Interest;
import br.ufrrj.labweb.campussocial.repositories.InterestRepository;

@Service
public class InterestService {

  private final InterestRepository repository;

  public InterestService(InterestRepository repository) {
    this.repository = repository;
  }

  public List<InterestResultData> getByName(String interestName) {
    List<SearchHit<Interest>> searchHits = repository.searchByName(interestName);

    return toResultData(searchHits);
  }

  private List<InterestResultData> toResultData(List<SearchHit<Interest>> searchHits) {
    return searchHits.stream().map(searchHit -> {
      Interest interestPOI = searchHit.getContent();
      return new InterestResultData(interestPOI.getId(), interestPOI.getPost_id(), interestPOI.getInterest_id(),
          interestPOI.getInterest_name());
    }).collect(Collectors.toList());
  }

}
