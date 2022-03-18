package br.ufrrj.labweb.campussocial.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * The entity stored in Elasticsearch, a POI of the topic category
 *
 */
@Document(indexName = "interest-post")
public class Interest {
  
  @Id
  private long id;

  @Field(type = FieldType.Integer)
  private long post_id;

  @Field(type = FieldType.Integer)
  private long interest_id;

  @Field(type = FieldType.Text)
  private String interest_name;

  public Interest(long id, long post_id, long interest_id, String interest_name) {
    this.id = id;
    this.post_id = post_id;
    this.interest_id = interest_id;
    this.interest_name = interest_name;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getPost_id() {
    return post_id;
  }

  public void setPost_id(long post_id) {
    this.post_id = post_id;
  }

  public long getInterest_id() {
    return interest_id;
  }

  public void setInterest_id(long interest_id) {
    this.interest_id = interest_id;
  }

  public String getInteret_name() {
    return interest_name;
  }

  public void setInteret_name(String interest_name) {
    this.interest_name = interest_name;
  }

}
