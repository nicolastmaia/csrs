package br.ufrrj.labweb.campussocial.repositories;

import java.util.List;
import java.util.Map;

public interface IInterestRepository {

  public List<Map<String, Object>> getByPostIdListAndInterestIdList(List<Long> postIdList, List<Long> interestIdList);

}
