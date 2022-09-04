package br.ufrrj.labweb.campussocial.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InterestRepository implements IInterestRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<Map<String, Object>> getByPostIdListAndInterestIdList(List<Long> topicIdList, List<Long> interestIdList) {

    String interestIdListAsString = interestIdList.toString().replace("[", "(").replace("]",
        ")");
    String topicIdListAsString = topicIdList.toString().replace("[", "(").replace("]", ")");

    String interestSql = "SELECT * FROM interestpost WHERE interest_id IN "
        + interestIdListAsString + " AND post_id IN " + topicIdListAsString;

    return jdbcTemplate.queryForList(interestSql);
  };
}
