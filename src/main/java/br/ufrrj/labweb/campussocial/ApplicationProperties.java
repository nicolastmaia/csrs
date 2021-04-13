package br.ufrrj.labweb.campussocial;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "br.ufrrj.labweb.campussocial")
public class ApplicationProperties {
  /** host:port of the Elasticsearch cluster */
  private String elasticSearchHost;

  /** host:port of a proxy to use */
  private String elasticSearchProxy;

  public String getElasticSearchHost() {
    return elasticSearchHost;
  }

  public void setElasticSearchHost(String elasticSearchHost) {
    this.elasticSearchHost = elasticSearchHost;
  }

  public String getElasticSearchProxy() {
    return elasticSearchProxy;
  }

  public void setElasticSearchProxy(String elasticSearchProxy) {
    this.elasticSearchProxy = elasticSearchProxy;
  }
}
