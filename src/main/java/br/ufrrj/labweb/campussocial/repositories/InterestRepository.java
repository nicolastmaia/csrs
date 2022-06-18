package br.ufrrj.labweb.campussocial.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class InterestRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<Map<String, Object>> getByPostIdListAndInterestIdList(String postIdList, String interestIdList) {

    // get list of interests of found topics and concat with the list of the
    // previous iteration
    String interestSql = "SELECT * FROM interestpost WHERE interest_id IN "
        + String.join(",",
            interestIdList)
        + " AND post_id IN "
        + String.join(",", postIdList);

    return jdbcTemplate.queryForList(interestSql);
  }

}
