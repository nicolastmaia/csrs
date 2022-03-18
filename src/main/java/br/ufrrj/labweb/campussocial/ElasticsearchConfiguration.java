package br.ufrrj.labweb.campussocial;

import java.time.Duration;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.util.StringUtils;

@Configuration
public class ElasticsearchConfiguration extends AbstractElasticsearchConfiguration {

    private final ApplicationProperties applicationProperties;

    public ElasticsearchConfiguration(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {

        ClientConfiguration.TerminalClientConfigurationBuilder builder = ClientConfiguration.builder()
                .connectedTo(applicationProperties.getElasticSearchHost());

        if (StringUtils.hasLength(applicationProperties.getElasticSearchProxy())) {
            builder = builder.withProxy(applicationProperties.getElasticSearchProxy());
        }

        final ClientConfiguration clientConfiguration = builder.withSocketTimeout(Duration.ofSeconds(60)) //
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

}
