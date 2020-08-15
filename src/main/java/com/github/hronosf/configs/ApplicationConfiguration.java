package com.github.hronosf.configs;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public class ApplicationConfiguration {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AmazonS3 getS3Client() {
        return AmazonS3ClientBuilder.
                standard()
                .withPathStyleAccessEnabled(true)
                .withRegion(Regions.US_EAST_2)
                .build();

    }
}
