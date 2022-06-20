package br.ufrrj.labweb.campussocial.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrrj.labweb.campussocial.repositories.InterestRepository;

@Service
public class InterestService {

  @Autowired
  private InterestRepository interestRepository;

  public List<Map<String, Object>> getByPostIdListAndInterestIdList(List<Long> postIdList,
      List<Long> interestIdList) {

    if (postIdList.isEmpty() || interestIdList.isEmpty()) {
      return new ArrayList<Map<String, Object>>();
    }

    String interestIdListAsString = interestIdList.toString().replace("[", "(").replace("]",
        ")");
    String postIdListAsString = postIdList.toString().replace("[", "(").replace("]", ")");

    return interestRepository.getByPostIdListAndInterestIdList(postIdListAsString, interestIdListAsString);
  }

}
