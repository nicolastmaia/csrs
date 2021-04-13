/*
 Copyright 2020 the original author(s)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package br.ufrrj.labweb.campussocial;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

/**
 * The entity stored in Elasticsearch, a POI of the topic category
 *
 */
@Document(indexName = "topicpois")
public class TopicPOI {
  @Id
  private long id;

  @Field(type = FieldType.Text)
  private String name;

  @Field(type = FieldType.Text)
  private String text;

  @Field(type = FieldType.Text)
  private String comments;

  private GeoPoint location;

  public TopicPOI(long id, String name, String text, String comments, GeoPoint location) {
    this.id = id;
    this.name = name;
    this.text = text;
    this.comments = comments;
    this.location = location;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public GeoPoint getLocation() {
    return location;
  }

  public void setLocation(GeoPoint location) {
    this.location = location;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
