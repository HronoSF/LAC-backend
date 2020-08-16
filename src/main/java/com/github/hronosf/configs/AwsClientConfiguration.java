package com.github.hronosf.configs;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.*;

@SpringBootConfiguration
public class AwsClientConfiguration {

    @Bean
    @Profile("!local")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AmazonS3 getS3Client(@Value("${aws.accessKey}") String accessKey
            , @Value("${aws.secretKey}") String secretKey, @Value("${aws.region}") String region) {
        return AmazonS3ClientBuilder.
                standard()
                .withCredentials(
                        new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey))
                )
                .withRegion(Regions.fromName(region))
                .build();
    }

    @Bean
    @Profile("local")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AmazonS3 getS3ClientForLocalStack(@Value("${s3.host}") String host) {
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder
                .EndpointConfiguration(host, Regions.US_EAST_2.getName());

        return AmazonS3ClientBuilder.
                standard()
                .withEndpointConfiguration(endpoint)
                .withPathStyleAccessEnabled(true)
                .build();
    }
}
