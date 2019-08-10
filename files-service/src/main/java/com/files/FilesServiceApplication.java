package com.files;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;

@SpringBootApplication
@EnableDiscoveryClient
public class FilesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilesServiceApplication.class, args);
    }

    @Bean
    public GridFSBucket getGridFSBuckets(MongoDbFactory mongoDbFactory) {
        return GridFSBuckets.create(mongoDbFactory.getDb());
    }
}
