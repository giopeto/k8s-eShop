package com.files.config;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FilesServiceApplicationConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public GridFSBucket getGridFSBuckets(MongoDbFactory mongoDbFactory) {
        return GridFSBuckets.create(mongoDbFactory.getDb());
    }
}
