package br.ufrrj.labweb.campussocial.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class InterestService {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<Map<String, Object>> getByPostIdListAndInterestIdList(List<Long> postIdList,
      List<Long> interestIdList) {

    String interestIdListAsString = interestIdList.toString().replace("[", "(").replace("]",
        ")");
    String postIdListAsString = postIdList.toString().replace("[", "(").replace("]", ")");

    // get list of interests of found topics and concat with the list of the
    // previous iteration
    String interestSql = "SELECT * FROM interestpost WHERE interest_id IN "
        + String.join(",",
            interestIdListAsString)
        + " AND post_id IN "
        + String.join(",", postIdListAsString);

    return jdbcTemplate.queryForList(interestSql);
  }

}
