package com.vaibhav.spring.elastic.configurations;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.InetSocketAddress;


@Configuration
@EnableElasticsearchRepositories(basePackages = "com.vaibhav.spring.elastic.repositories")
public class ElasticConfiguration {

    @Value("${spring.data.elasticsearch.cluster-name}")
    private String EsClusterName;

    @Value("${spring.data.elasticsearch.host}")
    private String EsHost;

    @Value("${spring.data.elasticsearch.port}")
    private int EsPort;

    @Bean
    public Client client() throws Exception {

        Settings settings = Settings.settingsBuilder()
                            .put("cluster.name", EsClusterName)
                            .build();

        Client client = TransportClient.builder()
                        .settings(settings)
                        .build()
                        .addTransportAddress(
                                new InetSocketTransportAddress(
                                        new InetSocketAddress("127.0.0.1", 9200)));

        return client;

    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(client());
    }


}
