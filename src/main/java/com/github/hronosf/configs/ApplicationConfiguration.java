package com.github.hronosf.configs;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import static com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;

@SpringBootConfiguration
public class ApplicationConfiguration {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AmazonS3 getS3Client(@Value("${s3.host}") String host) {
        EndpointConfiguration endpoint = new EndpointConfiguration(host, Regions.US_EAST_2.getName());

        return AmazonS3ClientBuilder.
                standard()
                //TODO: migrate from local stack to real S3
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .withRegion(Regions.US_EAST_2)
                .withEndpointConfiguration(endpoint)
                .withPathStyleAccessEnabled(true)
                .build();

    }
}
