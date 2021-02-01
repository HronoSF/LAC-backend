package com.github.hronosf.configs;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@SpringBootConfiguration
public class AwsClientConfiguration {

    @Bean
    @Profile("!local")
    public AmazonS3 getS3Client(@Autowired AWSCredentialsProvider credentialsProvider, @Value("${aws.region}") String region) {
        return AmazonS3ClientBuilder.
                standard()
                .withCredentials(credentialsProvider)
                .withRegion(Regions.fromName(region))
                .build();
    }

    @Bean
    @Profile("!local")
    public AWSCognitoIdentityProvider cognitoIdentityProvider(@Autowired AWSCredentialsProvider credentialsProvider,
                                                              @Value("${aws.region}") String region) {
        return AWSCognitoIdentityProviderClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(region)
                .build();
    }

    @Bean
    @Profile("!local")
    public AWSCredentialsProvider credentialsProvider(@Value("${aws.accessKey}") String accessKey
            , @Value("${aws.secretKey}") String secretKey) {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
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
