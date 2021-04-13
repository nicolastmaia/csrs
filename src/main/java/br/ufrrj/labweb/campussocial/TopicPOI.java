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

  @Field(type = FieldType.Date)
  private String created_at;

  @Field(type = FieldType.Text)
  private String text;

  @Field(type = FieldType.Text)
  private String image;

  @Field(type = FieldType.Text)
  private String video;

  @Field(type = FieldType.Double)
  private Integer latitude;

  @Field(type = FieldType.Double)
  private Integer longitude;

  private GeoPoint location;

  public TopicPOI(long id, String text, String name, String created_at, String image, String video, Integer latitude,
      Integer longitude, GeoPoint location) {
    this.id = id;
    this.text = text;
    this.name = name;
    this.created_at = created_at;
    this.image = image;
    this.video = video;
    this.latitude = latitude;
    this.longitude = longitude;
    this.location = location;
  }

  public TopicPOI(long id, String name, String text, String comments, GeoPoint location) {
    this.id = id;
    this.name = name;
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

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getVideo() {
    return video;
  }

  public void setVideo(String video) {
    this.video = video;
  }

  public Integer getLatitude() {
    return latitude;
  }

  public void setLatitude(Integer latitude) {
    this.latitude = latitude;
  }

  public Integer getLongitude() {
    return longitude;
  }

  public void setLongitude(Integer longitude) {
    this.longitude = longitude;
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

  public String getCreated_at() {
    return created_at;
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }

  @Override
  public String toString() {
    return "TopicPOI [created_at=" + created_at + ", id=" + id + ", image=" + image + ", latitude=" + latitude
        + ", location=" + location + ", longitude=" + longitude + ", text=" + text + ", name=" + name + ", video="
        + video + "]";
  }

}
